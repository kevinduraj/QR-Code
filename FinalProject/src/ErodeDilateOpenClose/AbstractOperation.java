package ErodeDilateOpenClose;

import java.awt.image.BufferedImage;

public abstract class AbstractOperation implements MorphologicalOperation {

    protected int shapeSize;

    protected short[][] constructShape(STRUCTURING_ELEMENT_SHAPE shape, int shapeSize) {

        int size = 2 * shapeSize + 1;
        short[][] structElem = new short[size][size];
        switch (shape) {
            case SQUARE:
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        structElem[i][j] = 1;
                    }
                }
                break;
                
            case VERTICAL_LINE:
                for (int i = 0; i < size; i++) {
                    structElem[i][shapeSize] = 1;
                }
                break;
                
            case HORIZONTAL_LINE:
                for (int i = 0; i < size; i++) {
                    structElem[shapeSize][i] = 1;
                }
                break;
                
            default:
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        structElem[i][j] = 1;
                    }
                }
        }
        return structElem;
    }
    /*--------------------------------------------------------------------------------------------*/
    public abstract BufferedImage execute(BufferedImage img);
    public int getShapeSize() {  return shapeSize;  }
    /*--------------------------------------------------------------------------------------------*/    
}
