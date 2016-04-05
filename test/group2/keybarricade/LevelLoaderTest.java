package group2.keybarricade;

import java.io.InputStream;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class LevelLoaderTest {

    public LevelLoaderTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of loadLevels method, of class LevelLoader.
     */
    @Test
    public void testLoadLevels1() {
        LevelLoader instance = new LevelLoader();
        int expResult = 7;
        int result = instance.loadLevels().size();
        assertEquals("#loadLevels - Normal Load Test", expResult, result);
    }

    /**
     * A test to see what happens when the InputStream is null
     */
    @Test
    public void testLoadLevel1() {
        InputStream input = null;
        LevelLoader instance = new LevelLoader();
        PlayField expResult = null;
        PlayField result = instance.loadLevel(input);
        assertEquals("#loadLevel - Null Test", expResult, result);
    }

    /**
     * A test to see what happens when the InputStream is not a json file
     */
    @Test
    public void testLoadLevel2() {
        InputStream input = getInputStream("/testfiles/nonjson.yml");
        LevelLoader instance = new LevelLoader();
        PlayField expResult = null;
        PlayField result = instance.loadLevel(input);
        assertEquals("#loadLevel - Non json file test", expResult, result);
    }

    /**
     * A test to see what happens when the json file isn't correctly formatted
     */
    @Test
    public void testLoadLevel3() {
        InputStream input = getInputStream("/testfiles/wrongformatted.json");
        LevelLoader instance = new LevelLoader();
        PlayField expResult = null;
        PlayField result = instance.loadLevel(input);
        assertEquals("#loadLevel - Wrong formatted json", expResult, result);
    }

    /**
     * A test to see what happens when certain fields that are necessary to load
     * the playfield are missing. (Necessary fields are: playfieldHeight,
     * playfieldWidth & tiles)
     */
    @Test
    public void testLoadLevel4() {
        InputStream input = getInputStream("/testfiles/missingfields.json");
        LevelLoader instance = new LevelLoader();
        PlayField expResult = null;
        PlayField result = instance.loadLevel(input);
        assertEquals("#loadLevel - Missing fields", expResult, result);
    }

    /**
     * A test to see what happens when the tiles field in the json file is
     * present, but isn't an array
     */
    @Test
    public void testLoadLevel5() {
        InputStream input = getInputStream("/testfiles/tilesnotarray.json");
        LevelLoader instance = new LevelLoader();
        PlayField expResult = null;
        PlayField result = instance.loadLevel(input);
        assertEquals("#loadLevel - Tiles field not array", expResult, result);
    }

    /**
     * A test to see what happens when the playfieldHeight or the playfieldWidth
     * field is lower or equals than 0
     */
    @Test
    public void testLoadLevel6() {
        InputStream input = getInputStream("/testfiles/negativesize.json");
        LevelLoader instance = new LevelLoader();
        int expResult = 1;
        PlayField playField = instance.loadLevel(input);
        int resultVertical = playField.getVerticalTiles();
        int resultHorizontal = playField.getHorizontalTiles();

        assertEquals("#loadLevel - Negative height or width", expResult, resultVertical);
        assertEquals("#loadLevel - Negative height or width", expResult, resultHorizontal);
    }

    /**
     * A test to see what happens when there is no startfield defined in the
     * json file.
     */
    @Test
    public void testLoadLevel7() {
        InputStream input = getInputStream("/testfiles/nostartfield.json");
        LevelLoader instance = new LevelLoader();
        PlayField expResult = null;
        PlayField result = instance.loadLevel(input);
        assertEquals("#loadLevel - No startfield", expResult, result);
    }

    /**
     * A test to see what happens when there is no endfield defined in the json
     * file.
     */
    @Test
    public void testLoadLevel8() {
        InputStream input = getInputStream("/testfiles/noendfield.json");
        LevelLoader instance = new LevelLoader();
        PlayField expResult = null;
        PlayField result = instance.loadLevel(input);
        assertEquals("#loadLevel - No endfield", expResult, result);
    }

    /**
     * A test to see what happens when a tile in the json files is not in the
     * bounds of the playfield
     */
    @Test
    public void testLoadLevel9() {
        InputStream input = getInputStream("/testfiles/outofbounds.json");
        LevelLoader instance = new LevelLoader();
        boolean expResult = true;

        PlayField result = instance.loadLevel(input);
        boolean hasOutOfBounds = false;
        for (Tile tile : result.getTiles()) {
            int x = tile.getLocationX();
            int y = tile.getLocationY();
            if (x < 0 || y < 0 || x >= result.getHorizontalTiles() || y >= result.getVerticalTiles()) {
                hasOutOfBounds = true;
                break;
            }
        }

        assertEquals("#loadLevel - Tiles out of bounds", expResult, hasOutOfBounds);
    }

    private InputStream getInputStream(String path) {
        return getClass().getResourceAsStream(path);
    }

}
