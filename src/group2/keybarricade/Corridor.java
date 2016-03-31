package group2.keybarricade;

import java.awt.Graphics;

public class Corridor extends Tile {

    private InteractableObject interactableObject;

    public Corridor(int x, int y) {
        super(x, y);
    }

    public Corridor(int x, int y, InteractableObject interactableObject) {
        super(x, y);
        this.interactableObject = interactableObject;
    }

    public InteractableObject getInteractableObject() {
        return interactableObject;
    }

    @Override
    public void paint(Graphics g) {
        if (interactableObject != null) {
            interactableObject.paint(g, getWidth());
        }
    }

    @Override
    public Tile clone() {
        return new Corridor(getLocationX(), getLocationY(), interactableObject);
    }

    public void removeInteractableObject() {
        interactableObject = null;
        repaint();
    }

}
