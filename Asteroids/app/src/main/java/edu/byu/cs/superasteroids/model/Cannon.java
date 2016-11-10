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
 * Provides information regarding the cannon of the ship
 */
public class Cannon extends VisibleShipParts {
    /** The point on the cannon which attaches to the body */


    /** The point on the cannon which the projectile comes out of */
    private int emitPointX;
    private int emitPointY;
    /** The projectile that comes out of the cannon and information relating to it */
    private Projectile projectile;

    /**
     * Default Constructor
     */
    public Cannon(){}

    /**
     *
     * @param id - cannon id
     * @param image_path - image path of cannon
     * @param width - width of cannon image
     * @param height - height of cannon image
     * @param attachPointX - point x cannon attaches to body
     * @param attachPointY - point y cannon attaches to body
     * @param emitPointX - point x projectile comes out of cannon
     * @param emitPointY - point y projectile comes out of cannon
     * @param projectile - cannon's projectile
     */

    public Cannon(int id, String image_path, int width, int height, int attachPointX, int attachPointY, int emitPointX, int emitPointY, Projectile projectile)
    {
        super(id, image_path, width, height,0,0,0,0, attachPointX, attachPointY);
        this.emitPointX = emitPointX;
        this.emitPointY = emitPointY;
        this.projectile = projectile;
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
        //super.update(elapsedTime);
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("ID " + getId() + "| ATTACH(X,Y) " + Integer.toString(getAttachPointX()) + ", " + getAttachPointY() + "| EMIT(X,Y) " + Integer.toString(getEmitPointX()) + ", " + Integer.toString(getEmitPointY())+ "| IMAGE " + getImagePath() + "| WIDTH " + Integer.toString(getWidth()) +  "| HEIGHT " + Integer.toString(getHeight()));
        sb.append(getProjectile().toString());
        return sb.toString();
    }


    public void setEmitPointX(int attach)
    {
        this.emitPointX = attach;
    }
    public int getEmitPointX()
    {
        return this.emitPointX;
    }
    public void setEmitPointY(int attach)
    {
        this.emitPointY = attach;
    }
    public int getEmitPointY()
    {
        return this.emitPointY;
    }
    public void setProjectile(Projectile p)
    {
        this.projectile = p;
    }
    public Projectile getProjectile()
    {
        return this.projectile;
    }
}
