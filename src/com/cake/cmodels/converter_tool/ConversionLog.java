package com.cake.cmodels.converter_tool;

import com.cake.cmodels.converter_tool.source.exception.SourceCompileError;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConversionLog {

    public static boolean isConvertingLogEnabled = false;
    public static List<LogEntry> LOG = new ArrayList<>();

    protected static HashMap<String, Long> activeTimerStarts = new HashMap<>();
    
    public static void log(String msg) {
        LOG.add(new LogEntry(EntryCategory.INFO, msg));
    }
    
    public static void startTimer(String key, String msg) {
        activeTimerStarts.put(key, System.currentTimeMillis());
        log(msg);
    }
    
    public static void endTimer(String key, String msg) {
        String time = activeTimerStarts.containsKey(key)
            ? (System.currentTimeMillis() - activeTimerStarts.get(key)) + "ms"
            : " unknown time";
        activeTimerStarts.remove(key);
        log(msg + " in " + time);
    }
    
    
    public static void error(SourceCompileError e) {
        LOG.add(new LogEntry(EntryCategory.ERROR, "Error in generation:"));
        LOG.add(new LogEntry(EntryCategory.ERROR, e.getMessage()));
        LOG.add(new LogEntry(EntryCategory.ERROR, e.getRecommendation()));
    }
    
    public static void warn(String msg) {
        LOG.add(new LogEntry(EntryCategory.WARN, msg));
    }
    
    public static void clear() {
        LOG = new ArrayList<>();
    }
    
    public record LogEntry(EntryCategory entryCategory, String message) { }
    
    private enum EntryCategory {
        INFO(new Color(0, 0, 0)),
        WARN(new Color(150, 150, 0)),
        ERROR(new Color(255, 0, 0));
        
        final Color color;
        
        EntryCategory(Color color) {
            this.color = color;
        }
    
        public Color getColor() {
            return color;
        }
    }
    
}
