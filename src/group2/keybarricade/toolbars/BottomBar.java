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

    public void setKey(Key key) {
        pinCodeLabel.setText(key.getPinCode() + "");
    }

    public void showNotification(String notification, NotificationType notificationType) {
        notificationLabel.setForeground(notificationType == NotificationType.ERROR ? Color.RED : Color.GREEN);
        notificationLabel.setText(notification);
    }

    public void reset() {
        pinCodeLabel.setText("None");
        notificationLabel.setText("");
    }

    public void resizePanel(int frameWidth, int frameHeight) {
        setSize(frameWidth, 30);
        setLocation(0, frameHeight - getHeight());
        repaint();
    }

}
