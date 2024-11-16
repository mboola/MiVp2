package com.example.p2.main;

import com.example.p2.Square;
import com.example.p2.auxiliary.GraphicStorage;
import com.example.p2.auxiliary.Mesh;
import com.example.p2.auxiliary.Vector3;
import com.example.p2.entities.Entity;

import javax.microedition.khronos.opengles.GL10;

public class SpaceShip extends Entity
{
    public SpaceShip(Vector3 position)
    {
        this.position = position;
        mesh = GraphicStorage.getMesh("armwing", "armwing_texture");
    }

    public void move(float x, float y, float z)
    {
        position.translate(x, y, z);
    }

    public boolean update() {
        return false;
    }

    public void draw(GL10 gl)
    {
        gl.glPushMatrix();
        gl.glTranslatef(position.x, position.y, position.z);
        mesh.draw(gl);
        gl.glPopMatrix();
    }

    @Override
    protected boolean hasCollided(Vector3 position) {
        return false;
    }
}
