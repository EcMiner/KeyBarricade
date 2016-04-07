package group2.keybarricade.interactable;

import java.awt.image.BufferedImage;
import static group2.keybarricade.utilities.ImageUtil.*;

public class EndField extends InteractableObject {

    /**
     * The image of the EndField
     *
     * @see BufferedImage
     * @see ImageUtil
     */
    private static final BufferedImage image = loadImage("/images/endfield.png");

    public EndField() {
        super(image);
    }

    /**
     * We use this for unit testing
     *
     * @param obj The other EndField object
     * @return Returns whether the both EndFields are the same
     */
    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof EndField) {
            return true;
        }
        return false;
    }

}
