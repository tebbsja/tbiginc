package edu.byu.cs.superasteroids.base;

import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.FloatMath;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import android.graphics.Rect;

import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.game.GameActivity;
import edu.byu.cs.superasteroids.game.InputManager;
import edu.byu.cs.superasteroids.game_objects.*;
import edu.byu.cs.superasteroids.main_menu.MainActivity;
import edu.byu.cs.superasteroids.main_menu.MainMenuController;
import edu.byu.cs.superasteroids.model.*;

import static java.lang.Math.abs;

/**
 * Created by Austin on 10/29/16.
 */

public class GameController implements IGameDelegate {

    private int backgroundID;
    private int mainBodyID;
    private int prevMBID;
    private int cannonID;
    private int prevCID;
    private int extraPartID;
    private int prevEPID;
    private int engineID;
    private int prevEnID;
    private int projectileID;
    private int currentLevel;
    private ArrayList<AsteroidType> asteroids;
    private ArrayList<BackgroundObject> bgs;
    private String pImgPath;
    private int pWidth;
    private int pHeight;
    private int pDamage;
    private String pSoundPath;
    private boolean endLevel;
    private int sixtyCount;
    private int secondCount;
    private String levelString;
    public static boolean endGame;
    public static boolean gameOver;
    private GameActivity gameActivity;
    private int totalNumLevels;
    private int projectileSoundId;
    private int musicLoopId;
    public static boolean newGame = true;

    public GameController(GameActivity gameActivity) {
        levelString = "Level One";
        this.gameActivity = gameActivity;

        endLevel = false;
        endGame = false;
        sixtyCount = 0;
        secondCount = 0;
        prevCID = 0;
        prevEnID = 0;
        prevEPID = 0;
        prevMBID = 0;
        projectileSoundId = 0;
        musicLoopId = 0;
        gameOver = false;



        currentLevel = AsteroidGame.SINGLETON.getCurrentLevel();
        bgs = new ArrayList<>();
        asteroids = new ArrayList<>();

        pImgPath = null;
        pWidth = 0;
        pHeight = 0;
        pDamage = 0;
        pSoundPath = null;

        Ship.SINGLETON.setTotalDamage(Ship.SINGLETON.getCannon().getProjectile().getDamage()
                + Ship.SINGLETON.getPowerCore().getCannonBoost());
        Ship.SINGLETON.setTotalSpeed(Ship.SINGLETON.getEngine().getBaseSpeed()
                + Ship.SINGLETON.getPowerCore().getEngineBoost());
        Ship.SINGLETON.getMainBody().setX(AsteroidGame.SINGLETON.getCurrLevel().getWidth()/2);
        Ship.SINGLETON.getMainBody().setY(AsteroidGame.SINGLETON.getCurrLevel().getHeight()/2);


    }

