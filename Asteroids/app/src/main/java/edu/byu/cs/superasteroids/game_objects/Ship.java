package edu.byu.cs.superasteroids.game_objects;

/**
 * Created by Austin on 10/11/16.
 */

import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.FloatMath;
import android.util.Log;

import edu.byu.cs.superasteroids.base.GameController;
import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.core.GraphicsUtils;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.game.InputManager;
import edu.byu.cs.superasteroids.model.Cannon;
import edu.byu.cs.superasteroids.model.Engine;
import edu.byu.cs.superasteroids.model.ExtraPart;
import edu.byu.cs.superasteroids.model.MainBody;
import edu.byu.cs.superasteroids.model.MovingObject;
import edu.byu.cs.superasteroids.model.PowerCore;
import edu.byu.cs.superasteroids.model.VisibleShipParts;

import static edu.byu.cs.superasteroids.base.GameController.newGame;
import static edu.byu.cs.superasteroids.core.GraphicsUtils.PI;
import static java.lang.Math.atan;
import static java.lang.Math.toDegrees;

/**
 * Ship provides all the information related to the ship the player controls
 */
public class Ship extends MovingObject {
    /** MainBody of the Ship */
    private MainBody mainBody;
    /** Cannon of the Ship */
    private Cannon cannon;
    /** ExtraPart of the Ship */
    private ExtraPart extraPart;
    /** Engine of the Ship */
    private Engine engine;
    /** PowerCore of the Ship */
    private PowerCore powerCore;

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    private float scale;
    private PointF coordinates;
    private boolean fired;
    private int totalSpeed;
    private int totalDamage;
    private RectF boundingBox;
    private static boolean safeMode;
    private int sixtyCount;
    private int secondCount;
    private int lives;

    /**
     *
     * @param body - body of the ship
     * @param cannon - cannon of the ship
     * @param extraPart - left wing of the ship
     * @param engine - engine of the ship
     * @param powerCore - powerCore of the ship
     */

    public static final Ship SINGLETON = new Ship();

    public RectF getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(RectF boundingBox) {
        this.boundingBox = boundingBox;
    }

    public Ship(MainBody body, Cannon cannon, ExtraPart extraPart, Engine engine, PowerCore powerCore)
    {
        super(-1,null,0,0,0,0,1,0);
        this.mainBody = body;
        this.cannon = cannon;
        this.extraPart = extraPart;
        this.engine = engine;
        this.powerCore = powerCore;
        this.boundingBox = new RectF();


    }

    public PointF getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(PointF coordinates) {
        this.coordinates = coordinates;
        this.setX((int) coordinates.x);
        this.setY((int) coordinates.y);


    }

