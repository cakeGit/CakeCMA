package com.cake.cmodels.core.submoodel.types;

import com.cake.cmodels.core.model.Face;
import com.cake.cmodels.core.model.Vertex;
import com.cake.cmodels.core.submoodel.Submodel;

import java.util.ArrayList;
import java.util.List;

public class ObjSubmodel implements Submodel {

    List<Face> faces = new ArrayList<>();

    public ObjSubmodel() {}


    @Override
    public List<Face> getFaceVerticies() {
        return faces;
    }

}
