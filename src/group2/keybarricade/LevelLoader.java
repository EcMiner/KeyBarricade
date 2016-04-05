package group2.keybarricade;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class LevelLoader {

    private final JsonParser parser = new JsonParser();

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

    PlayField loadLevel(InputStream input) {
        try {
            JsonObject obj = parser.parse(new InputStreamReader(input)).getAsJsonObject();

            if (obj.has("playfieldWidth") && obj.has("playfieldHeight") && (obj.has("tiles") && obj.get("tiles").isJsonArray())) {
                int horizontalTiles = obj.get("playfieldWidth").getAsInt();
                int verticalTiles = obj.get("playfieldHeight").getAsInt();

                ArrayList<Tile> tiles = new ArrayList<>();
                JsonArray jsonTiles = obj.get("tiles").getAsJsonArray();
                Tile startField = null;
                for (JsonElement element : jsonTiles) {
                    if (element.isJsonObject()) {

                        JsonObject jsonTile = element.getAsJsonObject();

                        if (jsonTile.has("x") && jsonTile.has("y") && jsonTile.has("type")) {
                            int x = jsonTile.get("x").getAsInt();
                            int y = jsonTile.get("y").getAsInt();

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
                if (startField != null) {
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
