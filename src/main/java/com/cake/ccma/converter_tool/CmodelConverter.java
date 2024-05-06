package com.cake.ccma.converter_tool;

import com.cake.ccma.converter_tool.source.MisreadSource;
import com.cake.ccma.converter_tool.source.ConversionSource;
import com.cake.ccma.converter_tool.source.SourcesReader;
import com.cake.ccma.converter_tool.source.exception.SourceCompileError;
import com.cake.ccma.converter_tool.user_interface.ConverterInterface;
import com.cake.ccma.converter_tool.user_interface.component.SourceSelect;
import com.cake.ccma.core.Geometry;

import java.util.*;

public class CmodelConverter {

    public static boolean RUNNING = true;
    public static boolean IS_CONVERTING = false;
    
    public static List<ConversionSource> AVAILABLE_SOURCES = new ArrayList<>();
    public static List<MisreadSource> MISREAD_SOURCES = new ArrayList<>();
    
    /**Should get overwritten*/
    public static String SOURCES_PATH = "unknown";

    private static final Timer interfaceTickTimer = new Timer();
    /**Used for generating output in Cobj-sources, see usage docs for app usage ... if ive made them yet, bug me if not*/
    public static void main(String[] args) {
        System.out.println("Good Morning Starshine, The Earth Says \"Hello!\"");

        SourcesReader.readAvailableSources();

        ConverterInterface.init();
        ConverterInterface.frame.addWindowListener(new ConverterInterface.CloseListener());

        interfaceTickTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                ConverterInterface.frame.repaint();
            }
        }, 0, (1000 / 45));
    }

    /**
     * @Returns a map of the selected source to its newly generated geometry
     * */
    public static HashMap<ConversionSource, Geometry> generateSelectedSources() {
    
        List<ConversionSource> sourcesToConvert = ConverterInterface.SOURCE_SELECTS
            .stream().filter(SourceSelect::isSelected).map(SourceSelect::getSource).toList();
        
        HashMap<ConversionSource, Geometry> generatedSources = new HashMap<>();
        
        for (ConversionSource source : sourcesToConvert) {
            ConversionLog.log("Converting source " + source.getDisplayName());
    
            try {
                Geometry geometry = source.compileGeometry();
    
                generatedSources.put(source, geometry);
            } catch (SourceCompileError e) {
                ConversionLog.error(e);
            }
        }
        
        return generatedSources;
    }

}
