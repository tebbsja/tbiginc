package edu.byu.cs.superasteroids.model;

/**
 * Created by Austin on 10/11/16.
 */

import android.graphics.PointF;
import android.util.Log;

import edu.byu.cs.superasteroids.core.GraphicsUtils;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.game_objects.Ship;

import static edu.byu.cs.superasteroids.core.GraphicsUtils.rotate;

/**
 * This Class provides information about the engine
 */
public class Engine extends VisibleShipParts {
    /** maximum velocity of the ship in pixels per second */
    private int baseSpeed;
    /** turn rate of the ship in degrees per second */
    private int baseTurnRate;
    /** the point at which the engine attaches to the main body */





    /**
     * Default Constructor
     */
    public Engine(){}

    /**
     *
     * @param id - engine id
     * @param baseSpeed - maximum velocity of the ship in pixels per second
     * @param baseTurnRate - turn rate of the ship in degrees per second
     * @param attachPointX - the point x at which the engine attaches to the main body
     * @param attachPointX - the point x at which the engine attaches to the main body
     * @param image_path - the image path of the engine
     * @param width - the width of the engine image
     * @param height - the height of the engine image
     */
    public Engine(int id, int baseSpeed, int baseTurnRate, int attachPointX, int attachPointY, String image_path, int width, int height)
    {
        super(id,image_path,width,height,0,0,0,0, attachPointX, attachPointY);
        this.baseSpeed = baseSpeed;
        this.baseTurnRate = baseTurnRate;


    }

    /**
     * Draw is called 60 times every second and is the default method of the object to draw itself on the screen.
     */
    @Override
    public void Draw()
    {
        super.Draw();
    }

    /**
     * Update is called 60 times every second, and Updates every object. It may change the state of an object,
     * move an object, detect collisions, etc.
     */

    public void update(double elapsedTime)
    {
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("ID " + Integer.toString(getId()) + "| BASESPEED " + Integer.toString(getBaseSpeed()) +" | BASETURNRATE " + Integer.toString(getBaseTurnRate()) + " | ATTACH(X,Y) " + Integer.toString(getAttachPointX()) + ", " + Integer.toString(getAttachPointY()) + " | IMAGE " + getImagePath() + "| WIDTH " + Integer.toString(getWidth()) + "| HEIGHT " + Integer.toString(getHeight()));

        return sb.toString();
    }
    public void setBaseSpeed(int s)
    {
        this.baseSpeed = s;
    }
    public int getBaseSpeed()
    {
        return this.baseSpeed;
    }
    public void setBaseTurnRate(int s)
    {
        this.baseTurnRate = s;
    }
    public int getBaseTurnRate()
    {
        return this.baseTurnRate;
    }

}
