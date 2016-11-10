package edu.byu.cs.superasteroids.ship_builder;

import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import edu.byu.cs.superasteroids.base.IView;
import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.core.GraphicsUtils;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.game_objects.AsteroidGame;
import edu.byu.cs.superasteroids.game_objects.Ship;
import edu.byu.cs.superasteroids.game_objects.ViewPort;
import edu.byu.cs.superasteroids.model.*;

/**
 * Created by Austin on 10/28/16.
 */

public class ShipBuildingController implements IShipBuildingController {

    private HashMap<Integer, MainBody> mainBodyMap;
    private HashMap<Integer, Cannon> cannnonMap;
    private HashMap<Integer, ExtraPart> extraPartMap;
    private HashMap<Integer, Engine> engineMap;
    private HashMap<Integer, PowerCore> powerCoreMap;
    private ArrayList<Integer> mainBodyImgLoadedIDs;
    private ArrayList<Integer> cannonImgLoadedIds;
    private ArrayList<Integer> extraPartImgLoadedIds;
    private ArrayList<Integer> engineImgLoadedIds;
    private ArrayList<Integer> powerCoreImgLoadedIds;


    private IShipBuildingView currentView;
    private IShipBuildingView.PartSelectionView partView;

    private static boolean mainBody;
    private static boolean cannon;
    private static boolean extraPart;
    private static boolean engine;
    private static boolean powerCore;






    public ShipBuildingController(IShipBuildingView shipBuildingView)
    {
        AsteroidGame.SINGLETON.loadModels();
        currentView = shipBuildingView;

        mainBodyMap = new HashMap<>();
        cannnonMap = new HashMap<>();
        engineMap = new HashMap<>();
        extraPartMap = new HashMap<>();
        powerCoreMap = new HashMap<>();

        mainBodyImgLoadedIDs = new ArrayList<>();
        cannonImgLoadedIds = new ArrayList<>();
        engineImgLoadedIds = new ArrayList<>();
        extraPartImgLoadedIds = new ArrayList<>();
        powerCoreImgLoadedIds = new ArrayList<>();

        mainBody = false;
        cannon = false;
        extraPart = false;
        engine = false;
        powerCore = false;
    }

