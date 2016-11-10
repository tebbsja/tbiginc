package edu.byu.cs.imageeditor.studentCode;

/**
 * Created by Austin on 9/7/16.
 */
public class Image {

    private int width;
    private int height;

    public String strHeight()
    {
        return Integer.toString(height);
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Pixel[][] createPixelArray(){
        Pixel[][] img = new Pixel[height][width];
        return img;
    }

    public int getWidth()
    {
        return width;
    }
    public int getHeight()
    {
        return height;
    }

}
