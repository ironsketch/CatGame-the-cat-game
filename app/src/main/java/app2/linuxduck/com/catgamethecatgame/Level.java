package app2.linuxduck.com.catgamethecatgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;

import java.util.Random;

import java.util.ArrayList;

public class Level {
    private ArrayList<GameObject> groundElements;
    private ArrayList<GameObject> groundElementsRepeat;
    private ArrayList<GameObject> evilDoers;
    private ArrayList<GameObject> evilDoersRepeats;
    private Bitmap background;
    private ArrayList<GameObjectBasic> designElementsAlwaysUpdate;
    private ArrayList<GameObjectBasic> designElementsAlwaysUpdateRepeat;
    private ArrayList<GameObjectBasic> designElementsPlayerDependent;
    private ArrayList<GameObjectBasic> designElementsPlayerDependentRepeat;
    private float difficulty;
    private long time;

    public Level(ArrayList<GameObject> newGroundElements,
                 ArrayList<GameObject> newEvilDoers,
                 Bitmap newBackground,
                 ArrayList<GameObjectBasic> newDesignElementsAlwaysUpdate,
                 ArrayList<GameObjectBasic> newDesignElementsPlayerDependent,
                 float newDifficulty, Point size) {
        groundElements = newGroundElements;
        groundElementsRepeat = new ArrayList<>();
        background = newBackground;
        evilDoers = newEvilDoers;
        evilDoersRepeats = new ArrayList<>();
        designElementsAlwaysUpdate = newDesignElementsAlwaysUpdate;
        designElementsAlwaysUpdateRepeat = new ArrayList<>();
        designElementsPlayerDependent = newDesignElementsPlayerDependent;
        designElementsPlayerDependentRepeat = new ArrayList<>();
        difficulty = newDifficulty;
        time = System.currentTimeMillis();
        updateElements(size);
    }

    // I simply keep track of evildoers per level. But I handle update out of here.
    public ArrayList<GameObject> getEvilDoers() {
        return evilDoers;
    }

    // If evildoer #n was killed, let's remove it!
    public void removeEvilDoer(int loc) {
        evilDoers.remove(loc);
    }

    // A new evildoer was created. Add it to the list
    public void addEvilDoer(GameObject newEvilDoer) {
        evilDoers.add(newEvilDoer);
    }

    public void updateElements(Point size) {
        int screenWidth = size.x;
        int screenHeight = size.y;

        // Cleaning up old backgrounds, old evildoers, old playerDependent, old alwaysUpdate
        for(int i = 0; i < groundElementsRepeat.size(); i++){
            if(groundElementsRepeat.get(i).getX() < -groundElementsRepeat.get(i).getWidth() * 2){
                groundElementsRepeat.remove(i);
            }
        }
        for(int i = 0; i < evilDoersRepeats.size(); i++){
            if(evilDoersRepeats.get(i).getX() < -evilDoersRepeats.get(i).getWidth() * 2){
                evilDoersRepeats.remove(i);
            }
        }
        for(int i = 0; i < designElementsAlwaysUpdateRepeat.size(); i++){
            if(designElementsAlwaysUpdateRepeat.get(i).getX() <  -designElementsAlwaysUpdateRepeat.get(i).getWidth() * 2){
                designElementsAlwaysUpdateRepeat.remove(i);
            }
        }
        for(int i = 0; i < designElementsPlayerDependentRepeat.size(); i++){
            if(designElementsPlayerDependentRepeat.get(i).getX() < -designElementsPlayerDependentRepeat.get(i).getWidth() * 2){
                designElementsPlayerDependentRepeat.remove(i);
            }
        }

        // Add enough ground to cover the playing field
        int tempX = (groundElementsRepeat.isEmpty()) ? 0 : groundElementsRepeat.get(groundElementsRepeat.size() - 1).getX() +
                groundElementsRepeat.get(groundElementsRepeat.size() - 1).getWidth();
        if(!groundElements.isEmpty()) {
            while (tempX <= screenWidth + (groundElements.get(0).getWidth() * 2)) {
                Random rand = new Random();
                // Generate random integers in range 0 to # of possible options for the ground
                int randGround = rand.nextInt(groundElements.size());
                GameObject tempGround = new GameObject(groundElements.get(randGround).getBitmap(),
                        tempX, screenHeight - groundElements.get(randGround).getHeight(), 1, 2,
                        groundElements.get(randGround).getHealth() , groundElements.get(randGround).getPower());
                tempGround.changeVelocityy(0);
                groundElementsRepeat.add(tempGround);
                tempX += tempGround.getWidth();
            }
        }
        // Stub for Add evilDoers

        // Add alwaysUpdate
        long newTime = System.currentTimeMillis();
        if(newTime - time > 3000){
            Random rand = new Random();
            int randAlways = rand.nextInt(designElementsAlwaysUpdate.size());
            int randY = rand.nextInt((int)(size.y * .75));
            GameObjectBasic tempAlways = new GameObjectBasic(size.x, randY, designElementsAlwaysUpdate.get(randAlways).getBitmap());
            tempAlways.setSpeedX(designElementsAlwaysUpdate.get(randAlways).getSpeedX());
            designElementsAlwaysUpdateRepeat.add(tempAlways);
            time = System.currentTimeMillis();
        }

        // Stub for Add playerDependent
    }

