package com.cake.ccma.converter_tool.source;

import com.cake.ccma.core.model.Face;
import com.cake.ccma.core.types.ResourceLocationLike;

import java.util.HashMap;
import java.util.List;

public interface Submodel {

    List<Face> generateVerticies();
    HashMap<String, ResourceLocationLike> getMaterialTextureMap();

}
