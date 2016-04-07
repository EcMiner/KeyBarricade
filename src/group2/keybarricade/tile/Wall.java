package group2.keybarricade.tile;

import group2.keybarricade.utilities.ImageUtil;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import static group2.keybarricade.utilities.ImageUtil.*;

public class Wall extends Tile {

    /**
     * The image of the wall
     *
     * @see BufferedImage
     * @see ImageUtil
     */
    private static final BufferedImage image = loadImage("/images/wall.png");

    public Wall(int x, int y) {
        super(x, y);
    }

    /**
     * Paints the wall image on the tile
     *
     * @param g The graphics to paint on
     * @see Graphics
     */
    @Override
    public void paint(Graphics g) {
        int cubeSize = getWidth();

        // Resize the image to fit the tile
        BufferedImage resized = ImageUtil.resize(image, cubeSize);

        // Draw the image in the center
        g.drawImage(resized, (cubeSize - resized.getWidth()) / 2, (cubeSize - resized.getHeight()) / 2, null);
    }

    /**
     * Clone the wall to be able to reset the level
     *
     * @return Returns the cloned wall
     */
    @Override
    public Tile clone() {
        return new Wall(getLocationX(), getLocationY());
    }

}
