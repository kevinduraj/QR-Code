package finalproject;

import ErodeDilateOpenClose.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/*------------------------------------------------------------------------------------------------*/
public class Main {

    private static String filename;
    public static void main(String[] args) throws IOException {

        filename = "qrcode1";
        ProcessHough(filename);    
                
        /*--------- Opening ----------*/
        BufferedImage image1 = ReadBI2Gray("src/images/"+filename+"-b.png");                
        AbstractOperation opening1 = new Opening();
        BufferedImage bi1 = opening1.execute(image1);
        File output1 = new File("src/images/"+filename+"-c.png");
        ImageIO.write(bi1, "png", output1); 
         
        /*--------- Erode ----------*/
        BufferedImage image3 = ReadBI2Gray("src/images/"+filename+"-c.png");                
        AbstractOperation erode3 = new Erosion();
        BufferedImage bi3 = erode3.execute(image3);
        File output3 = new File("src/images/"+filename+"-e.png");
        ImageIO.write(bi3, "png", output3);  
        
        /*--------- Closing ----------*/
        BufferedImage image2 = ReadBI2Gray("src/images/"+filename+"-e.png");                
        AbstractOperation closing = new Closing();
        BufferedImage bi2 = closing.execute(image2);
        File output2 = new File("src/images/"+filename+"-f.png");
        ImageIO.write(bi2, "png", output2);         
                
    }
        
   
    /*--------------------------------------------------------------------------------------------*/
    private static void ProcessHough(String filename) throws IOException {
        
        BufferedImage gray = ReadBI2Gray("src/images/"+filename+"-a.png");
        HoughTransformation hough = new HoughTransformation(
                180
                , gray.getWidth()
                , gray.getHeight());
        hough.addPoints(gray);
        System.out.println("Total Points: " + hough.totalPoints);
        BufferedImage oimage = hough.getHoughImage();
        
        File outputfile = new File("src/images/"+filename+"-b.png");
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
    /*--------------------------------------------------------------------------------------------*/      
    
}
