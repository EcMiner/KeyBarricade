package group2.keybarricade.toolbars;

import group2.keybarricade.game.KeyBarricade;
import group2.keybarricade.game.PlayField;
import group2.keybarricade.utilities.MenuScroller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuBar extends JMenuBar {

    private final KeyBarricade keyBarricade;
    private final JButton resetButton;
    private final JButton homeButton;

    public MenuBar(KeyBarricade keyBarricade) {
        this.keyBarricade = keyBarricade;

        resetButton = new JButton("Reset");
        // We set focusable to false to prevent our key listener in KeyBarricade from not working
        resetButton.setFocusable(false);
        // Add the click listener to the reset button
        resetButton.addActionListener(new ResetListener());
        add(resetButton);
        // Set the reset button invisible by default, because the first screen is the homescreen
        setResetVisible(false);

        JButton randomButton = new JButton("Random");
        // We set focusable to false to prevent our key listener in KeyBarricade from not working
        randomButton.setFocusable(false);
        // Add the click listener to the random map button
        randomButton.addActionListener(new RandomListener());
        add(randomButton);

        homeButton = new JButton("Home");
        // We set focusable to false to prevent our key listener in KeyBarricade from not working
        homeButton.setFocusable(false);
        // Add the click listener to the home button
        homeButton.addActionListener(new HomeListener());
        add(homeButton);
        // Set the home button invisible by default, becaue the first screen is the homescreen
        setHomeVisible(false);

        JMenu levelSelector = new JMenu("Levels");
        LevelSelectListener levelSelectListener = new LevelSelectListener();
        // Loop through all the loaded playfields and add them to the dropdown menu
        for (PlayField playField : keyBarricade.getPlayFields()) {
            JMenuItem menuItem = new JMenuItem("map " + (playField.getId() + 1));
            menuItem.addActionListener(levelSelectListener);
            levelSelector.add(menuItem);
        }

        // We set focusable to false to prevent our key listener in KeyBarricade from not working
        levelSelector.setFocusable(false);
        add(levelSelector);

        // We set focusable to false to prevent our key listener in KeyBarricade from not working
        setFocusable(false);
        MenuScroller.setScrollerFor(levelSelector, 5);
    }

    /**
     * Sets whether the reset button should be visible
     *
     * @param visible Whether the reset button should be visible
     */
    public void setResetVisible(boolean visible) {
        resetButton.setVisible(visible);
    }

    /**
     * Sets whether the home button should be visible
     *
     * @param visible Whether the home button should be visible
     */
    public void setHomeVisible(boolean visible) {
        homeButton.setVisible(visible);
    }

    private class ResetListener implements ActionListener {

        /**
         * This method is called whenever the reset button is clicked
         *
         * @param e The action event
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            // Reset the level
            keyBarricade.reset();
        }

    }

    private class LevelSelectListener implements ActionListener {

        /**
         * This method is called whenever you click an item in the dropdown menu
         *
         * @param e The action event
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            // Using substring to cut off the map part of the name
            int levelId = Integer.parseInt(e.getActionCommand().substring(4)) - 1;

            // Start the level
            keyBarricade.start(levelId);
        }

    }

    private class RandomListener implements ActionListener {

        /**
         * This method is called whenever you click the random map button
         *
         * @param e The action event
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            // Generate and load new random map
            keyBarricade.randomMap();
        }

    }

    private class HomeListener implements ActionListener {

        /**
         * This method is called whenever you click the home button
         *
         * @param e The action event
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            // Switch to the homescreen
            keyBarricade.showHomeScreen();
        }

    }

}
