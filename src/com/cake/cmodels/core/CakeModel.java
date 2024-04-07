package com.cake.cmodels.core;

import java.util.List;

/**Core submodel type, since the compiled types are essentially obj submodels*/
public class CakeModel {
    
    /**
     * When sourced from a compiled .cake this is a single element
     * But when sourced it will contain each submodel
     * */
    List<RawGeometry> rawGeometries;
    
    public CakeModel(List<RawGeometry> rawGeometries) {
        this.rawGeometries = rawGeometries;
    }
    
    public CakeModel(RawGeometry rawGeometry) {
        this.rawGeometries = List.of(rawGeometry);
    }

}
