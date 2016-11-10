package edu.byu.cs.superasteroids.model;

/**
 * Created by Austin on 10/11/16.
 */

import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

import edu.byu.cs.superasteroids.game_objects.Ship;

/**
 * The PositionedObject class contains VisualObjects that have an X and Y coordinate.
 */
public abstract class PositionedObject extends VisualObject {
    /** Position of object (x and y coordinates) */
    private float x;
    private float y;
    /** The boundingRectangle is used to detect collision */
    private RectF boundingRectangle;
    private PointF pos;

    /**
     *
     * @param id - id of object
     * @param image_path - image path of object
     * @param width - width of image
     * @param height - height of image
     * @param x - position of object (x coordinate)
     * @param y - position of object (y coordinate)
     */
    public PositionedObject(int id, String image_path, int width, int height, float x, float y)
    {
        super(id, image_path, width, height);
        this.x = x;
        this.y = y;
        pos = new PointF(0,0);
        boundingRectangle = new RectF();
    }

    /**
     * Default Constructor
     */
    public PositionedObject(){}

    /**
     * Draw is called 60 times every second and is the default method of the object to draw itself on the screen.
     */
    @Override
    public void Draw()
    {

    }
    /**
     * Update is called 60 times every second, and Updates every object. It may change the state of an object,
     * move an object, detect collisions, etc.
     */
    @Override
    public void Update()
    {

    }

    /**
     * This method is called when it collides with another object
     * @param obj - the object that this object has collided into
     */
    public void touch(PositionedObject obj)
    {

    }

    public RectF getBoundingRectangle()
    {
        return this.boundingRectangle;
    }
    public void setBoundingRectangle(RectF r)
    {
        this.boundingRectangle = r;
    }

    public float getX()
    {
        return this.x;
    }
    public float getY()
    {
        return this.y;
    }
    public void setX(float x_cor)
    {
        this.x = x_cor;

    }
    public void setY(float y_cor)
    {
        this.y = y_cor;
    }


}
