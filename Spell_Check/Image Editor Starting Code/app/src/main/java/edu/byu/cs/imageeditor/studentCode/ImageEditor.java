package edu.byu.cs.imageeditor.studentCode;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by Austin on 9/6/16.
 */
public class ImageEditor implements IImageEditor {

    private Image img = new Image();
    private Pixel[][] px_array;

       @Override
       public void load (BufferedReader bufferedReader)
       {
           try {
               String WH = "";
               //P3
               bufferedReader.readLine();
               //Comment
               bufferedReader.readLine();
               //Width Height
               WH = bufferedReader.readLine();

               String[] dimensions = WH.split(" ");

               int width = Integer.parseInt(dimensions[0]);
               int height = Integer.parseInt(dimensions[1]);


               img.setWidth(width);
               img.setHeight(height);

               px_array = img.createPixelArray();

               //255
               bufferedReader.readLine();
               Log.d("test", WH);
               for (int i = 0; i < height; i++) {
                   for (int j=0; j < width; j++) {
                       px_array[i][j] = new Pixel();
                       px_array[i][j].red = Integer.parseInt(bufferedReader.readLine());
                       px_array[i][j].green = Integer.parseInt(bufferedReader.readLine());
                       px_array[i][j].blue = Integer.parseInt(bufferedReader.readLine());
                   }
               }

               //scope of px_array ends, i need it to go to invert
           } catch (IOException e) {
               e.printStackTrace();
           }
       }

       @Override
       public String invert () {
           int new_red = 0;
           int new_green = 0;
           int new_blue = 0;


           for (int i=0; i < img.getHeight(); i++)
           {
               for (int j=0; j < img.getWidth(); j++)
               {
                   new_red = 255 - px_array[i][j].getRed();
                   new_green = 255 - px_array[i][j].getGreen();
                   new_blue = 255 - px_array[i][j].getBlue();

                   px_array[i][j].setRed(new_red);
                   px_array[i][j].setGreen(new_green);
                   px_array[i][j].setBlue(new_blue);

               }
           }

           return toPPM();
       }

       @Override
       public String grayscale () {
           int avg = 0;

           for (int i=0; i < img.getHeight(); i++)
           {
               for (int j=0; j < img.getWidth(); j++)
               {
                   avg = (px_array[i][j].getRed() + px_array[i][j].getGreen() + px_array[i][j].getBlue()) / 3;

                   px_array[i][j].setRed(avg);
                   px_array[i][j].setGreen(avg);
                   px_array[i][j].setBlue(avg);

               }
           }

           return toPPM();
       }

       @Override
       public String emboss () {

           int sred_diff = 0;
           int sgreen_diff = 0;
           int sblue_diff = 0;
           int red_diff = 0;
           int green_diff = 0;
           int blue_diff = 0;
           int emboss_value = 0;
           int largest_diff = 0;

           for (int i = img.getHeight() - 1; i >= 0; i--)
           {
               for (int j = img.getWidth() - 1; j >= 0; j--) {
                   if (i == 0 || j == 0)
                   {
                       px_array[i][j].setRed(128);
                       px_array[i][j].setGreen(128);
                       px_array[i][j].setBlue(128);
                   }
                   else
                   {
                       sred_diff = px_array[i][j].getRed() - (px_array[i - 1][j - 1].getRed());
                       sgreen_diff = px_array[i][j].getGreen() - (px_array[i - 1][j - 1].getGreen());
                       sblue_diff = px_array[i][j].getBlue() - (px_array[i - 1][j - 1].getBlue());
                       red_diff = Math.abs(sred_diff);
                       green_diff = Math.abs(sgreen_diff);
                       blue_diff = Math.abs(sblue_diff);

                       if (red_diff >= green_diff && red_diff >= blue_diff) {
                           largest_diff = sred_diff;
                       } else {
                           if (green_diff >= blue_diff) {
                               largest_diff = sgreen_diff;
                           } else {
                               largest_diff = sblue_diff;
                           }
                       }

                       emboss_value = 128 + largest_diff;
                       if (emboss_value > 255) {
                           emboss_value = 255;
                       }
                       if (emboss_value < 0) {
                           emboss_value = 0;
                       }

                       px_array[i][j].setRed(emboss_value);
                       px_array[i][j].setGreen(emboss_value);
                       px_array[i][j].setBlue(emboss_value);


                   }
               }
           }
           return toPPM();
       }

       @Override
       public String motionblur ( int blurLength){



           for (int i=0; i < img.getHeight(); i++)
           {
               for (int j=0; j < img.getWidth(); j++)
               {


                   if (j + blurLength < img.getWidth())
                   {
                       int avg_red = 0;
                       int avg_green = 0;
                       int avg_blue = 0;
                       for (int k=0; k < blurLength; k++)
                       {
                           avg_red += px_array[i][j+k].getRed();
                           avg_green += px_array[i][j+k].getGreen();
                           avg_blue += px_array[i][j+k].getBlue();
                       }
                       avg_red = avg_red/blurLength;
                       avg_green = avg_green/blurLength;
                       avg_blue = avg_blue/blurLength;

                       px_array[i][j].setRed(avg_red);
                       px_array[i][j].setGreen(avg_green);
                       px_array[i][j].setBlue(avg_blue);
                   }
                   else
                   {
                       int avg_red = 0;
                       int avg_green = 0;
                       int avg_blue = 0;
                       int blur_grabs = 0;
                       blur_grabs = img.getWidth() - j;

                       for (int k=0; k < blur_grabs; k++)
                       {
                           avg_red += px_array[i][j+k].getRed();
                           avg_green += px_array[i][j+k].getGreen();
                           avg_blue += px_array[i][j+k].getBlue();
                       }

                       avg_red = avg_red/blur_grabs;
                       avg_green = avg_green/blur_grabs;
                       avg_blue = avg_blue/blur_grabs;

                       px_array[i][j].setRed(avg_red);
                       px_array[i][j].setGreen(avg_green);
                       px_array[i][j].setBlue(avg_blue);

                   }
               }
           }


           return toPPM();
       }

        public String toPPM()
        {
            StringBuilder s = new StringBuilder();
            s.append("P3\n");
            s.append(img.getWidth());
            s.append(" ");
            s.append(img.getHeight());
            s.append("\n");
            s.append("255\n");

            for (int i=0; i < img.getHeight(); i++)
            {
                for (int j=0; j < img.getWidth(); j++)
                {
                    s.append(px_array[i][j].getRed());
                    s.append("\n");
                    s.append(px_array[i][j].getGreen());
                    s.append("\n");
                    s.append(px_array[i][j].getBlue());
                    s.append("\n");
                }
            }

            return s.toString();
        }
}
