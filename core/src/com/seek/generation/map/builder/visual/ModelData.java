package com.seek.generation.map.builder.visual;

import com.badlogic.gdx.utils.Array;

public class ModelData {

    private String model;
    private Array<ModelInstanceData> instances;

    public ModelData(){

    }

    public String getModel()
    {
        return model;
    }

    public void setModel(String model){
        this.model = model;
    }

    public Array<ModelInstanceData> getInstances()
    {
        return instances;
    }

    public void setInstances(Array<ModelInstanceData> instances){
        this.instances = instances;
    }
}
