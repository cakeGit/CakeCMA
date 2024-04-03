package com.cake.cmodels.core.submoodel.types;

import com.cake.cmodels.core.model.Face;
import com.cake.cmodels.core.model.Vertex;
import com.cake.cmodels.core.submoodel.Submodel;

import java.util.ArrayList;
import java.util.List;

/**Core submodel type, since the compiled types are essentially obj submodels*/
public class ObjSubmodel implements Submodel {

    List<Face> faces = new ArrayList<>();

    public ObjSubmodel() {}


    @Override
    public List<Face> getFaceVerticies() {
        return faces;
    }

}
