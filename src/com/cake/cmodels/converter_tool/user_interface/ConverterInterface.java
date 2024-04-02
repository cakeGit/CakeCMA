package com.cake.cmodels.converter_tool.user_interface;

import com.cake.cmodels.converter_tool.ConversionSource;
import com.cake.cmodels.converter_tool.CmodelConverter;
import com.cake.cmodels.converter_tool.user_interface.component.FloatingFolderInfoLink;
import com.cake.cmodels.converter_tool.user_interface.component.GenerateButton;
import com.cake.cmodels.converter_tool.user_interface.component.MissingSourcesLabel;
import com.cake.cmodels.converter_tool.user_interface.component.SelectedSourcesLabel;
import com.cake.cmodels.converter_tool.user_interface.component.SourceSelect;
import com.cake.cmodels.converter_tool.user_interface.layout.FixedMarginLayout;
import com.cake.cmodels.converter_tool.user_interface.layout.ListLayout;

import javax.swing.*;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

public class ConverterInterface {

    public static JFrame frame;

    public static class ScreenComponents {

        public static JPanel sourceSelectPanel = new JPanel();
        public static JScrollPane sourceSelectScrollPane;

    }

    public static List<SourceSelect> SOURCE_SELECTS = new ArrayList<>();

    public static void init() {
        frame = new JFrame();
        frame.setSize(1000, 600);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        buildSourceSelectSection();

        frame.setLayout(new FixedMarginLayout(frame.getSize(), ScreenComponents.sourceSelectScrollPane, frame.getSize().height - 70));

        frame.add(ScreenComponents.sourceSelectScrollPane);

        String ethicacyMessage = "Sources responsibly and ethically fetched from ";
        frame.add(new FloatingFolderInfoLink(
            ethicacyMessage + "📁" + CmodelConverter.SOURCES_PATH,
            ethicacyMessage + "📂" + CmodelConverter.SOURCES_PATH,
            CmodelConverter.SOURCES_PATH));

        frame.add(new SelectedSourcesLabel());
        frame.add(new GenerateButton(
            () -> {
                System.out.println("Generated");
                JOptionPane.showMessageDialog(null, "Generated", "Success!", JOptionPane.INFORMATION_MESSAGE);
            }
        ));

        frame.setVisible(true);
    }

    private static void buildSourceSelectSection() {

        if (CmodelConverter.SOURCES_ORDERED.size() == 0) {
            ScreenComponents.sourceSelectPanel.add(new MissingSourcesLabel());
        } else {
            ScreenComponents.sourceSelectPanel.add(new Label("Available sources:"));
            for (String group : CmodelConverter.SOURCES_ORDERED) {
                ConversionSource source = CmodelConverter.SOURCES_MAP.get(group);

                SourceSelect sourceSelect = new SourceSelect(
                    group,
                    source.isComplete(),
                    source.getAvailabilityMessage()
                );
                SOURCE_SELECTS.add(sourceSelect);
                ScreenComponents.sourceSelectPanel.add(sourceSelect);
            }
        }

        ScreenComponents.sourceSelectPanel.setLayout(new ListLayout());

        ScreenComponents.sourceSelectScrollPane = new JScrollPane(ScreenComponents.sourceSelectPanel);
        ScreenComponents.sourceSelectScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

    }

    public static class CloseListener implements WindowListener {

        @Override
        public void windowClosing(WindowEvent e) {
            CmodelConverter.RUNNING = false;
        }

        @Override
        public void windowOpened(WindowEvent e) { }
        @Override
        public void windowClosed(WindowEvent e) { }
        @Override
        public void windowIconified(WindowEvent e) { }
        @Override
        public void windowDeiconified(WindowEvent e) { }
        @Override
        public void windowActivated(WindowEvent e) { }
        @Override
        public void windowDeactivated(WindowEvent e) { }

    }

}
