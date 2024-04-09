package com.cake.cmodels.core.types;

import java.util.Objects;

public class ResourceLocationLike {
    
    String namespace;
    String path;
    
    public ResourceLocationLike(String namespace, String path) {
        this.namespace = namespace;
        this.path = path;
    }
    
    public static ResourceLocationLike fromString(String resourceLocation) {
        int splitCharIndex = resourceLocation.indexOf(":");
        if (splitCharIndex == -1 || splitCharIndex +1 > resourceLocation.length()) return null;
        String namespace = resourceLocation.substring(0, splitCharIndex);
        String path = resourceLocation.substring(splitCharIndex + 1);
        return new ResourceLocationLike(namespace, path);
    }
    
    @Override
    public String toString() {
        return this.namespace + ":" + this.path;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResourceLocationLike that = (ResourceLocationLike) o;
        return Objects.equals(namespace, that.namespace) && Objects.equals(path, that.path);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(namespace, path);
    }
    
    public String getNamespace() {
        return namespace;
    }
    public String getPath() {
        return path;
    }
    
}
