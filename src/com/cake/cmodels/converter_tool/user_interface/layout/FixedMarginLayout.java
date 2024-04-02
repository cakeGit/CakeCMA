package com.cake.cmodels.converter_tool.user_interface.layout;

import com.cake.cmodels.converter_tool.user_interface.component.FloatingFolderInfoLink;

import java.awt.*;

public class FixedMarginLayout implements LayoutManager {

    int marginWidth;
    int marginHeight;
    Component marginComponent;
    Dimension dimension;
    public FixedMarginLayout(Dimension dimension, Component marginComponent, int marginHeight) {
        this.dimension = dimension;
        this.marginComponent = marginComponent;
        this.marginHeight = marginHeight;
        this.marginWidth = marginComponent.getPreferredSize().width;
    }

    @Override
    public void addLayoutComponent(String name, Component comp) {

    }
    @Override
    public void removeLayoutComponent(Component comp) {

    }
    @Override
    public Dimension preferredLayoutSize(Container parent) {
        return dimension;
    }
    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return dimension;
    }
    @Override
    public void layoutContainer(Container parent) {
        int sideY = 0;
        for (Component component : parent.getComponents()) {
            component.setSize(component.getPreferredSize());
            if (component == this.marginComponent) {

                component.setLocation(0, 0);
                component.setSize(this.marginWidth, this.marginHeight);

            } else if (component instanceof FloatingFolderInfoLink floatingLabel) {

                component.setLocation(floatingLabel.getPreferredLocation(parent));

            } else {

                component.setLocation(this.marginWidth, sideY);
                sideY += component.getPreferredSize().height;

            }
        }
    }

}
