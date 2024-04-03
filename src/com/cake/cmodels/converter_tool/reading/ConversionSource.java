package com.cake.cmodels.converter_tool.reading;

import com.cake.cmodels.converter_tool.CmodelConverter;
import com.cake.cmodels.converter_tool.reading.exception.SourceProcessingError;
import com.cake.cmodels.converter_tool.reading.exception.SourceReadError;
import com.cake.cmodels.converter_tool.SubmodelParsers;
import com.cake.cmodels.core.types.ResourceLocationLike;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ConversionSource {
    
    String displayName;
    String groupName;
    String directory;
    JSONArray submodelSource;
    
    public ConversionSource(String displayName, String groupName, String directory, JSONArray submodelSource) {
        this.directory = directory;
        this.groupName = groupName;
        this.displayName = displayName == null ? groupName : displayName;
        this.submodelSource = submodelSource;
    }
    
    public List<SubmodelSource> readSubmodelFiles() throws SourceProcessingError {
    
        ArrayList<SubmodelSource> submodelSources = new ArrayList<>();
    
        //Reading submodels
        for (int i = 0; i < submodelSource.length(); i++) {
            int safeFinalI = i;
            JSONObject modelSource = safeJSONAction(()->submodelSource.getJSONObject(safeFinalI));
        
            if (!modelSource.has("type"))
                throw new SourceProcessingError("Missing submodel model source type", "Add a corresponding 'type' property to the entry");
        
            String submodelType = modelSource.getString("type");
        
            ResourceLocationLike submodelTypeResourceLocator = ResourceLocationLike.fromString(submodelType);
        
            if (submodelTypeResourceLocator == null)
                throw new SourceProcessingError("Invalid resource locator '" + submodelType + "'", "Missing a colon?");
        
            //Get the reader thing for them
            SubmodelParser submodelReader = SubmodelParsers.getModelSourceProviderFor(submodelTypeResourceLocator);
        
            if (submodelReader == null)
                throw new SourceProcessingError("Could not find model source of type "+ submodelType, "Is it spelled correctly? is everything registered?");
        
            String subpath;
            if (modelSource.has("file"))
                subpath = modelSource.getString("file");
            else
                subpath = directory + File.separator + groupName + submodelReader.getDefaultExtension();
            
            File submodelSource = new File(CmodelConverter.SOURCES_PATH + subpath);
        
            if (!submodelSource.exists())
                throw new SourceProcessingError("Could not find file for source in type " + subpath, "Is it spelled correctly? Is the file in the right loc?");
        
            submodelSources.add(new SubmodelSource(modelSource, submodelSource));
        }
        
        return submodelSources;
    }
    
    /**Catches JSONExceptions and throws out a SourceProcessingError with generic text*/
    private static <T> T safeJSONAction(Supplier<T> action) throws SourceProcessingError {
        try {
            return action.get();
        } catch (JSONException e) {
            throw new SourceProcessingError("Error in parsing json", "Check console and syntax");
        }
    }
    
    public String getDisplayName() {
        return displayName;
    }
    public String getGroupName() {
        return groupName;
    }
    public String getDirectory() {
        return directory;
    }
    public JSONArray getSubmodelSource() {
        return submodelSource;
    }
    
}
