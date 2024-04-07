package com.cake.cmodels.core;

/**Core submodel type, since the compiled types are essentially obj submodels*/
public class CakeModel {
    
    ModelGeometry modelGeometry;
    
    public CakeModel(ModelGeometry modelGeometry) {
        this.modelGeometry = modelGeometry;
    }
    
    public ModelGeometry getGeometry() {
        return modelGeometry;
    }

}