    /** Default Constructor */
    public Ship(){
        sixtyCount = 0;
        secondCount = 0;
        lives = 10;
        safeMode = false;
    }
    /**
     * Draw is called 60 times every second and is the default method of the object to draw itself on the screen.
     */
    @Override
    public void Draw()
    {
        mainBody.Draw();
        cannon.Draw();
        extraPart.Draw();
        engine.Draw();
        //if ship is in safemode, draw a protective shield around it
        if(safeMode)
        {
            PointF point = ViewPort.SINGLETON.worldToView(new PointF(mainBody.getX(),mainBody.getY()));
            DrawingHelper.drawFilledCircle(new PointF(point.x, point.y), ((mainBody.getHeight()/2 * scale) + (engine.getHeight() * getScale())), Color.CYAN, 140);
        }

    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void decLives()
    {
        this.lives--;
    }
    public boolean getFired() {
        return this.fired;
    }


    /**
     * Update is called 60 times every second, and Updates every object. It may change the state of an object,
     * move an object, detect collisions, etc.
     *
     */


    public void Update(double elapsedTime)
    {
        PointF point = InputManager.movePoint;
        //updates the direction angle
        if (point != null)
        {
            double rotate = findAngle(point);
            Ship.SINGLETON.setAngle(rotate);
            mainBody.update(elapsedTime);
        }

        //checks collisions of the ship.
        checkCollisions();
        if (safeMode) {

            sixtyCount++;
            if (sixtyCount == 30)
            {
                secondCount++;
                sixtyCount = 0;
            }
            if (secondCount == 5) {
                safeMode = false;
                secondCount = 0;
            }
        }



        if(InputManager.firePressed)
        {
            fired = true;
        }
        else
        {
            fired = false;
        }


    }

    public void checkCollisions() {
        PointF point = new PointF();
        RectF temp;
        for (int i = AsteroidGame.SINGLETON.getAsteroidsOnLevel().size() - 1; i >= 0; i--) {

            float x = AsteroidGame.SINGLETON.getAsteroidsOnLevel().get(i).getX();
            float y = AsteroidGame.SINGLETON.getAsteroidsOnLevel().get(i).getY();
            point.x = x;
            point.y = y;
            PointF view = ViewPort.SINGLETON.worldToView(point);
            temp = new RectF(view.x - AsteroidGame.SINGLETON.getAsteroidsOnLevel().get(i).getWidth() / 2,
                    view.y - AsteroidGame.SINGLETON.getAsteroidsOnLevel().get(i).getHeight() / 2,
                    view.x + AsteroidGame.SINGLETON.getAsteroidsOnLevel().get(i).getWidth() / 2,
                    view.y + AsteroidGame.SINGLETON.getAsteroidsOnLevel().get(i).getHeight() / 2);

            if (RectF.intersects(temp, mainBody.getBoundingRectangle()) || RectF.intersects(temp, cannon.getBoundingRectangle())
                    || RectF.intersects(temp, extraPart.getBoundingRectangle()) || RectF.intersects(temp, engine.getBoundingRectangle()))
            {
                if (!safeMode)
                {
                    decLives();
                    if(getLives() == 0)
                    {
                        GameController.gameOver = true;
                        break;
                    }

                    AsteroidGame.SINGLETON.getAsteroidsOnLevel().get(i).decHP();
                    if (AsteroidGame.SINGLETON.getAsteroidsOnLevel().get(i).getHitPoints() <= 0)
                    {
                        AsteroidGame.SINGLETON.getAsteroidsOnLevel().remove(i);
                    }
                    safeMode = true;
                    break;
                }

            }

        }
    }

    public int getTotalSpeed() {
        return totalSpeed;
    }

    public void setTotalSpeed(int totalSpeed) {
        this.totalSpeed = totalSpeed;
    }

    public int getTotalDamage() {
        return totalDamage;
    }

    public void setTotalDamage(int totalDamage) {
        this.totalDamage = totalDamage;
    }

    /**
     * Finds the angle of the ship and the point pressed and sets the ship to that angle.
     * @param click
     * @return
     */
    public double findAngle(PointF click) {
        click = ViewPort.SINGLETON.viewToWorld(click);
        float deltaX = click.x - (float) mainBody.getX();
        float deltaY = (click.y - (float) mainBody.getY());
        float tanTheta = deltaY / deltaX;
        double angle = atan((double) tanTheta);
        double degrees = 0;

        if (deltaX < 0) {
            degrees = GraphicsUtils.radiansToDegrees(angle + GraphicsUtils.THREE_HALF_PI);
        } else if (deltaX > 0) {
            degrees = GraphicsUtils.radiansToDegrees(angle + GraphicsUtils.HALF_PI);
        }
        return degrees;
    }


    public void setMainBody(MainBody body)
    {
        this.mainBody = body;
    }
    public MainBody getMainBody()
    {
        return this.mainBody;
    }
    public void setCannon(Cannon cannon)
    {
        this.cannon = cannon;
    }
    public Cannon getCannon()
    {
        return this.cannon;
    }
    public void setExtraPart(ExtraPart extra)
    {
        this.extraPart = extra;
    }
    public ExtraPart getExtraPart()
    {
        return this.extraPart;
    }
    public void setEngine(Engine engine)
    {
        this.engine = engine;
    }
    public Engine getEngine()
    {
        return this.engine;
    }
    public void setPowerCore(PowerCore pc)
    {
        this.powerCore = pc;
    }
    public PowerCore getPowerCore()
    {
        return this.powerCore;
    }
}