    /**
     * Updates all the objects in the game.
     * @param elapsedTime Time since the last update. For this game, elapsedTime is always 1/60th of a second
     */
    @Override
    public void update(double elapsedTime) {

        // we know the level is done if there are no more asteroids.
        if (AsteroidGame.SINGLETON.getAsteroidsOnLevel().size() == 0)
        {
            endLevel = true;
        }

        Ship.SINGLETON.Update(elapsedTime);

        //small timing loop to display a message
        if(gameOver)
        {
            sixtyCount++;
            if (sixtyCount == 30)
            {
                secondCount++;
                sixtyCount = 0;
            }
            if (secondCount < 3)
            {
                DrawingHelper.drawFilledRectangle(new Rect(0, 0, ViewPort.SINGLETON.getWidth(), ViewPort.SINGLETON.getHeight()), Color.BLACK, 255);
                DrawingHelper.drawCenteredText("GAME OVER YOU LOSE", 35, Color.WHITE);
            }
            else
            {
                DrawingHelper.drawFilledRectangle(new Rect(0, 0, ViewPort.SINGLETON.getWidth(), ViewPort.SINGLETON.getHeight()), Color.BLACK, 255);
                DrawingHelper.drawCenteredText("GAME OVER YOU LOSE", 35, Color.WHITE);
                gameActivity.onBackPressed();
            }

        }
        ViewPort.SINGLETON.Update();

        //if fire is pressed then we create a new projectile and add it to the Game's projectile ArrayList and play the projectile sound.
        if (InputManager.firePressed)
        {
            AsteroidGame.SINGLETON.getProjectiles().add(new Projectile(projectileID,pImgPath,pWidth,pHeight,pSoundPath, pDamage));
            ContentManager.getInstance().playSound(projectileSoundId, 1, (float)1.5);
        }

        for(AsteroidType ast : AsteroidGame.SINGLETON.getAsteroidsOnLevel())
        {
            ast.update(elapsedTime);

        }
        for (Projectile p : AsteroidGame.SINGLETON.getProjectiles()) {
            p.Update(elapsedTime);
            if (p.getX() < 0 || p.getX() > ViewPort.SINGLETON.getWidth() || p.getY() < 0 || p.getY() > ViewPort.SINGLETON.getHeight()) {
                AsteroidGame.SINGLETON.getProjectiles().remove(p);
                break;
            }

        }

        //check for collisions with asteroids and projectile.
        checkCollision(elapsedTime);

        //if the end of the level, then display a banner with the next levels title and hint, and jump to unload
        if (endLevel)
        {
            if(sixtyCount == 0 && secondCount == 0)
            {
                currentLevel++;
            }
            AsteroidGame.SINGLETON.setCurrLevel(AsteroidGame.SINGLETON.getSpecificLevel(currentLevel));

            levelString = "LEVEL " + Integer.toString(currentLevel) +  " " + AsteroidGame.SINGLETON.getCurrLevel().getHint();
            sixtyCount++;
            if(sixtyCount == 30)
            {
                secondCount++;
                sixtyCount = 0;
            }

            if (secondCount < 3)
            {
                if(currentLevel <= totalNumLevels) {
                    DrawingHelper.drawFilledRectangle(new Rect(0, 0, ViewPort.SINGLETON.getWidth(), ViewPort.SINGLETON.getHeight()), Color.BLACK, 255);
                    DrawingHelper.drawCenteredText(levelString, 35, Color.WHITE);
                }
                else
                {
                    DrawingHelper.drawFilledRectangle(new Rect(0, 0, ViewPort.SINGLETON.getWidth(), ViewPort.SINGLETON.getHeight()), Color.BLACK, 255);
                    DrawingHelper.drawCenteredText("CONGRATS! WINNER!", 35, Color.WHITE);
                    endGame = true;

                }

            }
            else
            {
                if (endGame)
                {
                    DrawingHelper.drawFilledRectangle(new Rect(0, 0, ViewPort.SINGLETON.getWidth(), ViewPort.SINGLETON.getHeight()), Color.BLACK, 255);
                    DrawingHelper.drawCenteredText("CONGRATS! WINNER!", 35, Color.WHITE);
                    gameActivity.onBackPressed();
                }
                else {
                    unloadContent(ContentManager.getInstance());
                    endLevel = false;
                }
            }


        }


    }


    /**
     * Checks for collisions between asteroids and Projectiles,  performs the necessary operations if conditions are met.
     * @param elapsedTime
     */
    public void checkCollision(double elapsedTime)
    {
        PointF point = new PointF();
        RectF temp;
        for (int i = AsteroidGame.SINGLETON.getAsteroidsOnLevel().size()-1; i >= 0; i--)
        {

            float x = AsteroidGame.SINGLETON.getAsteroidsOnLevel().get(i).getX();
            float y = AsteroidGame.SINGLETON.getAsteroidsOnLevel().get(i).getY();
            point.x = x;
            point.y = y;
            PointF view = ViewPort.SINGLETON.worldToView(point);
            temp = new RectF(view.x - AsteroidGame.SINGLETON.getAsteroidsOnLevel().get(i).getWidth()/2,
                    view.y - AsteroidGame.SINGLETON.getAsteroidsOnLevel().get(i).getHeight()/2,
                    view.x + AsteroidGame.SINGLETON.getAsteroidsOnLevel().get(i).getWidth()/2,
                    view.y + AsteroidGame.SINGLETON.getAsteroidsOnLevel().get(i).getHeight()/2);


            for (int k = AsteroidGame.SINGLETON.getProjectiles().size()-1; k>= 0; k--) {
                if (RectF.intersects(temp, AsteroidGame.SINGLETON.getProjectiles().get(k).getBoundingRect())) {
                    breakAsteroid(AsteroidGame.SINGLETON.getAsteroidsOnLevel().get(i));
                    AsteroidGame.SINGLETON.getAsteroidsOnLevel().remove(i);
                    AsteroidGame.SINGLETON.getProjectiles().remove(k);
                    break;
                }
            }
        }


    }

