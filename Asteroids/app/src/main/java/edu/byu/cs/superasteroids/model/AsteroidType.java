package edu.byu.cs.superasteroids.model;

/**
 * Created by Austin on 10/11/16.
 */

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import java.util.Random;

import edu.byu.cs.superasteroids.core.GraphicsUtils;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.game_objects.AsteroidGame;
import edu.byu.cs.superasteroids.game_objects.ViewPort;

/**
 * This Class provides information about Different Asteroid Types
 */
public class AsteroidType extends MovingObject {
    /** Name of the Asteroid Type*/
    private String name;
    /** The type of asteroid, this is used to determine the behavior and characteristics of the asteroid */
    private String type;
    private RectF bound;
    private float scale;
    private boolean broken;
    private int hitPoints;

    /**
     * Default Constructor
     */
    public AsteroidType(){}

    /**
     *
     * @param id - id of Asteroid
     * @param name - name of type of asteroid
     * @param image_path - image path to asteroid
     * @param width - width of asteroid image
     * @param height - height of asteroid image
     */

    public AsteroidType(int id, String name, String image_path, int width, int height, String type)
    {
        super(id, image_path, width, height,0,0,0,0);
        this.name = name;
        this.type = type;
        bound = new RectF();
        this.scale = 1;
        broken = false;
        hitPoints = 4;
    }

    public void decHP()
    {
        this.hitPoints--;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public RectF getBound() {
        return bound;
    }

    public void setBound(RectF bound) {
        this.bound = bound;
    }

    /**
     * Draw is called 60 times every second and is the default method of the object to draw itself on the screen.
     */
    @Override
    public void Draw()
    {
        PointF point = new PointF(getX(),getY());
        PointF p = ViewPort.SINGLETON.worldToView(point);
        DrawingHelper.drawImage(getId(), p.x ,p.y , (float)(getAngle()), getScale() , getScale(), 255);

    }
    /**
     * Update is called 60 times every second, and Updates every object. It may change the state of an object,
     * move an object, detect collisions, etc.
     */
    public void update(double elapsedTime)
    {

        PointF point = new PointF(getX(),getY());
        RectF bound = new RectF(getX() - getWidth()/2, getY() - getHeight()/2, getX() + getWidth()/2, getY() + getHeight()/2);


        //calculates new position of asteroid
        GraphicsUtils.MoveObjectResult result = GraphicsUtils.moveObject(point, bound, getSpeed(),
                GraphicsUtils.degreesToRadians(getAngle()), elapsedTime);
        this.setX(result.getNewObjPosition().x);
        this.setY(result.getNewObjPosition().y);
        this.setBound(result.getNewObjBounds());

        //because the asteroids will bounce off the walls of the world, we need to determine the position when they ricochet
        GraphicsUtils.RicochetObjectResult ricochetObjectResult = GraphicsUtils.ricochetObject(new PointF(getX(), getY()), getBound(),
                GraphicsUtils.degreesToRadians(getAngle()), AsteroidGame.SINGLETON.getCurrLevel().getWidth(), AsteroidGame.SINGLETON.getCurrLevel().getHeight());
        this.setX(ricochetObjectResult.getNewObjPosition().x);
        this.setY(ricochetObjectResult.getNewObjPosition().y);
        this.setBound(ricochetObjectResult.getNewObjBounds());
        this.setAngle((float)GraphicsUtils.radiansToDegrees(ricochetObjectResult.getNewAngleRadians()));


        if (this.getType() == "growing" && this.isBroken())
        {
            setGrowingHitPoints();
        }


    }

    /**
     * for growing asteroids, there are additional steps that need to be taken, we will grow the asteroids by a small ammount
     *  because the function is called 60 times / second. We cap the scale of the growing asteroid at 2 so the asteroids don't
     *  take up the whole screen.
     */

    public void setGrowingHitPoints()
    {
        float scale = (float) (this.getScale() + .001);
        if (scale >= 2)
        {
            scale = 2;
        }
        setScale(scale);
        if (scale > 1.25f)
        {
            this.setHitPoints(4);
        }
        else if (scale > .75f)
        {

            this.setHitPoints(2);

        }
        else if (scale > .5f)
        {
            setHitPoints(1);
        }
    }

    public boolean isBroken() {
        return broken;
    }

    public void setBroken(boolean broken) {
        this.broken = broken;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("ID " + getId() + "| NAME " + getName() + "| IMAGE " + getImagePath() + "| WIDTH " + Integer.toString(getWidth()) + "| HEIGHT " + Integer.toString(getHeight()) + "| TYPE " + getType());

        return sb.toString();
    }

    public void setName(String n)
    {
        this.name = n;
    }
    public String getName()
    {
        return this.name;
    }
    public void setType(String t)
    {
        this.type = t;
    }
    public String getType()
    {
        return this.type;
    }

}
