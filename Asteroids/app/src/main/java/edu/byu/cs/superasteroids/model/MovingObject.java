package edu.byu.cs.superasteroids.model;

/**
 * Created by Austin on 10/11/16.
 */

import android.graphics.PointF;

/**
 * The MovingObject class contains PositionedObjects that have a speed and direction
 */
public abstract class MovingObject extends PositionedObject {
    /** speed is the speed of the object */
    private double speed;
    /** angle is the angle or rotation of the object */

    private double angle;




    /**
     *
     * @param id - id of object
     * @param image_path - image path of object
     * @param width - width of object
     * @param height - height of object
     * @param x - x - coordinate
     * @param y - y - coordinate
     * @param speed - speed of object
     * @param angle - rotation or direction of object
     */
    public MovingObject(int id, String image_path, int width, int height, float x, float y, double speed, double angle)
    {
        super(id, image_path, width, height, x, y);
        this.speed = speed;
        this.angle = angle;
    }

    /**
     * Default Constructor
     */
    public MovingObject(){}
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

    public void update(double elapsedTime)
    {

    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public void setSpeed(double s)
    {
        this.speed = s;
    }
    public double getSpeed()
    {
        return this.speed;
    }
}
