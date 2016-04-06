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

    private final ArrayList<PlayField> playFields;
    private final BottomBar bottomBar;
    private PlayField currentPlayField;

    private final HomeScreen homeScreen;

    public KeyBarricade() {
        super("Key Barricade");
        this.playFields = new LevelLoader().loadLevels();
        this.bottomBar = new BottomBar();
        this.homeScreen = new HomeScreen(this);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(500, 500));
        setPreferredSize(new Dimension(600, 600));
        addComponentListener(new ResizeListener());
        addKeyListener(new MoveListener());
        setJMenuBar(new MenuBar(this));

        pack();
        setVisible(true);

        this.add(homeScreen);
    }

    /**
     * This method will reset the map you're currently playing on
     */
    public void reset() {
        if (currentPlayField != null && currentPlayField.getId() < playFields.size()) {
            start(currentPlayField.getId());
        } else if (currentPlayField != null && RandomMapGenerator.getLastGeneratedPlayField() != null) {
            load(RandomMapGenerator.getLastGeneratedPlayField().clone());
        }
    }

    public void start(int playFieldId) {
        load(playFields.get(playFieldId).clone());
    }

    /**
     * This method will make a playfield appear on the screen
     *
     * @param playField The playfield that should appear on the screen
     */
    private void load(PlayField playField) {
        if (currentPlayField != null) {
            remove(currentPlayField);
        }
        remove(homeScreen);
        MenuBar menuBar = (MenuBar) getJMenuBar();
        menuBar.setResetVisible(true);
        menuBar.setHomeVisible(true);

        add(playField);
        this.currentPlayField = playField;
        this.currentPlayField.getPlayer().setKeyBarricade(this);
        bottomBar.reset();
        add(bottomBar);
        playField.resizeField(getContentPane().getWidth(), getContentPane().getHeight() - bottomBar.getHeight());
        validate();
        repaint();
    }

    public void randomMap() {
        load(new RandomMapGenerator().generateRandomMap());
    }

    public ArrayList<PlayField> getPlayFields() {
        return playFields;
    }

    public BottomBar getBottomBar() {
        return bottomBar;
    }

    public PlayField getCurrentPlayField() {
        return currentPlayField;
    }

    public void showHomeScreen() {
        if (currentPlayField != null) {
            remove(currentPlayField);
            currentPlayField = null;
        }

        MenuBar menuBar = (MenuBar) getJMenuBar();
        menuBar.setResetVisible(false);
        menuBar.setHomeVisible(false);

        remove(bottomBar);
        add(homeScreen);
        repaint();
    }

    private class ResizeListener extends ComponentAdapter {

        @Override
        public void componentResized(ComponentEvent evt) {
            if (currentPlayField != null) {
                /* 
                 We use getContentPane().getWidth() (and height) because it returns the actual frame size
                 without adding the size of the top bar
                 */
                currentPlayField.resizeField(getContentPane().getWidth(), getContentPane().getHeight() - bottomBar.getHeight());
            }
            bottomBar.resizePanel(getContentPane().getWidth(), getContentPane().getHeight());
            homeScreen.resizePanel(getContentPane().getWidth(), getContentPane().getHeight());
        }

    }

    private class MoveListener extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            if (currentPlayField != null && !currentPlayField.isFinished()) {
                switch (e.getKeyCode()) {
                    case VK_UP:
                        currentPlayField.getPlayer().move(Direction.UP);
                        break;
                    case VK_DOWN:
                        currentPlayField.getPlayer().move(Direction.DOWN);
                        break;
                    case VK_LEFT:
                        currentPlayField.getPlayer().move(Direction.LEFT);
                        break;
                    case VK_RIGHT:
                        currentPlayField.getPlayer().move(Direction.RIGHT);
                        break;
                }
            }
        }

    }
}