    /**
     * In the Event of an asteroid breaking, depending on what type of asteroid it is, we need to break it into smaller asteroids
     * and give certain special asteroids their features.
     * @param a
     */
    public void breakAsteroid(AsteroidType a)
    {
        int asteroidHealth = a.getHitPoints() - Ship.SINGLETON.getTotalDamage();

        if(asteroidHealth <= 0) {

            if (a.getType() == "regular") {
                int n = 2;
                if (a.getScale() != .5f) {
                    for (int i = 0; i < n; i++) {
                        RegularAsteroid temp = new RegularAsteroid(a.getId(), a.getImagePath(), a.getWidth(), a.getHeight());
                        temp.setX(a.getX());
                        temp.setY(a.getY());
                        temp.setAngle(generateAngle());
                        temp.setSpeed(generateVelocity());
                        temp.setScale(.5f);
                        temp.setBroken(true);
                        temp.setHitPoints(a.getHitPoints() / 2);
                        AsteroidGame.SINGLETON.getAsteroidsOnLevel().add(temp);
                    }
                }

            } else if (a.getType() == "growing") {

                int n = 2;
                if (a.getScale() > .49)
                {
                    for (int i = 0; i < n; i++) {
                        GrowingAsteroid temp = new GrowingAsteroid(a.getId(), a.getImagePath(), a.getWidth(), a.getHeight());
                        temp.setX(a.getX());
                        temp.setY(a.getY());
                        temp.setAngle(generateAngle());
                        temp.setSpeed(generateVelocity());
                        if (a.getScale() > 1.25f) {
                            temp.setScale(1);
                            temp.setHitPoints(4);
                        }
                        else if (a.getScale() > .75f) {
                            temp.setScale(.5f);
                            temp.setHitPoints(2);
                        }
                        else if (a.getScale() > .5f) {
                            temp.setScale(.25f);
                            temp.setHitPoints(1);
                        }
                        temp.setBroken(true);
                        AsteroidGame.SINGLETON.getAsteroidsOnLevel().add(temp);
                    }
                }
            } else if (a.getType() == "octeroid") {
                int n = 8;
                if (a.getScale() != .125f)
                {
                    for (int i = 0; i < n; i++) {
                        OcteroidAsteroid temp = new OcteroidAsteroid(a.getId(), a.getImagePath(), a.getWidth(), a.getHeight());
                        temp.setX(a.getX());
                        temp.setY(a.getY());
                        temp.setAngle(generateAngle());
                        temp.setSpeed(generateVelocity());
                        temp.setScale(.125f);
                        temp.setBroken(true);
                        temp.setHitPoints(a.getHitPoints() / 2);
                        AsteroidGame.SINGLETON.getAsteroidsOnLevel().add(temp);
                    }
                }

            }
        }

    }
    /**
     * Unloads images and sounds we arent using
     *
     * @param content An instance of the content manager. This should be used to unload image and
     */
    @Override
    public void unloadContent(ContentManager content) {



        for (BackgroundObject bgObjs : AsteroidGame.SINGLETON.getBgOnLevel())
        {
            content.unloadImage(bgObjs.getId());
        }

        AsteroidGame.SINGLETON.getProjectiles().clear();
        AsteroidGame.SINGLETON.getBgOnLevel().clear();
        AsteroidGame.SINGLETON.getAsteroidsOnLevel().clear();

        content.pauseLoop(musicLoopId);

        sixtyCount = 0;
        secondCount = 0;




        if (currentLevel > totalNumLevels || gameOver)
        {
            /*content.unloadImage(Ship.SINGLETON.getMainBody().getId());
            content.unloadImage(Ship.SINGLETON.getCannon().getId());
            content.unloadImage(Ship.SINGLETON.getEngine().getId());
            content.unloadImage(Ship.SINGLETON.getExtraPart().getId());
            content.unloadImage(projectileID);*/
            content.unloadLoopSound(musicLoopId);
            content.unloadSound(projectileSoundId);
            Ship.SINGLETON.getMainBody().setId(prevMBID);
            Ship.SINGLETON.getCannon().setId(prevCID);
            Ship.SINGLETON.getExtraPart().setId(prevEPID);
            Ship.SINGLETON.getEngine().setId(prevEnID);
            projectileID = -1;
            BackgroundImage.SINGLETON.setId(0);
            newGame = true;
            endGame = true;
            currentLevel = 0;
            AsteroidGame.SINGLETON.setCurrentLevel(0);
            gameOver = false;

            for(AsteroidType a : asteroids)
            {
                content.unloadImage(a.getId());
            }

        }
        else
        {
            loadContent(content);
        }


    }

