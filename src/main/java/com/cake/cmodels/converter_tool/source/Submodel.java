package com.cake.cmodels.converter_tool.source;

import com.cake.cmodels.core.model.Face;
import com.cake.cmodels.core.types.ResourceLocationLike;

import java.util.HashMap;
import java.util.List;

public interface Submodel {

    List<Face> generateVerticies();
    HashMap<String, ResourceLocationLike> getMaterialTextureMap();

}
