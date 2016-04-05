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
    //@Test
    public void testLoadLevels1() {
        LevelLoader instance = new LevelLoader();
        int expResult = 7;
        int result = instance.loadLevels().size();
        assertEquals("#loadLevels - Normal Load Test", expResult, result);
    }

    /**
     * Test of loadLevel method, of class LevelLoader.
     */
    @Test
    public void testLoadLevel1() {
        InputStream input = null;
        LevelLoader instance = new LevelLoader();
        PlayField expResult = null;
        PlayField result = instance.loadLevel(input);
        assertEquals("#loadLevel - Null Test", expResult, result);
    }

    @Test
    public void testLoadLevel2() {
        InputStream input = getInputStream("/testfiles/nonjson.yml");
        LevelLoader instance = new LevelLoader();
        PlayField expResult = null;
        PlayField result = instance.loadLevel(input);
        assertEquals("#loadLevel - Non json file test", expResult, result);
    }

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
        InputStream input = getInputStream("/testfiles/minussize.json");
        LevelLoader instance = new LevelLoader();
        int expResult = 1;
        int result = instance.loadLevel(input);
        assertEquals("#loadLevel - Negative height or width", expResult, result);
    }

    private InputStream getInputStream(String path) {
        return getClass().getResourceAsStream(path);
    }

}
