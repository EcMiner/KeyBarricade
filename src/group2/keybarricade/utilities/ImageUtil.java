package group2.keybarricade.utilities;

import group2.keybarricade.interactable.InteractableObject;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.imgscalr.Scalr;

public class ImageUtil {

    /**
     * Loads an image from a file in the jar file
     *
     * @param location The location to the file in the jar
     * @return Returns the loaded image
     */
    public static BufferedImage loadImage(String location) {
        try {
            return ImageIO.read(InteractableObject.class.getResourceAsStream(location));
        } catch (IOException ex) {
            Logger.getLogger(InteractableObject.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * This method will scale an image down to fit a cube with the size cubeSize
     *
     * @param source The image to resize
     * @param cubeSize The size to fit
     * @return Returns a resized image
     */
    public static BufferedImage resize(BufferedImage source, int cubeSize) {
        double scale = Math.min((double) cubeSize / (double) source.getWidth(),
                (double) cubeSize / (double) source.getHeight());
        return resize(source, scale);
    }

    /**
     * This method will resize an image while keeping it's scale
     *
     * @param source The image to resize
     * @param scale The size to fit
     * @return Returns a resized image
     */
    public static BufferedImage resize(BufferedImage source, double scale) {
        BufferedImage resized = Scalr.resize(source, Scalr.Method.SPEED,
                (int) Math.round(scale * source.getWidth()), (int) Math.round(scale * source.getHeight()),
                Scalr.OP_ANTIALIAS);
        return resized;
    }
}
