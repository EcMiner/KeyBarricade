package group2.keybarricade;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class BarricadeTest {

    public BarricadeTest() {
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
     * Test of getPinCode method, of class Barricade.
     */
    @Test
    public void testGetPinCode() {
        System.out.println("getPinCode");
        Barricade instance = new Barricade(100);
        int expResult = 100;
        int result = instance.getPinCode();
        assertEquals(expResult, result);
    }

    /**
     * Test of keyFits method, of class Barricade.
     */
    @Test
    public void testKeyFits() {
        System.out.println("keyFits");
        Key key = new Key(333);
        Barricade instance = new Barricade(333);
        boolean expResult = true;
        boolean result = instance.keyFits(key);

        Key key2 = new Key(133);
        boolean expResult2 = false;
        boolean result2 = instance.keyFits(key2);
        assertEquals(expResult, result);
        assertEquals(expResult2, result2);
    }

}