    public void updateDependent(int speed){
        // Update Ground Elements
        for(GameObject eachGround : groundElementsRepeat){
            eachGround.changeSpeed(-speed);
            eachGround.update();
        }
        // Update the rest
        for(GameObject eachEvilDoer : evilDoersRepeats){
            eachEvilDoer.changeX(-speed);
            eachEvilDoer.update();
        }
        // Update playerDependent
        for(GameObjectBasic eachDependent : designElementsPlayerDependentRepeat){
            eachDependent.setX(-speed);
            eachDependent.update();
        }
    }

    public void updateAlways(){
        for(GameObjectBasic eachAlways : designElementsAlwaysUpdateRepeat){
            eachAlways.update();
        }
    }

    public void update(GameObject player, Point size){
        updateElements(size);
        int screenWidth = size.x;
        if(player.getWalking() && player.getX() >= screenWidth * .75){
            updateDependent(player.getSpeed());
        }
        updateAlways();
    }

    public ArrayList<Boolean> getBounds(Point size, GameObject player){
        ArrayList<Boolean> bounds = new ArrayList<>();
        boolean leftX = false;
        boolean rightX = false;
        boolean topY = false;
        boolean bottomY = false;

        // Checking leftX (Behind)
        if(player.getX() <= 10){
            leftX = true;
        }


        // Checking rightX (In front)
        if(player.getX() > size.x * .75) {
            rightX = true;
        }
        // Ground
        for(GameObject eachGround : groundElementsRepeat){
            // Below
            if(player.getY() + player.getHeight() - 15 >= eachGround.getY() && player.getX() >= eachGround.getX() - (player.getWidth() / 2)
                    && player.getX() + player.getWidth() <= eachGround.getX() + eachGround.getWidth() + (player.getWidth() / 2)){
                bottomY = true;
            }
            // Behind you
            if(player.getX() <= eachGround.getX() + eachGround.getWidth()
                    && player.getX() + player.getWidth() <= eachGround.getX() +
                    eachGround.getWidth() + player.getWidth() && player.getHeight() - 40 >= eachGround.getY()) {
                leftX = true;
            }
            // In front of you
            if(player.getX() + player.getWidth() >= eachGround.getX()
                    && player.getX() >= eachGround.getX() - player.getWidth() && player.getY() + player.getHeight() - 40 >= eachGround.getY()){
                rightX= true;
            }
        }

        bounds.add(leftX); bounds.add(rightX); bounds.add(topY); bounds.add(bottomY);
        return bounds;
    }

    public void draw(Canvas canvas, Paint paint){
        // Draw background
        canvas.drawBitmap(background, 0, 0, paint);
        // Draw always
        for(GameObjectBasic eachAlways : designElementsAlwaysUpdateRepeat){
            canvas.drawBitmap(eachAlways.getBitmap(), eachAlways.getX(), eachAlways.getY(), paint);
        }
        // Draw Ground
        for(GameObject eachGround : groundElementsRepeat){
            canvas.drawBitmap(eachGround.getCurrentMoveBitmap(), eachGround.getX(), eachGround.getY(), paint);
        }
    }
}