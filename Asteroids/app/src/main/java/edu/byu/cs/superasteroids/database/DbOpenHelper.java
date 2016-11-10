package edu.byu.cs.superasteroids.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import edu.byu.cs.superasteroids.model.PowerCore;

/**
 * Created by Austin on 10/11/16.
 */

/**
 * The database open helper aides in opening the database
 */
public class DbOpenHelper extends SQLiteOpenHelper {
    /**
     * Data base name
     */
    private static final String DB_NAME = "asteroidGame.sqlite";
    /** Database Version */
    private static final int DB_VERSION = 1;

    /**
     * Constructor
     * @param context
     */
    public DbOpenHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    public void deleteTables(SQLiteDatabase db)
    {
        db.execSQL(DELETE_TABLE_BG_OBJ);
        db.execSQL(DELETE_TABLE_ASTEROID_TYPE);
        db.execSQL(DELETE_TABLE_LEVELS);
        db.execSQL(DELETE_TABLE_LEVEL_OBJECTS);
        db.execSQL(DELETE_TABLE_LEVEL_ASTEROIDS);
        db.execSQL(DELETE_TABLE_MAIN_BODIES);
        db.execSQL(DELETE_TABLE_CANNONS);
        db.execSQL(DELETE_TABLE_EXTRA_PARTS);
        db.execSQL(DELETE_TABLE_ENGINES);
        db.execSQL(DELETE_TABLE_POWER_CORES);

        recreateTables(db);
    }
    public void recreateTables(SQLiteDatabase db)
    {
        db.execSQL(CREATE_BG_OBJ_TABLE);
        db.execSQL(CREATE_ASTEROID_TYPE_TABLE);
        db.execSQL(CREATE_LEVELS_TABLE);
        db.execSQL(CREATE_LEVEL_OBJECTS_TABLE);
        db.execSQL(CREATE_LEVEL_ASTEROIDS_TABLE);
        db.execSQL(CREATE_MAIN_BODIES_TABLE);
        db.execSQL(CREATE_CANNONS_TABLE);
        db.execSQL(CREATE_EXTRA_PARTS_TABLE);
        db.execSQL(CREATE_ENGINES_TABLE);
        db.execSQL(CREATE_POWER_CORES_TABLE);
    }
    /**
     * Creates database
     * @param db - database to create
     */

    public void onCreate(SQLiteDatabase db)
    {

        db.execSQL(DELETE_TABLE_BG_OBJ);
        db.execSQL(DELETE_TABLE_ASTEROID_TYPE);
        db.execSQL(DELETE_TABLE_LEVELS);
        db.execSQL(DELETE_TABLE_LEVEL_OBJECTS);
        db.execSQL(DELETE_TABLE_LEVEL_ASTEROIDS);
        db.execSQL(DELETE_TABLE_MAIN_BODIES);
        db.execSQL(DELETE_TABLE_CANNONS);
        db.execSQL(DELETE_TABLE_EXTRA_PARTS);
        db.execSQL(DELETE_TABLE_ENGINES);
        db.execSQL(DELETE_TABLE_POWER_CORES);

        db.execSQL(CREATE_BG_OBJ_TABLE);
        db.execSQL(CREATE_ASTEROID_TYPE_TABLE);
        db.execSQL(CREATE_LEVELS_TABLE);
        db.execSQL(CREATE_LEVEL_OBJECTS_TABLE);
        db.execSQL(CREATE_LEVEL_ASTEROIDS_TABLE);
        db.execSQL(CREATE_MAIN_BODIES_TABLE);
        db.execSQL(CREATE_CANNONS_TABLE);
        db.execSQL(CREATE_EXTRA_PARTS_TABLE);
        db.execSQL(CREATE_ENGINES_TABLE);
        db.execSQL(CREATE_POWER_CORES_TABLE);

    }

