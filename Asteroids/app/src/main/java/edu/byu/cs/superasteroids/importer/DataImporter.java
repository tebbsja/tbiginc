package edu.byu.cs.superasteroids.importer;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import edu.byu.cs.superasteroids.database.DAO;
import edu.byu.cs.superasteroids.game_objects.AsteroidGame;
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
 * Created by Austin on 10/19/16.
 */
public class DataImporter implements IGameDataImporter {


    /**
     * Imports the data from the .json file the given InputStreamReader is connected to. Imported data
     * should be stored in a SQLite database for use in the ship builder and the game.
     * @param dataInputReader The InputStreamReader connected to the .json file needing to be imported.
     * @return TRUE if the data was imported successfully, FALSE if the data was not imported due
     * to any error.
     */

    private static boolean firstImport = true;
    public static boolean uploaded = false;

    @Override
    public boolean importData(InputStreamReader dataInputReader) throws IOException {

        boolean added = true;


        if(!firstImport)
        {
            AsteroidGame.SINGLETON.unload();
            DAO.SINGLETON.clearAll();
        }



        try {

                JSONObject rootObject = new JSONObject(makeString(dataInputReader));
                JSONObject asteroidGameData = rootObject.getJSONObject("asteroidsGame");
                JSONArray objects = asteroidGameData.getJSONArray("objects");

                for (int i = 0; i < objects.length(); i++) {

                    BackgroundObject bgObj = new BackgroundObject(i, objects.getString(i), 0, 0, 0, 0);
                    added = DAO.SINGLETON.addBackgroundObject(bgObj);
                    if (added == false) {
                        return false;
                    }
                }


                JSONArray asteroidType = asteroidGameData.getJSONArray("asteroids");
                for (int i = 0; i < asteroidType.length(); i++) {

                    JSONObject asType = asteroidType.getJSONObject(i);
                    String name = asType.getString("name");

                    String image = asType.getString("image");
                    int width = asType.getInt("imageWidth");
                    int height = asType.getInt("imageHeight");
                    String type = asType.getString("type");

                    AsteroidType temp = new AsteroidType(i, name, image, width, height, type);
                    added = DAO.SINGLETON.addAsteroid(temp);
                    if (added == false) {
                        return false;
                    }
                }

                JSONArray levels = asteroidGameData.getJSONArray("levels");
                for (int i = 0; i < levels.length(); i++) {
                    JSONObject lvl = levels.getJSONObject(i);
                    int number = lvl.getInt("number");
                    String title = lvl.getString("title");
                    String hint = lvl.getString("hint");
                    int width = lvl.getInt("width");
                    int height = lvl.getInt("height");
                    String music = lvl.getString("music");

                    JSONArray lvlObj = lvl.getJSONArray("levelObjects");
                    ArrayList<BackgroundObject> lobjs = new ArrayList<>();
                    for (int j = 0; j < lvlObj.length(); j++) {
                        JSONObject temp = lvlObj.getJSONObject(j);
                        String position = temp.getString("position");
                        String[] posA = position.split(",");
                        int x = Integer.parseInt(posA[0]);
                        int y = Integer.parseInt(posA[1]);
                        int objectID = temp.getInt("objectId");
                        String scales = temp.getString("scale");
                        float scale = Float.parseFloat(scales);
                        BackgroundObject bg = new BackgroundObject(objectID, null, x, y, scale, i + 1);
                        lobjs.add(bg);
                    }

                    JSONArray lvlAst = lvl.getJSONArray("levelAsteroids");
                    ArrayList<LevelAsteroids> lasts = new ArrayList<>();
                    for (int k = 0; k < lvlAst.length(); k++) {
                        JSONObject temp = lvlAst.getJSONObject(k);
                        int numberAst = temp.getInt("number");
                        int asteroidId = temp.getInt("asteroidId");
                        LevelAsteroids la = new LevelAsteroids(k, numberAst, asteroidId, i + 1, null);
                        lasts.add(la);
                    }

                    Level l = new Level(number, title, hint, width, height, music, lobjs, lasts);
                    added = DAO.SINGLETON.addLevel(l);
                    if (added == false) {
                        return false;
                    }

                }

                JSONArray mainBodies = asteroidGameData.getJSONArray("mainBodies");
                for (int i = 0; i < mainBodies.length(); i++) {
                    JSONObject mb = mainBodies.getJSONObject(i);
                    String cannonAttach = mb.getString("cannonAttach");
                    String[] ca = cannonAttach.split(",");
                    int cannonX = Integer.parseInt(ca[0]);
                    int cannonY = Integer.parseInt(ca[1]);

                    String engineAttach = mb.getString("engineAttach");
                    String[] ea = engineAttach.split(",");
                    int engineX = Integer.parseInt(ea[0]);
                    int engineY = Integer.parseInt(ea[1]);

                    String extraAttach = mb.getString("extraAttach");
                    String[] xa = extraAttach.split(",");
                    int extraX = Integer.parseInt(xa[0]);
                    int extraY = Integer.parseInt(xa[1]);

                    String image = mb.getString("image");
                    int imageWidth = mb.getInt("imageWidth");
                    int imageHeight = mb.getInt("imageHeight");

                    MainBody m = new MainBody(i, cannonX, cannonY, engineX, engineY, extraX, extraY, image, imageWidth, imageHeight);
                    added = DAO.SINGLETON.addMainBodies(m);
                    if (added == false) {
                        return false;
                    }
                }


                JSONArray cannons = asteroidGameData.getJSONArray("cannons");
                for (int i = 0; i < cannons.length(); i++) {
                    JSONObject c = cannons.getJSONObject(i);
                    String attachPoint = c.getString("attachPoint");
                    String[] ap = attachPoint.split(",");
                    int attachX = Integer.parseInt(ap[0]);
                    int attachY = Integer.parseInt(ap[1]);

                    String emitPoint = c.getString("emitPoint");
                    String[] ep = emitPoint.split(",");
                    int emitX = Integer.parseInt(ep[0]);
                    int emitY = Integer.parseInt(ep[1]);

                    String image = c.getString("image");
                    int imageWidth = c.getInt("imageWidth");
                    int imageHeight = c.getInt("imageHeight");

                    String attackImage = c.getString("attackImage");
                    int attackImageWidth = c.getInt("attackImageWidth");
                    int attackImageHeight = c.getInt("attackImageHeight");
                    String attackSound = c.getString("attackSound");
                    int damage = c.getInt("damage");

                    Projectile p = new Projectile(0, attackImage, attackImageWidth, attackImageHeight, attackSound, damage);
                    Cannon cn = new Cannon(i, image, imageWidth, imageHeight, attachX, attachY, emitX, emitY, p);
                    added = DAO.SINGLETON.addCannons(cn);
                    if (added == false) {
                        return false;
                    }
                }

                JSONArray extraParts = asteroidGameData.getJSONArray("extraParts");
                for (int i = 0; i < extraParts.length(); i++) {
                    JSONObject e = extraParts.getJSONObject(i);
                    String attachPoint = e.getString("attachPoint");
                    String[] ap = attachPoint.split(",");
                    int attachX = Integer.parseInt(ap[0]);
                    int attachY = Integer.parseInt(ap[1]);


                    String image = e.getString("image");
                    int imageWidth = e.getInt("imageWidth");
                    int imageHeight = e.getInt("imageHeight");

                    ExtraPart ep = new ExtraPart(i, image, imageWidth, imageHeight, attachX, attachY);
                    added = DAO.SINGLETON.addExtraParts(ep);
                    if (added == false) {
                        return false;
                    }
                }


                JSONArray engines = asteroidGameData.getJSONArray("engines");
                for (int i = 0; i < engines.length(); i++) {
                    JSONObject e = engines.getJSONObject(i);
                    int baseSpeed = e.getInt("baseSpeed");
                    int baseTurnRate = e.getInt("baseTurnRate");
                    String attachPoint = e.getString("attachPoint");
                    String[] ap = attachPoint.split(",");
                    int attachX = Integer.parseInt(ap[0]);
                    int attachY = Integer.parseInt(ap[1]);
                    String image = e.getString("image");
                    int imageWidth = e.getInt("imageWidth");
                    int imageHeight = e.getInt("imageHeight");

                    Engine en = new Engine(i, baseSpeed, baseTurnRate, attachX, attachY, image, imageWidth, imageHeight);
                    added = DAO.SINGLETON.addEngines(en);
                    if (added == false) {
                        return false;
                    }
                }


                JSONArray powerCores = asteroidGameData.getJSONArray("powerCores");
                for (int i = 0; i < powerCores.length(); i++) {
                    JSONObject p = powerCores.getJSONObject(i);
                    int cannonBoost = p.getInt("cannonBoost");
                    int engineBoost = p.getInt("engineBoost");
                    String image = p.getString("image");

                    PowerCore pc = new PowerCore(i, cannonBoost, engineBoost, image);
                    added = DAO.SINGLETON.addPowerCores(pc);
                    if (added == false) {
                        return false;
                    }
                }


            } catch (JSONException e) {
                uploaded = false;
                e.printStackTrace();
                throw new IOException(e.toString());
        }

        firstImport = false;




        uploaded = true;
        return true;

    }

    private String makeString(InputStreamReader input) throws IOException
    {
        BufferedReader br = new BufferedReader(input);
        String s = br.readLine();
        StringBuilder sb = new StringBuilder();

        while (s != null)
        {
            sb.append(s);
            s = br.readLine();
        }
        return sb.toString();
    }

}