    /**
     * Loads the images and sounds for the level.
     * @param content An instance of the content manager. This should be used to load images and sound
     */
    @Override
    public void loadContent(ContentManager content) {

        totalNumLevels = AsteroidGame.SINGLETON.getLevels().size();
        if(currentLevel == 1) {
            backgroundID = content.loadImage("images/space.bmp");
        }
        //load level background objects
        for(Level l : AsteroidGame.SINGLETON.getLevels())
        {
            if (l.getLevelNum() == currentLevel)
            {
                for (BackgroundObject bg : l.getLevelObjects())
                {
                    int id;
                    id = content.loadImage(bg.getImagePath());
                    Bitmap img = content.getImage(id);
                    bg.setId(id);
                    bg.setWidth(img.getWidth());
                    bg.setHeight(img.getHeight());
                    bgs.add(bg);
                }
                try {
                    musicLoopId = content.loadLoopSound(l.getMusicPath());
                    content.playLoop(musicLoopId);
                }catch (IOException e)
                {
                    e.printStackTrace();
                }
                //Generating all the asteroids for the level.
                for (LevelAsteroids la : l.getLevelAsteroids())
                {
                    int id;
                    id = content.loadImage(la.getAsteroidType().getImagePath());

                    for (int i=0; i < la.getNumAsteroids(); i++)
                    {
                        if (la.getAsteroidID() == 1)
                        {
                            RegularAsteroid temp = new RegularAsteroid(id, la.getAsteroidType().getImagePath(),
                                    la.getAsteroidType().getWidth(), la.getAsteroidType().getHeight());
                            temp.setId(id);
                            temp.setX((float)generatePos().x);
                            temp.setY((float)generatePos().y);
                            temp.setAngle(generateAngle());
                            temp.setSpeed(generateVelocity());
                            temp.setScale(1);
                            asteroids.add(temp);
                        }
                        else if (la.getAsteroidID() == 2)
                        {
                            GrowingAsteroid temp = new GrowingAsteroid(id, la.getAsteroidType().getImagePath(),
                                    la.getAsteroidType().getWidth(), la.getAsteroidType().getHeight());
                            temp.setId(id);
                            temp.setX((float)generatePos().x);
                            temp.setY((float)generatePos().y);
                            temp.setAngle(generateAngle());
                            temp.setSpeed(generateVelocity());
                            temp.setScale(1);
                            asteroids.add(temp);
                        }
                        else if (la.getAsteroidID() == 3)
                        {
                            OcteroidAsteroid temp = new OcteroidAsteroid(id, la.getAsteroidType().getImagePath(),
                                    la.getAsteroidType().getWidth(), la.getAsteroidType().getHeight());
                            temp.setId(id);
                            temp.setX((float)generatePos().x);
                            temp.setY((float)generatePos().y);
                            temp.setAngle(generateAngle());
                            temp.setSpeed(generateVelocity());
                            temp.setScale(1);
                            asteroids.add(temp);
                        }

                    }


                }
            }
        }

        //make sure all of the ship images are loaded, and we save the img ID's in the content manager.
        AsteroidGame.SINGLETON.setAsteroidsOnLevel(asteroids);
        AsteroidGame.SINGLETON.setBgOnLevel(bgs);

        if(currentLevel == 1) {
            mainBodyID = content.loadImage(Ship.SINGLETON.getMainBody().getImagePath());
            cannonID = content.loadImage(Ship.SINGLETON.getCannon().getImagePath());
            extraPartID = content.loadImage(Ship.SINGLETON.getExtraPart().getImagePath());
            engineID = content.loadImage(Ship.SINGLETON.getEngine().getImagePath());

            projectileID = content.loadImage(Ship.SINGLETON.getCannon().getProjectile().getImagePath());
            try {
                projectileSoundId = content.loadSound(Ship.SINGLETON.getCannon().getProjectile().getAttackSoundPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            pImgPath = Ship.SINGLETON.getCannon().getProjectile().getImagePath();
            pWidth = Ship.SINGLETON.getCannon().getProjectile().getWidth();
            pHeight = Ship.SINGLETON.getCannon().getProjectile().getHeight();
            pDamage = Ship.SINGLETON.getCannon().getProjectile().getDamage();
            pSoundPath = Ship.SINGLETON.getCannon().getProjectile().getAttackSoundPath();


            prevMBID = Ship.SINGLETON.getMainBody().getId();
            prevCID = Ship.SINGLETON.getCannon().getId();
            prevEPID = Ship.SINGLETON.getExtraPart().getId();
            prevEnID = Ship.SINGLETON.getEngine().getId();
            Ship.SINGLETON.getMainBody().setId(mainBodyID);
            Ship.SINGLETON.getCannon().setId(cannonID);
            Ship.SINGLETON.getExtraPart().setId(extraPartID);
            Ship.SINGLETON.getEngine().setId(engineID);

            BackgroundImage.SINGLETON.setId(backgroundID);
        }

    }


    /**
     * This initializes the bounds for the mainbody of the ship.
     */
    public void initializeBoundingBox()
    {
        PointF xy = ViewPort.SINGLETON.worldToView(new PointF(Ship.SINGLETON.getMainBody().getX() ,Ship.SINGLETON.getMainBody().getY() ));
        RectF mb = new RectF(
                (float) (xy.x - (Ship.SINGLETON.getMainBody().getWidth() * Ship.SINGLETON.getScale())),
                (float) (xy.y - (Ship.SINGLETON.getMainBody().getHeight()/2 * Ship.SINGLETON.getScale())),
                (float) (xy.x + (Ship.SINGLETON.getMainBody().getWidth() * Ship.SINGLETON.getScale())),
                (float) (xy.y + (Ship.SINGLETON.getMainBody().getHeight()/4 * Ship.SINGLETON.getScale()))
        );
        Ship.SINGLETON.getMainBody().setBoundingRectangle(mb);
    }

    /**
     * Generates a random velocity for the asterois, minimum 100
     * @return velocity
     */
    public double generateVelocity()
    {
        Random rand = new Random();
        double velocity = (rand.nextDouble() * 300) + 100;
        return velocity;
    }

    /**
     * Generates a random direction for the asteroids.
     * @return angle
     */

    public double generateAngle()
    {
        Random rand = new Random();
        double angle = rand.nextDouble() * 360;

        return angle;
    }

    /**
     * Generates a random position for the asteroids, makes sure not to be within 100 pixels of the ship when they spawn.
     * @return point for the asteroids to be drawn
     */

    public Point generatePos()
    {
        Point pnt = new Point(0,0);
        Random rand = new Random();
        pnt.x = abs(rand.nextInt(AsteroidGame.SINGLETON.getCurrLevel().getWidth()));
        while(abs(Ship.SINGLETON.getMainBody().getX() - pnt.x) < 100 ) {
            pnt.x = abs(rand.nextInt(AsteroidGame.SINGLETON.getCurrLevel().getWidth()));
        }
        pnt.y = abs(rand.nextInt(AsteroidGame.SINGLETON.getCurrLevel().getHeight()));
        while(abs(Ship.SINGLETON.getMainBody().getY() - pnt.y) < 100 ) {
            pnt.y = abs(rand.nextInt(AsteroidGame.SINGLETON.getCurrLevel().getHeight()));
        }

        return pnt;


    }



    /**
     * Draws everything in the game, this is called 60 times/ second.
     */
    @Override
    public void draw() {

        if(newGame)
        {

            Ship.SINGLETON.setScale(.325f);
            PointF coordinates = new PointF((Ship.SINGLETON.getMainBody().getX()),(Ship.SINGLETON.getMainBody().getY()));
            Ship.SINGLETON.setCoordinates(coordinates);
            Ship.SINGLETON.Draw();
            initializeBoundingBox();
            newGame = false;
        }

        BackgroundImage.SINGLETON.levelScaleX();
        BackgroundImage.SINGLETON.levelScaleY();
        DrawingHelper.drawImage(backgroundID, BackgroundImage.SINGLETON.getSpaceToDraw(),null);
        drawBGobj();
        Ship.SINGLETON.Draw();


        for(AsteroidType a : AsteroidGame.SINGLETON.getAsteroidsOnLevel())
        {
            a.Draw();

        }

        for (Projectile prj : AsteroidGame.SINGLETON.getProjectiles())
        {
            prj.Draw();
        }

        MiniMap.SINGLETON.Draw();
    }


    public void drawBGobj()
    {
        for (int i=0; i < bgs.size(); i++)
        {
            bgs.get(i).Draw();
        }

    }


}
