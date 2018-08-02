package com.seek.generation.map.builder.visual;

import com.badlogic.gdx.utils.Array;

public class VisualData {

    private Array<ModelData> models;

    public VisualData()
    {

    }

    public Array<ModelData> getModels()
    {
        return models;
    }

    public void setModels(Array<ModelData> models){
        this.models = models;
    }
}
