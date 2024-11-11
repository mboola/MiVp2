package com.example.p2;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.KeyEvent;

public class MainActivity extends Activity {

    private MyOpenGLRenderer renderer;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GLSurfaceView view = new GLSurfaceView(this);
        renderer = new MyOpenGLRenderer(this);
        view.setRenderer(renderer);
        setContentView(view);
    }

    /*
     *  When a key is pressed it gets stored in a queue inside
     *  renderer to be handled in the next update.
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        renderer.addKey(keyCode);
        return super.onKeyUp(keyCode, event);  // ??
    }
}