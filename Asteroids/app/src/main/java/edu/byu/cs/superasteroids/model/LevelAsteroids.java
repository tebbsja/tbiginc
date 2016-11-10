package edu.byu.cs.superasteroids.model;

/**
 * Created by Austin on 10/11/16.
 */

import android.graphics.PointF;
import android.util.Log;

import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.game_objects.Ship;
import edu.byu.cs.superasteroids.game_objects.ViewPort;

/**
 * This Class gives us the information on the asteroids for the level.
 */
public class LevelAsteroids {
    /** ID of asteroid type we will generate */
    private int asteroidID;
    /** amount of asteroids to generate */
    private int numAsteroids;
    /** Asteroid type */
    private AsteroidType asteroidType;
    private int level_id;
    private int id;

    /**
     *
     * @param numAsteroids - amount of asteroids to generate
     * @param asteroidID - the type of asteroid to generate
     */
    public LevelAsteroids(int id, int numAsteroids, int asteroidID, int level_id, AsteroidType asteroidType)
    {
        this.id = id;
        this.numAsteroids = numAsteroids;
        this.asteroidID = asteroidID;
        this.level_id = level_id;
        this.asteroidType = asteroidType;

    }
    public LevelAsteroids()
    {

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("NUMBER " + Integer.toString(getNumAsteroids()) + " | ASTEROID ID " + Integer.toString(getAsteroidID()) + " | LEVEL ID " + Integer.toString(getLevelId()));
        return sb.toString();
    }


    public void setAsteroidType(AsteroidType asteroid) { this.asteroidType = asteroid;}
    public AsteroidType getAsteroidType() {return this.asteroidType;}
    public void setAsteroidID(int id)
    {
        this.asteroidID = id;
    }
    public int getAsteroidID()
    {
        return this.asteroidID;
    }
    public void setNumAsteroids(int n)
    {
        this.numAsteroids = n;
    }
    public int getNumAsteroids()
    {
        return this.numAsteroids;
    }
    public void setLevel_id(int id)
    {
        this.level_id = id;
    }
    public int getLevelId()
    {
        return this.level_id;
    }
}

