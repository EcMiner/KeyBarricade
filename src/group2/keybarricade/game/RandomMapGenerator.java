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

    private static PlayField lastGeneratedPlayField;

    public static PlayField getLastGeneratedPlayField() {
        return lastGeneratedPlayField;
    }

    private final Random random = new Random();
    private final HashMap<Point, Tile> tiles = new HashMap<>();
    private final int horizontalTiles;
    private final int verticalTiles;

    public RandomMapGenerator() {
        verticalTiles = horizontalTiles = 10 + random.nextInt(6);
    }

    public PlayField generateRandomMap() {
        // Random getal tussen 10 en 15
        Corridor startTile;
        Corridor endTile;
        switch (random.nextInt(4)) {
            case 0:
                startTile = new Corridor(random.nextInt(horizontalTiles), 0);
                endTile = new Corridor(random.nextInt(horizontalTiles), verticalTiles - 1, new EndField());
                break;
            case 1:
                startTile = new Corridor(horizontalTiles - 1, random.nextInt(verticalTiles));
                endTile = new Corridor(0, random.nextInt(verticalTiles), new EndField());
                break;
            case 2:
                startTile = new Corridor(random.nextInt(horizontalTiles), verticalTiles - 1);
                endTile = new Corridor(random.nextInt(horizontalTiles), 0, new EndField());
                break;
            default:
                startTile = new Corridor(0, random.nextInt(verticalTiles));
                endTile = new Corridor(horizontalTiles - 1, random.nextInt(verticalTiles), new EndField());
                break;
        }
        tiles.put(new Point(startTile.getLocationX(), startTile.getLocationY()), startTile);
        tiles.put(new Point(endTile.getLocationX(), endTile.getLocationY()), endTile);

        int pinCode = generateAroundEndField(endTile);

        for (int i = 0; i < 2; i++) {
            Point randomPoint = randomPoint();

            tiles.put(randomPoint, new Corridor((int) randomPoint.getX(), (int) randomPoint.getY(), new Key(pinCode)));
        }

        for (int i = 0; i < 3 + random.nextInt(11); i++) {
            Point randomPoint = randomPoint();

            tiles.put(randomPoint, new Corridor((int) randomPoint.getX(), (int) randomPoint.getY(), new Key(randomPinCode())));
        }

        for (int i = 0; i < (15 + (verticalTiles - 10) * 4) + random.nextInt(33 + (verticalTiles - 10) * 3); i++) {
            Point randomPoint = randomPoint();

            tiles.put(randomPoint, new Corridor((int) randomPoint.getX(), (int) randomPoint.getY(), new Barricade(randomPinCode())));
        }

        for (int i = 0; i < (15 + (verticalTiles - 10) * 4) + random.nextInt(40 + (verticalTiles - 10) * 3); i++) {
            Point randomPoint = randomPoint();

            tiles.put(randomPoint, new Wall((int) randomPoint.getX(), (int) randomPoint.getY()));
        }

        for (int x = 0; x < horizontalTiles; x++) {
            for (int y = 0; y < horizontalTiles; y++) {
                Point point = new Point(x, y);
                if (!tiles.containsKey(point)) {
                    tiles.put(point, new Corridor(x, y));
                }
            }
        }

        PlayField playField = new PlayField(horizontalTiles, verticalTiles, new ArrayList<>(tiles.values()), startTile);
        lastGeneratedPlayField = playField.clone();
        tiles.clear();
        return playField;
    }

    private int generateAroundEndField(Tile endField) {
        Corridor barricade;

        if (endField.getLocationX() == 0) {
            barricade = new Corridor(endField.getLocationX() + 1, endField.getLocationY(), new Barricade(randomPinCode()));
        } else if (endField.getLocationX() == horizontalTiles - 1) {
            barricade = new Corridor(endField.getLocationX() - 1, endField.getLocationY(), new Barricade(randomPinCode()));
        } else if (endField.getLocationY() == 0) {
            barricade = new Corridor(endField.getLocationX(), endField.getLocationY() + 1, new Barricade(randomPinCode()));
        } else {
            barricade = new Corridor(endField.getLocationX(), endField.getLocationY() - 1, new Barricade(randomPinCode()));
        }
        tiles.put(new Point(barricade.getLocationX(), barricade.getLocationY()), barricade);

        for (int x = endField.getLocationX() - 1; x <= endField.getLocationX() + 1; x++) {
            for (int y = endField.getLocationY() - 1; y <= endField.getLocationY() + 1; y++) {
                Point point = new Point(x, y);
                if (x >= 0 && x < horizontalTiles && y >= 0 && y < verticalTiles && !tiles.containsKey(point)) {
                    tiles.put(point, random.nextBoolean() ? new Wall(x, y) : new Corridor(x, y, new Barricade(randomPinCode())));
                }
            }
        }

        return ((Barricade) barricade.getInteractableObject()).getPinCode();
    }

    private int randomPinCode() {
        return (random.nextInt(5) + 1) * 50;
    }

    private Point randomPoint() {
        if (tiles.size() < horizontalTiles * verticalTiles) {
            Point randomPoint = new Point(random.nextInt(horizontalTiles),
                    random.nextInt(verticalTiles));
            while (tiles.containsKey(randomPoint)) {
                randomPoint = new Point(random.nextInt(horizontalTiles),
                        random.nextInt(verticalTiles));
            }
            return randomPoint;
        }
        return null;
    }

}
