package group2.keybarricade;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.imgscalr.Scalr;

public class ImageUtil {

    public static BufferedImage loadImage(String location) {
        try {
            return ImageIO.read(InteractableObject.class.getResourceAsStream(location));
        } catch (IOException ex) {
            Logger.getLogger(InteractableObject.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static BufferedImage resize(BufferedImage source, int cubeSize) {
        double scale = Math.min((double) cubeSize / (double) source.getWidth(),
                (double) cubeSize / (double) source.getHeight());

        BufferedImage resized = Scalr.resize(source, Scalr.Method.SPEED,
                (int) Math.round(scale * source.getWidth()), (int) Math.round(scale * source.getHeight()),
                Scalr.OP_ANTIALIAS);
        return resized;
    }
}
