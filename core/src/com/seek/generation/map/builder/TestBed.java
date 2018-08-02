package com.seek.generation.map.builder;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.utils.Array;
import com.seek.generation.map.builder.visual.ModelData;
import com.seek.generation.map.builder.visual.ModelInstanceData;

import java.util.HashMap;

public class TestBed extends ApplicationAdapter {

    private AssetManager assetManager;
    private PerspectiveCamera camera;
    private ModelBatch modelBatch;

    private HashMap<String, ModelInstance> graphicsData = new HashMap<String, ModelInstance>();
    private MapBuilder mapBuilder;

    @Override
    public void create() {
        assetManager = new AssetManager();
        modelBatch = new ModelBatch();

        camera = new PerspectiveCamera(70, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.near = 0.1f;
        camera.far = 5000f;
        camera.update();


        OnLoadedListener onLoadedListener = new OnLoadedListener() {
            @Override
            public void allFinished(HashMap<String, ModelInstance> gd) {
//                graphicsData = gd;
            }

            @Override
            public void instanceFinished(String id, ModelInstance instance) {
                graphicsData.put(id, instance);
            }
        };

        mapBuilder = new MapBuilder(onLoadedListener);
        ModelData modelData = new ModelData();
        modelData.setModel("box.obj");
        Array<ModelInstanceData> instances = new Array<ModelInstanceData>();

        ModelInstanceData instance = new ModelInstanceData();
        instance.setZ("-5");
        instance.setRz("25");
        instance.setRy("35");
        instances.add(instance);
        modelData.setInstances(instances);
        mapBuilder.addModel(modelData);

        mapBuilder.load(assetManager);
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        assetManager.update();

        mapBuilder.update(assetManager);

        camera.update();

        modelBatch.begin(camera);
        modelBatch.render(graphicsData.values());
        modelBatch.end();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        modelBatch.dispose();
        assetManager.dispose();
    }
}
