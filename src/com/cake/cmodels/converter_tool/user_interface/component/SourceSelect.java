package com.cake.cmodels.converter_tool.user_interface.component;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SourceSelect extends JPanel {

    public String text;

    public String filename;
    public boolean isSelected = false;
    public boolean isValid;

    public SourceSelect(String filename, boolean isValid, String availabilityMessage) {
        this.filename = filename;
        this.isValid = isValid;

        text = filename + availabilityMessage;
        addMouseListener(new ToggleSelectMouseListener());
        setMinimumSize(new Dimension(500, 30));
        setPreferredSize(new Dimension(500, 30));
    }

    @Override
    public void paintComponent(Graphics g) {
        g.translate(10, 10);

        g.setColor(Color.BLACK);
        g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
        g.drawString(text, 30, 13);

        g.drawRect(0, 0, 15, 15);

        if (!isValid) {
            g.setColor(new Color(255, 93, 93));
            g.drawLine(1, 1, 15, 15);
            g.drawLine(15, 1, 1, 15);
        } else if (isSelected) {
            g.setColor(new Color(93, 195, 255));
            g.fillRect(1, 1, 14, 14);
        }
        g.translate(-10, -10);

    }

    protected class ToggleSelectMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            isSelected = !isSelected;
        }
        @Override
        public void mousePressed(MouseEvent e) { }
        @Override
        public void mouseReleased(MouseEvent e) { }
        @Override
        public void mouseEntered(MouseEvent e) { }
        @Override
        public void mouseExited(MouseEvent e) { }

    }

}
