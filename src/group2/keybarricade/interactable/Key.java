package group2.keybarricade.interactable;

import group2.keybarricade.utilities.ImageUtil;
import java.awt.image.BufferedImage;
import static group2.keybarricade.utilities.ImageUtil.*;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

public class Key extends InteractableObject {

    /**
     * The image of the Key. We made this public, to access the already loaded
     * image in the BottomBar class
     *
     * @see BufferedImage
     * @see ImageUtil
     */
    public static final BufferedImage image = loadImage("/images/key.png");

    private final int pinCode;

    public Key(int pinCode) {
        super(image);
        this.pinCode = pinCode;
    }

    /**
     * We use this for unit testing
     *
     * @param obj The other Key object
     * @return Returns whether the two keys are the same
     */
    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof Key) {
            Key key2 = (Key) obj;
            return key2.pinCode == this.pinCode;
        }
        return false;
    }

    /**
     * Gets the pincode of the key
     *
     * @return Returns the pincode of the key
     */
    public int getPinCode() {
        return pinCode;
    }

    /**
     * Paints the pin code as text on the tile
     *
     * @param g The Tile's graphics to paint on
     * @param cubeSize The cube size of the tile
     */
    @Override
    public void paint(Graphics g, int cubeSize) {
        super.paint(g, cubeSize);
        String text = pinCode + "";

        g.setFont(new Font("Tahoma", Font.PLAIN, Math.round(cubeSize * .28f)));

        Rectangle2D bounds = g.getFontMetrics().getStringBounds(text, g);
        int stringHeight = (int) Math.round(bounds.getHeight() * .75);

        g.drawString(text, cubeSize / 15, cubeSize - ((int) Math.round(cubeSize * .055)));
    }

}
