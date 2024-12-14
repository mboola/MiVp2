package com.example.p2.entities.prefabs;

import com.example.p2.auxiliary.Limits;
import com.example.p2.auxiliary.Mesh;
import com.example.p2.auxiliary.Vector3;
import com.example.p2.entities.Entity;

public class DotEntity extends Entity
{
    public DotEntity(Vector3 position, Mesh mesh)
    {
        this.mesh = mesh;
        this.position = position;
    }

    @Override
    public void update()
    {
        position.z += 0.1f;
        if (Limits.outOfLimits(position))
            position.z = Limits.getFarZ() + position.z;
    }
}
