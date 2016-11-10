package edu.byu.cs.superasteroids.model;

/**
 * Created by Austin on 10/11/16.
 */

/**
 * This class provides information about the PowerCore
 */
public class PowerCore extends MovingObject {
    /** The value of the extra damage that should be added to the cannon's base damage */
    private int cannonBoost;
    /** Adds to the base speed of the engine */
    private int engineBoost;

    /**
     * Default Constructor
     */
    public PowerCore(){}

    /**
     *
     * @param id - id of PowerCore
     * @param cannonBoost - The value of the extra damage that should be added to the cannon's base damage
     * @param engineBoost - Adds to the base speed of the engine
     * @param image_path - The image path of the PowerCore
     */
    public PowerCore(int id, int cannonBoost, int engineBoost, String image_path)
    {
        super(id, image_path, 0,0,0,0,0,0);
        this.cannonBoost = cannonBoost;
        this.engineBoost = engineBoost;
    }

    /**
     * Draw is called 60 times every second and is the default method of the object to draw itself on the screen.
     */
    @Override
    public void Draw()
    {

    }
    /**
     * Update is called 60 times every second, and Updates every object. It may change the state of an object,
     * move an object, detect collisions, etc.
     */
    @Override
    public void Update()
    {

    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("ID " + Integer.toString(getId()) + "| CANNONBOOST " + Integer.toString(getCannonBoost()) +" | ENGINEBOOST " + Integer.toString(getEngineBoost()) + "| IMAGE " + getImagePath());

        return sb.toString();
    }

    public void setCannonBoost(int c)
    {
        this.cannonBoost = c;
    }
    public int getCannonBoost()
    {
        return this.cannonBoost;
    }
    public void setEngineBoost(int e)
    {
        this.engineBoost = e;
    }
    public int getEngineBoost()
    {
        return this.engineBoost;
    }
}
