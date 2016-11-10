package edu.byu.cs.superasteroids.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

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

/**
 * Created by Austin on 10/11/16.
 */

/**
 * All information about the databse can be found here, objects can be added to the database from here
 */
public class DAO {


    public static final DAO SINGLETON = new DAO();
    /** This is the AsteroidGame Database */
    private SQLiteDatabase db;
    private DbOpenHelper dbOpenHelper;

    /**
     * Constructor
     * @param db - database
     */

    public DAO(SQLiteDatabase db)
    {
        this.db = db;
    }
    public void setDB(SQLiteDatabase db, DbOpenHelper dbOpenHelper)
    {
        this.db = db;
        this.dbOpenHelper = dbOpenHelper;
    }
    public DAO(){}
    /**
     * Given an object, will add to data base
     * @param bg_obj - BackgroundObject
     */

    public boolean addBackgroundObject(BackgroundObject bg_obj)
    {
        ContentValues values = new ContentValues();
        values.put("image", bg_obj.getImagePath());
        long bool = db.insert("bg_objects", null, values);
        if (bool == -1)
        {
            return false;
        }

        return true;


    }
    public boolean addLevel(Level l)
    {
        ContentValues values = new ContentValues();
        //values.put("number", l.getLevelNum());
        values.put("title", l.getTitle());
        values.put("hint", l.getHint());
        values.put("width", l.getWidth());
        values.put("height", l.getHeight());
        values.put("music", l.getMusicPath());
        long bool = db.insert("levels", null, values);

        if (bool == -1)
        {
            return false;
        }

        boolean insert = true;


        for(BackgroundObject lo : l.getLevelObjects())
        {
            insert = addLevelObjects(lo);
        }
        if (insert == false)
        {
            return false;
        }

        for(LevelAsteroids la : l.getLevelAsteroids())
        {
            insert = addLevelAsteroidObjects(la);
        }
        if (insert == false)
        {
            return false;
        }

        return true;

    }

    /**
     * DAO will add asteroid to database
     * @param asteroidType - asteroid to add
     */
    public boolean addAsteroid(AsteroidType asteroidType)
    {
        ContentValues values = new ContentValues();
        values.put("name", asteroidType.getName());
        values.put("image", asteroidType.getImagePath());
        values.put("imageWidth", asteroidType.getWidth());
        values.put("imageHeight", asteroidType.getHeight());
        values.put("type", asteroidType.getType());

        long bool = db.insert("asteroid_type", null, values);

        if (bool == -1)
        {
            return false;
        }

        return true;

    }

    /**
     * Adds all the level objects of a level to the database
     * @param lvObj - Background Object
     */
    public boolean addLevelObjects(BackgroundObject lvObj)
    {
        ContentValues values = new ContentValues();
        values.put("position_x", lvObj.getX());
        values.put("position_y", lvObj.getY());
        values.put("bg_object_id", lvObj.getId());
        values.put("scale", lvObj.getScale());
        values.put("level_id", lvObj.getLevelId());

        long bool = db.insert("level_objects", null, values);
        if (bool == -1)
        {
            return false;
        }

        return true;

    }

    /**
     * Adds all the level asteroid objects for a level to the database
     * @param la - level asteroid to add
     */
    public boolean addLevelAsteroidObjects(LevelAsteroids la)
    {
        ContentValues values = new ContentValues();
        values.put("asteroid_id", la.getAsteroidID());
        values.put("number", la.getNumAsteroids());
        values.put("level_id", la.getLevelId());

        long bool = db.insert("level_asteroids", null, values);
        if (bool == -1)
        {
            return false;
        }

        return true;

    }

    /**
     * Adds a main body to the databse
     * @param m - main body object
     */
    public boolean addMainBodies(MainBody m)
    {
        ContentValues values = new ContentValues();
        values.put("cannon_attach_x", m.getCannonAttachX());
        values.put("cannon_attach_y", m.getCannonAttachY());
        values.put("engine_attach_x", m.getEngineAttachX());
        values.put("engine_attach_y", m.getEngineAttachY());
        values.put("extra_attach_x", m.getExtraAttachX());
        values.put("extra_attach_y", m.getExtraAttachY());
        values.put("image", m.getImagePath());
        values.put("image_width", m.getWidth());
        values.put("image_height", m.getHeight());

        long bool = db.insert("main_bodies", null, values);
        if (bool == -1)
        {
            return false;
        }

        return true;


    }

