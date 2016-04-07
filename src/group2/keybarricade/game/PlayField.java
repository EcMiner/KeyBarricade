package group2.keybarricade.game;

import group2.keybarricade.tile.Tile;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;

public class PlayField extends JPanel {

    /**
     * A static id that will automatically increment when a new PlayField is
     * created
     */
    private static int currentId = 0;

    private final int horizontalTiles;
    private final int verticalTiles;
    private final int id;
    private final ArrayList<Tile> tiles;
    private final Tile startTile;
    private final Player player;
    private boolean finished;

    public PlayField(int horizontalTiles, int verticalTiles, ArrayList<Tile> tiles, Tile startTile) {
        this(horizontalTiles, verticalTiles, tiles, currentId++, startTile);
    }

    private PlayField(int horizontalTiles, int verticalTiles, ArrayList<Tile> tiles, int id, Tile startTile) {
        // Set the layout to null to stop interference
        setLayout(null);

        // Make sure that the amount of tiles are never lower or equals to 0
        this.horizontalTiles = Math.max(1, horizontalTiles);
        this.verticalTiles = Math.max(1, verticalTiles);
        this.id = id;
        this.tiles = tiles;
        this.startTile = startTile;
        this.player = new Player(startTile);
        // Loop through all tiles to find their neighbours and set the position and size
        for (Tile tile : tiles) {
            // Tell the tile to find his own neighbours
            tile.findNeighbours(tiles);
            // Set the size of the tile
            tile.setSize((getWidth() / horizontalTiles), (getWidth() / horizontalTiles));
            // Set the correct location of the tile
            tile.setLocation(tile.getLocationX() * (getWidth() / horizontalTiles), tile.getLocationY() * (getWidth() / horizontalTiles));
            // Add the tile to the screen so it will appear
            add(tile);
        }
        // Add the player to the panel
        add(player);

        // Repaint to make sure everything is painted corrrectly
        repaint();
    }

    /**
     * Clones the PlayField object so we are able to reset playfields.
     *
     * @return Returns the cloned PlayField
     */
    public PlayField clone() {
        ArrayList<Tile> clonedTiles = new ArrayList<>();
        Tile startTile = null;
        // Loop through all tiles and clone them
        for (Tile tile : tiles) {
            Tile cloned = tile.clone();
            clonedTiles.add(cloned);
            // Check if the tile is the startTile, if so clone it so we can use it in the constructor
            if (tile.getX() == this.startTile.getX() && tile.getY() == this.startTile.getY()) {
                startTile = cloned;
            }
        }
        // Create the new PlayField instance
        PlayField cloned = new PlayField(horizontalTiles, verticalTiles, clonedTiles, id, startTile);
        return cloned;
    }

    /**
     * Gets the id of the PlayField
     *
     * @return Returns the id of the play field
     */
    public int getId() {
        return id;
    }

    /**
     * This method will be called when the JFrame is resized. It will then
     * resize and reposition all the tiles, and the player
     *
     * @param frameWidth
     * @param frameHeight
     */
    public void resizeField(int frameWidth, int frameHeight) {
        if (frameWidth > 0 && frameHeight > 0) {
            // Get the smallest cube size
            int cubeSize = Math.min(frameWidth / horizontalTiles, frameHeight / verticalTiles);

            // Set the size of the panel
            setSize(cubeSize * horizontalTiles, cubeSize * verticalTiles);

            // Center the panel
            setLocation((frameWidth - getWidth()) / 2, 0);

            // Loop through all tiles and resize and reposition them
            for (Tile tile : tiles) {
                tile.resize(cubeSize);
                tile.setLocation(tile.getLocationX() * cubeSize, tile.getLocationY() * cubeSize);
            }
            // Resize the player
            player.resize(cubeSize);

            // Repaint to make sure everything is painted correctly
            invalidate();
            repaint();
        }
    }

    /**
     * This method will paint the borders of the PlayField
     *
     * @param g The graphics object
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // Determine the cubeSize
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
     * @return Returns the player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets whether the player has finished this level
     *
     * @return Returns whether the player has finished this level
     */
    public boolean isFinished() {
        return finished;
    }

    /**
     * Sets whether the player has finished this level
     *
     * @param finished A boolean whether the playfield has finished
     */
    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    /**
     * We use this method for our tests
     *
     * @param obj The other playfield
     * @return Returns whether the two playfields are exactly the same
     */
    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof PlayField) {
            PlayField playField2 = (PlayField) obj;
            if (this.tiles.size() == playField2.tiles.size()) {
                for (int i = 0; i < this.tiles.size(); i++) {
                    if (!this.tiles.get(i).equals(playField2.tiles.get(i))) {
                        return false;
                    }
                }
                return playField2.getId() == getId() && playField2.horizontalTiles == this.horizontalTiles
                        && playField2.verticalTiles == this.verticalTiles
                        && playField2.player.equals(this.player);
            }
        }
        return false;
    }

    /**
     * Gets the amount of tiles vertically
     *
     * @return Returns the amount of tiles vertically
     */
    public int getVerticalTiles() {
        return verticalTiles;
    }

    /**
     * Gets the amount of tiles horizontally
     *
     * @return Returns the amount of tiles horizontally
     */
    public int getHorizontalTiles() {
        return horizontalTiles;
    }

    /**
     * Gets all the tiles on the PlayField
     *
     * @return Returns all the tiles on the PlayField
     */
    public ArrayList<Tile> getTiles() {
        return tiles;
    }
}
