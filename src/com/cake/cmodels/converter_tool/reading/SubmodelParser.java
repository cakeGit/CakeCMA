package com.cake.cmodels.converter_tool.reading;

import com.cake.cmodels.core.submoodel.Submodel;
import org.json.JSONObject;

import java.io.File;

/**Defines something able to generate a submodel from a source def and a file*/
public interface SubmodelParser {

    /**Create the associated model from its file, and the config from the source json*/
    Submodel read(JSONObject source, File file);
    
    /**Used for filename autocomplete*/
    String getDefaultExtension();
    
}