    /**
     * Adds a cannon to the database
     * @param c - cannon object
     */
    public boolean addCannons(Cannon c)
    {
        ContentValues values = new ContentValues();
        values.put("attach_point_x", c.getAttachPointX());
        values.put("attach_point_y", c.getAttachPointY());
        values.put("emit_point_x", c.getEmitPointX());
        values.put("emit_point_y", c.getEmitPointY());
        values.put("image", c.getImagePath());
        values.put("image_width", c.getWidth());
        values.put("image_height", c.getHeight());
        values.put("attack_image", c.getProjectile().getImagePath());
        values.put("attack_image_width", c.getProjectile().getWidth());
        values.put("attack_image_height", c.getProjectile().getHeight());
        values.put("attack_sound", c.getProjectile().getAttackSoundPath());
        values.put("damage", c.getProjectile().getDamage());

        long bool = db.insert("cannons", null, values);
        if (bool == -1)
        {
            return false;
        }

        return true;

    }

    /**
     * Adds an  extraPart object to the database
     * @param ep - ExtraPart object
     */
    public boolean addExtraParts(ExtraPart ep)
    {
        ContentValues values = new ContentValues();
        values.put("attach_point_x", ep.getAttachPointX());
        values.put("attach_point_y", ep.getAttachPointY());
        values.put("image", ep.getImagePath());
        values.put("image_width", ep.getWidth());
        values.put("image_height", ep.getHeight());

        long bool = db.insert("extra_parts", null, values);
        if (bool == -1)
        {
            return false;
        }

        return true;
    }

    /**
     * Adds an engine to the database
     * @param e - Engine object
     */
    public boolean addEngines(Engine e)
    {
        ContentValues values = new ContentValues();
        values.put("base_speed", e.getBaseSpeed());
        values.put("base_turn_rate", e.getBaseTurnRate());
        values.put("attach_point_x", e.getAttachPointX());
        values.put("attach_point_y", e.getAttachPointY());
        values.put("image", e.getImagePath());
        values.put("image_width", e.getWidth());
        values.put("image_height", e.getHeight());

        long bool = db.insert("engines", null, values);
        if (bool == -1)
        {
            return false;
        }

        return true;
    }

    /**
     * Adds a Power Core Object to the database
     * @param pc - PowerCore object
     */
    public boolean addPowerCores(PowerCore pc)
    {
        ContentValues values = new ContentValues();
        values.put("cannon_boost", pc.getCannonBoost());
        values.put("engine_boost", pc.getEngineBoost());
        values.put("image", pc.getImagePath());

        long bool = db.insert("power_cores", null, values);
        if (bool == -1)
        {
            return false;
        }

        return true;
    }

    /**
     *
     * @return - ArrayList of all levels
     */
    public ArrayList<Level> getLevels()
    {

        ArrayList<Level> levels = new ArrayList<>();
        Cursor c = db.rawQuery("select * from levels", new String[]{});
        try {
            c.moveToFirst();
            while (!c.isAfterLast()) {

                int number = c.getInt(0);
                String title = c.getString(1);
                String hint = c.getString(2);
                int width = c.getInt(3);
                int height = c.getInt(4);
                String music = c.getString(5);
                ArrayList<BackgroundObject> lvlObjs = getLevelObjects(number);
                ArrayList<LevelAsteroids> lvlAstds = getLevelAsteroids(number);
                Level l = new Level(number, title, hint, width, height, music, lvlObjs, lvlAstds);
                levels.add(l);
                c.moveToNext();

            }
        } finally {
            c.close();
        }

        return levels;
    }

    public String getObject(int i)
    {
        String path = " ";
        Cursor c = db.rawQuery("select * from bg_objects where id = ?", new String[]{Integer.toString(i)});
        try{
            c.moveToFirst();
            while (!c.isAfterLast()) {
                path = c.getString(1);
                c.moveToNext();
            }
        } finally {
            c.close();
        }


        return path;

    }

    /**
     *
     * @return - ArrayList of Asteroid Types
     */
    public ArrayList<AsteroidType> getAllAsteroidTypes()
    {
        ArrayList<AsteroidType> asteroidTypes = new ArrayList<>();
        Cursor c = db.rawQuery("select * from asteroid_type", new String[]{});
        try {
            c.moveToFirst();
            while (!c.isAfterLast()) {
                int id = c.getInt(0);
                String name = c.getString(1);
                String image = c.getString(2);
                int imageWidth = c.getInt(3);
                int imageHeight = c.getInt(4);
                String type = c.getString(5);
                AsteroidType aT = new AsteroidType(id, name, image, imageWidth, imageHeight, type);
                asteroidTypes.add(aT);
                c.moveToNext();
            }
        } finally {
            c.close();
        }

        return asteroidTypes;
    }

