package com.cake.cmodels.converter_tool.reading.exception;

public class SourceProcessingError extends Exception {
    
    String recommendation;
    
    public SourceProcessingError(String message, String recommendation) {
        super(message);
        this.recommendation = recommendation;
    }
    
    public String getRecommendation() {
        return recommendation;
    }
    
}
