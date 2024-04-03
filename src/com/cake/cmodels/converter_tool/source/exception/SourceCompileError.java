package com.cake.cmodels.converter_tool.source.exception;

public class SourceCompileError extends Exception {
    
    String recommendation;
    
    public SourceCompileError(String message, String recommendation) {
        super(message);
        this.recommendation = recommendation;
    }
    
    public String getRecommendation() {
        return recommendation;
    }
    
}
