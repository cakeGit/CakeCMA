package com.cake.cmodels.converter_tool.user_interface;

import com.cake.cmodels.converter_tool.reading.ConversionSource;
import com.cake.cmodels.converter_tool.CmodelConverter;
import com.cake.cmodels.converter_tool.reading.MisreadSource;
import com.cake.cmodels.converter_tool.user_interface.component.*;
import com.cake.cmodels.converter_tool.user_interface.layout.FixedMarginLayout;
import com.cake.cmodels.converter_tool.user_interface.layout.ListLayout;

import javax.swing.*;

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

        if (CmodelConverter.AVAILABLE_SOURCES.size() == 0 && CmodelConverter.MISREAD_SOURCES.size() == 0) {
            ScreenComponents.sourceSelectPanel.add(new MissingSourcesLabel());
        } else {
            for (ConversionSource source : CmodelConverter.AVAILABLE_SOURCES) {
                SourceSelect sourceSelect = new SourceSelect(source);
                SOURCE_SELECTS.add(sourceSelect);
                ScreenComponents.sourceSelectPanel.add(sourceSelect);
            }
            for (MisreadSource source : CmodelConverter.MISREAD_SOURCES) {
                ScreenComponents.sourceSelectPanel.add(new MisreadSourceDisplay(source));
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
