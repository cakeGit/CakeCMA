package com.cake.ccma.converter_tool.user_interface.component;

import com.cake.ccma.converter_tool.user_interface.layout.FloatingComponent;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

public class FloatingFolderInfoLink extends Component implements FloatingComponent {

    String content, hoverContent, clickPath;
    boolean hoverState = false;

    public FloatingFolderInfoLink(String content, String hoverContent, String clickPath) {
        this.content = content;
        this.hoverContent = hoverContent;
        this.clickPath = clickPath;
        setPreferredSize(new Dimension(1000, 20));
        addMouseListener(new FileLinkMouseListener());
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(Color.BLACK);
        g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        String drawnText = hoverState ? hoverContent : content;
        g.drawString(drawnText, 0, 18);
    }

    @Override
    public Point getDesiredLocation(Component parent) {
        return new Point(10, parent.getHeight() - 30);
    }
    
    private class FileLinkMouseListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            try {
                Desktop.getDesktop().open(new File(clickPath));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        @Override
        public void mouseEntered(MouseEvent e) {
            hoverState = true;
        }
        @Override
        public void mouseExited(MouseEvent e) {
            hoverState = false;
        }
        @Override
        public void mousePressed(MouseEvent e) { }
        @Override
        public void mouseReleased(MouseEvent e) { }
    }

}
