package com.cake.cmodels.core.model;

import com.cake.cmodels.core.types.ResourceLocationLike;

import java.util.List;

public class Face {

    List<FaceVertex> vertices;
    VertexNormal vertexNormal;
    ResourceLocationLike texture;
    
    public Face(List<FaceVertex> vertices, VertexNormal vertexNormal, ResourceLocationLike texture) {
        this.vertices = vertices;
        this.vertexNormal = vertexNormal;
        this.texture = texture;
    }
    
    public void setVertices(List<FaceVertex> vertices) {
        this.vertices = vertices;
    }
    public void setVertexNormal(VertexNormal vertexNormal) {
        this.vertexNormal = vertexNormal;
    }
    public void setTexture(ResourceLocationLike texture) {
        this.texture = texture;
    }
    
    public List<FaceVertex> getVertices() {
        return vertices;
    }
    public ResourceLocationLike getTexture() {
        return texture;
    }
    public VertexNormal getVertexNormal() {
        return vertexNormal;
    }
    
}
