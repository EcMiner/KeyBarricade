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
        resetButton.setFocusable(false);
        resetButton.addActionListener(new ResetListener());
        add(resetButton);
        setResetVisible(false);

        JButton randomButton = new JButton("Random");
        randomButton.setFocusable(false);
        randomButton.addActionListener(new RandomListener());
        add(randomButton);

        homeButton = new JButton("Home");
        homeButton.setFocusable(false);
        homeButton.addActionListener(new HomeListener());
        add(homeButton);
        setHomeVisible(false);

        JMenu levelSelector = new JMenu("Levels");
        LevelSelectListener levelSelectListener = new LevelSelectListener();
        for (PlayField playField : keyBarricade.getPlayFields()) {
            JMenuItem menuItem = new JMenuItem("map " + (playField.getId() + 1));
            menuItem.addActionListener(levelSelectListener);
            levelSelector.add(menuItem);
        }
        levelSelector.setFocusable(false);
        add(levelSelector);

        setFocusable(false);
        MenuScroller.setScrollerFor(levelSelector, 5);
    }

    public void setResetVisible(boolean visible) {
        resetButton.setVisible(visible);
    }

    public void setHomeVisible(boolean visible) {
        homeButton.setVisible(visible);
    }

    private class ResetListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            keyBarricade.reset();
        }

    }

    private class LevelSelectListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int levelId = Integer.parseInt(e.getActionCommand().substring(4)) - 1;
            keyBarricade.start(levelId);
        }

    }

    private class RandomListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            keyBarricade.randomMap();
        }

    }

    private class HomeListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            keyBarricade.showHomeScreen();
        }

    }

}
