package group2.keybarricade;

import java.awt.image.BufferedImage;
import static group2.keybarricade.ImageUtil.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

public class Barricade extends InteractableObject {

    private static final BufferedImage image = loadImage("/images/barricade.png");

    private final int pinCode;

    public Barricade(int pinCode) {
        super(image);
        this.pinCode = pinCode;
    }

    public int getPinCode() {
        return pinCode;
    }

    public boolean keyFits(Key key) {
        return this.pinCode == key.getPinCode();
    }
    
      @Override
    public boolean equals (Object obj){
       if(obj instanceof Barricade) {
           Barricade barricade2 = (Barricade) obj;
           return barricade2.pinCode == this.pinCode;
       }
        return false;
    }
    
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
