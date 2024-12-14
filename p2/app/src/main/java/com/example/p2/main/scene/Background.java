package com.example.p2.main.scene;

import com.example.p2.auxiliary.GraphicStorage;
import com.example.p2.auxiliary.Limits;
import com.example.p2.auxiliary.MeshFactory;
import com.example.p2.auxiliary.Vector3;
import com.example.p2.entities.prefabs.DotEntity;
import com.example.p2.entities.Entity;
import com.example.p2.entities.IEntity;

import javax.microedition.khronos.opengles.GL10;

public class Background extends Entity
{
    private final int size;
    private final IEntity[] dots;

    public Background(Vector3 position, int size)
    {
        int dotsLength = (int) Math.abs(Limits.getFarZ());
        this.position = position;
        this.size = size;
        dots = new IEntity[dotsLength];
        int offset = -1;
        for (int i = 0; i < dotsLength; i++)
        {
            dots[i] = new DotEntity(new Vector3(0, Limits.getSpawnY(), (float) (i * offset)), GraphicStorage.getMesh("dots", "base_texture"));
        }
        mesh = MeshFactory.createMesh("background");
    }

    @Override
    public void draw(GL10 gl)
    {
        gl.glPushMatrix();
        gl.glScalef(size, size, 1);
        gl.glTranslatef(position.x, position.y, position.z);
        mesh.draw(gl);
        gl.glPopMatrix();

        for (IEntity dot : dots) dot.draw(gl);
    }

    @Override
    public boolean update()
    {
        for (IEntity dot : dots) dot.update();
        return false;
    }

    @Override
    public boolean hasCollided(Vector3 position) {
        return false;
    }
}
