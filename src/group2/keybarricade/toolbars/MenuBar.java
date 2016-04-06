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

    public MenuBar(KeyBarricade keyBarricade) {
        this.keyBarricade = keyBarricade;

        JButton resetButton = new JButton("Reset");
        resetButton.setFocusable(false);
        resetButton.addActionListener(new ResetListener());
        add(resetButton);

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
        MenuScroller.setScrollerFor(levelSelector, 20);
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

}
