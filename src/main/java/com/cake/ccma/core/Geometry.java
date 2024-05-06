package com.cake.ccma.core;

import com.cake.ccma.converter_tool.source.Submodel;
import com.cake.ccma.core.model.Face;
import com.cake.ccma.core.types.ResourceLocationLike;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Geometry implements Submodel {
    
    HashMap<String, List<Face>> groupedGeometry;
    HashMap<String, ResourceLocationLike> materialMap;
    
    public Geometry(HashMap<String, List<Face>> groupedGeometry, HashMap<String, ResourceLocationLike> materialMap) {
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
