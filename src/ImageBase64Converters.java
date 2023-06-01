import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

public class ImageBase64Converters {

    public static String fromBufferedImage(BufferedImage bufferedImage) {
        return fromBufferedImage(bufferedImage, "png");
    }

    public static String fromBufferedImage(BufferedImage bufferedImage, String formatName) {
        try {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, formatName, output);
            return Base64.getEncoder().encodeToString(output.toByteArray());
        } catch (IOException ex) {
            throw new RuntimeException("I/O exception was catched during encoding a buffered image!", ex);
        }
    }
}
