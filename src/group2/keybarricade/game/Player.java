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

    // All the images for the direction
    private static final BufferedImage[] images = new BufferedImage[]{loadImage("/images/playerleft.png"),
        loadImage("/images/playerright.png"), loadImage("/images/playerup.png"), loadImage("/images/playerdown.png")};

    private BufferedImage currentImage;
    private Tile currentTile;
    private Key key;
    private KeyBarricade keyBarricade;
    private final Score score;

    public Player(Tile startTile) {
        this.currentTile = startTile;
        // Set the default image to the one looking down
        setImage(Direction.DOWN);
        score = new Score();
    }

    /**
     * We use this for testing
     *
     * @param obj The other Player object
     * @return Returns whether the two player objects are the same
     */
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

    /**
     * Sets the image of the player to the direction it's looking at
     *
     * @param direction The direction to change
     * @see Direction
     */
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

    /**
     * Resize and reposition the Player
     *
     * @param cubeSize The cubesize
     */
    public void resize(int cubeSize) {
        setSize(cubeSize, cubeSize);
        setLocation(currentTile.getX(), currentTile.getY());
        repaint();
    }

    /**
     * Try to move the player in a certain direction, and do interaction if
     * possible
     *
     * @param direction The direction to move in
     * @see Direction
     */
    public void move(Direction direction) {
        setImage(direction);

        // Checks if the player can move to that direction
        if (canMove(direction)) {
            // Changes the current tile
            currentTile = currentTile.getNeighbour(direction);
            // Repositions
            setLocation(currentTile.getX(), currentTile.getY());

            // Tries to interact
            tryInteraction();

            // Adds a step to the score
            score.addStep();
        }
    }

    /**
     * Checks whether a player can move in a direction
     *
     * @param direction The direction to move in
     * @return Returns whether a player can move in a direction
     */
    private boolean canMove(Direction direction) {
        Tile neighbour = currentTile.getNeighbour(direction);
        // Checks if the current tile has a neighbour in the direction and isn't a wall
        if (neighbour != null && neighbour instanceof Corridor) {
            Corridor corridor = (Corridor) neighbour;
            if (corridor.getInteractableObject() != null) {
                InteractableObject interactableObject = corridor.getInteractableObject();

                // Checks if the interactable object is a barricade and if the player has the right key to open it
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
                // Return true if there is no interatable object (a.k.a empty corridor)
                return true;
            }
        }
        return false;
    }

    /**
     * Tries to do the interaction with the current tile, so if the player is on
     * a key then pick it up
     */
    private void tryInteraction() {
        if (currentTile instanceof Corridor) {
            Corridor corridor = (Corridor) currentTile;
            if (corridor.getInteractableObject() != null) {
                InteractableObject interactableObject = corridor.getInteractableObject();
                if (interactableObject instanceof Key) {
                    // If the interactable object is a key, set the player's picked up key to the key you're standing on
                    this.key = (Key) interactableObject;

                    // Update the bottom bor to show the pin code of the key you just picked up
                    keyBarricade.getBottomBar().setKey(this.key);

                    // Remove the key from the corridor tile
                    corridor.removeInteractableObject();
                } else if (interactableObject instanceof Barricade) {
                    // Remove the barricade from the corridor tile. You don't need to check if the key's pin code match
                    // Because you did that in Player#canMove(direction)
                    corridor.removeInteractableObject();
                } else if (interactableObject instanceof EndField) {
                    if (keyBarricade != null && keyBarricade.getCurrentPlayField() != null) {
                        // If the interactable object is an endfield, tell the playfield that the player just finished the level
                        keyBarricade.getCurrentPlayField().setFinished(true);

                        // Checks whether the current play field is in the loaded playfields array
                        if (keyBarricade.getCurrentPlayField().getId() < keyBarricade.getPlayFields().size() - 1) {
                            // Prompt the player's stats and ask them if they want to continue to the next level
                            int result = JOptionPane.showConfirmDialog(keyBarricade, "You completed this level in " + score.getSteps() + " steps and " + score.getDurationSeconds() + "s. \nNext level?", "Do you want to continue to the next level?", JOptionPane.YES_NO_OPTION);
                            if (result == JOptionPane.CANCEL_OPTION || result == JOptionPane.NO_OPTION) {
                                // If they cancelled the pop-up or pressed the no button, return to the homescreen
                                keyBarricade.showHomeScreen();
                            } else if (result == JOptionPane.YES_OPTION) {
                                // If they clicked the yes button start the next level
                                keyBarricade.start(keyBarricade.getCurrentPlayField().getId() + 1);
                            }
                        } else {
                            // If the id isn't in the loaded playfields array, or is the last map in the array, then
                            // prompt the player's stats, and ask if they want to play a new random level
                            int result = JOptionPane.showConfirmDialog(keyBarricade, "You completed this level in " + score.getSteps() + " steps and " + score.getDurationSeconds() + "s. \nNew random level?", "You completed the game!", JOptionPane.YES_NO_OPTION);
                            if (result == JOptionPane.CANCEL_OPTION || result == JOptionPane.NO_OPTION) {
                                // If they cancel or click the no button, go back to the home screen
                                keyBarricade.showHomeScreen();
                            } else if (result == JOptionPane.YES_OPTION) {
                                // If they clicked the yes button, then generate and load a new random map on the screen
                                keyBarricade.randomMap();
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Paints the player's current image, with the right image size
     *
     * @param g The graphics to paint on
     * @see Graphics
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int cubeSize = getWidth();

        // Resize the current image to fit into the tile
        BufferedImage resized = ImageUtil.resize(currentImage, cubeSize);

        // Draw the image in the center of the tile
        g.drawImage(resized, (cubeSize - resized.getWidth()) / 2, (cubeSize - resized.getHeight()) / 2, null);
    }

    /**
     * Sets the player's KeyBarricade attribute to a KeyBarricade instance. This
     * instance will be used for showing notifications and loading next/random
     * levels
     *
     * @param keyBarricade The KeyBarricade instance
     * @see KeyBarricade
     */
    public void setKeyBarricade(KeyBarricade keyBarricade) {
        this.keyBarricade = keyBarricade;
    }
}
