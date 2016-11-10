package edu.byu.cs.imageeditor.studentCode;

/**
 * Created by Austin on 9/7/16.
 */
public class Pixel {
    int red;
    int blue;
    int green;

    public String redString()
    {
        return Integer.toString(red);
    }

    public void setRed(int r)
    {
        this.red = r;
    }
    public void setBlue(int b)
    {
        this.blue = b;
    }
    public void setGreen(int g)
    {
        this.green = g;
    }

    public int getRed()
    {
        return red;
    }
    public int getBlue()
    {
        return blue;
    }
    public int getGreen()
    {
        return green;
    }
}
