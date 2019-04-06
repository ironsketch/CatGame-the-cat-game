package app2.linuxduck.com.catgamethecatgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.util.ArrayList;

public class MakeLevels {
    // All Levels
    public ArrayList<Level> allLevels;

    // Level 1
    public Level level1;
    public ArrayList<GameObject> groundObjectsL1;
    public Bitmap groundL1;
    public GameObject groundGOL1;
    public ArrayList<GameObject> evilDoersL1;
    public Bitmap backgroundL1;
    public ArrayList<Bitmap> alwaysUpdateL1;
    public Bitmap cloudSmallL1;
    public Bitmap cloudLargeL1;
    public ArrayList<Bitmap> playerDependentL1;

    public Level makeLevel1(Context context){
        // Ground elements
        groundObjectsL1 = new ArrayList<>();
        groundL1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.grass);
        groundGOL1 = new GameObject(groundL1, groundL1.getWidth(), groundL1.getHeight() / 2,1,2,100,0);
        groundObjectsL1.add(groundGOL1);

        // Evil Doers
        evilDoersL1 = new ArrayList<>();

        // Background
        backgroundL1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.sky);

        // Items that always update
        alwaysUpdateL1 = new ArrayList<>();
        cloudSmallL1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.small_cloud);
        cloudLargeL1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.large_cloud);
        alwaysUpdateL1.add(cloudSmallL1); alwaysUpdateL1.add(cloudLargeL1);

        // Items that update only when player moves (except the ground)
        playerDependentL1 = new ArrayList<>();

        level1 = new Level(groundObjectsL1, evilDoersL1, backgroundL1, alwaysUpdateL1, playerDependentL1, 1);
        return level1;
    }

    public ArrayList<Level> MakeLevels(Context context){
        allLevels = new ArrayList<>();
        allLevels.add(makeLevel1(context));
        return allLevels;
    }
}
