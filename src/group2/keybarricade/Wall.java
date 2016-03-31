package group2.keybarricade;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import static group2.keybarricade.ImageUtil.*;

public class Wall extends Tile {

    private static final BufferedImage image = loadImage("/images/wall.png");

    public Wall(int x, int y) {
        super(x, y);
    }

    @Override
    public void paint(Graphics g) {
        int cubeSize = getWidth();
        BufferedImage resized = ImageUtil.resize(image, cubeSize);
        g.drawImage(resized, (cubeSize - resized.getWidth()) / 2, (cubeSize - resized.getHeight()) / 2, null);
    }

    @Override
    public Tile clone() {
        return new Wall(getLocationX(), getLocationY());
    }

}
