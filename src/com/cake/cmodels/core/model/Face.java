package com.cake.cmodels.core.model;

import com.cake.cmodels.core.types.ResourceLocationLike;

import java.util.List;

public class Face {

    List<FaceVertex> vertices;
    ResourceLocationLike texture;

    public Face(List<FaceVertex> vertices, ResourceLocationLike texture) {
        this.vertices = vertices;
        this.texture = texture;
    }

    public List<FaceVertex> getVertices() {
        return vertices;
    }
    public ResourceLocationLike getTexture() {
        return texture;
    }
    
}
