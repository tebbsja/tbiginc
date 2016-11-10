package edu.byu.cs.superasteroids.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.io.InputStreamReader;

/**
 * Created by Austin on 10/11/16.
 */
public class Database {
    private DbOpenHelper dbOpenHelper;
    private SQLiteDatabase database;
    private DAO dao;
    private Context context;
    private static Database instance;

    public Database(Context baseContext)
    {
        this.context = baseContext;
        dbOpenHelper = new DbOpenHelper(baseContext);
        database = dbOpenHelper.getWritableDatabase();
        dao = new DAO(database);
    }

    public static Database getInstanceOf(Context context)
    {
        if (instance == null)
        {
           instance = new Database(context);
        }

        return instance;

    }

    public static Database getInstance()
    {
        return instance;
    }


    /**
     * Calls ClearAll on DAO
     */
    public void ClearAll()
    {
        database.execSQL(DELETE_TABLE_BG_OBJ);
        database.execSQL(DELETE_TABLE_ASTEROID_TYPE);
        database.execSQL(DELETE_TABLE_LEVELS);
        database.execSQL(DELETE_TABLE_LEVEL_OBJECTS);
        database.execSQL(DELETE_TABLE_LEVEL_ASTEROIDS);
        database.execSQL(DELETE_TABLE_MAIN_BODIES);
        database.execSQL(DELETE_TABLE_CANNONS);
        database.execSQL(DELETE_TABLE_EXTRA_PARTS);
        database.execSQL(DELETE_TABLE_ENGINES);
        database.execSQL(DELETE_TABLE_POWER_CORES);

        database.execSQL(CREATE_BG_OBJ_TABLE);
        database.execSQL(CREATE_ASTEROID_TYPE_TABLE);
        database.execSQL(CREATE_LEVELS_TABLE);
        database.execSQL(CREATE_LEVEL_OBJECTS_TABLE);
        database.execSQL(CREATE_LEVEL_ASTEROIDS_TABLE);
        database.execSQL(CREATE_MAIN_BODIES_TABLE);
        database.execSQL(CREATE_CANNONS_TABLE);
        database.execSQL(CREATE_EXTRA_PARTS_TABLE);
        database.execSQL(CREATE_ENGINES_TABLE);
        database.execSQL(CREATE_POWER_CORES_TABLE);
    }

    /**
     * Returns DbOpenHelper
     * @return DbOpenHelper
     */
    public DbOpenHelper getDbOpenHelper()
    {
        return dbOpenHelper;
    }

    /**
     *
     * @return - DAO
     */
    public DAO getDao()
    {
        return dao;
    }

    /**
     *
     * @return - Context
     */
    public Context getContext()
    {
        return context;
    }

    /**
     *
     * @param inputStreamReader - pointer to input file from which JSON object comes
     */
    public void importData(InputStreamReader inputStreamReader)
    {

    }

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
