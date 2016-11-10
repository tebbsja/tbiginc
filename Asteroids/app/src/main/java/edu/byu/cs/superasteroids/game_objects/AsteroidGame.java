package edu.byu.cs.superasteroids.game_objects;

import java.util.ArrayList;


import edu.byu.cs.superasteroids.database.DAO;
import edu.byu.cs.superasteroids.model.AsteroidType;
import edu.byu.cs.superasteroids.model.BackgroundObject;
import edu.byu.cs.superasteroids.model.Cannon;
import edu.byu.cs.superasteroids.model.Engine;
import edu.byu.cs.superasteroids.model.ExtraPart;
import edu.byu.cs.superasteroids.model.Level;
import edu.byu.cs.superasteroids.model.LevelAsteroids;
import edu.byu.cs.superasteroids.model.MainBody;
import edu.byu.cs.superasteroids.model.PowerCore;
import edu.byu.cs.superasteroids.model.Projectile;

import android.util.Log;

/**
 * Created by Austin on 10/28/16.
 */

/**
 * This class holds all of the necessary information for the game. It gets all of this information from the database.
 */

public class AsteroidGame {
    private ArrayList<BackgroundObject> backgroundObjects;
    private ArrayList<AsteroidType> asteroidTypes;
    private ArrayList<Level> levels;
    private ArrayList<BackgroundObject> levelObjects;
    private ArrayList<LevelAsteroids> levelAsteroids;
    private ArrayList<MainBody> mainBodies;
    private ArrayList<Cannon> cannons;
    private ArrayList<ExtraPart> extraParts;
    private ArrayList<Engine> engines;
    private ArrayList<PowerCore> powerCores;
    private ArrayList<Projectile> projectiles;

    private ArrayList<BackgroundObject> bgOnLevel;

    private ArrayList<AsteroidType> asteroidsOnLevel;
    private Level currLevel;

    private int currentLevel;


    public static final AsteroidGame SINGLETON = new AsteroidGame();

    private AsteroidGame(){
    }


    public void loadModels()
    {
        backgroundObjects = DAO.SINGLETON.getAllBgObjects();
        asteroidTypes = DAO.SINGLETON.getAllAsteroidTypes();
        levels = DAO.SINGLETON.getLevels();
        mainBodies = DAO.SINGLETON.getMainBodies();
        cannons = DAO.SINGLETON.getCannons();
        extraParts = DAO.SINGLETON.getExtraParts();
        engines = DAO.SINGLETON.getEngines();
        powerCores = DAO.SINGLETON.getPowerCores();
        projectiles = new ArrayList<>();
        asteroidsOnLevel = new ArrayList<>();
        bgOnLevel = new ArrayList<>();

        currentLevel = 0;
        currLevel = DAO.SINGLETON.getSpecificLevel(1);

    }
    public void unload()
    {
        backgroundObjects.clear();
        asteroidTypes.clear();
        levels.clear();
        mainBodies.clear();
        cannons.clear();
        extraParts.clear();
        engines.clear();
        powerCores.clear();
        projectiles.clear();
        asteroidsOnLevel.clear();
        bgOnLevel.clear();
        currentLevel = 0;
        currLevel = null;
    }

    public Level getSpecificLevel(int level)
    {
        return DAO.SINGLETON.getSpecificLevel(level);
    }

    public ArrayList<BackgroundObject> getBgOnLevel() {
        return bgOnLevel;
    }

    public void setBgOnLevel(ArrayList<BackgroundObject> bgOnLevel) {
        this.bgOnLevel = bgOnLevel;
    }

    public Level getCurrLevel()
    {
        return this.currLevel;
    }

    public void setCurrLevel(Level l)
    {
        this.currLevel = l;
    }
    public void setCurrentLevel(int l)
    {
        this.currentLevel = l;
    }

    public ArrayList<Projectile> getProjectiles()
    {
        return this.projectiles;
    }
    public void setProjectiles (ArrayList<Projectile> projectiles)
    {
        this.projectiles = projectiles;
    }


