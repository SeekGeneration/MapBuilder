package com.seek.generation.map.builder;

import com.badlogic.gdx.graphics.g3d.ModelInstance;

import java.util.HashMap;

public interface OnLoadedListener {

    /**
     * called when everything is finished loading
     * @param graphicsData
     */
    void allFinished(HashMap<String, ModelInstance> graphicsData);

    /**
     * called when a single model/instance has finished loading
     */
    void instanceFinished(String id, ModelInstance instance);
}
