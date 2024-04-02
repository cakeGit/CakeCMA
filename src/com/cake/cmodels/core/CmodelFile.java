package com.cake.cmodels.core;

import com.cake.cmodels.core.submoodel.Submodel;

import java.util.ArrayList;
import java.util.List;

public class CmodelFile {

    List<Submodel> submodels = new ArrayList<>();

    public CmodelFile() {}

    public CmodelFile(List<Submodel> submodels) {
        this.submodels = submodels;
    }

}
