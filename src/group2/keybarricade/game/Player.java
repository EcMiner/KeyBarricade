package group2.keybarricade.game;

import group2.keybarricade.toolbars.NotificationType;
import group2.keybarricade.utilities.ImageUtil;
import group2.keybarricade.tile.Tile;
import group2.keybarricade.tile.Corridor;
import group2.keybarricade.interactable.EndField;
import group2.keybarricade.interactable.Key;
import group2.keybarricade.interactable.InteractableObject;
import group2.keybarricade.interactable.Barricade;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;
import static group2.keybarricade.utilities.ImageUtil.*;
import java.awt.Graphics;
import javax.swing.JOptionPane;

public class Player extends JComponent {

    private static final BufferedImage[] images = new BufferedImage[]{loadImage("/images/playerleft.png"),
        loadImage("/images/playerright.png"), loadImage("/images/playerup.png"), loadImage("/images/playerdown.png")};

    private BufferedImage currentImage;
    private Tile currentTile;
    private Key key;
    private KeyBarricade keyBarricade;

    public Player(Tile startTile) {
        this.currentTile = startTile;
        setImage(Direction.DOWN);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof Player) {
            Player player2 = (Player) obj;
            return player2.currentTile.equals(this.currentTile)
                    && ((this.key == null && player2.key == null) || this.key.equals(player2.key))
                    && this.currentImage == player2.currentImage;
        }
        return false;
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
        repaint();
    }

    public void move(Direction direction) {
        setImage(direction);
        if (canMove(direction)) {
            currentTile = currentTile.getNeighbour(direction);
            setLocation(currentTile.getX(), currentTile.getY());
            tryInteraction();
        }
    }

    private boolean canMove(Direction direction) {
        Tile neighbour = currentTile.getNeighbour(direction);
        if (neighbour != null && neighbour instanceof Corridor) {
            Corridor corridor = (Corridor) neighbour;
            if (corridor.getInteractableObject() != null) {
                InteractableObject interactableObject = corridor.getInteractableObject();

                if (interactableObject instanceof Barricade) {
                    if (key != null && ((Barricade) interactableObject).keyFits(key)) {
                        keyBarricade.getBottomBar().showNotification("You opened a barricade!", NotificationType.SUCCESS);
                        return true;
                    } else {
                        keyBarricade.getBottomBar().showNotification("You don't have the correct key!", NotificationType.ERROR);
                    }
                } else if (interactableObject instanceof Key) {
                    keyBarricade.getBottomBar().showNotification("You picked up a key!", NotificationType.SUCCESS);
                    return true;
                } else if (interactableObject instanceof EndField) {
                    keyBarricade.getBottomBar().showNotification("You completed the map!", NotificationType.SUCCESS);
                    return true;
                }
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    private void tryInteraction() {
        if (currentTile instanceof Corridor) {
            Corridor corridor = (Corridor) currentTile;
            if (corridor.getInteractableObject() != null) {
                InteractableObject interactableObject = corridor.getInteractableObject();
                if (interactableObject instanceof Key) {
                    this.key = (Key) interactableObject;
                    keyBarricade.getBottomBar().setKey(this.key);
                    corridor.removeInteractableObject();
                } else if (interactableObject instanceof Barricade) {
                    corridor.removeInteractableObject();
                } else if (interactableObject instanceof EndField) {
                    if (keyBarricade != null && keyBarricade.getCurrentPlayField() != null) {
                        keyBarricade.getCurrentPlayField().setFinished(true);
                        if (keyBarricade.getCurrentPlayField().getId() < keyBarricade.getPlayFields().size() - 1) {
                            int result = JOptionPane.showConfirmDialog(keyBarricade, "Next level?", "Do you want to continue to the next level?", JOptionPane.YES_NO_OPTION);
                            if (result == JOptionPane.CANCEL_OPTION || result == JOptionPane.NO_OPTION) {
                                // TODO Go to home screen.
                            } else if (result == JOptionPane.YES_OPTION) {
                                keyBarricade.start(keyBarricade.getCurrentPlayField().getId() + 1);
                            }
                        }
                    }
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

    public void setKeyBarricade(KeyBarricade keyBarricade) {
        this.keyBarricade = keyBarricade;
    }
}
