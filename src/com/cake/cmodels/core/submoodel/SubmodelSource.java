package com.cake.cmodels.core.submoodel;

import java.io.File;

public interface SubmodelSource {

    Submodel read(File file);
    
}
