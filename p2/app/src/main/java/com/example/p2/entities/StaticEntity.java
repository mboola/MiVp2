package com.example.p2.entities;

import com.example.p2.auxiliary.Mesh;
import com.example.p2.auxiliary.Vector3;

import javax.microedition.khronos.opengles.GL10;

public class StaticEntity extends Entity
{
    public StaticEntity(Vector3 position, Mesh mesh)
    {
        this.mesh = mesh;
        this.position = position;
    }

    @Override
    protected boolean hasCollided(Vector3 position) {
        return false;
    }
}
