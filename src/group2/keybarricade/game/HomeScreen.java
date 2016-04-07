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

/**
 * HomeScreen will be the main screen you'll see and when you finish the game
 * you will go back to this screen as well
 */
public class HomeScreen extends JPanel {

    // This loads our logo image
    private static final BufferedImage logoImage = loadImage("/images/keybarricade.png");

    private final KeyBarricade keyBarricade;
    private final JLabel logo;
    private final JButton startButton;
    private final JButton randomButton;

    public HomeScreen(KeyBarricade keyBarricade) {
        this.keyBarricade = keyBarricade;
        this.logo = new JLabel();
        this.startButton = new JButton("Start");
        this.randomButton = new JButton("Random");

        // We set the layout to null so it doesn't interfer with our lay-out functions (like resize)
        setLayout(null);
        // We set focusable to false to prevent our key listener in KeyBarricade from not working
        setFocusable(false);

        startButton.setFocusable(false);
        // Add the click listener to the start button
        startButton.addActionListener(new StartListener());

        randomButton.setFocusable(false);
        // Add the click listener to the random map button
        randomButton.addActionListener(new RandomListener());

        // Add components to the panel
        add(logo);
        add(startButton);
        add(randomButton);
    }

    /**
     * This method is called whenever the JFrame is resized. In this method
     * we'll reposition our components correctly
     *
     * @param frameWidth The JFrame's width
     * @param frameHeight The JFrame's height
     */
    public void resizePanel(int frameWidth, int frameHeight) {
        setSize(frameWidth, frameHeight);

        // We're resizing the logo to fit the panel, so this is the scale that it needs to be resized to
        double scale = (double) (frameWidth - (frameWidth * .25)) / logoImage.getWidth();
        // Create an ImageIcon object so we can set the JLabel's icon
        ImageIcon icon = new ImageIcon(ImageUtil.resize(logoImage, scale));

        // Set the size and location of the logo label to fit the panel
        logo.setBounds((int) (frameWidth * .125), 20, icon.getIconWidth(), icon.getIconHeight());
        // Change the icon to the resized image
        logo.setIcon(icon);

        // Resize and reposition the startButton
        startButton.setSize((int) (frameWidth * .4), (int) (frameHeight * .08));
        startButton.setLocation((frameWidth - startButton.getWidth()) / 2, logo.getY() + logo.getHeight() + ((int) (getHeight() * .1)));

        // Resize and reposition the random map button
        randomButton.setBounds(startButton.getX(), startButton.getY() + (int) (startButton.getHeight() * 1.5), startButton.getWidth(), startButton.getHeight());
    }

    private class StartListener implements ActionListener {

        /**
         * This method is called whenever you click the startButton on the
         * HomeScreen
         *
         * @param e The action event
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            // Load the first map of the array list (index 0)
            keyBarricade.start(0);
        }

    }

    private class RandomListener implements ActionListener {

        /**
         * This method is called whenever you click the random map button on the
         * HomeScreen
         *
         * @param e The action event
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            // Load a random map on the screen.
            keyBarricade.randomMap();
        }

    }

}
