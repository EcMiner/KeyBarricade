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
        InputStream input = getInputStream("/test/testfiles/nonjson.yml");
        System.out.println("");
        LevelLoader instance = new LevelLoader();
        PlayField expResult = null;
        PlayField result = instance.loadLevel(input);
        assertEquals("#loadLevel - Non json test", expResult, result);
    }

    private InputStream getInputStream(String path) {
        return getClass().getResourceAsStream(path);
    }

}
