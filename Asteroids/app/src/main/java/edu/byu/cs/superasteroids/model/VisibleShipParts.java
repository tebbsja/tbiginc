package edu.byu.cs.superasteroids.model;

import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;

import edu.byu.cs.superasteroids.core.GraphicsUtils;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.game.InputManager;
import edu.byu.cs.superasteroids.game_objects.AsteroidGame;
import edu.byu.cs.superasteroids.game_objects.Ship;
import edu.byu.cs.superasteroids.game_objects.ViewPort;

/**
 * Created by Austin on 11/1/16.
 */

public class VisibleShipParts extends MovingObject {

    private Point attachPoint;

    public VisibleShipParts(int id, String image_path, int width, int height, float x, float y, int speed, double angle, int attachx, int attachy)
    {
        super(id, image_path, width, height, x, y, speed, angle);
        this.attachPoint = new Point(attachx, attachy);
    }
    public VisibleShipParts(){}

    public int getAttachPointX()
    {
        return this.attachPoint.x;
    }
    public int getAttachPointY()
    {
        return this.attachPoint.y;
    }
    public void setAttachPoint(Point p)
    {
        this.attachPoint = p;
    }
    public void setAttachPointX(int x)
    {
        this.attachPoint.x = x;
    }
    public void setAttachPointY(int y)
    {
        this.attachPoint.y = y;
    }
    public void Update(){}

    public void Draw()
    {
        float x = Ship.SINGLETON.getMainBody().getX();
        float y = Ship.SINGLETON.getMainBody().getY();

        float scale = Ship.SINGLETON.getScale();
        PointF xy = new PointF(x,y);
        PointF coordinates = getCoordinatesPart(Ship.SINGLETON.getMainBody(), this, xy, scale);
        DrawingHelper.drawImage(this.getId(), coordinates.x , coordinates.y , (float)Ship.SINGLETON.getAngle(), scale, scale, 255);
    }

    /**
     *
     * @param body - the main body of the ship
     * @param part - the part of the ship we are trying to get coordinates to attach
     * @param point - the middle point of the main body of the ship
     * @param scale - the scale at which to draw
     * @return - coordinates where to draw the part we pass in
     */
    public PointF getCoordinatesPart(MainBody body, VisibleShipParts part, PointF point, double scale)
    {
        point = ViewPort.SINGLETON.worldToView(point);
        PointF coordinates = new PointF(0,0);

        if (this.getClass() == Cannon.class)
        {
            coordinates.x = (body.getCannonAttachX() - body.getWidth() / 2) + (part.getWidth() / 2 - part.getAttachPointX());
            coordinates.y = (body.getCannonAttachY() - body.getHeight() / 2) + (part.getHeight() / 2 - part.getAttachPointY());
        }
        else if (this.getClass() == ExtraPart.class)
        {
            coordinates.x = (body.getExtraAttachX() - body.getWidth() / 2) + (part.getWidth() / 2 - part.getAttachPointX());
            coordinates.y = (body.getExtraAttachY() - body.getHeight() / 2) + (part.getHeight() / 2 - part.getAttachPointY());
        }
        else if (this.getClass() == Engine.class)
        {
            coordinates.x = (body.getEngineAttachX() - body.getWidth() / 2) + (part.getWidth() / 2 - part.getAttachPointX());
            coordinates.y = (body.getEngineAttachY() - body.getHeight() / 2) + (part.getHeight() / 2 - part.getAttachPointY());
        }

        coordinates.x = (float)(coordinates.x * scale);
        coordinates.y = (float)(coordinates.y * scale);

        double angleRad = GraphicsUtils.degreesToRadians(Ship.SINGLETON.getAngle());
        PointF rotatedPartOffset = GraphicsUtils.rotate(coordinates, angleRad);

        coordinates.x = (rotatedPartOffset.x) + point.x;
        coordinates.y = (rotatedPartOffset.y) + point.y;

        if (this.getClass() != MainBody.class)
        {
            this.setX(coordinates.x);
            this.setY(coordinates.y);
        }

        RectF r = new RectF((float)(this.getX() - (this.getWidth()/2) * scale),
                (float)(this.getY() - (this.getHeight()/2 * scale)),
                (float)(this.getX() + (this.getWidth()/2 * scale)),
                (float)(this.getY() + (this.getHeight()/2 * scale)));

        this.setBoundingRectangle(r);

        return coordinates;

    }

    public void update(double elapsedTime)
    {
            PointF xy = new PointF(getX(), getY());
        RectF rect = new RectF(getX(),getY(),getX()+1,getY()+1);


            GraphicsUtils.MoveObjectResult result = GraphicsUtils.moveObject(xy, rect,
                    Ship.SINGLETON.getTotalSpeed(), GraphicsUtils.degreesToRadians(Ship.SINGLETON.getAngle()-90), elapsedTime);


            PointF pos = result.getNewObjPosition();
            RectF res = result.getNewObjBounds();

            //these are for testing purposes, this all happens inside GraphicUtils.MoveObjectResult
            double distance =  Ship.SINGLETON.getTotalSpeed() * elapsedTime/48;
            double deltaX = distance * Math.cos(GraphicsUtils.degreesToRadians(Ship.SINGLETON.getAngle()-90));
            double deltaY = distance * Math.sin(GraphicsUtils.degreesToRadians(Ship.SINGLETON.getAngle()-90));




            if(pos.x < 0)
            {
                pos.x = 0;
            }
            if (pos.x > AsteroidGame.SINGLETON.getCurrLevel().getWidth())
            {
                pos.x = AsteroidGame.SINGLETON.getCurrLevel().getWidth();

            }
            if(pos.y < 0)
            {
                pos.y = 0;
            }
            if(pos.y  > AsteroidGame.SINGLETON.getCurrLevel().getHeight())
            {
                pos.y = AsteroidGame.SINGLETON.getCurrLevel().getHeight();

            }

            setX(pos.x);
            setY(pos.y);

    }

}
