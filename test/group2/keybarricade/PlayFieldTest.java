package group2.keybarricade;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class PlayFieldTest {

    static PlayField staticTestField;
    PlayField testField;

    public PlayFieldTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        LevelLoader myloader = new LevelLoader();
        staticTestField = myloader.loadLevel(PlayField.class.getResourceAsStream("/maps/map1.json"));
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        testField = staticTestField.clone();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testClone() {
        PlayField instance = testField;
        PlayField expResult = testField;
        PlayField result = instance.clone();

        assertEquals("#clone - Normal clone test", expResult, result);
    }

    @Test
    public void testGetId() {
        PlayField instance = testField;
        int expResult = 0;
        int result = instance.getId();
        assertEquals("#getId - Normal equals check", expResult, result);
    }

    @Test
    public void testResizeField1() {
        int frameWidth = 500;
        int frameHeight = 500;
        PlayField instance = testField;
        instance.resizeField(frameWidth, frameHeight);

        assertEquals("#resize - Normal resize", frameWidth, instance.getWidth());
        assertEquals("#resize - Normal resize", frameHeight, instance.getHeight());
    }

    @Test
    public void testResizeField2() {
        int frameWidth = -100;
        int frameHeight = -100;
        PlayField instance = testField;
        instance.resizeField(frameWidth, frameHeight);

        assertNotSame("#resize - Negative size", frameWidth, instance.getWidth());
        assertNotSame("#resize - Negative size", frameHeight, instance.getHeight());
    }

    @Test
    public void testSetFinished() {
        boolean finished = false;
        PlayField instance = testField;
        instance.setFinished(finished);

        assertEquals("#setFinished - Normal set test", finished, instance.isFinished());
    }

    @Test
    public void testConstructor() {
        PlayField playField = new PlayField(-1, -1, new ArrayList<>(), new Corridor(0, 0));

        int expResult = 1;

        assertEquals("#Constructor - Negative horizontal and vertical tiles", expResult, playField.getVerticalTiles());
        assertEquals("#Constructor - Negative horizontal and vertical tiles", expResult, playField.getHorizontalTiles());
    }

}
