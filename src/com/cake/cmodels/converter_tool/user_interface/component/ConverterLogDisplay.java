package com.cake.cmodels.converter_tool.user_interface.component;

import com.cake.cmodels.converter_tool.ConversionLog;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class ConverterLogDisplay extends Component {
    
    /**-1 means track height, otherwise translate*/
    int scrollY = -1;
    int scrollHeight = 100;
    
    public ConverterLogDisplay() {
        addMouseWheelListener(new LogScrollingMouseHandler());
        setPreferredSize(new Dimension(480, 239));
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        BorderFactory.createBevelBorder(BevelBorder.LOWERED).paintBorder(this, g, 0, 0, getWidth()-1, getHeight()-1);
        
        int lineHeight = 15;
        int totalLineCount = ConversionLog.LOG.size();
        
        int renderedLinesCount = (int) Math.ceil(getHeight() / (double) lineHeight);
        int renderedLinesStartIndex = scrollY == -1 ?
            Math.max(0, totalLineCount - renderedLinesCount)
            : scrollY / lineHeight;
        
        scrollHeight = (totalLineCount - renderedLinesCount) * lineHeight;
        
        g.setColor(Color.BLACK);
        g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, lineHeight -2));
        
        for (int i = renderedLinesStartIndex; i <= renderedLinesStartIndex + renderedLinesCount; i++) {
            if (i >= ConversionLog.LOG.size())
                return;
            
            int currentLineY = i * lineHeight;
            
            g.drawString(
                ConversionLog.LOG.get(i), 0,
                scrollY == -1 ?
                    currentLineY - (renderedLinesStartIndex * lineHeight)
                    : currentLineY - scrollY
                );
            
        }
        
    }
    
    private class LogScrollingMouseHandler implements MouseWheelListener {
        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            int scrollAmount = (e.getWheelRotation() * e.getScrollAmount() * 6);
            
            if (scrollY == -1)
                scrollY = scrollHeight + scrollAmount;
            else
                scrollY += scrollAmount;
            
            if (scrollY > scrollHeight)
                scrollY = -1;
            else if (scrollY != -1)
                scrollY = Math.max(0, scrollY);
        }
    
    }
    
}
