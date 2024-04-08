package com.cake.cmodels.converter_tool.source.exception;

public class SourceReadError extends Exception {
    
    String recommendation;
    
    public SourceReadError(String message, String recommendation) {
        super(message);
        this.recommendation = recommendation;
    }
    
    public String getRecommendation() {
        return recommendation;
    }
    
}
