package com.cake.cmodels.converter_tool.user_interface.component;

import com.cake.cmodels.converter_tool.source.ConversionSource;
import com.cake.cmodels.core.CmodelFileExtensions;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SourceSelect extends JPanel {
    
    ConversionSource source;
    boolean isSelected;
    
    public SourceSelect(ConversionSource source) {
        this.source = source;
        setPreferredSize(new Dimension(500, 60));
        addMouseListener(new ToggleSelectMouseListener());
    }
    
    @Override
    public void paintComponent(Graphics g) {
        g.translate(10, 10);

        g.setColor(Color.BLACK);
        g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
        g.drawString(source.getDisplayName(), 30, 13);
        g.drawString(source.getDirectory() + source.getGroupName() + CmodelFileExtensions.SOURCE_DEFINITION_EXTENSION, 30, 28);

        g.drawRect(0, 0, 15, 15);

        if (isSelected) {
            g.setColor(new Color(93, 195, 255));
            g.fillRect(1, 1, 14, 14);
        }
        g.translate(-10, -10);

    }
    
    public ConversionSource getSource() {
        return source;
    }
    public boolean isSelected() {
        return isSelected;
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
