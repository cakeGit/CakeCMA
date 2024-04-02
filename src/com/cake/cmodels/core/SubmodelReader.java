package com.cake.cmodels.core;

import com.cake.cmodels.core.submoodel.SubmodelSource;
import com.cake.cmodels.core.types.ResourceLocationLike;

import java.util.HashMap;

public class SubmodelReader {
    
    protected static HashMap<ResourceLocationLike, SubmodelSource> MODEL_SOURCE_PROVIDERS = new HashMap<>();
    
    public static void register(ResourceLocationLike resourceLocation, SubmodelSource submodelSource) {
        MODEL_SOURCE_PROVIDERS.put(resourceLocation, submodelSource);
    }
    
    public static SubmodelSource getModelSourceProviderFor(ResourceLocationLike resourceLocation) {
        return MODEL_SOURCE_PROVIDERS.get(resourceLocation);
    }
    
}
