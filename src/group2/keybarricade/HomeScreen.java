package group2.keybarricade.game;

import group2.keybarricade.utilities.ImageUtil;
import javax.swing.JPanel;
import static group2.keybarricade.utilities.ImageUtil.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

public class HomeScreen extends JPanel {

    private static final BufferedImage image = loadImage("/images/keybarricade.png");

    private final KeyBarricade keyBarricade;
    private final JLabel logo;
    private final JButton startButton;
    private final JButton randomButton;

    public HomeScreen(KeyBarricade keyBarricade) {
        this.keyBarricade = keyBarricade;
        this.logo = new JLabel();
        this.startButton = new JButton("Start");
        this.randomButton = new JButton("Random");

        setLayout(null);
        setFocusable(false);

        startButton.setFocusable(false);
        startButton.addActionListener(new StartListener());

        randomButton.setFocusable(false);
        randomButton.addActionListener(new RandomListener());

        add(logo);
        add(startButton);
        add(randomButton);
    }

    public void resizePanel(int frameWidth, int frameHeight) {
        setSize(frameWidth, frameHeight);

        double scale = (double) (frameWidth - (frameWidth * .25)) / image.getWidth();
        ImageIcon icon = new ImageIcon(ImageUtil.resize(image, scale));

        logo.setBounds((int) (frameWidth * .125), 20, icon.getIconWidth(), icon.getIconHeight());
        logo.setIcon(icon);

        startButton.setSize((int) (frameWidth * .4), (int) (frameHeight * .08));
        startButton.setLocation((frameWidth - startButton.getWidth()) / 2, logo.getY() + logo.getHeight() + ((int) (getHeight() * .1)));

        randomButton.setBounds(startButton.getX(), startButton.getY() + (int) (startButton.getHeight() * 1.5), startButton.getWidth(), startButton.getHeight());
    }

    private class StartListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            keyBarricade.start(0);
        }

    }

    private class RandomListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            keyBarricade.randomMap();
        }

    }

}
