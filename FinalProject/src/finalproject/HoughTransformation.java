package finalproject;

import java.awt.Color;
import java.awt.image.BufferedImage;

/*------------------------------------------------------------------------------------------------*/
public final class HoughTransformation {

    private double thetaStep;
    private int thetaMax, width, height;
    
    private int[][] houghArray;
    private float centerX, centerY;
    private int houghHeight;
    private int doubleHeight;
    private double[] sinCache;
    private double[] cosCache;
    public int totalPoints;

    /*--------------------------------------------------------------------------------------------*/
    public HoughTransformation(int thetaMax, int width, int height) {

        this.thetaMax = thetaMax;
        this.thetaStep = Math.PI / thetaMax;
        this.width = width;
        this.height = height;
        initialize();
    }
    /*--------------------------------------------------------------------------------------------*/
    private void initialize() {

        houghHeight = (int) (Math.sqrt(2) * Math.max(height, width)) / 2;
        doubleHeight = 2 * houghHeight;
        houghArray = new int[thetaMax][doubleHeight];

        centerX = width / 2;
        centerY = height / 2;

        totalPoints = 0;
        sinCache = new double[thetaMax];
        cosCache = sinCache.clone();

        for (int t = 0; t < thetaMax; t++) {
            double realTheta = t * thetaStep;
            sinCache[t] = Math.sin(realTheta);
            cosCache[t] = Math.cos(realTheta);
        }
    }
    /*--------------------------------------------------------------------------------------------*/
    public void addPoints(BufferedImage image) {

        // Now find edge points and update the hough array
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {

                // Find non-black pixels
                if ((image.getRGB(x, y) & 0x000000ff) != 0) {
                    addPoint(x, y);
                }
            }
        }
    }

    /*--------------------------------------------------------------------------------------------*/
    private void addPoint(int x, int y) {

        // for each value of theta
        for (int t = 0; t < thetaMax; t++) {

            int r = (int) (((x - centerX) * cosCache[t]) + ((y - centerY) * sinCache[t]));
            r += houghHeight;

            if (r < 0 || r >= doubleHeight) {
                continue;
            }
            houghArray[t][r]++;
        }

        totalPoints++;
    }

    /*--------------------------------------------------------------------------------------------*/
    private int getHighestValue() {
        int max = 0;
        for (int t = 0; t < thetaMax; t++) {
            for (int r = 0; r < doubleHeight; r++) {
                if (houghArray[t][r] > max) {
                    max = houghArray[t][r];
                }
            }
        }
        return max;
    }
    /*--------------------------------------------------------------------------------------------*/

    public BufferedImage getHoughImage() {

        int max = getHighestValue();
        BufferedImage image = new BufferedImage(thetaMax, doubleHeight, BufferedImage.TYPE_INT_ARGB);

        for (int t = 0; t < thetaMax; t++) {
            for (int r = 0; r < doubleHeight; r++) {
                
                double value = 255 * ((double) houghArray[t][r]) / max;
                int v = 255 - (int) value;
                int c = new Color(v, v, v).getRGB();
                image.setRGB(t, r, c);
                
            }
        }
        return image;
    }
    /*--------------------------------------------------------------------------------------------*/
}