    /**
     * The ship building view calls this function when a part selection view is loaded. This function
     * should be used to configure the part selection view. Example: Set the arrows for the view in
     * this function.
     * @param partView
     */
    @Override
    public void onViewLoaded(IShipBuildingView.PartSelectionView partView) {
        switch (partView){
            case MAIN_BODY:
                this.partView = partView;
                currentView.setArrow(IShipBuildingView.PartSelectionView.MAIN_BODY, IShipBuildingView.ViewDirection.LEFT, false, "");
                currentView.setArrow(IShipBuildingView.PartSelectionView.MAIN_BODY, IShipBuildingView.ViewDirection.RIGHT, true, "Cannons");
                currentView.setArrow(IShipBuildingView.PartSelectionView.MAIN_BODY, IShipBuildingView.ViewDirection.UP, false, "MAIN BODIES");
                currentView.setArrow(IShipBuildingView.PartSelectionView.MAIN_BODY, IShipBuildingView.ViewDirection.DOWN, false, "");
                break;
            case CANNON:
                this.partView = partView;
                currentView.setArrow(IShipBuildingView.PartSelectionView.CANNON, IShipBuildingView.ViewDirection.LEFT, true, "Main Bodies");
                currentView.setArrow(IShipBuildingView.PartSelectionView.CANNON, IShipBuildingView.ViewDirection.RIGHT, true, "Extra Parts");
                currentView.setArrow(IShipBuildingView.PartSelectionView.CANNON, IShipBuildingView.ViewDirection.UP, false, "CANNONS");
                currentView.setArrow(IShipBuildingView.PartSelectionView.CANNON, IShipBuildingView.ViewDirection.DOWN, false, "");
                break;
            case EXTRA_PART:
                this.partView = partView;
                currentView.setArrow(IShipBuildingView.PartSelectionView.EXTRA_PART, IShipBuildingView.ViewDirection.LEFT, true, "Cannons");
                currentView.setArrow(IShipBuildingView.PartSelectionView.EXTRA_PART, IShipBuildingView.ViewDirection.RIGHT, true, "Engines");
                currentView.setArrow(IShipBuildingView.PartSelectionView.EXTRA_PART, IShipBuildingView.ViewDirection.UP, false, "EXTRA PARTS");
                currentView.setArrow(IShipBuildingView.PartSelectionView.EXTRA_PART, IShipBuildingView.ViewDirection.DOWN, false, "");
                break;
            case ENGINE:
                this.partView = partView;
                currentView.setArrow(IShipBuildingView.PartSelectionView.ENGINE, IShipBuildingView.ViewDirection.LEFT, true, "Extra Parts");
                currentView.setArrow(IShipBuildingView.PartSelectionView.ENGINE, IShipBuildingView.ViewDirection.RIGHT, true, "Power Cores");
                currentView.setArrow(IShipBuildingView.PartSelectionView.ENGINE, IShipBuildingView.ViewDirection.UP, false, "ENGINES");
                currentView.setArrow(IShipBuildingView.PartSelectionView.ENGINE, IShipBuildingView.ViewDirection.DOWN, false, "");
                break;
            case POWER_CORE:
                this.partView = partView;
                currentView.setArrow(IShipBuildingView.PartSelectionView.POWER_CORE, IShipBuildingView.ViewDirection.LEFT, true, "Engines");
                currentView.setArrow(IShipBuildingView.PartSelectionView.POWER_CORE, IShipBuildingView.ViewDirection.RIGHT, true, "Main Bodies");
                currentView.setArrow(IShipBuildingView.PartSelectionView.POWER_CORE, IShipBuildingView.ViewDirection.UP, false, "POWER CORES");
                currentView.setArrow(IShipBuildingView.PartSelectionView.POWER_CORE, IShipBuildingView.ViewDirection.DOWN, false, "");
                break;
        }

    }

    @Override
    public void loadContent(ContentManager content) {

        if(mainBodyImgLoadedIDs.size() == 0) {
            for (MainBody m : AsteroidGame.SINGLETON.getMainBodies()) {
                int id = content.loadImage(m.getImagePath());
                mainBodyImgLoadedIDs.add(id);
                m.setId(id);
                mainBodyMap.put(id, m);
            }
        }
        currentView.setPartViewImageList(IShipBuildingView.PartSelectionView.MAIN_BODY, mainBodyImgLoadedIDs);

        if(cannonImgLoadedIds.size() == 0) {
            for (Cannon c : AsteroidGame.SINGLETON.getCannons()) {
                int id = content.loadImage(c.getImagePath());
                cannonImgLoadedIds.add(id);
                c.setId(id);
                cannnonMap.put(id,c);
            }
        }
        currentView.setPartViewImageList(IShipBuildingView.PartSelectionView.CANNON, cannonImgLoadedIds);

        if(extraPartImgLoadedIds.size() == 0) {
            for (ExtraPart e : AsteroidGame.SINGLETON.getExtraParts()) {
                int id = content.loadImage(e.getImagePath());
                extraPartImgLoadedIds.add(id);
                e.setId(id);
                extraPartMap.put(id,e);
            }
        }
        currentView.setPartViewImageList(IShipBuildingView.PartSelectionView.EXTRA_PART, extraPartImgLoadedIds);
        if(engineImgLoadedIds.size() == 0) {
            for (Engine e : AsteroidGame.SINGLETON.getEngines()) {
                int id = content.loadImage(e.getImagePath());
                engineImgLoadedIds.add(id);
                e.setId(id);
                engineMap.put(id,e);
            }
        }
        currentView.setPartViewImageList(IShipBuildingView.PartSelectionView.ENGINE, engineImgLoadedIds);
        if(powerCoreImgLoadedIds.size() == 0) {
            for (PowerCore p : AsteroidGame.SINGLETON.getPowerCores()) {
                int id = content.loadImage(p.getImagePath());
                powerCoreImgLoadedIds.add(id);
                p.setId(id);
                powerCoreMap.put(id,p);
            }
        }
        currentView.setPartViewImageList(IShipBuildingView.PartSelectionView.POWER_CORE, powerCoreImgLoadedIds);



    }


