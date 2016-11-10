package edu.byu.cs.superasteroids.game_objects;

import android.graphics.Rect;
import android.util.Log;

/**
 * Created by Austin on 10/30/16.
 */

public class BackgroundImage {

    private int id;
    private int height;
    private int width;
    private float scaleX;
    private float scaleY;
    private Rect spaceToDraw;

    public static final BackgroundImage SINGLETON = new BackgroundImage();

    //the dimensions of the image are constant.
    public BackgroundImage()
    {

        this.height = 2048;
        this.width = 2048;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public void levelScaleX()
    {
        float levelWidth = AsteroidGame.SINGLETON.getCurrLevel().getWidth();
        this.scaleX = (this.width/levelWidth);

    }
    public float getScaleX()
    {
        return this.scaleX;
    }
    public float getScaleY()
    {
        return this.scaleY;
    }
    public void levelScaleY()
    {
        float levelHeight = AsteroidGame.SINGLETON.getCurrLevel().getHeight();
        this.scaleY = (this.height/levelHeight);
    }

    /**
     *
     * @return returns the rectangle that is intersected from the background from the viewport.
     */
    public Rect getSpaceToDraw()
    {
        Rect r = ViewPort.SINGLETON.getvP();
        int left = (int)(r.left * getScaleX());
        int top = (int)(r.top * getScaleY());
        int right = (int)(r.right * getScaleX());
        int bottom = (int)(r.bottom * getScaleY());
        Rect toReturn = new Rect(left,top,right,bottom);
        this.spaceToDraw = toReturn;

        return toReturn;
    }
}
