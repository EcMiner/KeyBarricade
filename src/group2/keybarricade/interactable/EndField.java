package group2.keybarricade.interactable;

import java.awt.image.BufferedImage;
import static group2.keybarricade.utilities.ImageUtil.*;

public class EndField extends InteractableObject {

    private static final BufferedImage image = loadImage("/images/endfield.png");

    public EndField() {
        super(image);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof EndField) {
            return true;
        }
        return false;
    }

}