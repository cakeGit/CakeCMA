package com.cake.cmodels.core;

public class CmodelFileExtensions {

    // In the event that I get cancelled (or you don't want a dumbness filename), please change this to remove my name, c stands for compressed in that case
    private static final boolean isCakeCancelled = false;

    public static String SOURCE_DEFINITION_EXTENSION = isCakeCancelled ? ".cmodelsource.json" : ".cakesource.json";
    public static String COMPILED_FILE_EXTENSION = isCakeCancelled ? ".cmodel" : ".cake";

}
