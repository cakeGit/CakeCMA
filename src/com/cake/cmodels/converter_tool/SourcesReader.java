package com.cake.cmodels.converter_tool;

import com.cake.cmodels.converter_tool.exceptions.SourceParseError;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SourcesReader {

    public static void readAvailableSources() {

        //Get current dir
        String workingDir = System.getProperty("user.dir");
        String sourcesDir = workingDir + File.separator + "in";

        System.out.println("Searching in " + sourcesDir);
        CmodelConverter.SOURCES_PATH = sourcesDir;

        readForSources(new File(sourcesDir));

        System.out.println("Read " + CmodelConverter.SOURCES_MAP.keySet().size() + " sources");

        List<String> keySet = new ArrayList<>(CmodelConverter.SOURCES_MAP.keySet().stream().toList());
        keySet.sort((groupA, groupB) -> {
            if (CmodelConverter.SOURCES_MAP.get(groupA).isComplete()) {
                if (CmodelConverter.SOURCES_MAP.get(groupB).isComplete())
                    return 0;
                else
                    return -1;
            } else {
                if (CmodelConverter.SOURCES_MAP.get(groupB).isComplete())
                    return 1;
                else
                    return 0;
            }
        });

        CmodelConverter.SOURCES_ORDERED = keySet;
    }

    /**Takes a file or folder and either searches for sources inside or tries to read it for a source*/
    protected static void readForSources(File file) {
        if (!file.exists())
            return;
        else if (file.isFile())
            tryReadSource(file);
        else
            for (File subFile : file.listFiles()) {
                readForSources(subFile);
            }
    }

    /**Reads a file, if it fails it just skips*/
    protected static void tryReadSource(File file) {
        try {

        } catch (SourceParseError parseError) {
            if (!parseError.isMuted()) {

            }
        }
    }

    protected static

    public static String getExtensionOfFile(File file) {
        String name = file.getName();
        int lastPeriodIndex = name.lastIndexOf(".");
        if (name.length() < lastPeriodIndex +1)
            return "";
        return name.substring(lastPeriodIndex +1);
    }

}
