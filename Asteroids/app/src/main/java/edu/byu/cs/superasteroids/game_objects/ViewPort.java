package edu.byu.cs.superasteroids.game_objects;

import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.Log;

import edu.byu.cs.superasteroids.core.GraphicsUtils;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;

/**
 * Created by Austin on 10/29/16.
 */

public class ViewPort {

    private int width;
    private int height;
    private Rect vP;
    private float x;
    private float y;

    private PointF topLeft;
    private PointF topRight;
    private PointF bottomLeft;
    private PointF bottomRight;



    public static final ViewPort SINGLETON = new ViewPort();

    public ViewPort()
    {
        this.x = (AsteroidGame.SINGLETON.getCurrLevel().getWidth()/2)-(DrawingHelper.getGameViewWidth()/2);
        this.y =  (AsteroidGame.SINGLETON.getCurrLevel().getHeight()/2) - (DrawingHelper.getGameViewHeight()/2);
        this.height = DrawingHelper.getGameViewHeight();
        this.width = DrawingHelper.getGameViewWidth();
        setTopLeft(new PointF(this.x, this.y));
        this.vP = new Rect((int)this.x,(int)this.y,(int)(this.x + this.width),(int)(this.y + this.height));
    }

    /**
     * Updates the viewport to center on the ship.
     */
    public void Update()
    {
        PointF shipPos = new PointF(Ship.SINGLETON.getMainBody().getX(), Ship.SINGLETON.getMainBody().getY());

        PointF viewportCorner = new PointF(shipPos.x - DrawingHelper.getGameViewWidth()/2,shipPos.y - DrawingHelper.getGameViewHeight()/2);

        if (viewportCorner.x <= 0)
        {
            viewportCorner.x = 0;
        }
        if (viewportCorner.x >= AsteroidGame.SINGLETON.getCurrLevel().getWidth() - DrawingHelper.getGameViewWidth())
        {
            viewportCorner.x = AsteroidGame.SINGLETON.getCurrLevel().getWidth() - DrawingHelper.getGameViewWidth();
        }

        if (viewportCorner.y <= 0)
        {
            viewportCorner.y = 0;
        }
        if (viewportCorner.y >= AsteroidGame.SINGLETON.getCurrLevel().getHeight() - DrawingHelper.getGameViewHeight())
        {
            viewportCorner.y = AsteroidGame.SINGLETON.getCurrLevel().getHeight() - DrawingHelper.getGameViewHeight();
        }


        setTopLeft(viewportCorner);


        Rect r = new Rect((int)getTopLeft().x,(int)getTopLeft().y,(int)getBottomRight().x,(int)getBottomRight().y);
        setvP(r);


    }



    /**
     * Converts World Coordinates to View Coordinates
     */
    public PointF worldToView(PointF point)
    {
        PointF newPoint = GraphicsUtils.subtract(point,topLeft);
        return newPoint;
    }

    /**
     *
     * @param point - point to convert to World coordinates
     * @return - point converted to world coordinates
     */
    public PointF viewToWorld(PointF point)
    {

        PointF world = GraphicsUtils.add(point,topLeft);

        return world;
    }

    public float getX() {
        return x;
    }

    /**
     *
     * @return Top Left corner of viewport in World Coordinates
     */
    public PointF getTopLeft() {
        return topLeft;
    }

    public void setTopLeft(PointF topLeft) {
        float y = topLeft.y;
        float x = topLeft.x + getWidth();
        PointF right = new PointF(x,y);
        setTopRight(right);
        this.topLeft = topLeft;
    }

    /**
     *
     * @return Top Right Corner of ViewPort in World Coordinates
     */
    public PointF getTopRight() {
        return topRight;
    }

    public void setTopRight(PointF topRight) {
        float x = topRight.x;
        float y = topRight.y + getHeight();
        PointF bottomRight = new PointF(x,y);
        setBottomRight(bottomRight);
        this.topRight = topRight;
    }

    /**
     *
     * @return Bottom Left Corner of ViewPort in World Coordinates
     */
    public PointF getBottomLeft() {
        return bottomLeft;
    }

    public void setBottomLeft(PointF bottomLeft) {
        this.bottomLeft = bottomLeft;
    }

    /**
     *
     * @return Bottom Right Corner of ViewPort in World Coordinates
     */
    public PointF getBottomRight() {
        return bottomRight;
    }

    public void setBottomRight(PointF bottomRight) {
        float x = bottomRight.x - getWidth();
        float y = bottomRight.y;
        PointF bottomLeft = new PointF(x,y);
        setBottomLeft(bottomLeft);
        this.bottomRight = bottomRight;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setHeight()
    {
        height = DrawingHelper.getGameViewHeight();
    }
    public void setWidth()
    {
        width = DrawingHelper.getGameViewWidth();
    }

    public void setvP(Rect r)
    {
        this.vP = r;
    }

    public Rect getvP()
    {
        return this.vP;
    }
    public int getWidth() { return this.width;}
    public int getHeight() { return this.height;}

}
