package edu.byu.cs.superasteroids.model;

/**
 * Created by Austin on 10/11/16.
 */

import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import edu.byu.cs.superasteroids.core.GraphicsUtils;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.game_objects.AsteroidGame;
import edu.byu.cs.superasteroids.game_objects.Ship;
import edu.byu.cs.superasteroids.game_objects.ViewPort;

/**
 * The Projectile class provides the necessary information about the projectile from the cannon
 */
public class Projectile extends MovingObject {


    /** The attack sound path */
    private String attackSoundPath;
    /** The amount of damage the projectile does */
    private int damage;
    private RectF boundingRect;
    private final double angleWhenShot = Ship.SINGLETON.getAngle();
    /**
     * Default Constructor
     */
    public Projectile(){}

    /**
     *
     * @param image_path - path of the attack image
     * @param width - width of attack image
     * @param height - height of attack image
     * @param sound_path - path of the sound of the attack
     * @param damage - the amount of damage done by projectile
     */
    public Projectile(int id, String image_path, int width, int height, String sound_path, int damage)
    {

        super(id,image_path, width, height,0,0,0,0);
        this.attackSoundPath = sound_path;
        this.damage = damage;
        if (Ship.SINGLETON.getCannon() != null) {
            PointF point = getCoordinatesEmit(Ship.SINGLETON.getCannon());
            setX(point.x);
            setY(point.y);
        }

        RectF rec = new RectF((float)(getX() - (getWidth()/2 * .1f)), (float)(getY()- (getWidth()/2 * .1f)),
                (float) (getX() + (getWidth()/2 * .1f)),(float)(getY() + (getWidth()/2 * .1f)));
        setBoundingRect(rec);
    }

    /**
     * Draw is called 60 times every second and is the default method of the object to draw itself on the screen.
     */
    @Override
    public void Draw()
    {
        DrawingHelper.drawImage(this.getId(), getX(), getY(), (float) (angleWhenShot), .1f, .1f, 255);


    }

    public RectF getBoundingRect() {
        return boundingRect;
    }

    public void setBoundingRect(RectF boundingRect) {
        this.boundingRect = boundingRect;
    }

    public PointF getCoordinatesEmit(Cannon body)
    {

        PointF cannonxy = new PointF(0,0);

        cannonxy.x = (body.getEmitPointX() - body.getWidth()/2) + (this.getWidth()/2 - this.getWidth()/2);
        cannonxy.y = (body.getEmitPointY() - body.getHeight()/2) - body.getEmitPointY();

        cannonxy.x = (float) (Ship.SINGLETON.getScale() * cannonxy.x);
        cannonxy.y = (float) (Ship.SINGLETON.getScale() * cannonxy.y);

        double angleRad = GraphicsUtils.degreesToRadians(Ship.SINGLETON.getAngle());
        PointF rotatedPartOffset = GraphicsUtils.rotate(cannonxy, angleRad);

        cannonxy.x = (rotatedPartOffset.x) + body.getX();
        cannonxy.y = (rotatedPartOffset.y) + body.getY();


        return cannonxy;



    }
    /**
     * Update is called 60 times every second, and Updates every object. It may change the state of an object,
     * move an object, detect collisions, etc.
     */

    public void Update(double elapsedTime)
    {


            PointF point = new PointF(getX(), getY());

            double angle = GraphicsUtils.degreesToRadians(angleWhenShot - 90);


            GraphicsUtils.MoveObjectResult moveObjectResult = GraphicsUtils.moveObject(point, getBoundingRect(),
                    Ship.SINGLETON.getTotalSpeed() * 2.5, angle, elapsedTime);

            this.setX(moveObjectResult.getNewObjPosition().x);
            this.setY(moveObjectResult.getNewObjPosition().y);
            this.setBoundingRect(moveObjectResult.getNewObjBounds());

    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("| IMAGE " + getImagePath() + "| WIDTH " + Integer.toString(getWidth()) + "| HEIGHT " + Integer.toString(getHeight()) + "| SOUND " + getAttackSoundPath() + " | DAMAGE " + Integer.toString(getDamage()));

        return sb.toString();
    }

    public void setAttackSoundPath(String path)
    {
        this.attackSoundPath = path;
    }
    public String getAttackSoundPath()
    {
        return this.attackSoundPath;
    }
    public void setDamage(int d)
    {
        this.damage = d;
    }
    public int getDamage()
    {
        return this.damage;
    }


}
