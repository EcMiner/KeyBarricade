package group2.keybarricade.interactable;

import group2.keybarricade.utilities.ImageUtil;
import java.awt.image.BufferedImage;
import static group2.keybarricade.utilities.ImageUtil.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

public class Barricade extends InteractableObject {

    /**
     * The image of the Barricade
     *
     * @see BufferedImage
     * @see ImageUtil
     */
    private static final BufferedImage image = loadImage("/images/barricade.png");

    private final int pinCode;

    public Barricade(int pinCode) {
        super(image);
        this.pinCode = pinCode;
    }

    /**
     * Gets the pincode of the barricade
     *
     * @return Returns the pincode of the barricade
     */
    public int getPinCode() {
        return pinCode;
    }

    /**
     * Checks whether a key fits on the barricade or not.
     *
     * @param key The key that needs to be checked
     * @return Returns whether the key fits on the barricade
     */
    public boolean keyFits(Key key) {
        return this.pinCode == key.getPinCode();
    }

    /**
     * We use this for unit testing
     *
     * @param obj The other Barricade object
     * @return Returns whether the two barricades are the same
     */
    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof Barricade) {
            Barricade barricade2 = (Barricade) obj;
            return barricade2.pinCode == this.pinCode;
        }
        return false;
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

        g.setColor(Color.WHITE);
        g.setFont(new Font("Tahoma", Font.BOLD, Math.round(cubeSize * .28f)));

        Rectangle2D bounds = g.getFontMetrics().getStringBounds(text, g);
        int stringHeight = (int) Math.round(bounds.getHeight() * .75);

        g.drawString(text, cubeSize / 15, stringHeight);
    }

}