    /**
     *
     * @return - ArrayList of Background (level) objects
     */
    public ArrayList<BackgroundObject> getLevelObjects(int number)
    {
        ArrayList<BackgroundObject> lvlObjs = new ArrayList<>();

        Cursor c = db.rawQuery("select * from level_objects where level_id = ?", new String[]{Integer.toString(number)});
        try {
            c.moveToFirst();
            while (!c.isAfterLast()) {

                int x =  c.getInt(1);
                int y = c.getInt(2);
                float scale = c.getFloat(3);
                int bgID = c.getInt(4);
                int levelID = c.getInt(5);
                String path = getObject(bgID);
                BackgroundObject bg = new BackgroundObject(bgID, path, x, y, scale, levelID);
                lvlObjs.add(bg);
                c.moveToNext();

            }
        } finally {
            c.close();
        }

        return lvlObjs;

    }

    public AsteroidType getAsteroidType(int astID)
    {
        AsteroidType aT = new AsteroidType();
        Cursor c = db.rawQuery("select * from asteroid_type where id = ?", new String[]{Integer.toString(astID)});
        try{
            c.moveToFirst();
            while (!c.isAfterLast()) {
                int id = c.getInt(0);
                String name = c.getString(1);
                String image = c.getString(2);
                int imageWidth = c.getInt(3);
                int imageHeight = c.getInt(4);
                String type = c.getString(5);
                aT = new AsteroidType(id, name, image, imageWidth, imageHeight, type);
                c.moveToNext();
            }
        } finally {
            c.close();
        }

        return aT;
    }

    /**
     *
     * @return - ArrayList of Level Asteroids
     */
    public ArrayList<LevelAsteroids> getLevelAsteroids(int level)
    {
        ArrayList<LevelAsteroids> lvlAstds = new ArrayList<>();

        Cursor c = db.rawQuery("select * from level_asteroids where level_id = ?", new String[]{Integer.toString(level)});
        try {
            c.moveToFirst();
            while (!c.isAfterLast()) {
                int id = c.getInt(0);
                int astID = c.getInt(1);
                int numAst = c.getInt(2);
                int lvlID = c.getInt(3);
                AsteroidType aT = getAsteroidType(astID);
                LevelAsteroids lvlAstd = new LevelAsteroids(id, numAst, astID, lvlID, aT );
                lvlAstds.add(lvlAstd);
                c.moveToNext();

            }
        } finally {
            c.close();
        }

        return lvlAstds;
    }

    /**
     *
     * @return - ArrayList of MainBodies
     */
    public ArrayList<MainBody> getMainBodies()
    {
        ArrayList<MainBody> mainBodies = new ArrayList<>();
        Cursor c = db.rawQuery("select * from main_bodies", new String[]{});
        try {
            c.moveToFirst();
            while (!c.isAfterLast()) {

                int id = c.getInt(0);
                int cannonX = c.getInt(1);
                int cannonY = c.getInt(2);
                int engineX = c.getInt(3);
                int engineY = c.getInt(4);
                int extraX = c.getInt(5);
                int extraY = c.getInt(6);
                String image = c.getString(7);
                int imageWidth = c.getInt(8);
                int imageHeight = c.getInt(9);
                MainBody mb = new MainBody(id, cannonX, cannonY, engineX, engineY, extraX, extraY, image, imageWidth, imageHeight);
                mainBodies.add(mb);

                c.moveToNext();

            }
        } finally {
            c.close();
        }

        return mainBodies;
    }

    /**
     *
     * @return - ArrayList of Cannons
     */
    public ArrayList<Cannon> getCannons()
    {
        ArrayList<Cannon> cannons = new ArrayList<>();
        Cursor c = db.rawQuery("select * from cannons", new String[]{});
        try {
            c.moveToFirst();
            while (!c.isAfterLast()) {

                int id = c.getInt(0);
                int attachX = c.getInt(1);
                int attachY = c.getInt(2);
                int emitX = c.getInt(3);
                int emitY = c.getInt(4);
                String image = c.getString(5);
                int imageWidth = c.getInt(6);
                int imageHeight = c.getInt(7);
                String attackImage = c.getString(8);
                int attackImageWidth = c.getInt(9);
                int attackImageHeight = c.getInt(10);
                String attackSound = c.getString(11);
                int damage = c.getInt(12);
                Projectile p = new Projectile(0,attackImage, attackImageWidth, attackImageHeight, attackSound, damage);
                Cannon cn = new Cannon(id, image, imageWidth, imageHeight, attachX, attachY, emitX, emitY, p);
                cannons.add(cn);

                c.moveToNext();

            }
        } finally {
            c.close();
        }

        return cannons;
    }

