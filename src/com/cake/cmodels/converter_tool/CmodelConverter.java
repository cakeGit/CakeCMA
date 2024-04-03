package com.cake.cmodels.converter_tool;

import com.cake.cmodels.converter_tool.reading.MisreadSource;
import com.cake.cmodels.converter_tool.reading.exception.SourceReadError;
import com.cake.cmodels.converter_tool.reading.ConversionSource;
import com.cake.cmodels.converter_tool.reading.SourcesReader;
import com.cake.cmodels.converter_tool.user_interface.ConverterInterface;

import java.util.*;

public class CmodelConverter {

    public static boolean RUNNING = true;
    
    public static List<ConversionSource> AVAILABLE_SOURCES = new ArrayList<>();
    public static List<MisreadSource> MISREAD_SOURCES = new ArrayList<>();
    
    public static String SOURCES_PATH = "unknown";

    private static final Timer tickTimer = new Timer();
    /**Used for generating output in Cobj-sources, see usage docs for app usage ... if ive made them yet, bug me if not*/
    public static void main(String[] args) {
        System.out.println("Good Morning Starshine, The Earth Says \"Hello!\"");

        SourcesReader.readAvailableSources();

        ConverterInterface.init();
        ConverterInterface.frame.addWindowListener(new ConverterInterface.CloseListener());

        tickTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                ConverterInterface.frame.repaint();
            }
        }, 0, (1000 / 45));
    }



}
