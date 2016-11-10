package edu.byu.cs.superasteroids.model;

import android.graphics.PointF;
import android.graphics.drawable.InsetDrawable;
import android.util.Log;
import android.view.SurfaceHolder;

import edu.byu.cs.superasteroids.core.GraphicsUtils;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.game_objects.Ship;

import static edu.byu.cs.superasteroids.core.GraphicsUtils.SEVEN_FOURTH_PI;
import static edu.byu.cs.superasteroids.core.GraphicsUtils.rotate;

/**
 * Created by Austin on 10/11/16.
 */

/**
 * This class contains information about the extra part or the left wing of the ship
 */
public class ExtraPart extends VisibleShipParts {
    /** Point at which the extra part attaches to the main body */



    /**
     * Default Constructor
     */
    public ExtraPart(){}

    public ExtraPart(int id, String image_path, int width, int height, int attachPointX, int attachPointY)
    {
        super(id,image_path,width,height,0,0,0,0, attachPointX, attachPointY);



    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("ID " + Integer.toString(getId()) + " | IMAGE " + getImagePath() + "| WIDTH " + Integer.toString(getWidth()) + "| HEIGHT " + Integer.toString(getHeight()) + "| ATTACH(X,Y) " + Integer.toString(getAttachPointX()) + ", " + Integer.toString(getAttachPointY()));

        return sb.toString();
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


}
