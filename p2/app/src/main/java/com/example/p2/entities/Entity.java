package com.example.p2.entities;

import com.example.p2.auxiliary.Limits;
import com.example.p2.auxiliary.Mesh;
import com.example.p2.auxiliary.Vector3;

import javax.microedition.khronos.opengles.GL10;

/*
 *  Entity is basically everything that has a mesh and a texture.
 */
public abstract class Entity implements IEntity
{
    protected Vector3 position;
    protected Mesh mesh;
    protected boolean alive = true;
    public boolean hasCollided(Vector3 position) {
        return false;
    }
    public void hit()
    {
        alive = false;
    }
    public boolean isHit()
    {
        return !alive;
    }

    public boolean update()
    {
        position.z += 0.1f;
        return Limits.outOfLimits(position);
    }
    public void draw(GL10 gl)
    {
        gl.glPushMatrix();
        gl.glTranslatef(position.x, position.y, position.z);
        mesh.draw(gl);
        gl.glPopMatrix();
    }
}
