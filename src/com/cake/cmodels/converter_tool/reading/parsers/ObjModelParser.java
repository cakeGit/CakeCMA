package com.cake.cmodels.converter_tool.reading.parsers;

import com.cake.cmodels.core.submoodel.Submodel;
import com.cake.cmodels.converter_tool.reading.SubmodelParser;
import com.cake.cmodels.core.submoodel.types.ObjSubmodel;
import org.json.JSONObject;

import java.io.File;

public class ObjModelParser implements SubmodelParser {
    
    @Override
    public Submodel read(JSONObject source, File file) {
        //TODO: write lmao
        return new ObjSubmodel();
    }
    
    @Override
    public String getDefaultExtension() {
        return "obj";
    }
    
}
