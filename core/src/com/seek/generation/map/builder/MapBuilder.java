package com.seek.generation.map.builder;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.seek.generation.map.builder.exception.IDConflictException;
import com.seek.generation.map.builder.visual.ModelData;
import com.seek.generation.map.builder.visual.ModelInstanceData;
import com.seek.generation.map.builder.visual.VisualData;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.HashMap;

public class MapBuilder {

    private Json json = new Json();

    private boolean finishedLoading;
    private OnLoadedListener onLoadedListener;

    private boolean visualsLoaded = false;

    private VisualData visualData = new VisualData();
    private Array<ModelData> modelDataArray = new Array<ModelData>();

    private ArrayList<ModelData> que = new ArrayList<ModelData>();
    private ArrayList<ModelData> queToRemove = new ArrayList<ModelData>();

    private HashMap<String, ModelInstance> graphicsData = new HashMap<String, ModelInstance>();

    public MapBuilder(OnLoadedListener onLoadedListener) {
        this.onLoadedListener = onLoadedListener;
        visualData.setModels(modelDataArray);
    }

    public MapBuilder addModel(ModelData data) {
        modelDataArray.add(data);

        return this;
    }

    public void load(AssetManager assetManager) {
        finishedLoading = false;
        for (ModelData modelData : modelDataArray) {
            assetManager.load(modelData.getModel(), Model.class);

            que.add(modelData);
        }
    }

    /**
     * this method takes in the asset manager but does NOT! update it
     * @param assetManager
     */
    public void update(AssetManager assetManager) {
        if (onLoadedListener == null) {
            return;
        }

        for (ModelData entry : que) {
            if (assetManager.isLoaded(entry.getModel())) {
                Model model = assetManager.get(entry.getModel(), Model.class);
                Array<ModelInstanceData> instances = entry.getInstances();

                for (int i = 0; i < instances.size; i++) {
                    try {
                        addModelIstance(model, instances.get(i));
                    } catch (IDConflictException e) {
                        e.printStackTrace();
                    }
                }

                queToRemove.add(entry);
            }
        }

        for (ModelData removeQue : queToRemove) {
            que.remove(removeQue);
        }
        queToRemove.clear();

        if (!finishedLoading) {
            if (que.size() <= 0) {
                onLoadedListener.allFinished(graphicsData);
                finishedLoading = true;
            }
        }
    }

    private void addModelIstance(Model model, ModelInstanceData data) throws IDConflictException {
        String id = data.getId();
        String x = data.getX() != null ? data.getX() : "0";
        String y = data.getY() != null ? data.getY() : "0";
        String z = data.getZ() != null ? data.getZ() : "0";
        String rx = data.getRx() != null ? data.getRx() : "0";
        String ry = data.getRy() != null ? data.getRy() : "0";
        String rz = data.getRz() != null ? data.getRz() : "0";

        ModelInstance instance = new ModelInstance(model);
        System.out.println("X : " + x + " Y: " + y + " Z: " + z);
        instance.transform.trn(Float.parseFloat(x), Float.parseFloat(y), Float.parseFloat(z));
        instance.transform.rotate(Vector3.X, Float.parseFloat(rx));
        instance.transform.rotate(Vector3.Y, Float.parseFloat(ry));
        instance.transform.rotate(Vector3.Z, Float.parseFloat(rz));

        for(String key : graphicsData.keySet()){
            if(key.equals(id)){
                throw new IDConflictException("Model Instance with id \"" + id + "\" already exists");
            }
        }

        graphicsData.put(id, instance);
        onLoadedListener.instanceFinished(id, instance);
    }

    private void loadVisualsMap(String dir) {

    }

    private void loadPhysicsMap(String dir) {
        if (visualsLoaded) {
            System.out.println("Loading physics before visuals will mean any bind to a visuals object will not bind");
        }
    }
}
