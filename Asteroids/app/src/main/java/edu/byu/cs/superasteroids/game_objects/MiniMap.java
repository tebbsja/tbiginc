package edu.byu.cs.superasteroids.game_objects;

import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.Rect;

import edu.byu.cs.superasteroids.core.GraphicsUtils;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.model.AsteroidType;
import edu.byu.cs.superasteroids.model.PositionedObject;

/**
 * Created by Austin on 11/3/16.
 */

public class MiniMap extends PositionedObject{


    public static final MiniMap SINGLETON = new MiniMap();

    //Draws a minimap. The numbers are arbitrary numbers fit to personal preference as to where the minimap is drawn and the size.
    public void Draw()
    {
        int miniMapWidth = AsteroidGame.SINGLETON.getCurrLevel().getWidth()/16;
        int miniMapHeight = AsteroidGame.SINGLETON.getCurrLevel().getHeight()/16;
        Rect outside = new Rect(1022-miniMapWidth, 0, 1022, miniMapHeight);
        Rect inside = new Rect(1023-miniMapWidth, 1, 1021, miniMapHeight-1);
        DrawingHelper.drawRectangle(outside, Color.WHITE, 255);
        DrawingHelper.drawFilledRectangle(inside, Color.BLACK, 255);

        PointF point = new PointF(Ship.SINGLETON.getMainBody().getX(), Ship.SINGLETON.getMainBody().getY());
        PointF ship = GraphicsUtils.scale(point, (float)1/16);
        ship.x = ship.x + 1022 - miniMapWidth;
        DrawingHelper.drawPoint(ship, 4, Color.GREEN, 255);

        for (AsteroidType a : AsteroidGame.SINGLETON.getAsteroidsOnLevel())
        {

            PointF asteroid = GraphicsUtils.scale(new PointF(a.getX(), a.getY()), (float)1/16);
            asteroid.x = asteroid.x + 1022 - miniMapWidth;
            DrawingHelper.drawPoint(asteroid, 4, Color.RED, 255);
        }

    }

}
