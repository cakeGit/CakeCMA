package com.cake.cmodels.core.submoodel.sources;

import com.cake.cmodels.core.submoodel.Submodel;
import com.cake.cmodels.core.submoodel.SubmodelSource;
import com.cake.cmodels.core.submoodel.types.ObjSubmodel;

import java.io.File;

public class ObjModelSource implements SubmodelSource {
    
    @Override
    public Submodel read(File file) {
        //TODO: write lmao
        return new ObjSubmodel();
    }
    
}
