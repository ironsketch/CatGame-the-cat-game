package app2.linuxduck.com.catgamethecatgame;

import android.graphics.Bitmap;
import android.util.Log;

public class GameObjectBasic {
    private int x;
    private int y;
    private int width;
    private int height;
    private int speedX;
    private int speedY;
    private Bitmap bitmap;
    public GameObjectBasic(int newX, int newY, Bitmap newBitmap){
        x = newX;
        y = newY;
        width = newBitmap.getWidth();
        height = newBitmap.getHeight();
        bitmap = newBitmap;
        speedX = 0;
        speedY = 0;
    }
    public void update(){
        x += speedX;
        y += speedY;
    }
    public int getX(){return x;}
    public int getY(){return y;}
    public void setX(int newX){x = newX;}
    public void setY(int newY){y = newY;}
    public int getWidth(){return width;}
    public int getHeight(){return height;}
    public int getSpeedX(){return speedX;}
    public int getSpeedY(){return speedY;}
    public void setSpeedX(int newSpeed){speedX = newSpeed;}
    public void setSpeedY(int newSpeed){speedY = newSpeed;}
    public Bitmap getBitmap(){return bitmap;}
}
