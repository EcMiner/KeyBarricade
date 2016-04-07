package group2.keybarricade.tile;

import group2.keybarricade.interactable.InteractableObject;
import java.awt.Graphics;

public class Corridor extends Tile {

    /**
     * The InteractableObject on the corridor, if this is null, the corridor is
     * empty
     *
     * @see InteractableObject
     */
    private InteractableObject interactableObject;

    /**
     * We use this for unit testing
     *
     * @param obj The other Corridor object
     * @return Returns whether the two corridor objects are the same
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Corridor) {
            Corridor corridor2 = (Corridor) obj;
            return super.equals(obj) && ((corridor2.interactableObject == null && this.interactableObject == null) || corridor2.getInteractableObject().equals(this.getInteractableObject()));
        }
        return false;
    }

    public Corridor(int x, int y) {
        super(x, y);
    }

    public Corridor(int x, int y, InteractableObject interactableObject) {
        super(x, y);
        this.interactableObject = interactableObject;
    }

    /**
     * Gets the InteractableObject on the Corridor
     *
     * @return Returns the InteractableObject on the Corridor
     * @see InteractableObject
     */
    public InteractableObject getInteractableObject() {
        return interactableObject;
    }

    /**
     * This method will tell the InteractableObject to paint on this Corridor
     * tile, if the Corridor has one
     *
     * @param g The graphics to paint on
     * @see Graphics
     */
    @Override
    public void paint(Graphics g) {
        if (interactableObject != null) {
            // Tell the interactable object to paint on this tile
            interactableObject.paint(g, getWidth());
        }
    }

    /**
     * Clones the Corridor tile so we are able to reset playfields
     *
     * @return Returns the cloned playfield
     */
    @Override
    public Tile clone() {
        return new Corridor(getLocationX(), getLocationY(), interactableObject);
    }

    /**
     * Removes the InteractableObject from the Corridor, like when picking up a
     * key, it needs to be removed from the Corridor
     */
    public void removeInteractableObject() {
        interactableObject = null;

        // Repaint to update the Tile, so the InteractableObject actually gets removed from the screen
        repaint();
    }

}
