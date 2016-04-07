package group2.keybarricade.interactable;

import group2.keybarricade.utilities.ImageUtil;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public abstract class InteractableObject {

    /**
     * The image of the InteractableObject
     */
    private final BufferedImage image;

    public InteractableObject(BufferedImage image) {
        this.image = image;
    }

    /**
     * This method will paint the InteractableObject's image on the Tile
     *
     * @param g The tile's graphics to paint on
     * @param cubeSize The cube size of the tile
     */
    public void paint(Graphics g, int cubeSize) {
        // Resize the image to fit the tile
        BufferedImage resized = ImageUtil.resize(image, cubeSize);
        // Draw the image in the center of the tile
        g.drawImage(resized, (cubeSize - resized.getWidth()) / 2, (cubeSize - resized.getHeight()) / 2, null);
    }

}
