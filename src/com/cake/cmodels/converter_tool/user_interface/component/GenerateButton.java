package com.cake.cmodels.converter_tool.user_interface.component;

import com.cake.cmodels.converter_tool.CmodelConverter;

import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class GenerateButton extends Component {

    protected static BufferedImage readAsset(String filename) {
        ClassLoader classLoader = GenerateButton.class.getClassLoader();
        try {
            return ImageIO.read(Objects.requireNonNull(classLoader.getResourceAsStream(filename)));
        } catch (IOException | NullPointerException e) {
            throw new RuntimeException("Unable to read inner asset", e);
        }
    }

    static final BufferedImage BUTTON_IMAGE = readAsset("generate-button.png");
    static final BufferedImage BUTTON_HOVER_IMAGE = readAsset("generate-button-hover.png");
    static final BufferedImage BUTTON_PRESSED_IMAGE = readAsset("generate-button-pressed.png");

    /**Ensure the user cant call generate while generating*/
    boolean isEnabled = true;
    boolean isPressed = false;
    boolean isHovered = false;

    static final int imagePadding = (480-466) / 2;

    public GenerateButton(Runnable onClick) {
        addMouseListener(new ButtonMouseListener(onClick));
        setPreferredSize(new Dimension(480, 250));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (isPressed || !isEnabled)
            g.drawImage(BUTTON_PRESSED_IMAGE, imagePadding, 0, null);
        else if (isHovered)
            g.drawImage(BUTTON_HOVER_IMAGE, imagePadding, 0, null);
        else
            g.drawImage(BUTTON_IMAGE, imagePadding, 0, null);
    }
    private class ButtonMouseListener implements MouseListener {

        Runnable onClick;

        public ButtonMouseListener(Runnable onClick) {
            this.onClick = onClick;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (isEnabled) {
                onClick.run();
                isEnabled = false;
            }
        }
        @Override
        public void mousePressed(MouseEvent e) {
            isPressed = true;
        }
        @Override
        public void mouseReleased(MouseEvent e) {
            isPressed = false;
        }
        @Override
        public void mouseEntered(MouseEvent e) {
            isHovered = true;
        }
        @Override
        public void mouseExited(MouseEvent e) {
            isHovered = false;
        }

    }

}
