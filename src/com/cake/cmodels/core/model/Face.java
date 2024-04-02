package com.cake.cmodels.core.model;

import java.util.List;

public class Face {

    List<FaceVertex> vertices;

    public Face(List<FaceVertex> vertices) {
        this.vertices = vertices;
    }

    public List<FaceVertex> getVertices() {
        return vertices;
    }
    public void setVertices(List<FaceVertex> vertices) {
        this.vertices = vertices;
    }

}
