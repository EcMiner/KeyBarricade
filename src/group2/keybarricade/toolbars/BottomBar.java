package group2.keybarricade.toolbars;

import group2.keybarricade.utilities.ImageUtil;
import group2.keybarricade.interactable.Key;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BottomBar extends JPanel {

    private final JLabel pinCodeLabel;
    private final JLabel notificationLabel;

    public BottomBar() {
        FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT, 10, 0);
        setLayout(flowLayout);
        this.pinCodeLabel = new JLabel("None");
        this.notificationLabel = new JLabel();

        JLabel keyImageLabel = new JLabel(new ImageIcon(ImageUtil.resize(Key.image, 25)));

        add(keyImageLabel);
        add(pinCodeLabel);
        add(notificationLabel);
    }

    /**
     * Changes the pinCodeLabel's text to the pincode of the key that the player
     * picked up.
     *
     * @param key
     * @see Key
     */
    public void setKey(Key key) {
        pinCodeLabel.setText(key.getPinCode() + "");
    }

    /**
     * Changes the text of the notificationLabel to a notification string, also
     * changes the color to red or green dependent on the notification type
     *
     * @param notification The notification string
     * @param notificationType The NotificationType used for setting the color
     * @see NotificationType
     */
    public void showNotification(String notification, NotificationType notificationType) {
        notificationLabel.setForeground(notificationType == NotificationType.ERROR ? Color.RED : Color.GREEN);
        notificationLabel.setText(notification);
    }

    /**
     * Resets the pinCodeLabel and the notificationLabel
     */
    public void reset() {
        pinCodeLabel.setText("None");
        notificationLabel.setText("");
    }

    /**
     * Resizes the bottomBar to fit the width of the frame, and change the
     * position so it always appears on the bottom
     *
     * @param frameWidth The JFrame's width
     * @param frameHeight The JFrame's height
     */
    public void resizePanel(int frameWidth, int frameHeight) {
        setSize(frameWidth, 30);
        setLocation(0, frameHeight - getHeight());
        repaint();
    }

}
