package GUI.Components;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class BookPanel extends HorizontalScrollpanePanel {

    @Override
    public boolean onButtonPress() {
        return false;
    }

    public BookPanel(String title, BufferedImage cover, String buttonText) {
        super(title, cover, buttonText);
    }
}