    /**
     * The ShipBuildingView calls this function when the user makes a swipe/fling motion in the
     * screen. Respond to the user's swipe/fling motion in this function.
     * @param direction The direction of the swipe/fling.
     */
    @Override
    public void onSlideView(IShipBuildingView.ViewDirection direction) {
        switch(partView)
        {
            case MAIN_BODY:
                if(direction == IShipBuildingView.ViewDirection.LEFT)
                {
                    currentView.animateToView(IShipBuildingView.PartSelectionView.CANNON, IShipBuildingView.ViewDirection.RIGHT);
                }
                break;
            case CANNON:
                if(direction == IShipBuildingView.ViewDirection.LEFT)
                {
                    currentView.animateToView(IShipBuildingView.PartSelectionView.EXTRA_PART, IShipBuildingView.ViewDirection.RIGHT);
                }
                if(direction == IShipBuildingView.ViewDirection.RIGHT)
                {
                    currentView.animateToView(IShipBuildingView.PartSelectionView.MAIN_BODY, IShipBuildingView.ViewDirection.LEFT);
                }
                break;
            case EXTRA_PART:
                if(direction == IShipBuildingView.ViewDirection.LEFT)
                {
                    currentView.animateToView(IShipBuildingView.PartSelectionView.ENGINE, IShipBuildingView.ViewDirection.RIGHT);
                }
                if(direction == IShipBuildingView.ViewDirection.RIGHT)
                {
                    currentView.animateToView(IShipBuildingView.PartSelectionView.CANNON, IShipBuildingView.ViewDirection.LEFT);
                }
                break;
            case ENGINE:
                if(direction == IShipBuildingView.ViewDirection.LEFT)
                {
                    currentView.animateToView(IShipBuildingView.PartSelectionView.POWER_CORE, IShipBuildingView.ViewDirection.RIGHT);
                }
                if(direction == IShipBuildingView.ViewDirection.RIGHT)
                {
                    currentView.animateToView(IShipBuildingView.PartSelectionView.EXTRA_PART, IShipBuildingView.ViewDirection.LEFT);
                }
                break;
            case POWER_CORE:
                if(direction == IShipBuildingView.ViewDirection.LEFT)
                {
                    currentView.animateToView(IShipBuildingView.PartSelectionView.MAIN_BODY, IShipBuildingView.ViewDirection.RIGHT);
                }
                if(direction == IShipBuildingView.ViewDirection.RIGHT)
                {
                    currentView.animateToView(IShipBuildingView.PartSelectionView.ENGINE, IShipBuildingView.ViewDirection.LEFT);
                }
                break;
        }

    }
    /**
     * The part selection fragments call this function when a part is selected from the parts list. Respond
     * to the part selection in this function.
     * @param index The list index of the selected part.
     */
    @Override
    public void onPartSelected(int index) {

        if (isComplete())
        {
            currentView.setStartGameButton(true);
        }
        switch(partView)
        {
            case MAIN_BODY: {
                MainBody mb = mainBodyMap.get(mainBodyImgLoadedIDs.get(index));
                Ship.SINGLETON.setMainBody(mb);
                mainBody = true;
                currentView.setStartGameButton(isComplete());
                break;
            }
            case CANNON: {
                Cannon c = cannnonMap.get(cannonImgLoadedIds.get(index));
                Ship.SINGLETON.setCannon(c);
                cannon = true;
                currentView.setStartGameButton(isComplete());
                break;
            }
            case EXTRA_PART: {
                ExtraPart e = extraPartMap.get(extraPartImgLoadedIds.get(index));
                Ship.SINGLETON.setExtraPart(e);
                extraPart = true;
                currentView.setStartGameButton(isComplete());
                break;
            }
            case ENGINE: {
                Engine en = engineMap.get(engineImgLoadedIds.get(index));
                Ship.SINGLETON.setEngine(en);
                engine = true;
                currentView.setStartGameButton(isComplete());
                break;
            }
            case POWER_CORE: {
                PowerCore p = powerCoreMap.get(powerCoreImgLoadedIds.get(index));
                Ship.SINGLETON.setPowerCore(p);
                powerCore = true;
                currentView.setStartGameButton(isComplete());
                break;
            }
        }



    }
    public boolean isComplete()
    {
        if (mainBody && cannon && extraPart && engine && powerCore)
        {
            return true;
        }

        return false;
    }

