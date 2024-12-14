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
    public boolean hasCollided(Vector3 entityPosition)
    {
        float dist_x = entityPosition.x - position.x;
        float dist_y = entityPosition.y - position.y;
        float dist_z = entityPosition.z - position.z;

        float dist_sqrd = dist_x * dist_x + dist_y * dist_y + dist_z + dist_z;
        if (dist_sqrd <= radius * radius)
            return true;
        return false;
    }
}
