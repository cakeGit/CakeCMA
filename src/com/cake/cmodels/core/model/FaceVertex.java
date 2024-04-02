package com.cake.cmodels.core.model;

public class FaceVertex {

    Vertex vertex;
    VertexNormal vertexNormal;
    VertexUV vertexUV;

    public FaceVertex(Vertex vertex, VertexNormal vertexNormal, VertexUV vertexUV) {
        this.vertex = vertex;
        this.vertexNormal = vertexNormal;
        this.vertexUV = vertexUV;
    }

    public Vertex getVertex() {
        return vertex;
    }
    public void setVertex(Vertex vertex) {
        this.vertex = vertex;
    }
    public VertexNormal getVertexNormal() {
        return vertexNormal;
    }
    public void setVertexNormal(VertexNormal vertexNormal) {
        this.vertexNormal = vertexNormal;
    }
    public VertexUV getVertexUV() {
        return vertexUV;
    }
    public void setVertexUV(VertexUV vertexUV) {
        this.vertexUV = vertexUV;
    }

}