    public ArrayList<AsteroidType> getAsteroidsOnLevel() {
        return asteroidsOnLevel;
    }

    public void setAsteroidsOnLevel(ArrayList<AsteroidType> asteroidsOnLevel) {
        this.asteroidsOnLevel = asteroidsOnLevel;
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("OBJECTS: ");
        sb.append("\n");
        for(BackgroundObject bg : backgroundObjects)
        {
            sb.append(bg.objtoString());
            sb.append("\n");
        }
        sb.append("\n");
        sb.append("ASTEROID TYPES: ");
        sb.append("\n");
        for(AsteroidType astdType : asteroidTypes)
        {
            sb.append(astdType.toString());
            sb.append("\n");
        }
        sb.append("\n");
        sb.append("\n");
        sb.append("LEVELS: ");
        sb.append("\n");
        for (Level l : levels)
        {
            sb.append(l.toString());
            sb.append("\n");
        }
        sb.append("MAINBODIES: ");
        sb.append("\n");
        for (MainBody m : mainBodies)
        {
            sb.append(m.toString());
            sb.append("\n");
        }
        sb.append("CANNONS: ");
        sb.append("\n");
        for (Cannon c : cannons)
        {
            sb.append(c.toString());
            sb.append("\n");
        }
        sb.append("EXTRAPARTS: ");
        sb.append("\n");
        for (ExtraPart e : extraParts)
        {
            sb.append(e.toString());
            sb.append("\n");
        }
        sb.append("ENGINES: ");
        sb.append("\n");
        for (Engine e : engines)
        {
            sb.append(e.toString());
            sb.append("\n");
        }
        sb.append("POWERCORES: ");
        sb.append("\n");
        for (PowerCore p : powerCores)
        {
            sb.append(p.toString());
            sb.append("\n");
        }

        return sb.toString();
    }

    public void incrementLevel()
    {
        currentLevel++;
    }
    public int getCurrentLevel()
    {
        return this.currentLevel;
    }


    public ArrayList<BackgroundObject> getBackgroundObjects() {
        return backgroundObjects;
    }

    public void setBackgroundObjects(ArrayList<BackgroundObject> backgroundObjects) {
        this.backgroundObjects = backgroundObjects;
    }

    public ArrayList<AsteroidType> getAsteroidTypes() {
        return asteroidTypes;
    }

    public void setAsteroidTypes(ArrayList<AsteroidType> asteroidTypes) {
        this.asteroidTypes = asteroidTypes;
    }

    public ArrayList<Level> getLevels() {
        return levels;
    }

    public void setLevels(ArrayList<Level> levels) {
        this.levels = levels;
    }

    public ArrayList<BackgroundObject> getLevelObjects() {
        return levelObjects;
    }

    public void setLevelObjects(ArrayList<BackgroundObject> levelObjects) {
        this.levelObjects = levelObjects;
    }

    public ArrayList<LevelAsteroids> getLevelAsteroids() {
        return levelAsteroids;
    }

    public void setLevelAsteroids(ArrayList<LevelAsteroids> levelAsteroids) {
        this.levelAsteroids = levelAsteroids;
    }

    public ArrayList<MainBody> getMainBodies() {
        return mainBodies;
    }

    public void setMainBodies(ArrayList<MainBody> mainBodies) {
        this.mainBodies = mainBodies;
    }

    public ArrayList<Cannon> getCannons() {
        return cannons;
    }

    public void setCannons(ArrayList<Cannon> cannons) {
        this.cannons = cannons;
    }

    public ArrayList<ExtraPart> getExtraParts() {
        return extraParts;
    }

    public void setExtraParts(ArrayList<ExtraPart> extraParts) {
        this.extraParts = extraParts;
    }

    public ArrayList<Engine> getEngines() {
        return engines;
    }

    public void setEngines(ArrayList<Engine> engines) {
        this.engines = engines;
    }

    public ArrayList<PowerCore> getPowerCores() {
        return powerCores;
    }

    public void setPowerCores(ArrayList<PowerCore> powerCores) {
        this.powerCores = powerCores;
    }
}
