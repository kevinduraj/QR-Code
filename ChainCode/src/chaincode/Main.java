package chaincode;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import static java.lang.String.format;
import javax.imageio.ImageIO;

public class Main {

    public static void main(String[] args) throws IOException {

        double factor = 1;

        String name = "qrcode5";
        BufferedImage image = ImageIO.read(new File("src/image/" + name + ".png"));
        BoundaryFilterOp bOp = new BoundaryFilterOp(factor);
        BufferedImage bImg = bOp.filter(image, null);

        System.out.println("src/output1/" + name);
        File file = new File("src/output1/" + name + ".png");
        ImageIO.write(bImg, "png", file);

    }
}
