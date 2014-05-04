package namethatshape;

//import algoritharium.*;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
 
public class NameThatShape {
    
    public static void main(String[] args) {
        identifyShape();
    }
     
    public static void identifyShape() {
        
        //Image img = ImageViewer.getImage();
        int[][] img = ImageRead("src/images/qrcode5.png");
        int[][] color = ImageRead("src/images/qrcode5.png");
        
        //recolor(color);
         
        int rowCount = 0;
        int row = 0;
        int rowTotal = 0;
        
        //Number of rows with black pixels
        for (int x = 0; x < img[0].length - 1; x++){
            for (int y = 0; y < img.length - 1; y++) {
                while (color[x][y] == 0 && x < img[0].length) {
                    row++;
                    y++;
                }
                if (row > rowTotal)
                    rowTotal = row;
                 
                row = 0;
           }
        }
        
        System.out.println(rowTotal);
        double[] blackPixels = new double[rowTotal];
        int arrayNumber = 0;
        
        
        //Number of black pixels per row
        for (int y = 0; y < img[0].length; y++) {
            for (int x = 0; x < img.length; x++) {
                if (color[x][y] == 0)
                    rowCount++;
            }
            if (rowCount > 0) {
                blackPixels[arrayNumber] = rowCount;
                arrayNumber++;
                rowCount = 0;
            }
        }
        double maxB = max(blackPixels);
        double meanB = mean(blackPixels);
        double varB = var(blackPixels);
        double stddevB = stddev(blackPixels);
        
        System.out.println();
        //mean(blackPixels);
        //var(blackPixels);
        //stddev(blackPixels);
        System.out.println("Total # Rows: " + rowTotal);
        System.out.println("Height: " + maxB);
        System.out.println("Width: " + blackPixels.length);
        
        
        //ID SHAPES HERE
        if (maxB == meanB && rowTotal == maxB)
            System.out.println("The shape is a square.");
        else if (maxB == meanB && rowTotal != maxB)
            System.out.println("The shape is a rectangle.");
        else if (maxB == rowTotal && stddevB != varB)
            System.out.println("The shape is a circle.");
        else if (.5 * maxB < meanB && rowTotal > maxB)
            System.out.println("The shape is a triangle.");
        else if (meanB != maxB&& (1 / 3) * maxB == (1 / 2) * rowTotal)
            System.out.println("The shape is a polygon.");
        else
            System.out.println("The shape is unknown.");
    }
    
    // depricated
    private static void recolor(Color[][] c) {
        for(int y = 0; y < c.length; y++)
            for(int x = 0; x < c[y].length; x++)
                if (c[y][x].getRed() < 50)
                    c[y][x] = Color.BLACK;
                else
                    c[y][x] = Color.white;
        
        //ImageViewer.createImage(c);
    }
     
    public static double max(double[] a) { // Compute maximum value in a[]
        double max = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < a.length; i++)
            if(a[i] > max)
                max = a[i];
 
        System.out.println("max: " + max);
        return max;
    }
     
    public static double mean(double[] a) { // Compute the average of the values in a[]
        double sum = 0.0;
        for (int i = 0; i < a.length; i++)
            sum += a[i];
 
        System.out.println("mean: " + sum / a.length);
        return sum / a.length;
    }
     
    public static double var(double[] a) { // Compute the sample variance of the values in a[]
        double avg = mean(a);
        double sum = 0.0;
        for (int i = 0; i < a.length; i++)
            sum += (a[i] - avg) * (a[i] - avg);
 
        System.out.println("var: " + sum / (a.length - 1));
        return sum / (a.length - 1);
    }
     
    public static double stddev(double[] a) { 
        double dev = Math.sqrt(var(a));
        System.out.println("stddev: " + dev);
        return dev;
         
    }
    
    /*---------------------------------------------------------------------------------------*/
    private static int[][] ImageRead(String filename) {

        try {
            File infile = new File(filename);
            BufferedImage bi = ImageIO.read(infile);

            int red[][] = new int[bi.getHeight()][bi.getWidth()];
            int grn[][] = new int[bi.getHeight()][bi.getWidth()];
            int blu[][] = new int[bi.getHeight()][bi.getWidth()];
            for (int i = 0; i < red.length; ++i) {
                for (int j = 0; j < red[i].length; ++j) {
                    red[i][j] = bi.getRGB(j, i) >> 16 & 0xFF;
                    grn[i][j] = bi.getRGB(j, i) >> 8 & 0xFF;
                    blu[i][j] = bi.getRGB(j, i) & 0xFF;
                }
            }

            return grn;

        } catch (IOException e) {
            System.out.println("image I/O error");
            return null;
        }
    }    
}
