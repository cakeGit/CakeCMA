package com.cake.cmodels.converter_tool;

import com.cake.cmodels.converter_tool.user_interface.ConverterInterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class CmodelConverter {

    public static boolean RUNNING = true;
    public static Map<String, ConversionSource> SOURCES_MAP = new HashMap<>();
    public static List<String> SOURCES_ORDERED = List.of();
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
