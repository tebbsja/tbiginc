package edu.byu.cs.superasteroids.core.database;

import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.test.AndroidTestCase;

import java.util.ArrayList;

import edu.byu.cs.superasteroids.database.Database;
import edu.byu.cs.superasteroids.database.DbOpenHelper;
import edu.byu.cs.superasteroids.model.BackgroundObject;
import edu.byu.cs.superasteroids.model.Cannon;
import edu.byu.cs.superasteroids.model.GrowingAsteroid;
import edu.byu.cs.superasteroids.model.Level;
import edu.byu.cs.superasteroids.model.LevelAsteroids;
import edu.byu.cs.superasteroids.model.MainBody;
import edu.byu.cs.superasteroids.model.PowerCore;
import edu.byu.cs.superasteroids.model.Projectile;
import edu.byu.cs.superasteroids.model.RegularAsteroid;

/**
 * Created by Austin on 11/4/16.
 */

public class DAO extends AndroidTestCase {

    public ArrayList<String> bgObjs = new ArrayList<>();
    public void initialize()
    {
        bgObjs.add("images/planet1.png");
        bgObjs.add("images/planet2.png");
        bgObjs.add("images/planet3.png");
        bgObjs.add("images/planet4.png");
        bgObjs.add("images/planet5.png");
        bgObjs.add("images/planet6.png");
        bgObjs.add("images/station.png");
        bgObjs.add("images/nebula1.png");
        bgObjs.add("images/nebula2.png");
        bgObjs.add("images/nebula3.png");
        bgObjs.add("images/nebula4.png");
        bgObjs.add("images/nebula5.png");
    }



    //added an object, now test if it has correct information
    public void testAddObject()
    {    //added an object, now test if it has correct information
        Database.getInstanceOf(getContext()).ClearAll();
        BackgroundObject bgObj = new BackgroundObject();
        bgObj.setImagePath("testing");
        Database.getInstance().getDao().addBackgroundObject(bgObj);
        ArrayList<BackgroundObject> objects = Database.getInstanceOf(getContext()).getDao().getAllBgObjects();
        //added an object, now test if it has correct information
        assertEquals(objects.size(), 1);
        assertEquals(objects.get(0).getImagePath(), "testing");
        assertEquals(objects.get(0).getId(), 1);

    }
    //try to add many
    public void testAddObjects()
    {
        Database.getInstanceOf(getContext()).ClearAll();
        initialize();
        for (int i=0; i < bgObjs.size(); i++)
        {
            BackgroundObject bgObj = new BackgroundObject();
            bgObj.setImagePath(bgObjs.get(i));
            Database.getInstance().getDao().addBackgroundObject(bgObj);

        }

        ArrayList<BackgroundObject> objects = Database.getInstanceOf(getContext()).getDao().getAllBgObjects();
        //added an object, now test if it has correct information
        assertEquals(objects.size(), 12);
        assertEquals(objects.get(2).getImagePath(), "images/planet3.png");
        assertEquals(objects.get(0).getId(), 1);
    }

    //add a complex object
    public void testAddJSONObj()
    {
        Database.getInstanceOf(getContext()).ClearAll();
        Level l = new Level();
        l.setLevelNum(1);
        l.setTitle("Level 1");
        l.setHint("Destroy 1 Asteroid");
        l.setWidth(3000);
        l.setHeight(3000);
        l.setMusicPath("sounds/SpyHunter.ogg");

        ArrayList<BackgroundObject> bgObjs = new ArrayList<>();
        BackgroundObject bg = new BackgroundObject();
        bg.setX(1000);
        bg.setY(1000);
        bg.setScale((float)1.5);
        bg.setId(1);
        bg.setLevelId(1);
        bg.setImagePath("images/planet1.png");
        bgObjs.add(bg);
        l.setLevelObjects(bgObjs);

        ArrayList<LevelAsteroids> lvlAstds = new ArrayList<>();
        LevelAsteroids la = new LevelAsteroids();
        la.setLevel_id(1);
        la.setAsteroidID(1);
        la.setAsteroidType(new RegularAsteroid());
        la.setNumAsteroids(4);
        lvlAstds.add(la);

        LevelAsteroids las = new LevelAsteroids();
        las.setLevel_id(1);
        las.setAsteroidID(2);
        las.setAsteroidType(new GrowingAsteroid());
        las.setNumAsteroids(4);
        lvlAstds.add(las);

        l.setLevelAsteroids(lvlAstds);

        Database.getInstance().getDao().addLevel(l);
        ArrayList<Level> levels = Database.getInstance().getDao().getLevels();

        assertEquals(levels.size(),1);
        assertEquals(levels.get(0).getLevelAsteroids().size(), 2);
        assertEquals(levels.get(0).getLevelObjects().get(0).getScale(), 1.5f);
        assertEquals(levels.get(0).getWidth(), 3000);

    }
    //test getting objects
    //first add powercores

