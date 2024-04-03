package com.cake.cmodels.converter_tool.user_interface.component;

import com.cake.cmodels.converter_tool.ConverterLog;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class ConverterLogDisplay extends Component {
    
    /**-1 means track height, otherwise translate*/
    int scrollY = -1;
    int height = 100;
    
    public ConverterLogDisplay() {
        addMouseWheelListener(new LogScrollingMouseHandler());
        setPreferredSize(new Dimension(480, 239));
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        BorderFactory.createBevelBorder(BevelBorder.LOWERED).paintBorder(this, g, 0, 0, getWidth()-1, getHeight()-1);
        g.translate(10, 10);
        
        int yPos = 0;
        g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
        for (String line : ConverterLog.LOG) {
            g.drawString(line, 0, yPos + 13);
            yPos += 20;
        }
    }
    
    private class LogScrollingMouseHandler implements MouseWheelListener {
        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            scrollY += e.getWheelRotation();
            if (scrollY > height)
                scrollY = -1;
            else
                scrollY = Math.max(0, scrollY);
        }
    
    }
    
}
