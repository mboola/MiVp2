package com.example.p2.entities;

import com.example.p2.auxiliary.Limits;
import com.example.p2.auxiliary.MeshFactory;
import com.example.p2.auxiliary.Vector3;

import javax.microedition.khronos.opengles.GL10;

public class ProjectileEntity extends Entity
{
    private float[] uvs = { // Texture coords for the above face (NEW)
            0.0f, 1.0f,  // A. left-bottom (NEW)
            0.2f, 1.0f,  // B. right-bottom (NEW)
            0.0f, 0.0f,  // C. left-top (NEW)
            0.2f, 0.0f   // D. right-top (NEW)
    };

    private int frames;
    private final int maxFrames = 15;
    private float velMult = 0.05f;
    private Vector3 direction;

    public ProjectileEntity(Vector3 position, Vector3 direction)
    {
        this.position = position;
        this.direction = direction;
        mesh = MeshFactory.createMesh("proyectile");
        mesh.setUVs(uvs);
        frames = 0;
    }

    @Override
    public boolean update()
    {
        // Update UVs
        if (frames > maxFrames)
        {
            // Change UVs
            uvs[0] += 0.2f;
            uvs[2] += 0.2f;
            uvs[4] += 0.2f;
            uvs[6] += 0.2f;
            mesh.setUVs(uvs);
            frames = 0;
        }
        frames++;
        position.x -= (direction.x * velMult * 0.1f);
        position.y -= (direction.y * -velMult * 0.1f);
        position.z -= (direction.z * velMult);
        return Limits.outOfLimits(position);
    }

    @Override
    protected boolean hasCollided(Vector3 position) {
        return false;
    }
}
