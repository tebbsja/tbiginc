package edu.byu.cs.superasteroids.main_menu;

import edu.byu.cs.superasteroids.base.IView;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.game_objects.AsteroidGame;
import edu.byu.cs.superasteroids.game_objects.Ship;
import edu.byu.cs.superasteroids.game_objects.ViewPort;
import edu.byu.cs.superasteroids.importer.DataImporter;

import android.util.Log;

/**
 * Created by Austin on 10/29/16.
 */

public class MainMenuController implements IMainMenuController {

    IMainMenuView view;
    public MainMenuController(IMainMenuView view) {
        this.view = view;
    }

    @Override
    public void onQuickPlayPressed() {
        AsteroidGame.SINGLETON.loadModels();

            Ship.SINGLETON.setMainBody(AsteroidGame.SINGLETON.getMainBodies().get(1));
            Ship.SINGLETON.setEngine(AsteroidGame.SINGLETON.getEngines().get(1));
            Ship.SINGLETON.setCannon(AsteroidGame.SINGLETON.getCannons().get(1));
            Ship.SINGLETON.setPowerCore(AsteroidGame.SINGLETON.getPowerCores().get(1));
            Ship.SINGLETON.setExtraPart(AsteroidGame.SINGLETON.getExtraParts().get(1));


            AsteroidGame.SINGLETON.incrementLevel();

            view.startGame();


    }

    @Override
    public IView getView() {
        return null;
    }

    @Override
    public void setView(IView view) {


    }


}
