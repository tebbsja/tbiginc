package edu.byu.cs.superasteroids.model;

import android.graphics.PointF;
import android.graphics.Rect;
import android.util.FloatMath;
import android.util.Log;

import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.game_objects.ViewPort;

import static android.util.FloatMath.sqrt;
import static java.lang.Math.pow;

/**
 * Created by Austin on 10/11/16.
 */

/**
 * This Class represents background objects
 */
public class BackgroundObject extends PositionedObject {
    /** scale is the scale to draw the object at*/
    private float scale;
    private int level_id;
    private static boolean draw = false;
    /**
     * Default Constructor
     */
    public BackgroundObject(){}

    /**
     *
     * @param id - image id
     * @param level_id - which level it belongs t
     * @param x - x coordinate
     * @param y - y coordinate
     * @param scale - scale to be drawn at
     */
    public BackgroundObject(int id, String path, int x, int y, float scale, int level_id) {
        super(id, path, 0, 0, x, y);
        this.scale = scale;
        this.level_id = level_id;
    }

    /**
     * Draw is called 60 times every second and is the default method of the object to draw itself on the screen.
     */
    @Override
    public void Draw()
    {

        PointF point = new PointF(getX(),getY());
        PointF p = ViewPort.SINGLETON.worldToView(point);
        DrawingHelper.drawImage(getId(),p.x,p.y,0, getScale(), getScale(), 255);

    }
    /**
     * Update is called 60 times every second, and Updates every object. It may change the state of an object,
     * move an object, detect collisions, etc.
     */
    @Override
    public void Update()
    {


    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("ID " + Integer.toString(getId()) + "| IMAGE " + getImagePath() + " | POS(X,Y) " + Float.toString(getX()) + "," + Float.toString(getY()) + " | SCALE " + Float.toString(getScale()) + " | LEVEL_ID " + Integer.toString(getLevelId()));

        return sb.toString();
    }
    public String objtoString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("ID " + Integer.toString(getId()) + " | IMAGEPATH " + getImagePath());
        return sb.toString();
    }
    public void setScale(float s)
    {
        this.scale = s;
    }
    public float getScale()
    {
        return this.scale;
    }
    public void setLevelId(int id) { this.level_id = id;}
    public int getLevelId() { return this.level_id; }

}
