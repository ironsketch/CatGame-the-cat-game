package app2.linuxduck.com.catgamethecatgame;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Iterator;

public class GameView extends SurfaceView implements Runnable{
    // Boolean variable to track if the game is playing or not
    volatile boolean playing;

    // The game thread
    private Thread gameThread = null;
    private Display display;
    private Point size;

    // Player
    private GameObject player;
    private Bitmap playerBitmap;

    // Movement Buttons
    private Bitmap forwardButtonBitmap;
    private int forwardX;
    private int forwardANDbackwardY;
    private Bitmap backwardButtonBitmap;
    private Bitmap jumpButtonBitmapRight;
    private int jumpXRight;
    private Bitmap jumpButtonBitmapLeft;
    private int jumpXLeft;

    // Art
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;

    // Levels
    private ArrayList<Level> levels;
    private int currentLevel;

    public GameView(Context context) {
        super(context);

        display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        size = new Point();
        display.getRealSize(size);

        // The primary Player
        playerBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.catwalk);
        player = new GameObject(playerBitmap, 45, 45, 8, 2, 100, 10);

        forwardButtonBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.forwardarrow);
        forwardX = size.x - forwardButtonBitmap.getWidth();
        forwardANDbackwardY = size.y - forwardButtonBitmap.getHeight();
        backwardButtonBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.backwardbutton);
        jumpButtonBitmapRight = BitmapFactory.decodeResource(context.getResources(), R.drawable.jumpbutton);
        jumpXRight = size.x - forwardButtonBitmap.getWidth() - jumpButtonBitmapRight.getWidth();
        jumpButtonBitmapLeft = BitmapFactory.decodeResource(context.getResources(), R.drawable.jumpbutton);
        jumpXLeft = jumpButtonBitmapLeft.getWidth();

        // Art
        surfaceHolder = getHolder();
        paint = new Paint();

        levels = new MakeLevels().MakeLevels(context, size);
        currentLevel = 0;
    }

    @Override
    public void run() {
        while (playing) {
            // Update the frame
            update();
            // Draw the frame
            draw();
            // Control
            control();
        }
    }

    private void update(){
        ArrayList<Boolean> bounds = levels.get(currentLevel).getBounds(size, player);
        if(bounds.get(3)){
            player.changeGrounded(true);
        }
        // This update is for moving characters
        // if(Going Backward and Nothing blocking to the left and the player is walking
        if(!player.getForward() && player.getWalking() && !bounds.get(0)) {
            player.changeVelocityx(-1);
            player.setRow(1);
        // else if(Walking forward and There is nothing ahead and the player is walking
        } else if(player.getForward() && player.getWalking() && !bounds.get(1)){
            player.changeVelocityx(1);
            player.setRow(0);
        // else we aren't needing velocity forward or backward
        } else {
            player.changeVelocityx(0);
        }

        // Checking if there isn't below me and I am not jumping I will fall!
        if(!bounds.get(3) && !player.getJumped()) {
            player.changeVelocityy(2);
        } else if(bounds.get(3) && !player.getJumped()){
            player.changeVelocityy(0);
        }
        // I need to check if I am jumping!!
        if(player.getJumped()) {
            player.changeJumpEnd(System.currentTimeMillis());
            // If 3 seconds have passed change my velocity so I fall again!
            if (player.getJumpEnd() - player.getJumpStart() > 300) {
                player.changeVelocityy(2);
                player.changeJumped(false);
            }
        }
        levels.get(currentLevel).update(player, size);
        player.update();
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        int index = MotionEventCompat.getActionIndex(e);
        int xPos = -1;
        int yPos = -1;
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // Checking for forward button
                if (e.getX() >= forwardX && e.getX() <= forwardX + forwardButtonBitmap.getWidth() &&
                        e.getY() >= forwardANDbackwardY) {
                    player.changeWalking(true);
                    player.changeForward(true);
                }
                // Checking for backward button
                else if (e.getX() >= 0 && e.getX() <= backwardButtonBitmap.getWidth() &&
                        e.getY() >= forwardANDbackwardY) {
                    player.changeWalking(true);
                    player.changeForward(false);
                }
                // Checking jump button Right and Left
                if (player.getGrounded() && e.getX() >= jumpXRight && e.getX() <= jumpXRight + jumpButtonBitmapRight.getWidth()
                        && e.getY() >= size.y - jumpButtonBitmapRight.getHeight()
                        && !player.getJumped() || e.getX() >= jumpXLeft && e.getX() <= jumpXLeft + jumpButtonBitmapLeft.getWidth()
                        && e.getY() >= size.y - jumpButtonBitmapLeft.getHeight()
                        && !player.getJumped()) {
                    player.changeJumpStart(System.currentTimeMillis());
                    player.changeVelocityy(-1);
                    player.changeJumped(true);
                    player.changeGrounded(false);
                }
                break;

            case MotionEvent.ACTION_UP:
                // Checking for on up for movement buttons
                player.changeWalking(false);
                break;

            case MotionEvent.ACTION_MOVE:
                if (e.getPointerCount() > 1) {
                    // The coordinates of the current screen contact, relative to
                    // the responding View or Activity.
                    xPos = (int)MotionEventCompat.getX(e, index);
                    yPos = (int)MotionEventCompat.getY(e, index);
                    // Checking jump button Right and Left
                    if (player.getGrounded()) {
                        player.changeJumpStart(System.currentTimeMillis());
                        player.changeVelocityy(-2);
                        player.changeJumped(true);
                        if(player.getForward()){
                            player.changeWalking(true);
                            player.changeForward(true);
                            player.changeVelocityx(1);
                            player.setRow(0);
                        } else if(!player.getForward()){
                            player.changeWalking(true);
                            player.changeForward(false);
                            player.changeVelocityx(-1);
                            player.setRow(1);
                        }
                        player.changeGrounded(false);
                    }

                }
                break;

        }
        return true;
    }

    private void draw(){
        // checking if surface is valid
        if (surfaceHolder.getSurface().isValid()) {
            // locking the canvas
            canvas = surfaceHolder.lockCanvas();
            // drawing a background color for canvas
            canvas.drawColor(Color.BLACK);
            // Draw the level
            levels.get(currentLevel).draw(canvas, paint);
            // Drawing movement buttons
            canvas.drawBitmap(forwardButtonBitmap, (size.x - forwardButtonBitmap.getWidth()),
                    (size.y - forwardButtonBitmap.getHeight()), paint);
            canvas.drawBitmap(backwardButtonBitmap,0, (size.y - backwardButtonBitmap.getHeight()), paint);
            canvas.drawBitmap(jumpButtonBitmapRight, (size.x - forwardButtonBitmap.getWidth() - jumpButtonBitmapRight.getWidth()),
                    (size.y - jumpButtonBitmapRight.getHeight()), paint);
            canvas.drawBitmap(jumpButtonBitmapLeft, forwardButtonBitmap.getWidth(),
                    (size.y - jumpButtonBitmapLeft.getHeight()), paint);
            // Drawing the player
            canvas.drawBitmap(
                    player.getCurrentMoveBitmap(),
                    player.getX(),
                    player.getY(),
                    paint);
            // Unlocking the canvas
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void control(){
        try {
            gameThread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pause(){
        playing = false;
        try {
            // To stop the thread
            // NOTE to self. I may need to update this to keep trying
            gameThread.join();
        } catch (InterruptedException e) {
        }
    }

    public void resume(){
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }
}