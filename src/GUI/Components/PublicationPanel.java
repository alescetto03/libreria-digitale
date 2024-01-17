package GUI.Components;

import java.awt.image.BufferedImage;

public class PublicationPanel extends HorizontalScrollpanePanel {
    @Override
    public boolean onButtonPress() {
        return false;
    }

    public PublicationPanel(String title, BufferedImage cover, String buttonText) {
        super(title, cover, buttonText);
    }
}
