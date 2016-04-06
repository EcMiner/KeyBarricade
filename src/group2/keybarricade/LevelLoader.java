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
     * This will load all the map file in the maps folder. The maps have to be
     * named map + a number (i.e. 'map1') and the file type needs to be .json
     *
     * @return Returns a list of all the default PlayFields for the game
     */
    public ArrayList<PlayField> loadLevels() {
        ArrayList<PlayField> playFields = new ArrayList<>();

        for (int i = 1;; i++) {
            InputStream input = getClass().getResourceAsStream("/maps/map" + i + ".json");
            if (input != null) {
                PlayField playField = loadLevel(input);
                if (playField != null) {
                    playFields.add(playField);
                }
            } else {
                break;
            }
        }

        return playFields;
    }

    /**
     * This will try and parse a JsonObject from an input stream and from there
     * it will try and deserialze the json to a PlayField object, if possible.
     * We made this method public so we are able to access it from the Test
     * Class LevelLoaderTest
     *
     * @param input The input which needs to be deserialized
     * @return Returns a PlayField object if the deserialization was successful
     */
    public PlayField loadLevel(InputStream input) {
        try {
            JsonObject obj = parser.parse(new InputStreamReader(input)).getAsJsonObject();

            if (obj.has("playfieldWidth") && obj.has("playfieldHeight") && (obj.has("tiles") && obj.get("tiles").isJsonArray())) {
                int horizontalTiles = Math.max(1, obj.get("playfieldWidth").getAsInt());
                int verticalTiles = Math.max(1, obj.get("playfieldHeight").getAsInt());

                ArrayList<Tile> tiles = new ArrayList<>();
                JsonArray jsonTiles = obj.get("tiles").getAsJsonArray();
                Tile startField = null;
                boolean hasEndField = false;
                for (JsonElement element : jsonTiles) {
                    if (element.isJsonObject()) {
                        JsonObject jsonTile = element.getAsJsonObject();
                        if (jsonTile.has("x") && jsonTile.has("y") && jsonTile.has("type")) {
                            int x = jsonTile.get("x").getAsInt();
                            int y = jsonTile.get("y").getAsInt();
                            if ((x >= 0 && y >= 0) && (x < horizontalTiles && y < verticalTiles)) {

                                String type = jsonTile.get("type").getAsString().toLowerCase();
                                Tile tile;
                                switch (type) {
                                    case "wall":
                                        tile = new Wall(x, y);
                                        break;
                                    case "key":
                                        if (jsonTile.has("pinCode")) {
                                            tile = new Corridor(x, y, new Key(jsonTile.get("pinCode").getAsInt()));
                                        } else {
                                            continue;
                                        }
                                        break;
                                    case "barricade":
                                        if (jsonTile.has("pinCode")) {
                                            tile = new Corridor(x, y, new Barricade(jsonTile.get("pinCode").getAsInt()));
                                        } else {
                                            continue;
                                        }
                                        break;
                                    case "endfield":
                                        hasEndField = true;
                                        tile = new Corridor(x, y, new EndField());
                                        break;
                                    case "startfield":
                                        startField = tile = new Corridor(x, y);
                                        break;
                                    default:
                                        continue;
                                }
                                tiles.add(tile);
                            }
                        }
                    }
                }
                if (startField != null && hasEndField) {
                    for (int x = 0; x < horizontalTiles; x++) {
                        for (int y = 0; y < verticalTiles; y++) {
                            boolean tileExists = false;
                            for (Tile tile : tiles) {
                                if (tile.getLocationX() == x && tile.getLocationY() == y) {
                                    tileExists = true;
                                    break;
                                }
                            }

                            if (!tileExists) {
                                tiles.add(new Corridor(x, y));
                            }
                        }
                    }
                    return new PlayField(horizontalTiles, verticalTiles, tiles, startField);
                }
            }
        } catch (Exception ex) {
            // This exception could be called if the file isn't a correctly formatted json file
        }
        return null;
    }

}
