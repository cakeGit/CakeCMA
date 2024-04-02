package com.cake.cmodels.converter_tool.exceptions;

public class SourceParseError extends Exception {

    boolean muted;

    public SourceParseError(String message, boolean muted) {
        super(message);
        this.muted = muted;
    }

    public boolean isMuted() {
        return muted;
    }

}
