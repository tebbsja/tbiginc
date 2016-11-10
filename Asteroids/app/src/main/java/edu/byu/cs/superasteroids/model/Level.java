package edu.byu.cs.superasteroids.model;

/**
 * Created by Austin on 10/11/16.
 */

import java.util.ArrayList;

/**
 * Contains information to create a level
 */
public class Level {
    private int level_num;
    private String title;
    private String hint;
    private int width;
    private int height;
    private String music_path;
    private ArrayList<BackgroundObject> levelObjects;
    private ArrayList<LevelAsteroids> levelAsteroids;

    /**
     * Default Constructor
     */
    public Level(){}

    /**
     *
     * @param num - level number
     * @param title - level title
     * @param hint - level hint
     * @param width - width of level
     * @param height - height of level
     * @param music_path - path to the music of the level
     * @param levelObjects - array of level objects
     * @param levelAsteroids - array of level asteroids
     */
    public Level(int num, String title, String hint, int width, int height, String music_path, ArrayList<BackgroundObject> levelObjects, ArrayList<LevelAsteroids> levelAsteroids)
    {
        this.level_num = num;
        this.title = title;
        this.hint = hint;
        this.width = width;
        this.height = height;
        this.music_path = music_path;
        this.levelObjects = levelObjects;
        this.levelAsteroids = levelAsteroids;
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("NUMBER " + getLevelNum() + "| TITLE " + getTitle() + "| HINT " + getHint() + "| WIDTH " + Integer.toString(getWidth()) + "| HEIGHT " + Integer.toString(getHeight()) + "| MUSIC " + getMusicPath());

        for(BackgroundObject lvObj : getLevelObjects())
        {
            sb.append("\n");
            sb.append("\t");
            sb.append("LEVEL OBJECTS: " + lvObj.toString());
        }
        for(LevelAsteroids lvAst : getLevelAsteroids())
        {
            sb.append("\n");
            sb.append("\t");
            sb.append("LEVEL ASTEROIDS: " + lvAst.toString());
        }
        return sb.toString();
    }

    public void setLevelNum(int n)
    {
        this.level_num = n;
    }
    public int getLevelNum()
    {
        return this.level_num;
    }
    public void setTitle(String t)
    {
        this.title = t;
    }
    public String getTitle()
    {
        return this.title;
    }
    public void setHint(String h)
    {
        this.hint = h;
    }
    public String getHint()
    {
        return this.hint;
    }
    public void setWidth(int w)
    {
        this.width = w;
    }
    public int getWidth()
    {
        return this.width;
    }
    public void setHeight(int h)
    {
        this.height = h;
    }
    public int getHeight()
    {
        return this.height;
    }
    public void setMusicPath(String path)
    {
        this.music_path = path;
    }
    public String getMusicPath()
    {
        return this.music_path;
    }
    public void setLevelObjects(ArrayList<BackgroundObject> obj)
    {
        this.levelObjects = obj;
    }
    public ArrayList<BackgroundObject> getLevelObjects()
    {
        return this.levelObjects;
    }
    public void setLevelAsteroids(ArrayList<LevelAsteroids> obj)
    {
        this.levelAsteroids = obj;
    }
    public ArrayList<LevelAsteroids> getLevelAsteroids()
    {
        return this.levelAsteroids;
    }

}
