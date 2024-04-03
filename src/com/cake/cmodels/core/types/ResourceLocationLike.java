package com.cake.cmodels.core.types;

public class ResourceLocationLike {
    
    String namespace;
    String path;
    
    public ResourceLocationLike(String namespace, String path) {
        this.namespace = namespace;
        this.path = path;
    }
    
    public static ResourceLocationLike fromString(String resourceLocation) {
        int splitCharIndex = resourceLocation.indexOf(":");
        if (splitCharIndex == -1) return null;
        String namespace = resourceLocation.substring(0, splitCharIndex);
        String path = resourceLocation.substring(splitCharIndex);
        return new ResourceLocationLike(namespace, path);
    }
    
    @Override
    public String toString() {
        return this.namespace + ":" + this.path;
    }
    
    public String getNamespace() {
        return namespace;
    }
    public String getPath() {
        return path;
    }
    
}
