package GUI.Components;

import GUI.AppView;

import javax.swing.*;
import java.awt.*;

public class LinkButton extends CustomButton {
    public LinkButton(String text) {
        super(text);
        setText("<html><u>" + text + "</u></html>");
        setBorderPainted(false);
        setOpaque(false);
        setBackground(Color.lightGray);
    }
}