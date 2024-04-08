package com.cake.cmodels.core.model;

import com.cake.cmodels.core.types.ResourceLocationLike;

import java.util.List;

public class Face {

    List<FaceVertex> vertices;
    VertexNormal vertexNormal;
    String material;
    
    public Face(List<FaceVertex> vertices, VertexNormal vertexNormal, String material) {
        this.vertices = vertices;
        this.vertexNormal = vertexNormal;
        this.material = material;
    }
    
    public void setVertices(List<FaceVertex> vertices) {
        this.vertices = vertices;
    }
    public void setVertexNormal(VertexNormal vertexNormal) {
        this.vertexNormal = vertexNormal;
    }
    public void setMaterial(String material) {
        this.material = material;
    }
    
    public List<FaceVertex> getVertices() {
        return vertices;
    }
    public String getMaterial() {
        return material;
    }
    public VertexNormal getVertexNormal() {
        return vertexNormal;
    }
    
}
