package ErodeDilateOpenClose;

import java.awt.image.BufferedImage;

public class Closing extends AbstractOperation {

    private STRUCTURING_ELEMENT_SHAPE shape;
    /*--------------------------------------------------------------------------------------------*/
    public Closing() {
        shapeSize = 2;
        shape = STRUCTURING_ELEMENT_SHAPE.SQUARE;
    }
    /*--------------------------------------------------------------------------------------------*/
    public Closing(STRUCTURING_ELEMENT_SHAPE shape, int shapeSize) {
        this.shape = shape;
        super.shapeSize = shapeSize;
    }

    /*--------------------------------------------------------------------------------------------*/
    @Override
    public BufferedImage execute(BufferedImage img) {
        if (img.getType() != BufferedImage.TYPE_BYTE_GRAY) {
            throw new IllegalArgumentException("NOT TYPE_BYTE_GRAY");
        }

        BufferedImage dilatedImg, closedImg;
        Erosion erosion = new Erosion(shape, shapeSize);
        Dilation dilation = new Dilation(shape, shapeSize);

        dilatedImg = dilation.execute(img);
        closedImg = erosion.execute(dilatedImg);

        return closedImg;
    }
    /*--------------------------------------------------------------------------------------------*/
}
