package group2.keybarricade;

import java.awt.image.BufferedImage;
import javax.swing.JComponent;
import static group2.keybarricade.ImageUtil.*;
import java.awt.Graphics;

public class Player extends JComponent {

    private static final BufferedImage[] images = new BufferedImage[]{loadImage("/images/playerleft.png"),
        loadImage("/images/playerright.png"), loadImage("/images/playerup.png"), loadImage("/images/playerdown.png")};

    private BufferedImage currentImage;
    private Tile currentTile;
    private Key key;

    public Player(Tile startTile) {
        this.currentTile = startTile;
        setImage(Direction.DOWN);
    }

    public void setImage(Direction direction) {
        switch (direction) {
            case LEFT:
                currentImage = images[0];
                break;
            case RIGHT:
                currentImage = images[1];
                break;
            case UP:
                currentImage = images[2];
                break;
            default:
                currentImage = images[3];
                break;
        }
        repaint();
    }

    public void resize(int cubeSize) {
        setSize(cubeSize, cubeSize);
        setLocation(currentTile.getX(), currentTile.getY());
    }

    public void move(Direction direction) {
        setImage(direction);
        if (canMove(direction)) {
            currentTile = currentTile.getNeighbour(direction);
            System.out.println(currentTile.getLocationX() + ", " + currentTile.getLocationY());
            setLocation(currentTile.getX(), currentTile.getY());
            tryInteraction();
        }
    }

    public boolean canMove(Direction direction) {
        Tile neighbour = currentTile.getNeighbour(direction);
        if (neighbour != null && neighbour instanceof Corridor) {
            Corridor corridor = (Corridor) neighbour;
            if (corridor.getInteractableObject() != null) {
                InteractableObject interactableObject = corridor.getInteractableObject();
                return interactableObject instanceof Key || interactableObject instanceof EndField
                        || (interactableObject instanceof Barricade && key != null && ((Barricade) interactableObject).keyFits(key));
            } else {
                return true;
            }
        }
        return false;
    }

    public void tryInteraction() {
        if (currentTile instanceof Corridor) {
            Corridor corridor = (Corridor) currentTile;
            if (corridor.getInteractableObject() != null) {
                InteractableObject interactableObject = corridor.getInteractableObject();
                if (interactableObject instanceof Key) {
                    this.key = (Key) interactableObject;
                    corridor.removeInteractableObject();
                } else if (interactableObject instanceof Barricade) {
                    corridor.removeInteractableObject();
                }
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int cubeSize = getWidth();
        BufferedImage resized = ImageUtil.resize(currentImage, cubeSize);
        g.drawImage(resized, (cubeSize - resized.getWidth()) / 2, (cubeSize - resized.getHeight()) / 2, null);
    }

}
