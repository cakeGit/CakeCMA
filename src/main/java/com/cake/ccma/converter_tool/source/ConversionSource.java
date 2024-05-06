package com.cake.ccma.converter_tool.source;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.cake.ccma.converter_tool.CmodelConverter;
import com.cake.ccma.converter_tool.ConversionLog;
import com.cake.ccma.converter_tool.source.exception.SourceCompileError;
import com.cake.ccma.converter_tool.SubmodelParsers;
import com.cake.ccma.core.Geometry;
import com.cake.ccma.core.model.Face;
import com.cake.ccma.core.types.ResourceLocationLike;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
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
    
    public Geometry compileGeometry() throws SourceCompileError {
        ConversionLog.log("Generating geometry for " + displayName);
        List<Face> geometry = new ArrayList<>();
        HashMap<String, List<Face>> groupedGeometry = new HashMap<>();
        HashMap<String, ResourceLocationLike> materialMap = new HashMap<>();
    
        ConversionLog.log("Reading submodel geometries for " + displayName);
        List<Submodel> submodels = readSubmodels();
        
        ConversionLog.log("Compiling all submodel geometries for " + displayName);
        for (Submodel submodel : submodels) {
            geometry.addAll(submodel.generateVerticies());
            materialMap.putAll(submodel.getMaterialTextureMap());
        }
        for (Face face : geometry) {
            String group = face.getMaterial();
            groupedGeometry.put(group,
                groupedGeometry.containsKey(group) ?
                    List.of(face) : groupedGeometry.get(group)
            );
        }
        ConversionLog.log("Compiled geometry for " + displayName);
        
        return new Geometry(groupedGeometry, materialMap);
    }
    
    public List<Submodel> readSubmodels() throws SourceCompileError {
        ArrayList<Submodel> submodelSources = new ArrayList<>();
    
        //Reading submodels
        for (int i = 0; i < submodelSource.size(); i++) {
            ConversionLog.log("Reading submodel " + (i + 1));
            
            int safeFinalI = i;
            JSONObject modelSource = safeJSONAction(()->submodelSource.getJSONObject(safeFinalI));
        
            if (!modelSource.containsKey("type"))
                throw new SourceCompileError("Missing submodel model source type", "Add a corresponding 'type' property to the entry");
        
            String submodelType = modelSource.getString("type");
        
            ResourceLocationLike submodelTypeResourceLocator = ResourceLocationLike.fromString(submodelType);
        
            if (submodelTypeResourceLocator == null)
                throw new SourceCompileError("Invalid resource locator '" + submodelType + "'", "Missing a colon?");
        
            //Get the reader thing for them
            SubmodelReader submodelReader = SubmodelParsers.getModelSourceProviderFor(submodelTypeResourceLocator);
        
            if (submodelReader == null)
                throw new SourceCompileError("Could not find model source of type "+ submodelType, "Is it spelled correctly? is everything registered?");
        
            //Todo: Maybe in future give proper support for non-file model sources
            String subpath;
            if (modelSource.containsKey("file"))
                subpath = modelSource.getString("file");
            else
                subpath = directory + groupName + "." + submodelReader.getDefaultExtension();
            
            File submodelFileSource = new File(CmodelConverter.SOURCES_PATH + File.separator + subpath);
        
            if (!submodelFileSource.exists())
                throw new SourceCompileError("Could not find file for source in type " + submodelFileSource, "Is it spelled correctly? Is the file in the right loc?");
    
            ConversionLog.log("Reading model source of type " + submodelTypeResourceLocator);
            submodelSources.add(submodelReader.readSource(modelSource, submodelFileSource));
        }
        
        return submodelSources;
    }
    
    /**Catches JSONExceptions and throws out a SourceProcessingError with generic text*/
    private static <T> T safeJSONAction(Supplier<T> action) throws SourceCompileError {
        try {
            return action.get();
        } catch (JSONException e) {
            throw new SourceCompileError("Error in parsing json", "Check console and syntax");
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
    public String getOutputPath() {
        return directory + groupName;
    }
    public JSONArray getSubmodelSource() {
        return submodelSource;
    }
    
}
