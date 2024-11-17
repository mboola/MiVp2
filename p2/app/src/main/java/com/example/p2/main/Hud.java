package com.example.p2.main;

import com.example.p2.entities.IEntity;

import javax.microedition.khronos.opengles.GL10;

public class Hud implements IEntity
{
    private SceneRenderer renderer;
    public Hud(SceneRenderer renderer)
    {
        this.renderer = renderer;
    }

    @Override
    public boolean update() {
        return false;
    }

    @Override
    public void draw(GL10 gl)
    {
        //gl.glPushMatrix();
       // gl.glTranslatef(2,2,0);
       // gl.glScalef(0.5f,0.5f,0.5f);
       // squareText.draw(gl);
       // gl.glPopMatrix();
    }
}
