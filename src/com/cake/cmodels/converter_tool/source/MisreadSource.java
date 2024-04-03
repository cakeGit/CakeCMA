package com.cake.cmodels.converter_tool.source;

import com.cake.cmodels.converter_tool.source.exception.SourceReadError;

public class MisreadSource {
    
    String directory;
    String groupName;
    String displayName;
    SourceReadError readError;
    
    public MisreadSource(String directory, String groupName, String displayName, SourceReadError readError) {
        this.directory = directory;
        this.groupName = groupName;
        this.displayName = displayName == null ? groupName : displayName;
        this.readError = readError;
    }
    
    public String getDirectory() {
        return directory;
    }
    public String getGroupName() {
        return groupName;
    }
    public String getDisplayName() {
        return displayName;
    }
    public SourceReadError getReadError() {
        return readError;
    }
    
}
