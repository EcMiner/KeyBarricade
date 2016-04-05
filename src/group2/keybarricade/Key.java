package group2.keybarricade;

import java.awt.image.BufferedImage;
import static group2.keybarricade.ImageUtil.*;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

public class Key extends InteractableObject {

    public static final BufferedImage image = loadImage("/images/key.png");

    private final int pinCode;

    public Key(int pinCode) {
        super(image);
        this.pinCode = pinCode;
    }
    
    @Override
    public boolean equals (Object obj){
       if(obj instanceof Key) {
           Key key2 = (Key) obj;
           return key2.pinCode == this.pinCode;
       }
        return false;
    }
    
    public int getPinCode() {
        return pinCode;
    }

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
