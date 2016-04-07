package group2.keybarricade.tile;

import group2.keybarricade.game.Direction;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JComponent;

public abstract class Tile extends JComponent {

    private final int locationX;
    private final int locationY;
    private final HashMap<Direction, Tile> neighbours = new HashMap<>();

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof Tile) {
            Tile tile2 = (Tile) obj;
            return tile2.locationX == this.locationX && tile2.locationY == this.locationY;
        }
        return false;
    }

    public Tile(int x, int y) {
        this.locationX = x;
        this.locationY = y;
    }

    /**
     * Gets the x location on the playfield
     *
     * @return Returns the x location on the playfield
     */
    public int getLocationX() {
        return locationX;
    }

    /**
     * Gets the y location on the playfield
     *
     * @return Returns the y location on the playfield
     */
    public int getLocationY() {
        return locationY;
    }

    /**
     * Forces the subclasses to override this paint method and paint something
     * on it
     *
     * @param g The graphics to paint on
     */
    @Override
    public abstract void paint(Graphics g);

    /**
     * Resizes the tile
     *
     * @param cubeSize The cube size to resize to
     */
    public void resize(int cubeSize) {
        setSize(cubeSize, cubeSize);
        repaint();
    }

    /**
     * This method will find all the neighbours of the tile
     *
     * @param tiles All the tiles on the playfield
     */
    public void findNeighbours(ArrayList<Tile> tiles) {
        // Loop through all the tiles and check if they're a neighbour
        for (Tile tile : tiles) {
            if (tile.getLocationX() == locationX - 1 && tile.getLocationY() == locationY) {
                // If the x location is - 1 and y location is the same, it's to the left
                neighbours.put(Direction.LEFT, tile);
            } else if (tile.getLocationX() == locationX + 1 && tile.getLocationY() == locationY) {
                // If the x location is + 1 and y location is the same, it's to the right
                neighbours.put(Direction.RIGHT, tile);
            } else if (tile.getLocationX() == locationX && tile.getLocationY() == locationY + 1) {
                // If the y location is + 1 and x location is the same, it's below
                neighbours.put(Direction.DOWN, tile);
            } else if (tile.getLocationX() == locationX && tile.getLocationY() == locationY - 1) {
                // If the y location is - 1 and yxlocation is the same, it's above
                neighbours.put(Direction.UP, tile);
            }
        }
    }

    /**
     * Forces the subclasses to override this clone method which is used to be
     * able to reset playfields
     *
     * @return Returns the cloned tile
     */
    public abstract Tile clone();

    /**
     * Gets a neighbour in a specific direction
     *
     * @param direction The direction
     * @return Returns a neighbour in a direction
     * @see Direction
     */
    public Tile getNeighbour(Direction direction) {
        return neighbours.get(direction);
    }
}
