package group2.keybarricade.game;

import group2.keybarricade.toolbars.MenuBar;
import group2.keybarricade.toolbars.BottomBar;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.*;
import java.util.ArrayList;
import javax.swing.JFrame;

public class KeyBarricade extends JFrame {

    public static void main(String[] args) {
        new KeyBarricade();
    }

    // All the loaded playfields
    private final ArrayList<PlayField> playFields;
    private final BottomBar bottomBar;
    // The playfield that is currently shown on the screen
    private PlayField currentPlayField;

    private final HomeScreen homeScreen;

    public KeyBarricade() {
        super("Key Barricade");
        // Start playing the music
        new MusicPlayer().startPlaying();

        // Make the LevelLoader class load all the available PlayField json files
        this.playFields = new LevelLoader().loadLevels();
        this.bottomBar = new BottomBar();
        this.homeScreen = new HomeScreen(this);

        // Set layout to null to prevent interference
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(500, 500));
        setPreferredSize(new Dimension(600, 600));

        // Add a ComponentListener which will listen for when the JFrame resizes
        addComponentListener(new ResizeListener());

        // Add a KeyListener that will listen for when you press your arrow keys
        addKeyListener(new MoveListener());

        // Set the top menu bar to our custom menu bar
        setJMenuBar(new MenuBar(this));

        // Sets the JFrame size to the prefered size
        pack();
        // Show the JFrame
        setVisible(true);

        // Add the homescreen to the JFrame, this will be the first screen you see when you play
        this.add(homeScreen);
    }

    /**
     * This method will reset the map you're currently playing on.
     */
    public void reset() {
        // Check whether the current playfield is a PlayField in the playFields ArrayList
        if (currentPlayField != null && currentPlayField.getId() < playFields.size()) {
            // Re-start the current map
            start(currentPlayField.getId());
        } else if (currentPlayField != null && RandomMapGenerator.getLastGeneratedPlayField() != null) {
            // If the currentPlayField id is higher than the playField.size() (so when the playfield isn't in the ArrayList)
            // We know that the current playfield was a random generated map, then reset that random map
            load(RandomMapGenerator.getLastGeneratedPlayField().clone());
        }
    }

    /**
     * This will get a PlayField out of the loaded PlayFields (in the playFields
     * ArrayList), then it will clone it, then it will load it on the screen
     *
     * @param playFieldId The id of the PlayField that should be loaded
     */
    public void start(int playFieldId) {
        // Load the PlayField on the screen
        load(playFields.get(playFieldId).clone());
    }

    /**
     * This method will make a playfield appear on the screen
     *
     * @param playField The playfield that should appear on the screen
     */
    private void load(PlayField playField) {
        // Check if there's already a PlayField currently on the screen
        if (currentPlayField != null) {
            // If there is already a PlayField on the screen, remove this PlayField from the screen
            remove(currentPlayField);
        }
        // Remove the homescreen, so you won't see it when you're playing
        remove(homeScreen);
        MenuBar menuBar = (MenuBar) getJMenuBar();

        // Set the reset and home buttons from MenuBar to visible
        menuBar.setResetVisible(true);
        menuBar.setHomeVisible(true);

        // Add the PlayField panel to the JFrame, so it will appear on the screen
        add(playField);

        // Assign the current playfield
        this.currentPlayField = playField;

        // Set the KeyBarricade attribute in the Player class
        this.currentPlayField.getPlayer().setKeyBarricade(this);
        bottomBar.reset();

        // Add the bottom bar to the screen, if it isn't already
        add(bottomBar);

        // Call the resize function to make sure all the components in PlayField are repositioned
        playField.resizeField(getContentPane().getWidth(), getContentPane().getHeight() - bottomBar.getHeight());

        // No freaking idea what the heck this does, but the bottom bar won't appear if we don't do this
        // It does something with all child components that makes them appear for some reason
        validate();

        // Repaint the JFrame to make sure everything is painted on the screen
        repaint();
    }

    /**
     * This method will generate a new random map, and load it on the screen
     */
    public void randomMap() {
        // Generate the map and show it
        load(new RandomMapGenerator().generateRandomMap());
    }

    /**
     * Get all loaded PlayField objects
     *
     * @return Returns a list of all loaded PlayField objects
     */
    public ArrayList<PlayField> getPlayFields() {
        return playFields;
    }

    /**
     * Gets the BottomBar of the screen, this is used by {@link Player} to show
     * what key they currently have in their pocket, and to show notifications
     * on the bottombar
     *
     * @return Returns the bottom bar
     */
    public BottomBar getBottomBar() {
        return bottomBar;
    }

    /**
     * Gets the PlayField that is currently loaded on the screen, the Player
     * uses this to tell the current PlayField that it is finished, when a
     * PlayField is finished, a Player shouldn't be allowed to move anymore.
     * Player also uses this to determine whether he's currently playing a
     * random map, or a map from the jar file.
     *
     * @return Returns the PlayField that is currently loaded
     */
    public PlayField getCurrentPlayField() {
        return currentPlayField;
    }

    /**
     * Shows the HomeScreen on the JFrame (returns back to the homescreen)
     */
    public void showHomeScreen() {
        // Remove the current field you're playing on (if you are playing on one)
        if (currentPlayField != null) {
            remove(currentPlayField);
            currentPlayField = null;
        }

        // Hides the reset and the home button on the top menu bar, because they are useless
        // when you're on the homescreen
        MenuBar menuBar = (MenuBar) getJMenuBar();
        menuBar.setResetVisible(false);
        menuBar.setHomeVisible(false);

        // Remove the bottom bar from the screen
        remove(bottomBar);

        // Add the homescreen to the jframe
        add(homeScreen);

        // Repaint to make sure everything is shown correctly on the screen
        repaint();
    }

    private class ResizeListener extends ComponentAdapter {

        /**
         * This method is called whenever a user resizes the JFrame
         *
         * @param evt The resize event
         */
        @Override
        public void componentResized(ComponentEvent evt) {
            // NOTE: We use getContentPane().getWidth() (and height) because it returns the actual frame size
            // without adding the size of the top bar
            if (currentPlayField != null) {
                // Resize the current playField, set the height to the JFrame's height, minus the bottomBar's height
                currentPlayField.resizeField(getContentPane().getWidth(), getContentPane().getHeight() - bottomBar.getHeight());
            }
            // Resize the bottom bar
            bottomBar.resizePanel(getContentPane().getWidth(), getContentPane().getHeight());

            // Resize the homescreen
            homeScreen.resizePanel(getContentPane().getWidth(), getContentPane().getHeight());
        }

    }

    private class MoveListener extends KeyAdapter {

        /**
         * This method is called whenever you press a button on your keybaord
         *
         * @param e The key press event
         */
        @Override
        public void keyPressed(KeyEvent e) {
            // Checks whether you are currently playing a level, and if it's not finished
            // Else do nothing
            if (currentPlayField != null && !currentPlayField.isFinished()) {
                // Check what button you've pressed
                switch (e.getKeyCode()) {
                    case VK_UP:
                        // Try to move the player up a tile
                        currentPlayField.getPlayer().move(Direction.UP);
                        break;
                    case VK_DOWN:
                        // Try to move the player down a tile
                        currentPlayField.getPlayer().move(Direction.DOWN);
                        break;
                    case VK_LEFT:
                        // Try to move the player left a tile
                        currentPlayField.getPlayer().move(Direction.LEFT);
                        break;
                    case VK_RIGHT:
                        // Try to move the player right a tile
                        currentPlayField.getPlayer().move(Direction.RIGHT);
                        break;
                }
            }
        }

    }
}