    /**
     * default upgrade
     * @param db - databse
     * @param oldVersion
     * @param newVersion
     */
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        return;
    }

    /**
     * This will be the SQL string that creates all the necessary tables
     */
    public static final String CREATE_BG_OBJ_TABLE =
            "CREATE TABLE IF NOT EXISTS bg_objects " +
                    "(" +
                    "   id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "   image TEXT" +
                    ")";

    public static final String CREATE_ASTEROID_TYPE_TABLE =
            "CREATE TABLE IF NOT EXISTS asteroid_type " +
                    "(" +
                    "   id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    "   name TEXT NOT NULL," +
                    "   image TEXT NOT NULL," +
                    "   imageWidth INTEGER NOT NULL," +
                    "   imageHeight INTEGER NOT NULL," +
                    "   type TEXT NOT NULL" +
                    ")";

    public static final String CREATE_LEVELS_TABLE =
            "CREATE TABLE IF NOT EXISTS levels " +
                    "(" +
                    "   number INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    "   title TEXT NOT NULL," +
                    "   hint TEXT NOT NULL," +
                    "   width INTEGER NOT NULL," +
                    "   height INTEGER NOT NULL," +
                    "   music TEXT NOT NULL" +
                    ")";

    public static final String CREATE_LEVEL_OBJECTS_TABLE =
            "CREATE TABLE IF NOT EXISTS level_objects " +
                    "(" +
                    "   id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "   position_x INTEGER," +
                    "   position_y INTEGER," +
                    "   scale FLOAT," +
                    "   bg_object_id INTEGER," +
                    "   level_id INTEGER" +
                    ")";

    public static final String CREATE_LEVEL_ASTEROIDS_TABLE =
            "CREATE TABLE IF NOT EXISTS level_asteroids " +
                    "(" +
                    "   id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "   asteroid_id INTEGER NOT NULL," +
                    "   number INTEGER NOT NULL," +
                    "   level_id INTEGER NOT NULL" +
                    ")";

    public static final String CREATE_MAIN_BODIES_TABLE =
            "CREATE TABLE IF NOT EXISTS main_bodies " +
                    "(" +
                    "   id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    "   cannon_attach_x INTEGER NOT NULL," +
                    "   cannon_attach_y INTEGER NOT NULL," +
                    "   engine_attach_x INTEGER NOT NULL," +
                    "   engine_attach_y INTEGER NOT NULL," +
                    "   extra_attach_x INTEGER NOT NULL," +
                    "   extra_attach_y INTEGER NOT NULL," +
                    "   image TEXT NOT NULL," +
                    "   image_width INTEGER NOT NULL," +
                    "   image_height INTEGER NOT NULL" +
                    ")";

    public static final String CREATE_CANNONS_TABLE =
            "CREATE TABLE IF NOT EXISTS cannons " +
                    "(" +
                    "   id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    "   attach_point_x INTEGER NOT NULL," +
                    "   attach_point_y INTEGER NOT NULL," +
                    "   emit_point_x INTEGER NOT NULL," +
                    "   emit_point_y INTEGER NOT NULL," +
                    "   image TEXT NOT NULL," +
                    "   image_width INTEGER NOT NULL," +
                    "   image_height INTEGER NOT NULL," +
                    "   attack_image TEXT NOT NULL," +
                    "   attack_image_width INTEGER NOT NULL," +
                    "   attack_image_height INTEGER NOT NULL," +
                    "   attack_sound TEXT NOT NULL," +
                    "   damage INTEGER NOT NULL" +
                    ")";

    public static final String CREATE_EXTRA_PARTS_TABLE =
            "CREATE TABLE IF NOT EXISTS extra_parts " +
                    "(" +
                    "   id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    "   attach_point_x INTEGER NOT NULL," +
                    "   attach_point_y INTEGER NOT NULL," +
                    "   image TEXT NOT NULL," +
                    "   image_width INTEGER NOT NULL," +
                    "   image_height INTEGER NOT NULL" +
                    ")";

    public static final String CREATE_ENGINES_TABLE =
            "CREATE TABLE IF NOT EXISTS engines " +
                    "(" +
                    "   id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    "   base_speed INTEGER NOT NULL," +
                    "   base_turn_rate INTEGER NOT NULL," +
                    "   attach_point_x INTEGER NOT NULL," +
                    "   attach_point_y INTEGER NOT NULL," +
                    "   image TEXT NOT NULL," +
                    "   image_width INTEGER NOT NULL," +
                    "   image_height INTEGER NOT NULL" +
                    ")";

    public static final String CREATE_POWER_CORES_TABLE =
            "CREATE TABLE IF NOT EXISTS power_cores " +
                    "(" +
                    "   id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    "   cannon_boost INTEGER NOT NULL," +
                    "   engine_boost INTEGER NOT NULL," +
                    "   image TEXT NOT NULL" +
                    ")";

    public static final String DELETE_TABLE_BG_OBJ = "DROP TABLE IF EXISTS bg_objects;";
    public static final String DELETE_TABLE_ASTEROID_TYPE = "DROP TABLE IF EXISTS asteroid_type;";
    public static final String DELETE_TABLE_LEVELS ="DROP TABLE IF EXISTS levels;";
    public static final String DELETE_TABLE_LEVEL_OBJECTS = "DROP TABLE IF EXISTS level_objects;";
    public static final String DELETE_TABLE_LEVEL_ASTEROIDS = "DROP TABLE IF EXISTS level_asteroids;";
    public static final String DELETE_TABLE_MAIN_BODIES = "DROP TABLE IF EXISTS main_bodies;";
    public static final String DELETE_TABLE_CANNONS =  "DROP TABLE IF EXISTS cannons;";
    public static final String DELETE_TABLE_EXTRA_PARTS =  "DROP TABLE IF EXISTS extra_parts;";
    public static final String DELETE_TABLE_ENGINES =   "DROP TABLE IF EXISTS engines;";
    public static final String DELETE_TABLE_POWER_CORES =   "DROP TABLE IF EXISTS power_cores;";

}
