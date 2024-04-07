package com.cake.cmodels.core;

import com.cake.cmodels.core.model.Face;

import java.util.HashMap;
import java.util.List;

public class ModelGeometry {
    
    HashMap<String, Face> groupedGeometry;
    
    public ModelGeometry(HashMap<String, Face> groupedGeometry) {
        this.groupedGeometry = groupedGeometry;
    }
    
    public HashMap<String, Face> getGroupedGeometry() {
        return groupedGeometry;
    }
    
    public List<Face> getGeometry() {
        return groupedGeometry.values().stream().toList();
    }
    
}
