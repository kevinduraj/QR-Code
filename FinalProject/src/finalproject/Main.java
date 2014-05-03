package finalproject;

import Binarization.OtsuBinarize;
import ErodeDilateOpenClose.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/*-------------------------------------------------------------------------------------------*/
public class Main {

    private static String filename;
    
    /*---------------------------------------------------------------------------------------*/
    public static void main(String[] args) throws IOException {

        DetectQRCode("qrcode3");
        
    }
    /*---------------------------------------------------------------------------------------*/
    private static void DetectQRCode(String filename) throws IOException {
        
        ProcessHough(filename);
        
        /*-------------- Opening -----------*/
        BufferedImage image1 = ReadBI2Gray("src/output1/"+filename+"a-hough.png");                
        AbstractOperation opening1 = new Opening();
        BufferedImage bi1 = opening1.execute(image1);
        File output1 = new File("src/output1/"+filename+"b-opening.png");
        ImageIO.write(bi1, "png", output1);
        
        /*-------------- Erode -------------*/
        BufferedImage image3 = ReadBI2Gray("src/output1/"+filename+"b-opening.png");                
        AbstractOperation erode3 = new Erosion();
        BufferedImage bi3 = erode3.execute(image3);
        File output3 = new File("src/output1/"+filename+"c-erode.png");
        ImageIO.write(bi3, "png", output3);  
        
        /*----------- Binarization ----------*/
        //OtsuBinarize otsu = new OtsuBinarize("src/output/"+filename+"-e.png");
        //String filename = otsu.run("src/output/OtsuBin");
       
        /*------- Sobel Edge Detection ------*/
        Sobel sobel = new Sobel();
        sobel.process("src/output1/"+filename+"c-erode.png");
        ImageWrite(sobel.Magnitute, "src/output1/"+filename+"e-magnitude.png");    
        
        /*------------------ Otsu Binarize ----------------------*/
        OtsuBinarize otsu = new OtsuBinarize("src/output1/"+filename+"e-magnitude.png");
        otsu.run("src/output2/"+filename+"-otsu");
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
    
    
}