    @Override
    public void onStartGamePressed() {
        currentView.startGame();
    }

    @Override
    public void onResume() {

    }

    @Override
    public IView getView() {
        return null;
    }

    @Override
    public void setView(IView view) {


    }

    @Override
    public void update(double elapsedTime) {

    }

    //This never is called???
    @Override
    public void unloadContent(ContentManager content) {
        Log.d("test", "are we calling unload? ");
        for (int i=0; i < mainBodyImgLoadedIDs.size();i++)
        {
            if (mainBodyImgLoadedIDs.get(i) != Ship.SINGLETON.getMainBody().getId())
            {
                //Log.d("test", "unload");
                //Log.d("test", Integer.toString(mainBodyImgLoadedIDs.get(i)));
            }
        }

    }

    @Override
    public void draw() {
        int xCenter = DrawingHelper.getGameViewWidth() / 2;
        int yCenter = (int) (DrawingHelper.getGameViewHeight() / 2.5);
        PointF pnt = new PointF(xCenter,yCenter);
        float scale = .35f;
        Ship.SINGLETON.setScale(scale);

        if (mainBody) {
            DrawingHelper.drawImage(Ship.SINGLETON.getMainBody().getId(), xCenter, yCenter, 0, scale, scale, 255 );
        }
        if (cannon) {
            PointF point = getCoordinatesPart(Ship.SINGLETON.getMainBody(), Ship.SINGLETON.getCannon(),pnt,scale);
            DrawingHelper.drawImage(Ship.SINGLETON.getCannon().getId(), point.x + xCenter, point.y + yCenter, 0, scale, scale, 255);
        }
        if (extraPart)
        {
            PointF point = getCoordinatesPart(Ship.SINGLETON.getMainBody(), Ship.SINGLETON.getExtraPart(),pnt,scale);
            DrawingHelper.drawImage(Ship.SINGLETON.getExtraPart().getId(), point.x + xCenter, point.y + yCenter, 0, scale, scale, 255);
        }
        if (engine) {
            PointF point = getCoordinatesPart(Ship.SINGLETON.getMainBody(), Ship.SINGLETON.getEngine(),pnt,scale);
            DrawingHelper.drawImage(Ship.SINGLETON.getEngine().getId(), point.x + xCenter, point.y + yCenter, 0, scale, scale, 255);
        }
    }

    public PointF getCoordinatesPart(MainBody body, VisibleShipParts part, PointF point, double scale)
    {
        PointF coordinates = new PointF(0,0);

        if (part.getClass() == Cannon.class)
        {
            coordinates.x = (body.getCannonAttachX() - body.getWidth() / 2) + (part.getWidth() / 2 - part.getAttachPointX());
            coordinates.y = (body.getCannonAttachY() - body.getHeight() / 2) + (part.getHeight() / 2 - part.getAttachPointY());
        }
        else if (part.getClass() == ExtraPart.class)
        {
            coordinates.x = (body.getExtraAttachX() - body.getWidth() / 2) + (part.getWidth() / 2 - part.getAttachPointX());
            coordinates.y = (body.getExtraAttachY() - body.getHeight() / 2) + (part.getHeight() / 2 - part.getAttachPointY());
        }
        else if (part.getClass() == Engine.class)
        {
            coordinates.x = (body.getEngineAttachX() - body.getWidth() / 2) + (part.getWidth() / 2 - part.getAttachPointX());
            coordinates.y = (body.getEngineAttachY() - body.getHeight() / 2) + (part.getHeight() / 2 - part.getAttachPointY());
        }

        coordinates.x = (float)(coordinates.x * scale);
        coordinates.y = (float)(coordinates.y * scale);

        return coordinates;

    }

}
