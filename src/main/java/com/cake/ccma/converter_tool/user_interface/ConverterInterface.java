package com.cake.ccma.converter_tool.user_interface;

import com.cake.ccma.converter_tool.ConversionLog;
import com.cake.ccma.converter_tool.ModelWriter;
import com.cake.ccma.converter_tool.source.ConversionSource;
import com.cake.ccma.converter_tool.CmodelConverter;
import com.cake.ccma.converter_tool.source.MisreadSource;
import com.cake.ccma.converter_tool.user_interface.component.*;
import com.cake.ccma.converter_tool.user_interface.layout.CellResponsiveStrategy;
import com.cake.ccma.converter_tool.user_interface.layout.PanelWithFloatingLayout;
import com.cake.ccma.converter_tool.user_interface.layout.ListLayout;
import com.cake.ccma.converter_tool.user_interface.layout.ResponsiveGridLayout;
import com.cake.ccma.core.Geometry;

import javax.swing.*;
import javax.swing.border.BevelBorder;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConverterInterface {

    public static JFrame frame;

    public static class ScreenComponents {

        public static JPanel sourceSelectPanel = new JPanel();
        public static JScrollPane sourceSelectScrollPane;
        public static JPanel mainPanel = new JPanel();

    }

    public static List<SourceSelect> SOURCE_SELECTS = new ArrayList<>();

    public static void init() {
        frame = new JFrame();
        frame.setSize(1000, 600);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        buildSourceSelectSection();
    
        ResponsiveGridLayout gridLayout = new ResponsiveGridLayout(new Dimension(1000, 600));
    
        gridLayout.addRowStrategy(
            List.of(
                new CellResponsiveStrategy.Scaled(
                    CellResponsiveStrategy.ScalingStrategy.SET_SIZE,
                    CellResponsiveStrategy.ScalingStrategy.WEIGHT,
                    500, 1
                ),
                new CellResponsiveStrategy.Scaled(
                    CellResponsiveStrategy.ScalingStrategy.WEIGHT,
                    CellResponsiveStrategy.ScalingStrategy.SET_SIZE,
                    1, 250
                )
            )
        );
        gridLayout.addRowStrategy(
            List.of(
                new CellResponsiveStrategy.MergeAbove(),
                new CellResponsiveStrategy.Scaled(
                    CellResponsiveStrategy.ScalingStrategy.WEIGHT,
                    CellResponsiveStrategy.ScalingStrategy.WEIGHT,
                    1, 1
                )
            )
        );
    
        ScreenComponents.mainPanel.setLayout(gridLayout);
        
        ScreenComponents.mainPanel.add(ScreenComponents.sourceSelectScrollPane);
        //ScreenComponents.mainPanel.add(new SelectedSourcesLabel());
        ScreenComponents.mainPanel.add(new GenerateButton(
            () -> {
                ConversionLog.startTimer("source_generation", "Began source generation");
                
                System.out.println("Generating sources");
                HashMap<ConversionSource, Geometry> generatedSources = CmodelConverter.generateSelectedSources();
                
                System.out.println("Outputting sources");
                for (Map.Entry<ConversionSource, Geometry> entry : generatedSources.entrySet())
                    ModelWriter.writeGeometry(entry.getKey().getOutputPath(), entry.getValue());
                
                ConversionLog.endTimer("source_generation", "Finished source generation");
                JOptionPane.showMessageDialog(null, "Generated", "Success!", JOptionPane.INFORMATION_MESSAGE);
            }
        ));
        ScreenComponents.mainPanel.add(new ConverterLogDisplay());
    
        String ethicacyMessage = "Sources responsibly and ethically fetched from ";
        frame.add(new FloatingFolderInfoLink(
            ethicacyMessage + "📁" + CmodelConverter.SOURCES_PATH,
            ethicacyMessage + "📂" + CmodelConverter.SOURCES_PATH,
            CmodelConverter.SOURCES_PATH));
        frame.add(ScreenComponents.mainPanel);
        frame.setLayout(new PanelWithFloatingLayout(new Point(), new Dimension(0, 30)));

        frame.setVisible(true);
        frame.setResizable(true);
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
        ScreenComponents.sourceSelectScrollPane.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        ScreenComponents.sourceSelectScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        ScreenComponents.sourceSelectScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

    }

    public static class CloseListener implements WindowListener {

        @Override
        public void windowClosing(WindowEvent e) {
            CmodelConverter.IS_CONVERTING = false;
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
