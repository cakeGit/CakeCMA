package com.cake.cmodels.core;

import com.cake.cmodels.core.model.Face;

import java.util.List;

/**Core submodel type, since the compiled types are essentially obj submodels*/
public class CakeModel {
    
    List<Face> faces;
    
    public CakeModel(List<Face> faces) {
        this.faces = faces;
    }
    
    public List<Face> getFaceVerticies() {
        return faces;
    }

}
