package group2.keybarricade;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public abstract class InteractableObject {

    
    private final BufferedImage image;
    
    public InteractableObject(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void paint(Graphics g, int cubeSize) {
        BufferedImage resized = ImageUtil.resize(image, cubeSize);
        g.drawImage(resized, (cubeSize - resized.getWidth()) / 2, (cubeSize - resized.getHeight()) / 2, null);
    }

}
