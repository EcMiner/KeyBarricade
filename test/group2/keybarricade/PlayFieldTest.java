/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package group2.keybarricade;

import java.awt.BorderLayout;
import java.awt.Graphics;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Wouter
 */
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

    /**
     * Test of clone method, of class PlayField.
     */
    @Test
    public void testClone() {
        System.out.println("clone");
        PlayField instance = testField;
        PlayField expResult = testField;
        PlayField result = instance.clone();

        assertEquals(expResult, result);
    }

    /**
     * Test of getId method, of class PlayField.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        PlayField instance = testField;
        int expResult = 0;
        int result = instance.getId();
        assertEquals(expResult, result);
    }

    /**
     * Test of resizeField method, of class PlayField.
     */
    @Test
    public void testResizeField() {
        System.out.println("resizeField");
        int frameWidth = 500;
        int frameHeight = 500;
        PlayField instance = testField;
        instance.resizeField(frameWidth, frameHeight);
        if (instance.getWidth() != frameWidth && instance.getHeight() != frameHeight) {
            fail("Resize did not work");
        }
    }

    /**
     * Test of paint method, of class PlayField.
     */
    //@Test
    public void testPaint() {
        System.out.println("paint");
        Graphics g = null;
        PlayField instance = null;
        instance.paint(g);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPlayer method, of class PlayField.
     */
    @Test
    public void testGetPlayer() {
        System.out.println("getPlayer");
        PlayField instance = testField;
        Player expResult = null;
        Player result = instance.getPlayer();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isFinished method, of class PlayField.
     */
    @Test
    public void testIsFinished() {
        System.out.println("isFinished");
        PlayField instance = null;
        boolean expResult = false;
        boolean result = instance.isFinished();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setFinished method, of class PlayField.
     */
    @Test
    public void testSetFinished() {
        System.out.println("setFinished");
        boolean finished = false;
        PlayField instance = null;
        instance.setFinished(finished);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
