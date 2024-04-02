package com.cake.cmodels.core.types;

public class ResourceLocationLike {
    
    String namespace;
    String path;
    
    public ResourceLocationLike(String namespace, String path) {
        this.namespace = namespace;
        this.path = path;
    }
    
    public String getNamespace() {
        return namespace;
    }
    public ResourceLocationLike(String path) {
        this.path = path;
    }
    
}
