package edu.byu.cs.superasteroids.model;

/**
 * Created by Austin on 10/11/16.
 */

import android.util.Log;

/**
 * This class is designated as type "regular" asteroid
 */
public class RegularAsteroid extends AsteroidType {


    /**
     * Default Constructor
     */
    public RegularAsteroid(){}

    /**
     *
     * @param id - regular asteroid id
     * @param image_path - regular asteroid image path
     * @param width - width of image
     * @param height - height of image
     */
    public RegularAsteroid(int id, String image_path, int width, int height)
    {
        super(id,"regular",image_path,width,height,"regular");
    }


    /**
     * Draw is called 60 times every second and is the default method of the object to draw itself on the screen.
     */
    @Override
    public void Draw()
    {
        super.Draw();
    }
    /**
     * Update is called 60 times every second, and Updates every object. It may change the state of an object,
     * move an object, detect collisions, etc.
     */
    @Override
    public void Update()
    {

    }

}
