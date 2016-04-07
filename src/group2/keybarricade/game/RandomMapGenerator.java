package group2.keybarricade.game;

import group2.keybarricade.interactable.Barricade;
import group2.keybarricade.interactable.EndField;
import group2.keybarricade.interactable.Key;
import group2.keybarricade.tile.Corridor;
import group2.keybarricade.tile.Tile;
import group2.keybarricade.tile.Wall;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class RandomMapGenerator {

    /**
     * The PlayField that was generated most recently, this instance will be
     * used to reset random maps using the reset button
     *
     * @see PlayField
     */
    private static PlayField lastGeneratedPlayField;

    /**
     * Gets the most recently generated playfield
     *
     * @return Returns the most recently generated playfield
     * @see PlayField
     */
    public static PlayField getLastGeneratedPlayField() {
        return lastGeneratedPlayField;
    }

    private final Random random = new Random();
    private final HashMap<Point, Tile> tiles = new HashMap<>();
    private final int horizontalTiles;
    private final int verticalTiles;

    public RandomMapGenerator() {
        // This will generate a square field size beteen 10 and 15
        verticalTiles = horizontalTiles = 10 + random.nextInt(6);
    }

    /**
     * This method will generate a random PlayField using a small amount of
     * logic.
     *
     * @return Returns a randomly generated PlayField
     * @see PlayField
     */
    public PlayField generateRandomMap() {
        Corridor startTile;
        Corridor endTile;

        // This will generate a number between 0-3, which will be used to decide
        // on which side the startfield will be. The start field will always be at the edge of the playfield
        // the endfield will always be on the other side of the playfield
        switch (random.nextInt(4)) {
            // The startTile will be at the top edge, the endfield at the bottom
            case 0:
                startTile = new Corridor(random.nextInt(horizontalTiles), 0);
                endTile = new Corridor(random.nextInt(horizontalTiles), verticalTiles - 1, new EndField());
                break;
            // The startTile will be at the right edge, the endfield at the left
            case 1:
                startTile = new Corridor(horizontalTiles - 1, random.nextInt(verticalTiles));
                endTile = new Corridor(0, random.nextInt(verticalTiles), new EndField());
                break;
            // The startTile will be at the bottom edge, the endfield at the top
            case 2:
                startTile = new Corridor(random.nextInt(horizontalTiles), verticalTiles - 1);
                endTile = new Corridor(random.nextInt(horizontalTiles), 0, new EndField());
                break;
            // The startTile will be at the left edge, the endfield at the right
            default:
                startTile = new Corridor(0, random.nextInt(verticalTiles));
                endTile = new Corridor(horizontalTiles - 1, random.nextInt(verticalTiles), new EndField());
                break;
        }
        // Add the end & startfield to the tiles HashMap, we do this to make sure no other
        // tiles are generated at the same location
        tiles.put(new Point(startTile.getLocationX(), startTile.getLocationY()), startTile);
        tiles.put(new Point(endTile.getLocationX(), endTile.getLocationY()), endTile);

        /*
         This will generate random walls / endfields as a square around the endfield
         There will always be at least one barricade, so the generateAroundEndField,
         the generateAroundEndField method returns the pincode of that one barricade
         that is always created
         */
        int pinCode = generateAroundEndField(endTile);

        // This loop will make sure that at least 2 keys with the above pinCode
        // are generated
        for (int i = 0; i < 2; i++) {
            Point randomPoint = randomPoint();

            tiles.put(randomPoint, new Corridor((int) randomPoint.getX(), (int) randomPoint.getY(), new Key(pinCode)));
        }

        // This will generate a random amount of keys, with a max of 13 and a min of 3
        for (int i = 0; i < 3 + random.nextInt(11); i++) {
            // Generate a random unused point
            Point randomPoint = randomPoint();

            // Add this point to the tiles HashMap to make sure no other tiles gets generated over it
            tiles.put(randomPoint, new Corridor((int) randomPoint.getX(), (int) randomPoint.getY(), new Key(randomPinCode())));
        }

        // This will generate a random amount of barricades, with a minimum and a maximum
        // This minimum and maximum will get higher if the playfield size is higher
        for (int i = 0; i < (15 + (verticalTiles - 10) * 4) + random.nextInt(33 + (verticalTiles - 10) * 3); i++) {
            Point randomPoint = randomPoint();

            // Add this point to the tiles HashMap to make sure no other tiles gets generated over it
            tiles.put(randomPoint, new Corridor((int) randomPoint.getX(), (int) randomPoint.getY(), new Barricade(randomPinCode())));
        }

        // This will generate a random amount of barricades, with a minimum and a maximum
        // This minimum and maximum will get higher if the playfield size is higher
        for (int i = 0; i < (15 + (verticalTiles - 10) * 4) + random.nextInt(40 + (verticalTiles - 10) * 3); i++) {
            Point randomPoint = randomPoint();

            // Add this point to the tiles HashMap to make sure no other tiles gets generated over it
            tiles.put(randomPoint, new Wall((int) randomPoint.getX(), (int) randomPoint.getY()));
        }

        // This for loop will fill all unused tiles on the playfield with empty corridor
        for (int x = 0; x < horizontalTiles; x++) {
            for (int y = 0; y < horizontalTiles; y++) {
                Point point = new Point(x, y);
                // Check if the point hasn't been used yet
                if (!tiles.containsKey(point)) {
                    tiles.put(point, new Corridor(x, y));
                }
            }
        }

        PlayField playField = new PlayField(horizontalTiles, verticalTiles, new ArrayList<>(tiles.values()), startTile);

        // Set the last generated playfield to this generated one
        lastGeneratedPlayField = playField.clone();

        // Clear the tiles HashMap so you can re-use this class and generate a new random map (if necessary, not recommended)
        tiles.clear();
        return playField;
    }

    /**
     * This will generate a square of random walls / barricades around the
     * endfield tile. It will always generate one random barricade next to the
     * endfield to lower the chance of the random playfield not being solvable
     * lower. This method will return the pincode of this one barricade that is
     * always generated.
     *
     * @param endField The tile with the EndField interactable object on it
     * @return Returns the pincode of the one barricade that will always be
     * generated
     * @see Tile
     */
    private int generateAroundEndField(Tile endField) {
        Corridor barricade;

        if (endField.getLocationX() == 0) {
            // If the x location is 0, that means it's at the left edge of the playfield
            // Generate the barricade to the right of the endfield
            barricade = new Corridor(endField.getLocationX() + 1, endField.getLocationY(), new Barricade(randomPinCode()));
        } else if (endField.getLocationX() == horizontalTiles - 1) {
            // If the x location is horizontalTiles - 1, that means it's at the right edge of the playfield
            // Generate the barricade to the left of the endfield
            barricade = new Corridor(endField.getLocationX() - 1, endField.getLocationY(), new Barricade(randomPinCode()));
        } else if (endField.getLocationY() == 0) {
            // If the y location is 0, that means it's at the top edge of the playerfield
            // Generate the barricade below the endfield
            barricade = new Corridor(endField.getLocationX(), endField.getLocationY() + 1, new Barricade(randomPinCode()));
        } else {
            // If the y location is verticalTiles - 1, that means it's at the bottom edge of the playfield
            // Generate the barricade above the endfield
            barricade = new Corridor(endField.getLocationX(), endField.getLocationY() - 1, new Barricade(randomPinCode()));
        }
        // Add this point to the tiles HashMap to make sure no other tiles gets generated over it
        tiles.put(new Point(barricade.getLocationX(), barricade.getLocationY()), barricade);

        // Loop through all the locations in a square around the endfield
        for (int x = endField.getLocationX() - 1; x <= endField.getLocationX() + 1; x++) {
            for (int y = endField.getLocationY() - 1; y <= endField.getLocationY() + 1; y++) {
                Point point = new Point(x, y);
                // Check if the x and y is in the playfield, and not outside it
                // Also check if there isn't a tile already on that location
                if (x >= 0 && x < horizontalTiles && y >= 0 && y < verticalTiles && !tiles.containsKey(point)) {
                    // Add this point to the tiles HashMap to make sure no other tiles gets generated over it
                    tiles.put(point, random.nextBoolean() ? new Wall(x, y) : new Corridor(x, y, new Barricade(randomPinCode())));
                }
            }
        }

        return ((Barricade) barricade.getInteractableObject()).getPinCode();
    }

    /**
     * This method will generate a random pincode.
     *
     * @return Returns a random pincode
     */
    private int randomPinCode() {
        return (random.nextInt(5) + 1) * 50;
    }

    /**
     * This method will generate a random point on the playfield, it also makes
     * sure that there isn't a tile already on the randomly generated point.
     *
     * @return Returns a random point on the playfield
     */
    private Point randomPoint() {
        // Checks if the playfield isn't already full of tiles
        if (tiles.size() < horizontalTiles * verticalTiles) {
            Point randomPoint = new Point(random.nextInt(horizontalTiles),
                    random.nextInt(verticalTiles));

            // Generates a new random point as long as there is already a tile on that point
            // As soon as there isn't a tile on it, it will break out of the while loop and return that point
            while (tiles.containsKey(randomPoint)) {
                randomPoint = new Point(random.nextInt(horizontalTiles),
                        random.nextInt(verticalTiles));
            }
            return randomPoint;
        }
        return null;
    }

}
