package group2.keybarricade.game;

import group2.keybarricade.tile.Wall;
import group2.keybarricade.tile.Tile;
import group2.keybarricade.tile.Corridor;
import group2.keybarricade.interactable.EndField;
import group2.keybarricade.interactable.Key;
import group2.keybarricade.interactable.Barricade;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class LevelLoader {

    /**
     * The JsonParser object that will try and parse an InputStream to a
     * JsonObject ({@link #loadLevel(java.io.InputStream) loadLevel}
     */
    private final JsonParser parser = new JsonParser();

    /**
     * This will load all the map files in the maps folder. The maps have to be
     * named map + a number (i.e. 'map1') and the file type needs to be .json
     *
     * @return Returns a list of all the default PlayFields for the game
     */
    public ArrayList<PlayField> loadLevels() {
        ArrayList<PlayField> playFields = new ArrayList<>();

        // Keep looping to check if the map exists
        for (int i = 1;; i++) {
            // Get the inputstream from the map file
            InputStream input = getClass().getResourceAsStream("/maps/map" + i + ".json");

            // Check if the input is not null (so if the file exists)
            if (input != null) {

                // Parse the level from the file
                PlayField playField = loadLevel(input);
                // Check if the playfield isn't null (so the parsing was successfull)
                if (playField != null) {
                    playFields.add(playField);
                }
            } else {
                // As soon as a file doesn't exist, break out of this loop, else we'll get an infinite loop :O
                break;
            }
        }

        return playFields;
    }

    /**
     * This will try and parse a JsonObject from an input stream and from there
     * it will try and parse/deserialize the json to a PlayField object, if
     * possible. We made this method public so we are able to access it from the
     * Test Class LevelLoaderTest
     *
     * @param input The input which needs to be parsed/deserialized
     * @return Returns a PlayField object if the parsing/deserialization was
     * successful
     */
    public PlayField loadLevel(InputStream input) {
        try {
            // Use the JsonParser to try and parse/deserialize the InputStream
            JsonObject obj = parser.parse(new InputStreamReader(input)).getAsJsonObject();

            // Check if all the necessary attributes are defined, if it doesn't have them, it can't freaking parse it
            if (obj.has("playfieldWidth") && obj.has("playfieldHeight") && (obj.has("tiles") && obj.get("tiles").isJsonArray())) {
                // Get the horizontal width from the file, and using Math.max we make sure that it can't be lower or equals to 0
                int horizontalTiles = Math.max(1, obj.get("playfieldWidth").getAsInt());
                int verticalTiles = Math.max(1, obj.get("playfieldHeight").getAsInt());

                ArrayList<Tile> tiles = new ArrayList<>();

                // Retrieve the list of tiles from the file
                JsonArray jsonTiles = obj.get("tiles").getAsJsonArray();
                Tile startField = null;
                boolean hasEndField = false;

                // Loop through all the json tile elements
                for (JsonElement element : jsonTiles) {
                    // Check if it is a json object (and not an array or anything else)
                    if (element.isJsonObject()) {
                        JsonObject jsonTile = element.getAsJsonObject();
                        // Check if it has an x, y and a type value
                        if (jsonTile.has("x") && jsonTile.has("y") && jsonTile.has("type")) {
                            int x = jsonTile.get("x").getAsInt();
                            int y = jsonTile.get("y").getAsInt();

                            // Check if the x and y values are in bound of the field
                            if ((x >= 0 && y >= 0) && (x < horizontalTiles && y < verticalTiles)) {

                                String type = jsonTile.get("type").getAsString().toLowerCase();
                                Tile tile;
                                // Check what kind of type the tile is
                                switch (type) {
                                    case "wall":
                                        tile = new Wall(x, y);
                                        break;
                                    case "key":
                                        // Check if there is a pinCode defined
                                        if (jsonTile.has("pinCode")) {
                                            tile = new Corridor(x, y, new Key(jsonTile.get("pinCode").getAsInt()));
                                        } else {
                                            continue;
                                        }
                                        break;
                                    case "barricade":
                                        // Check if there is a pinCode defined
                                        if (jsonTile.has("pinCode")) {
                                            tile = new Corridor(x, y, new Barricade(jsonTile.get("pinCode").getAsInt()));
                                        } else {
                                            continue;
                                        }
                                        break;
                                    case "endfield":
                                        // Set the hasEndField boolean to true, this means that there is an EndField in the file
                                        hasEndField = true;
                                        tile = new Corridor(x, y, new EndField());
                                        break;
                                    case "startfield":
                                        startField = tile = new Corridor(x, y);
                                        break;
                                    default:
                                        continue;
                                }

                                // Add the tile to the tiles list
                                tiles.add(tile);
                            }
                        }
                    }
                }

                // Check if a startField and an endfield are defined
                if (startField != null && hasEndField) {
                    // Fill in all the empty tiles
                    for (int x = 0; x < horizontalTiles; x++) {
                        for (int y = 0; y < verticalTiles; y++) {
                            boolean tileExists = false;
                            // Check if there is already a tile on this x and y value
                            for (Tile tile : tiles) {
                                if (tile.getLocationX() == x && tile.getLocationY() == y) {
                                    tileExists = true;
                                    break;
                                }
                            }

                            if (!tileExists) {
                                // Add an empty corridor if there is not yet a tile on this position
                                tiles.add(new Corridor(x, y));
                            }
                        }
                    }

                    // Create and return the playfield :D
                    return new PlayField(horizontalTiles, verticalTiles, tiles, startField);
                }
            }
        } catch (Exception ex) {
            // This exception could be called if the file isn't a correctly formatted json file
        }
        return null;
    }

}
