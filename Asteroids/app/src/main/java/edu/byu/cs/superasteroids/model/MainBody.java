package edu.byu.cs.superasteroids.model;

/**
 * Created by Austin on 10/11/16.
 */

import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;

import edu.byu.cs.superasteroids.core.GraphicsUtils;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.game.InputManager;
import edu.byu.cs.superasteroids.game_objects.Ship;
import edu.byu.cs.superasteroids.game_objects.ViewPort;


/**
 * Provides all the information necessary for the Body of the Ship
 *
 */
public class MainBody extends VisibleShipParts {
    /** The attach point of the cannon */
    private int  cannonAttachX;
    private int  cannonAttachY;
    /** The attach point of the engine */
    private int engineAttachX;
    private int engineAttachY;
    /** The attach point of the cannon */
    private int extraAttachX;
    private int extraAttachY;

    /** Default Constructor */
    public MainBody(){}

    /**
     *
     * @param cannonAttachX - the point x at which the cannon attaches
     * @param cannonAttachY - the point y at which the cannon attaches
     * @param engineAttachX - the point x at which the engine attaches
     * @param engineAttachY - the point y at which the engine attaches
     * @param extraAttachX - the point x which the extraPart attaches
     * @param extraAttachY - the point y which the extraPart attaches
     * @param image_path - the image path of the main body
     * @param width - width of the body
     * @param height - height of the body
     */


    public MainBody(int id, int cannonAttachX, int cannonAttachY, int engineAttachX, int engineAttachY, int extraAttachX, int extraAttachY, String image_path, int width, int height)
    {
        super(id,image_path,width,height,0,0,0,0,0,0);
        this.cannonAttachX = cannonAttachX;
        this.cannonAttachY = cannonAttachY;
        this.engineAttachX = engineAttachX;
        this.engineAttachY = engineAttachY;
        this.extraAttachX = extraAttachX;
        this.extraAttachY = extraAttachY;
    }

    /**
     * Draw is called 60 times every second and is the default method of the object to draw itself on the screen.
     */
    @Override
    public void Draw()
    {

        PointF point = ViewPort.SINGLETON.worldToView(new PointF(getX(), getY()));
        float scale = Ship.SINGLETON.getScale();

        DrawingHelper.drawImage(Ship.SINGLETON.getMainBody().getId(), point.x, point.y, (float)Ship.SINGLETON.getAngle(), scale, scale, 255);

    }

    /**
     * Update is called 60 times every second, and Updates every object. It may change the state of an object,
     * move an object, detect collisions, etc.
     */
    public void update(double elapsedTime)
    {
        super.update(elapsedTime);
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("ID " + getId() + "| CANNON(X,Y) " + Integer.toString(getCannonAttachX()) + ", " + getCannonAttachY() + "| ENGINE(X,Y) " + Integer.toString(getEngineAttachX()) + ", " + Integer.toString(getEngineAttachY())+ "| EXTRAPART(X,Y) " + Integer.toString(getExtraAttachX()) + ", " + Integer.toString(getExtraAttachY())+ "| IMAGE " + getImagePath() + "| WIDTH " + Integer.toString(getWidth()) +  "| HEIGHT " + Integer.toString(getHeight()));

        return sb.toString();
    }
    public void setCannonAttachX(int attach)
    {
        this.cannonAttachX = attach;
    }
    public void setCannonAttachY(int attach)
    {
        this.cannonAttachY = attach;
    }
    public int getCannonAttachX()
    {
        return this.cannonAttachX;
    }
    public int getCannonAttachY()
    {
        return this.cannonAttachY;
    }
    public void setEngineAttachX(int attach)
    {
        this.engineAttachX = attach;
    }
    public void setEngineAttachY(int attach)
    {
        this.engineAttachY = attach;
    }

    public int getEngineAttachX()
    {
        return this.engineAttachX;
    }
    public int getEngineAttachY()
    {
        return this.engineAttachY;
    }

    public void setExtraAttachX(int attach)
    {
        this.extraAttachX = attach;
    }
    public void setExtraAttachY(int attach)
    {
        this.extraAttachY = attach;
    }

    public int getExtraAttachX()
    {
        return this.extraAttachX;
    }
    public int getExtraAttachY()
    {
        return this.extraAttachY;
    }

}
