package com.cake.cmodels.converter_tool.user_interface.layout;

import java.awt.*;

public class PanelWithFloatingLayout implements LayoutManager {
    
    Point offset;
    Dimension padding;
    
    public PanelWithFloatingLayout(Point offset, Dimension padding) {
        this.offset = offset;
        this.padding = padding;
    }
    
    @Override
    public Dimension preferredLayoutSize(Container parent) {
        return parent.getPreferredSize();
    }
    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return parent.getPreferredSize();
    }
    @Override
    public void layoutContainer(Container parent) {
        for (Component component : parent.getComponents()) {
            if (component instanceof FloatingComponent floatingComponent) {
                component.setSize(component.getPreferredSize());
                component.setLocation(floatingComponent.getDesiredLocation(parent));
            } else {
                component.setSize(
                    parent.getWidth() - padding.width,
                    parent.getHeight() - padding.height
                );
                component.setLocation(offset);
            }
        }
    }
    
    @Override
    public void addLayoutComponent(String name, Component comp) {}
    @Override
    public void removeLayoutComponent(Component comp) {}
    
}