    /**
     *
     * @return - ArrayList of ExtraParts
     */
    public ArrayList<ExtraPart> getExtraParts()
    {
        ArrayList<ExtraPart> extraParts = new ArrayList<>();
        Cursor c = db.rawQuery("select * from extra_parts", new String[]{});
        try {
            c.moveToFirst();
            while (!c.isAfterLast()) {

                int id = c.getInt(0);
                int attachX = c.getInt(1);
                int attachY = c.getInt(2);
                String image = c.getString(3);
                int imageWidth = c.getInt(4);
                int imageHeight = c.getInt(5);
                ExtraPart ep = new ExtraPart(id, image, imageWidth, imageHeight, attachX, attachY);
                extraParts.add(ep);

                c.moveToNext();

            }
        } finally {
            c.close();
        }

        return extraParts;
    }

    /**
     *
     * @return - ArrayList of Engines
     */
    public ArrayList<Engine> getEngines()
    {
        ArrayList<Engine> engines= new ArrayList<>();
        Cursor c = db.rawQuery("select * from engines", new String[]{});
        try {
            c.moveToFirst();
            while (!c.isAfterLast()) {

                int id = c.getInt(0);
                int baseSpeed = c.getInt(1);
                int baseTurnRate = c.getInt(2);
                int attachX = c.getInt(3);
                int attachY = c.getInt(4);
                String image = c.getString(5);
                int imageWidth = c.getInt(6);
                int imageHeight = c.getInt(7);
                Engine e = new Engine(id, baseSpeed, baseTurnRate, attachX, attachY, image, imageWidth, imageHeight);
                engines.add(e);

                c.moveToNext();

            }
        } finally {
            c.close();
        }

        return engines;
    }

    /**
     *
     * @return - ArrayList of PowerCores
     */
    public ArrayList<PowerCore> getPowerCores()
    {
        ArrayList<PowerCore> powerCores = new ArrayList<>();
        Cursor c = db.rawQuery("select * from power_cores", new String[]{});
        try {
            c.moveToFirst();
            while (!c.isAfterLast()) {

                int id = c.getInt(0);
                int cannonBoost = c.getInt(1);
                int engineBoost = c.getInt(2);
                String image = c.getString(3);
                PowerCore pc = new PowerCore(id, cannonBoost, engineBoost, image);
                powerCores.add(pc);

                c.moveToNext();

            }
        } finally {
            c.close();
        }

        return powerCores;
    }

    public ArrayList<BackgroundObject> getAllBgObjects()
    {
        ArrayList<BackgroundObject> objects= new ArrayList<>();
        Cursor c = db.rawQuery("select * from bg_objects", new String[]{});
        try {
            c.moveToFirst();
            while (!c.isAfterLast()) {

                int id = c.getInt(0);
                String image = c.getString(1);
                BackgroundObject obj = new BackgroundObject(id,image,0,0,0,0);
                objects.add(obj);
                c.moveToNext();
            }
        } finally {
            c.close();
        }

        return objects;
    }

    public Level getSpecificLevel(int level)
    {
        Level l = null;
        Cursor c = db.rawQuery("select * from levels where number = ?", new String[]{Integer.toString(level)});
        try {
            c.moveToFirst();
            while (!c.isAfterLast()) {

                int id = c.getInt(0);
                String title = c.getString(1);
                String hint = c.getString(2);
                int width = c.getInt(3);
                int height = c.getInt(4);
                String music = c.getString(5);
                ArrayList<BackgroundObject> lobjs = getLevelObjects(level);
                ArrayList<LevelAsteroids> lastds = getLevelAsteroids(level);
                l = new Level(id,title,hint,width,height,music,lobjs,lastds);
                c.moveToNext();
            }
        } finally {
            c.close();
        }

        return l;
    }

    /**
     * Removes Everything in DataBase
     */
    public void clearAll()
    {
        dbOpenHelper.deleteTables(db);
    }

    /**
     * Removes given object from database
     * @param object - object to remove
     */
    public void removeObject(JSONObject object)
    {

    }
}
