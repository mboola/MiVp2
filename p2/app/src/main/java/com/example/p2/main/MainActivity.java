package com.example.p2.main;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.KeyEvent;

import com.example.p2.auxiliary.GraphicStorage;

public class MainActivity extends Activity {

    private SceneRenderer renderer;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize all graphics (meshes and textures)
        GraphicStorage.Initialize(this);

        GLSurfaceView view = new GLSurfaceView(this);
        renderer = SceneRenderer.getRenderer();
        view.setRenderer(renderer);
        setContentView(view);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event)
    {
        renderer.keyPushed(keyCode);
        return true;
    }
}