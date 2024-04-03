package com.cake.cmodels.converter_tool.user_interface.component;

import com.cake.cmodels.converter_tool.source.MisreadSource;
import com.cake.cmodels.core.CmodelFileExtensions;

import java.awt.*;

public class MisreadSourceDisplay extends Component {
    
    MisreadSource source;
    public MisreadSourceDisplay(MisreadSource source) {
        this.source = source;
        setPreferredSize(new Dimension(500, 80));
    }
    
    @Override
    public void paint(Graphics g) {
        g.translate(10, 10);
    
        //Draw info text
        g.setColor(Color.BLACK);
        g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
        
        g.drawString(source.getDisplayName(), 30, 13);
        g.drawString(source.getDirectory() + source.getGroupName() + CmodelFileExtensions.SOURCE_DEFINITION_EXTENSION, 30, 28);
        g.drawString(source.getReadError().getMessage(), 30, 43);
        g.drawString(source.getReadError().getRecommendation(), 30, 58);
    
        //Make a select box
        g.drawRect(0, 0, 15, 15);
    
        //Cross out the select box
        g.setColor(new Color(255, 93, 93));
        g.drawLine(1, 1, 15, 15);
        g.drawLine(15, 1, 1, 15);
    
        g.translate(-10, -10);
    }
    
}
