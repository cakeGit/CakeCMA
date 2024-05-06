package com.cake.ccma.converter_tool.source.readers;

import com.cake.ccma.converter_tool.source.exception.SourceCompileError;

import java.util.List;

class SafeFieldAccessor {
    
    String source;
    List<String> fields;
    
    public SafeFieldAccessor(String source) {
        this.source = source;
        fields = List.of(source.split(" "));
    }
    
    public int getFieldCount() {
        return fields.size();
    }
    
    public String tryGet(int fieldIndex) throws SourceCompileError {
        if (fieldIndex >= fields.size())
            throw new SourceCompileError(
                "Missing field " + fieldIndex + " in .obj from line '" + source + "'",
                "Check .obj file formatting or report to developer"
            );
        else
            return fields.get(fieldIndex);
    }
    
    public Float tryGetFloat(int fieldIndex) throws SourceCompileError {
        if (fieldIndex >= fields.size())
            throw new SourceCompileError(
                "Missing field " + fieldIndex + " in .obj from line '" + source + "'",
                "Check .obj file formatting or report to developer"
            );
        else {
            try {
                return Float.valueOf(fields.get(fieldIndex));
            } catch (NumberFormatException e) {
                throw new SourceCompileError(
                    "Misformatted float value " + fields.get(fieldIndex) + " in .obj from line '" + source + "'",
                    "Check .obj file formatting or report to developer if this should be a valid value"
                );
            }
        }
    }
    
}
