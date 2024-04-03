package com.cake.cmodels.converter_tool.reading;

import org.json.JSONObject;

import java.io.File;

public class SubmodelSource {
    
    JSONObject source;
    File file;
    
    public SubmodelSource(JSONObject source, File file) {
        this.source = source;
        this.file = file;
    }
    
    public JSONObject getSource() {
        return source;
    }
    public File getFile() {
        return file;
    }
    
}
