package group2.keybarricade;

import java.awt.image.BufferedImage;
import static group2.keybarricade.ImageUtil.*;

public class EndField extends InteractableObject {

    private static final BufferedImage image = loadImage("/images/endfield.png");

    public EndField() {
        super(image);
    }

}
