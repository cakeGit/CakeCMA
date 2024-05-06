package com.cake.ccma.converter_tool.user_interface.component;

import java.awt.*;

import javax.swing.*;

public class MissingSourcesLabel extends JPanel {

    public MissingSourcesLabel() {
        setMinimumSize(new Dimension(500, 30));
        setPreferredSize(new Dimension(500, 30));
    }

    @Override
    public void paintComponent(Graphics g) {
        g.translate(10, 10);

        g.setColor(Color.BLACK);
        g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
        g.drawString("In sources is empty, are you using the correct working directory?", 30, 13);

        g.translate(-10, -10);

    }

}
