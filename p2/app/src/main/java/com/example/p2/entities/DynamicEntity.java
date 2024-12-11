package com.example.p2.entities;

import com.example.p2.auxiliary.Mesh;
import com.example.p2.auxiliary.Vector3;

import javax.microedition.khronos.opengles.GL10;

public class DynamicEntity extends Entity
{
    private boolean goingUp = true;
    private float maxHeight = 1f;
    public DynamicEntity(Vector3 position, Mesh mesh)
    {
        this.mesh = mesh;
        this.position = position;
    }
    @Override
    public boolean update() {
        if (goingUp)
        {
            position.y += 0.05f;
            if (position.y >= maxHeight)
                goingUp = false;
        }
        else
        {
            position.y -= 0.05f;
            if (position.y <= -maxHeight)
                goingUp = true;
        }
        return super.update();
    }
}
