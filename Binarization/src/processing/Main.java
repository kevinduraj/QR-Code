package processing;

import java.io.IOException;
/*--------------------------------------------------------------------------------------------------
Program Output:
    1.) src/image/Stretch.png
    Stretch 10th and 90th percentile - Image Output Mean = 133.19990921020508

    2.) src/image/MeanBin123.png
    Binarization using mean threshold (123) - Image Output Mean = 138.29360961914062

    3.) src/image/OtsuBin115.png
    Binarization using Otsu optimal threshold algorithm - Image Output Mean = 148.3531150817871
/*------------------------------------------------------------------------------------------------*/
public class Main {

    private static final String sInput = "src/image/Lenna.png";
    
    /*---------------------------------------------------------------------------------------------
                                          M A I N
     ---------------------------------------------------------------------------------------------*/
    public static void main(String[] args) throws IOException {        
        CutOffImage1();
        Binarization2();        
        OtsuBinarization3();
        System.out.println("\n");
    }           
    /*---------------------------------------------------------------------------------------------
    Program 1.)
        Perform a histogram stretch operation 
        using the 10th and 90th percentile bins as cutoff points
    ----------------------------------------------------------------------------------------------*/
    private static void CutOffImage1() {
        
        Histogram hist = new Histogram();
        int [] mapHist = hist.readHistogram(sInput);      
        int[] cutoff = hist.setCutoff(mapHist, 10.0); 
       
        ImageReadWrite image = new ImageReadWrite();
        int grn[][] = image.ImageRead(sInput);
        image.WriteStretchedImage(grn, hist.first, hist.last, new int[0], "src/image/CutOff.png");
        
        int[] stretchedHist = hist.stretchMap(hist.first, hist.last);
        
        String filename = "src/image/Stretch.png";
        image.WriteStretchedImage(grn, hist.first, hist.last, stretchedHist, filename);
        
        Statistics stat1 = new Statistics(filename);
        System.out.println("\n1.) " + filename 
                  + "\nStretch 10th and 90th percentile - Image Output Mean = " 
                  + stat1.getMean());
        
    }
    /*----------------------------------------------------------------------------------------------
      Program 2.)
        Perform a binarization operation using a threshold value of 128
      --------------------------------------------------------------------------------------------*/
    private static void Binarization2() {

        /*------------------- Histogram -------------------------*/
        Statistics stat = new Statistics(sInput);
        int mean = (int) stat.getMean();        
        
        ImageReadWrite image = new ImageReadWrite();
        int grn[][] = image.ImageRead(sInput);
        
        String filename = "src/image/Mean"+mean+".png";
        Binarization bin = new Binarization();
        bin.binarize(grn, mean,filename);
        
        Statistics stat2 = new Statistics(filename);
        System.out.println("\n2.) " + filename 
                + "\nBinarization using mean threshold (123) - Image Output Mean = " 
                + stat2.getMean());        
        
    }    
    /*----------------------------------------------------------------------------------------------
      Program 3.)
        Perform a binarization operation on the green component 
        using the Otsu optimal threshold algorithm to compute the threshold
      --------------------------------------------------------------------------------------------*/    
    private static void OtsuBinarization3() throws IOException {
        
        OtsuBinarize otsu = new OtsuBinarize(sInput);
        String filename = otsu.run();
        
        Statistics stat3 = new Statistics(filename);
        System.out.println("\n3.) " + filename 
                + "\nBinarization using Otsu optimal threshold algorithm - Image Output Mean = " 
                + stat3.getMean());              
        
    }    
    /*---------------------------------------------------------------------------------------------
                             Display Integer Histogram Array
    ----------------------------------------------------------------------------------------------*/
    private static void display(int[] map) {

        System.out.println("\n--------------------------------\n");
        for (int key=0; key<map.length; key++) {
            int value = map[key];
            System.out.format("%3d -> %5d\n", key, value);
        }
    } 
    /*--------------------------------------------------------------------------------------------*/    
}
