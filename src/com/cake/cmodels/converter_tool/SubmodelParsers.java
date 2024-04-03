package com.cake.cmodels.converter_tool;

import com.cake.cmodels.converter_tool.reading.SubmodelParser;
import com.cake.cmodels.converter_tool.reading.parsers.ObjModelParser;
import com.cake.cmodels.core.types.ResourceLocationLike;

import java.util.HashMap;
import java.util.Map;

public class SubmodelParsers {
    
    protected static HashMap<ResourceLocationLike, SubmodelParser> MODEL_SOURCE_PROCESSORS = new HashMap<>();
    
    //Register providers
    static {
        MODEL_SOURCE_PROCESSORS.putAll(Map.of(
            new ResourceLocationLike("cmodel", "obj"), new ObjModelParser()
        ));
    }
    
    public static void register(ResourceLocationLike resourceLocation, SubmodelParser submodelReader) {
        MODEL_SOURCE_PROCESSORS.put(resourceLocation, submodelReader);
    }
    
    public static SubmodelParser getModelSourceProviderFor(ResourceLocationLike resourceLocation) {
        return MODEL_SOURCE_PROCESSORS.get(resourceLocation);
    }
    
}
