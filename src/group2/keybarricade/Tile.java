package group2.keybarricade;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JComponent;

public abstract class Tile extends JComponent {

    private final int locationX;
    private final int locationY;
    private final HashMap<Direction, Tile> neighbours = new HashMap<>();

    public Tile(int x, int y) {
        this.locationX = x;
        this.locationY = y;
        setOpaque(true);
    }

    public int getLocationX() {
        return locationX;
    }

    public int getLocationY() {
        return locationY;
    }

    @Override
    public abstract void paint(Graphics g);

    public void resize(int cubeSize) {
        // TODO Resize shit
        System.out.println("Cube size: " + cubeSize);
        setSize(cubeSize, cubeSize);
        repaint();
    }

    public void findNeighbours(ArrayList<Tile> tiles) {
        for (Tile tile : tiles) {
            if (tile.getLocationX() == locationX - 1 && tile.getLocationY() == locationY) {
                neighbours.put(Direction.LEFT, tile);
            } else if (tile.getLocationX() == locationX + 1 && tile.getLocationY() == locationY) {
                neighbours.put(Direction.RIGHT, tile);
            } else if (tile.getLocationX() == locationX && tile.getLocationY() == locationY + 1) {
                neighbours.put(Direction.DOWN, tile);
            } else if (tile.getLocationX() == locationX && tile.getLocationY() == locationY - 1) {
                neighbours.put(Direction.UP, tile);
            }
        }
        System.out.println("Neighbours: " + neighbours.size());
    }

    public abstract Tile clone();

    public Tile getNeighbour(Direction direction) {
        return neighbours.get(direction);
    }
}
