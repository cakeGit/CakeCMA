package com.cake.cmodels.converter_tool.source;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.cake.cmodels.converter_tool.CmodelConverter;
import com.cake.cmodels.converter_tool.source.exception.SourceReadError;
import com.cake.cmodels.core.CmodelFileExtensions;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.function.Supplier;

public class SourcesReader {
    
    static String WORKING_DIR = System.getProperty("user.dir");
    public static void readAvailableSources() {

        //Get current dir
        String sourcesDir = WORKING_DIR + File.separator + "in";

        System.out.println("Searching in " + sourcesDir);
        CmodelConverter.SOURCES_PATH = sourcesDir;

        readForSources("", new File(sourcesDir));

        System.out.println("Read " + CmodelConverter.AVAILABLE_SOURCES.size() + " valid sources");
        System.out.println("With " + CmodelConverter.MISREAD_SOURCES.size() + " misread sources");
    }

    /**Takes a file or folder and either searches for sources inside or tries to read it for a source*/
    protected static void readForSources(String subdirectory, File file) {
        if (!file.exists())
            return;
        if (file.isFile())
            tryReadSource(subdirectory, file);
        else {
            File[] subfields = file.listFiles();
            subdirectory += file.getName() + File.separator;
            
            if (subfields == null)
                return;
            
            for (File subFile : subfields) {
                readForSources(subdirectory, subFile);
            }
        }
        
    }
    
    static String currentReaderDisplayName;
    /**Reads a file, if it fails it just skips*/
    protected static void tryReadSource(String subdirectory, File file) {
        try {
            if (file.getName().endsWith(CmodelFileExtensions.SOURCE_DEFINITION_EXTENSION)) {
                currentReaderDisplayName = null;
                readSourceFile(subdirectory, file);
            }
        } catch (SourceReadError readError) {
            CmodelConverter.MISREAD_SOURCES.add(new MisreadSource(subdirectory, getDefaultGroupNameOfFile(file), currentReaderDisplayName, readError));
        }
    }
    
    private static void readSourceFile(String subdirectory, File file) throws SourceReadError {
        StringBuilder fileContents = new StringBuilder();
    
        Scanner reader;
        try {
            reader = new Scanner(file);
        } catch (FileNotFoundException e) {
            //File is expected to exist
            throw new RuntimeException(e);
        }
        while (reader.hasNextLine()) fileContents.append(reader.nextLine());
        reader.close();
        
        JSONObject fileData = safeJSONAction(() -> JSONObject.parseObject(fileContents.toString()));
        
        String displayName = fileData.containsKey("display_name") ? fileData.getString("display_name") : null;
        currentReaderDisplayName = displayName;
        
        if (!fileData.containsKey("model_sources"))
            throw new SourceReadError("Missing model sources", "Add 'model_sources' property to the sources def");
        
        JSONArray modelSources = safeJSONAction(() -> fileData.getJSONArray("model_sources"));
        
        CmodelConverter.AVAILABLE_SOURCES.add(new ConversionSource(
            displayName, getDefaultGroupNameOfFile(file), subdirectory, modelSources
        ));
        
    }
    
    /**
     * Stolen from stack overflow with no shame
     * @author walen
     * */
    static int nthLastIndexOf(int nth, String ch, String string) {
        if (nth <= 0) return string.length();
        return nthLastIndexOf(--nth, ch, string.substring(0, string.lastIndexOf(ch)));
    }
    
    /**Get second occurrence of . which is the group name for .cakesource.json files*/
    private static String getDefaultGroupNameOfFile(File file) {
        String name = file.getName();
        return name.substring(0, nthLastIndexOf(2, ".", name));
    }
    
    /**Catches JSONExceptions and throws out a SourceReadError with generic text*/
    private static <T> T safeJSONAction(Supplier<T> action) throws SourceReadError {
        try {
            return action.get();
        } catch (JSONException e) {
            throw new SourceReadError("Error in parsing json", "Check console and syntax");
        }
    }
    
    public static String getExtensionOfFile(File file) {
        String name = file.getName();
        int lastPeriodIndex = name.lastIndexOf(".");
        if (name.length() < lastPeriodIndex +1)
            return "";
        return name.substring(lastPeriodIndex +1);
    }

}
