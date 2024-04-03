package com.cake.cmodels.converter_tool;

import java.util.ArrayList;
import java.util.List;

public class ConverterLog {

    public static boolean isConvertingLogEnabled = false;
    public static List<String> LOG = new ArrayList<>();

    public static void log(String msg) {
        if (isConvertingLogEnabled)
            new RuntimeException("Called putToLog but the converter is not currently converting, something broken ig").printStackTrace();
        else
            LOG.add(msg);
    }

    public static void clear() {
        LOG = new ArrayList<>();
    }

}
