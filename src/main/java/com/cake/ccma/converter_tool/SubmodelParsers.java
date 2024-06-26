package com.cake.ccma.converter_tool;

import com.cake.ccma.converter_tool.source.SubmodelReader;
import com.cake.ccma.converter_tool.source.readers.ObjModelReader;
import com.cake.ccma.core.types.ResourceLocationLike;

import java.util.HashMap;
import java.util.Map;

public class SubmodelParsers {
    
    protected static HashMap<ResourceLocationLike, SubmodelReader> MODEL_SOURCE_PROCESSORS = new HashMap<>();
    
    //Register providers
    static {
        MODEL_SOURCE_PROCESSORS.putAll(Map.of(
            new ResourceLocationLike("ccma", "obj"), new ObjModelReader()
        ));
    }
    
    public static void register(ResourceLocationLike resourceLocation, SubmodelReader submodelReader) {
        MODEL_SOURCE_PROCESSORS.put(resourceLocation, submodelReader);
    }
    
    public static SubmodelReader getModelSourceProviderFor(ResourceLocationLike resourceLocation) {
        System.out.println(MODEL_SOURCE_PROCESSORS);
        System.out.println(resourceLocation);
        return MODEL_SOURCE_PROCESSORS.get(resourceLocation);
    }
    
}
