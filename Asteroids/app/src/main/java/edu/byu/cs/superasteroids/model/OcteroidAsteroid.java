package edu.byu.cs.superasteroids.model;

/**
 * Created by Austin on 10/11/16.
 */

/**
 * This Class is designated as type octeroid asteroid
 */
public class OcteroidAsteroid extends AsteroidType {


    /**
     * Default Constructor
     */
    public OcteroidAsteroid(){}

    /**
     *
     * @param id - octeroid asteroid id
     * @param image_path - octeroid asteroid image path
     * @param width - width of image
     * @param height - height of image
     */
    public OcteroidAsteroid(int id, String image_path, int width, int height)
    {
        super(id,"octeroid",image_path,width,height,"octeroid");
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
