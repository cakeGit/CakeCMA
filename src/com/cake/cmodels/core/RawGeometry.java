package com.cake.cmodels.core;

import com.cake.cmodels.converter_tool.source.Submodel;
import com.cake.cmodels.core.model.Face;
import com.cake.cmodels.core.types.ResourceLocationLike;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RawGeometry implements Submodel {
    
    HashMap<String, List<Face>> groupedGeometry;
    HashMap<String, ResourceLocationLike> materialMap;
    
    //TODO: make reader include material map from source file
    public RawGeometry(HashMap<String, List<Face>> groupedGeometry) {
        this.groupedGeometry = groupedGeometry;
    }
    
    public RawGeometry(HashMap<String, List<Face>> groupedGeometry, HashMap<String, ResourceLocationLike> materialMap) {
        this.groupedGeometry = groupedGeometry;
        this.materialMap = materialMap;
    }
    
    public HashMap<String, List<Face>> getGroupedGeometry() {
        return groupedGeometry;
    }
    
    @Override
    public List<Face> generateVerticies() {
        List<Face> allGeometry = new ArrayList<>();
        for (List<Face> groupedGeometry : groupedGeometry.values())
            allGeometry.addAll(groupedGeometry);
        return allGeometry;
    }
    @Override
    public HashMap<String, ResourceLocationLike> getMaterialTextureMap() {
        return null;
    }
    
}
