package app2.linuxduck.com.catgamethecatgame;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class Level {
    private ArrayList<GameObject> groundElements;
    private ArrayList<GameObject> evilDoers;
    private Bitmap background;
    private ArrayList<Bitmap> designElementsAlwaysUpdate;
    private ArrayList<Bitmap> designElementsPlayerDependent;
    private int difficulty;

    public Level(ArrayList<GameObject> newGroundElements,
                 ArrayList<GameObject> newEvilDoers,
                 Bitmap newBackground,
                 ArrayList<Bitmap> newDesignElementsAlwaysUpdate,
                 ArrayList<Bitmap> newDesignElementsPlayerDependent,
                 int newDifficulty) {
        groundElements = newGroundElements;
        background = newBackground;
        designElementsAlwaysUpdate = newDesignElementsAlwaysUpdate;
        designElementsPlayerDependent = newDesignElementsPlayerDependent;
        difficulty = newDifficulty;
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

    public void updateGroundElements() {
        for (GameObject eachGround : groundElements) {
            eachGround.update();
        }
    }

    public void updateLevel(boolean playerIsMoving, GameObject player) {
        // Update the player dependent events
        if(playerIsMoving){
            updateGroundElements();
        }
    }
}