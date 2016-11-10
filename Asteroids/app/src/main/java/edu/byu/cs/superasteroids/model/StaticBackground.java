package edu.byu.cs.superasteroids.model;

/**
 * Created by Austin on 10/11/16.
 */
public class StaticBackground extends VisualObject {
    /**
     * Default Constructor
     */
    public StaticBackground(){}

    public StaticBackground(int id, String image_path)
    {
        super(id,image_path,0,0);
    }
    /**
     * Update is called 60 times every second, and Updates every object. It may change the state of an object,
     * move an object, detect collisions, etc.
     */
    public void Update()
    {

    }

    /**
     * Draw is called 60 times every second and is the default method of the object to draw itself on the screen.
     */
    public void Draw()
    {

    }
}
