package com.cake.ccma.core.model;

public class FaceVertex {

    Vertex vertex;
    VertexUV vertexUV;

    public FaceVertex(Vertex vertex, VertexUV vertexUV) {
        this.vertex = vertex;
        this.vertexUV = vertexUV;
    }
    
    public void setVertex(Vertex vertex) {
        this.vertex = vertex;
    }
    public void setVertexUV(VertexUV vertexUV) {
        this.vertexUV = vertexUV;
    }
    
    public Vertex getVertex() {
        return vertex;
    }
    public VertexUV getVertexUV() {
        return vertexUV;
    }

}
