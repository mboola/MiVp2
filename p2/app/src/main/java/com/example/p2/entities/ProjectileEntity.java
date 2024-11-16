package com.example.p2.entities;

import com.example.p2.auxiliary.Vector3;

import javax.microedition.khronos.opengles.GL10;

public class ProjectileEntity extends Entity
{
    public ProjectileEntity(Vector3 position)
    {
        this.position = position;
    }
    public boolean update() {
        return false;
    }

    @Override
    public void draw(GL10 gl) {

    }

    @Override
    protected boolean hasCollided(Vector3 position) {
        return false;
    }
}
