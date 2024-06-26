package com.cake.ccma.converter_tool.source;

import com.alibaba.fastjson.JSONObject;
import com.cake.ccma.converter_tool.source.exception.SourceCompileError;

import java.io.File;

/**Defines something able to generate a submodel from a source def and a file*/
public interface SubmodelReader {

    /**Create the associated model from its file, and the config from the source json*/
    Submodel readSource(JSONObject source, File file) throws SourceCompileError;
    
    /**Used for filename autocomplete*/
    String getDefaultExtension();
    
}
