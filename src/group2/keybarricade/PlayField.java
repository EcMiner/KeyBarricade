package group2.keybarricade;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;

public class PlayField extends JPanel {

    private static int currentId = 0;

    private final int horizontalTiles;
    private final int verticalTiles;
    private final int id;
    private final ArrayList<Tile> tiles;
    private final Tile startTile;
    private final Player player;

    public PlayField(int horizontalTiles, int verticalTiles, ArrayList<Tile> tiles, Tile startTile) {
        this(horizontalTiles, verticalTiles, tiles, currentId++, startTile);
    }

    private PlayField(int horizontalTiles, int verticalTiles, ArrayList<Tile> tiles, int id, Tile startTile) {
        setSize(600, 600);
        this.horizontalTiles = horizontalTiles;
        this.verticalTiles = verticalTiles;
        this.id = id;
        this.tiles = tiles;
        this.startTile = startTile;
        this.player = new Player(startTile);
        startTile.findNeighbours(tiles);
        for (Tile tile : tiles) {
            tile.findNeighbours(tiles);
            tile.setSize((getWidth() / horizontalTiles), (getWidth() / horizontalTiles));
            tile.setLocation(tile.getLocationX() * (getWidth() / horizontalTiles), tile.getLocationY() * (getWidth() / horizontalTiles));
            add(tile);
            System.out.println(tile.getX() + "," + tile.getY());
        }
        add(player);
        repaint();
    }

    public PlayField clone() {
        ArrayList<Tile> clonedTiles = new ArrayList<>();
        for (Tile tile : tiles) {
            clonedTiles.add(tile.clone());
        }
        PlayField cloned = new PlayField(horizontalTiles, verticalTiles, clonedTiles, id, startTile.clone());
        return cloned;
    }

    public int getId() {
        return id;
    }

    public void resizeField(int frameWidth, int frameHeight) {
        int cubeSize = Math.min(frameWidth / horizontalTiles, frameHeight / verticalTiles);
        setSize(cubeSize * horizontalTiles, cubeSize * verticalTiles);
        setLocation((frameWidth - getWidth()) / 2, 0);

        for (Tile tile : tiles) {
            tile.resize(cubeSize);
            tile.setLocation(tile.getLocationX() * cubeSize, tile.getLocationY() * cubeSize);
        }
        player.resize(cubeSize);
        invalidate();
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        int cubeSize = getWidth() / horizontalTiles;
        g.setColor(Color.BLACK);

        for (int i = 0; i <= horizontalTiles; i++) {
            g.drawLine(i * cubeSize, 0, i * cubeSize, getHeight());

        }
        for (int i = 0; i <= verticalTiles; i++) {
            g.drawLine(0, i * cubeSize, getWidth(), i * cubeSize);
        }
        g.drawLine(getWidth() - 1, 0, getWidth() - 1, getHeight());
        g.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
    }

    /**
     * We have a getPlayer because {@link KeyBarricade} needs to be able to get
     * the player in order for the key listener to tell the player to move.
     *
     * @return
     */
    public Player getPlayer() {
        return player;
    }

}
