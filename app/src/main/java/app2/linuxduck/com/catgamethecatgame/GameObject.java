package app2.linuxduck.com.catgamethecatgame;

import android.graphics.Bitmap;
import android.util.Log;

public class GameObject {
    private Bitmap bitmap;
    private int x;
    private int y;
    private int speed = 0;
    private final int col;
    private final int row;
    private final int WIDTH;
    private final int HEIGHT;
    private float GRAVITY = .5f;
    private final int width;
    private final int height;
    private final int movingRighttoLeft = 1;
    private final int movingLefttoRight = 0;
    private int usingRow = movingLefttoRight;
    private int usingCol;
    private Bitmap[] leftToRights;
    private Bitmap[] rightToLefts;
    private float VELOCITYX = 1f;
    private float VELOCITYY = 0f;
    private boolean jumped;
    private boolean forward;
    private boolean walking;
    private long jumpStart;
    private long jumpEnd;
    private boolean grounded;

    private int health;
    private int power;

    public GameObject(Bitmap newBitmap, int newX, int newY, int newCol, int newRow, int newHealth, int newPower){
        bitmap = newBitmap;
        x = newX;
        y = newY;
        col = newCol;
        row = newRow;
        WIDTH = bitmap.getWidth();
        HEIGHT = bitmap.getHeight();
        width = WIDTH / col;
        height = HEIGHT / row;
        leftToRights = new Bitmap[col];
        rightToLefts = new Bitmap[col];

        jumped = false;
        forward = true;
        walking = false;
        VELOCITYY = 2;
        GRAVITY = .5f;
        speed = 15;

        for(int i = 0; i < col; i++){
            rightToLefts[i] = createSubImageAt(movingRighttoLeft, i);
            leftToRights[i] = createSubImageAt(movingLefttoRight, i);
        }

        health = newHealth;
        power = newPower;
        jumpStart = 0;
        jumpEnd = 1;
        grounded = false;
    }

    public Bitmap[] getMoveBitmaps()  {
        switch (usingRow)  {
            case movingLefttoRight:
                return leftToRights;
            case movingRighttoLeft:
                return rightToLefts;
            default:
                return null;
        }
    }

    public Bitmap getCurrentMoveBitmap()  {
        Bitmap[] bitmaps = getMoveBitmaps();
        return bitmaps[usingCol];
    }

    public void update() {
        if(walking) {
            usingCol++;
            if (usingCol >= col) {
                usingCol = 0;
            }
        }
        x += speed * VELOCITYX;
        y += speed * VELOCITYY;
    }

    protected Bitmap createSubImageAt(int theRow, int theCol)  {
        // createBitmap(bitmap, x, y, width, height).
        Bitmap subImage = Bitmap.createBitmap(bitmap, theCol * width, theRow * height, width, height);
        return subImage;
    }

    public void setRow(int newRow){
        if(newRow == 0){
            usingRow = movingLefttoRight;
        } else if(newRow == 1) {
            usingRow = movingRighttoLeft;
        }
    }

    public int getX() {
        return x;
    }
    public void changeX(int newX){x = newX;}
    public int getY() {
        return y;
    }
    public void changeY(int newY){y = newY;}
    public int getWidth(){return width;}
    public int getHeight(){return height;}
    public void changeVelocityy(float newVelocityY){
        VELOCITYY = newVelocityY;
    }
    public void changeVelocityx(int newVelocityX){
        VELOCITYX = newVelocityX;
    }
    public int getSpeed() {
        return speed;
    }
    public void changeSpeed(int newSpeed){
        speed = newSpeed;
    }
    public boolean getJumped(){
        return jumped;
    }
    public void changeJumped(boolean change){
        jumped = change;
    }
    public boolean getWalking(){
        return walking;
    }
    public void changeWalking(boolean change){
        walking = change;
    }
    public boolean getForward(){
        return forward;
    }
    public void changeForward(boolean change){
        forward = change;
    }
    public int getHealth(){return health;}
    public void changeHealth(int healthModifier){health += healthModifier;}
    public int getPower(){return power;}
    public void changePower(int powerModifier){power += powerModifier;}
    public boolean getGrounded(){return grounded;}
    public void changeGrounded(boolean newGrounded){grounded = newGrounded;}
    public long getJumpStart(){return jumpStart;}
    public void changeJumpStart(long newStart){jumpStart = newStart;}
    public long getJumpEnd(){return jumpEnd;}
    public void changeJumpEnd(long newEnd){jumpEnd = newEnd;}
    public Bitmap getBitmap(){return bitmap;}
}
