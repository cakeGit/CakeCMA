package com.cake.cmodels.converter_tool.user_interface.layout;

import java.awt.*;

public class ListLayout implements LayoutManager {

    @Override
    public void addLayoutComponent(String name, Component comp) {

    }
    @Override
    public void removeLayoutComponent(Component comp) {

    }
    @Override
    public Dimension preferredLayoutSize(Container parent) {
        return getSize(parent);
    }
    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return getSize(parent);
    }
    @Override
    public void layoutContainer(Container parent) {
        int y = 0;
        for (Component component : parent.getComponents()) {
            component.setSize(component.getPreferredSize());
            component.setLocation(0, y);
            y += component.getHeight();
        }
    }

    public Dimension getSize(Container parent) {
        int width = 0, height = 0;
        for (Component component : parent.getComponents()) {
            width = Math.max(component.getPreferredSize().width, width);
            height += component.getPreferredSize().height;
        }
        return new Dimension(width, height);
    }

}
