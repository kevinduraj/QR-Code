package finalproject;

import Binarization.OtsuBinarize;
import ErodeDilateOpenClose.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/*-------------------------------------------------------------------------------------------
                            QR Code Detection
/-------------------------------------------------------------------------------------------*/
public class Main {

    
    /*---------------------------------------------------------------------------------------*/
    public static void main(String[] args) throws IOException {

        String output = DetectQRCode("qrcode2");
        System.out.println("6. output = " + output);
        
        int[][] out = ImageRead(output);
        Probability(out);
        
    }
    /*---------------------------------------------------------------------------------------*/
    private static String DetectQRCode(String filename) throws IOException {
        
        ProcessHough(filename);
        
        /*-------------- Opening -----------*/
        System.out.println("1. Opening");
        BufferedImage image1 = ReadBI2Gray("src/output1/"+filename+"a-hough.png");   
        AbstractOperation opening1 = new Opening();
        BufferedImage bi1 = opening1.execute(image1);
        File output1 = new File("src/output1/"+filename+"b-opening.png");
        ImageIO.write(bi1, "png", output1);
        
        /*-------------- Erode -------------*/
        System.out.println("2. Errode");        
        BufferedImage image3 = ReadBI2Gray("src/output1/"+filename+"b-opening.png");                
        AbstractOperation erode3 = new Erosion();
        BufferedImage bi3 = erode3.execute(image3);
        File output3 = new File("src/output1/"+filename+"c-erode.png");
        ImageIO.write(bi3, "png", output3);  
        
        /*----------- Binarization ----------*/
        //OtsuBinarize otsu = new OtsuBinarize("src/output/"+filename+"-e.png");
        //String filename = otsu.run("src/output/OtsuBin");
       
        /*------- Sobel Edge Detection ------*/
        System.out.println("3. Sobel edge detection - Magnitude");        
        Sobel sobel = new Sobel();
        sobel.process("src/output1/"+filename+"c-erode.png");
        ImageWrite(sobel.Magnitute, "src/output1/"+filename+"e-sobel-mag.png");    
        
        /*------------------ Otsu Binarize ----------------------*/
        System.out.println("4. Otsu binarize");        
        OtsuBinarize otsu = new OtsuBinarize("src/output1/"+filename+"e-sobel-mag.png");
        String output = otsu.run("src/output2/"+filename+"-otsu");
        return output;
    }

    /*---------------------------------------------------------------------------------------*/
    private static void ProcessHough(String filename) throws IOException {
        
        BufferedImage gray = ReadBI2Gray("src/images/"+filename+".png");
        HoughTransformation hough = new HoughTransformation(
                360
                , gray.getWidth()
                , gray.getHeight());
        hough.addPoints(gray);
        System.out.println("Total Points: " + hough.totalPoints);
        BufferedImage oimage = hough.getHoughImage();
        
        File outputfile = new File("src/output1/"+filename+"a-hough.png");
        ImageIO.write(oimage, "png", outputfile);
    }     
    /*--------------------------------------------------------------------------------------------*/          
    private static BufferedImage ReadBI2Gray(String filename) throws IOException {
        
        BufferedImage image = ImageIO.read(new File(filename));
        BufferedImage gray = new BufferedImage( image.getWidth()
                                              , image.getHeight()
                                              , BufferedImage.TYPE_BYTE_GRAY);
        
        //Convert the original colored image to grayscale
        ColorConvertOp op = new ColorConvertOp(
                image.getColorModel().getColorSpace()
                , gray.getColorModel().getColorSpace(),null);
        op.filter(image,gray);
        return gray;
    }
    /*---------------------------------------------------------------------------------------*/    
    public static void ImageWrite(double img[][], String filename) throws IOException {

            BufferedImage bi = new BufferedImage(img[0].length, img.length, BufferedImage.TYPE_INT_RGB);

            for (int i = 0; i < bi.getHeight(); ++i) {
                for (int j = 0; j < bi.getWidth(); ++j) {
                    int val = (int) img[i][j];
                    int pixel = (val << 16) | (val << 8) | (val);
                    bi.setRGB(j, i, pixel);
                }
            }

            File outputfile = new File(filename);
            ImageIO.write(bi, "png", outputfile);
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
    /*----------------------------------------------------------------------------------------*/    
    private static void Probability(int[][] image) {
        int h = image.length;
        int w = image[0].length;
        int half = (int) h /2;
        
        int range1 = (int) (half-(half*0.03));
        int range2 = (int) (half+(half*0.03)+2);
        
        
        for (int height = range1; height < range2; height++) {
            System.out.format("%3d ", height);
            
            for (int width = 0; width < w; width++) {

                System.out.format("%3d ", image[height][width]);
            }
            System.out.println();
        }
        System.out.println();
    } 
    /*----------------------------------------------------------------------------------------*/
    
}
