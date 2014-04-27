package sobel;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Main {

    private static final String sInput = "src/image/Lenna.png"; 
    private static final String sReflect = "src/image/Reflection.png"; 
    
    private static final int padding_x = 15; // x must be odd integer
    private static final int padding_y = 15; // y must be odd integer
    
    /*--------------------------------------------------------------------------------------------*/
    public static void main(String[] args) throws IOException {
        
        /*--------------- Reflection Padding --------------------*/
        int[][] iimage = ImageRead(sInput);
        Reflection ref = new Reflection();
        
        int[][] oimage = ref.conv(iimage, padding_x, padding_y);  
        ImageWrite(oimage, sReflect);
        
        
        /*---------------- Sobel Edge Detection -----------------*/
        Sobel sobel = new Sobel();
        sobel.process(sReflect);
        
        /*----------------------- Sobel Magnitute -----------------------------*/
        int[][] temp1 = ref.ScaleDown(sobel.Magnitute, padding_x, padding_x);
        ImageWrite(temp1, "src/image/Lenna_Mag.png");
        
        /* Statistics */
        Statistics stat1 = new Statistics("src/image/Lenna_Mag.png");
        System.out.println("\n" + "src/image/Lenna_Mag.png");
        System.out.format("Mean     : %.3f\n\n", + stat1.getMean());

        /*----------------------- Sobel Direction -----------------------------*/
        int[][] temp2 = ref.ScaleDown(sobel.Direction, padding_x, padding_x);
        ImageWrite(temp2, "src/image/Lenna_Dir.png");
        
        /* Statistics */
        Statistics stat2 = new Statistics("src/image/Lenna_Dir.png");
        System.out.println("\n" + "src/image/Lenna_Dir.png");
        System.out.format("Mean     : %.3f\n\n", + stat2.getMean());
                
        
    }
    /*--------------------------------------------------------------------------------------------*/     
    public static int[][] ImageRead(String filename) throws IOException {
        
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
    }
    /*--------------------------------------------------------------------------------------------*/
    public static void ImageWrite(int img[][], String filename) throws IOException {

            BufferedImage bi = new BufferedImage(img[0].length, img.length, BufferedImage.TYPE_INT_RGB);

            for (int i = 0; i < bi.getHeight(); ++i) {
                for (int j = 0; j < bi.getWidth(); ++j) {
                    int val = img[i][j];
                    int pixel = (val << 16) | (val << 8) | (val);
                    bi.setRGB(j, i, pixel);
                }
            }

            File outputfile = new File(filename);
            ImageIO.write(bi, "png", outputfile);
    }
    /*--------------------------------------------------------------------------------------------*/     
}
