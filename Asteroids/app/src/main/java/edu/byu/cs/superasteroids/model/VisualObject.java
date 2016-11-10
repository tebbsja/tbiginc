package edu.byu.cs.superasteroids.model;

/**
 * Created by Austin on 10/11/16.
 */
/** The VisualObject class contains methods that deal with updating and altering objects on screen  */
public abstract class VisualObject {

    private int id;
    private String image_path;
    private int width;
    private int height;

    /** Constructor for an object. All objects will have at least these parameters.
     *
     * @param id - unique identifier
     * @param image_path - location of image
     * @param width - width of object
     * @param height - height of object
     */
    public VisualObject(int id, String image_path, int width, int height)
    {
        this.id = id;
        this.image_path = image_path;
        this.width = width;
        this.height = height;
    }

    /**
     * Default constructor
     */
    public VisualObject(){}

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
    public void setHeight(int h)
    {
        this.height = h;
    }
    public int getHeight()
    {
        return this.height;
    }
    public void setWidth(int w)
    {
        this.width = w;
    }
    public int getWidth()
    {
        return this.width;
    }
    public void setImagePath(String img)
    {
        this.image_path = img;
    }
    public String getImagePath()
    {
        return this.image_path;
    }
    public void setId(int id)
    {
        this.id = id;
    }
    public int getId()
    {
        return this.id;
    }
}
