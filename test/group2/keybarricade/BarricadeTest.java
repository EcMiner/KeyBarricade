/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package group2.keybarricade;

import java.awt.Graphics;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author BlackStar
 */
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
        assertEquals(expResult, result);
    }

    @Test
    public void testKeyNotFits() {
        System.out.println("keyNotFits");
        Key key = new Key(333);
        Barricade instance = new Barricade(113);
        boolean expResult = false;
        boolean result = instance.keyFits(key);
        assertEquals(expResult, result);
    }

    /**
     * Test of paint method, of class Barricade.
     */
    //@Test
    public void testPaint() {
        System.out.println("paint");
        Graphics g = null;
        int cubeSize = 0;
        Barricade instance = null;
        instance.paint(g, cubeSize);
    }

}
