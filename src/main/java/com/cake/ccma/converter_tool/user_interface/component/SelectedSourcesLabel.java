package com.cake.ccma.converter_tool.user_interface.component;

import com.cake.ccma.converter_tool.user_interface.ConverterInterface;

import java.awt.*;

public class SelectedSourcesLabel extends Component {

    public SelectedSourcesLabel() {
        setPreferredSize(new Dimension(500, 40));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(Color.BLACK);
        g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        g.drawString("Selected " + getNumberOfSelectedSources() + " sources", 10, 30);
    }

    private int getNumberOfSelectedSources() {
        return (int) ConverterInterface.SOURCE_SELECTS.stream().filter(sourceSelect -> sourceSelect.isSelected).count();
    }

}