    public void testGettingPowerCores()
    {
        Database.getInstanceOf(getContext()).ClearAll();
        PowerCore p = new PowerCore(0,10,10,"images/Ellipse.png");
        PowerCore p1 = new PowerCore(1,10,10, "images/Triangle.png");

        Database.getInstance().getDao().addPowerCores(p);
        Database.getInstance().getDao().addPowerCores(p1);

        ArrayList<PowerCore> powerCores = Database.getInstanceOf(getContext()).getDao().getPowerCores();
        //added an object, now test if we got them back
        assertEquals(powerCores.size(), 2);
        assertEquals(powerCores.get(0).getImagePath(), "images/Ellipse.png");
        assertEquals(powerCores.get(1).getImagePath(), "images/Triangle.png");
        assertEquals(powerCores.get(0).getCannonBoost(), 10);
        assertEquals(powerCores.get(1).getEngineBoost(), 10);
    }

    //test a couple more objects

    public void testCannon()
    {
        Database.getInstanceOf(getContext()).ClearAll();
        Cannon c = new Cannon();
        Cannon c1 = new Cannon();
        c.setAttachPointX(100);
        c.setAttachPointY(50);
        c1.setAttachPointX(20);
        c1.setAttachPointY(40);
        c.setImagePath("yes");
        c1.setImagePath("no");
        c.setHeight(2);
        c.setWidth(24);
        c1.setHeight(4);
        c1.setWidth(42);
        Projectile p = new Projectile(0, "laser", 100, 400, "sound", 5);
        c.setProjectile(p);
        c1.setProjectile(p);
        Database.getInstance().getDao().addCannons(c);
        Database.getInstance().getDao().addCannons(c1);

        ArrayList<Cannon> cannons = Database.getInstanceOf(getContext()).getDao().getCannons();
        //added an object, now test if we got them back
        assertEquals(cannons.size(), 2);
        assertEquals(cannons.get(0).getImagePath(), "yes");
        assertEquals(cannons.get(1).getImagePath(), "no");
        assertEquals(cannons.get(0).getProjectile().getImagePath(), "laser");
    }

    public void testMainBodies()
    {
        Database.getInstanceOf(getContext()).ClearAll();
        MainBody m = new MainBody();
        m.setAttachPointX(100);
        m.setAttachPointY(50);
        m.setImagePath("yes");
        m.setHeight(2);
        m.setWidth(24);
        m.setCannonAttachX(2);
        m.setCannonAttachY(3);
        m.setExtraAttachX(8);
        m.setExtraAttachY(9);
        m.setEngineAttachX(12);
        m.setEngineAttachY(14);
        Database.getInstance().getDao().addMainBodies(m);

        ArrayList<MainBody> parts = Database.getInstanceOf(getContext()).getDao().getMainBodies();
        //added an object, now test if we got them back
        assertEquals(parts.size(), 1);
        assertEquals(parts.get(0).getImagePath(), "yes");
        assertEquals(parts.get(0).getWidth(), 24);
        assertEquals(parts.get(0).getEngineAttachX(), 12);
    }

}
