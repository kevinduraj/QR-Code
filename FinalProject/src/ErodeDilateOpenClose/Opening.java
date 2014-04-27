package ErodeDilateOpenClose;

import java.awt.image.BufferedImage;

public class Opening extends AbstractOperation {

    private STRUCTURING_ELEMENT_SHAPE shape;

    public Opening() {
        shapeSize = 2;
        shape = STRUCTURING_ELEMENT_SHAPE.SQUARE;
    }

    public Opening(STRUCTURING_ELEMENT_SHAPE shape, int shapeSize) {
        this.shape = shape;
        super.shapeSize = shapeSize;
    }

    @Override
    public BufferedImage execute(BufferedImage img) {
        if (img.getType() != BufferedImage.TYPE_BYTE_GRAY) {
            throw new IllegalArgumentException("NOT TYPE_BYTE_GRAY");
        }

        BufferedImage erodedImg, openedImg;
        Dilation dilation = new Dilation(shape, shapeSize);
        Erosion erosion = new Erosion(shape, shapeSize);

        erodedImg = erosion.execute(img);
        openedImg = dilation.execute(erodedImg);

        return openedImg;
    }

}
