package com.example.p2.entities;

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
    private final int maxFrames = 30;


    public ProjectileEntity(Vector3 position)
    {
        this.position = position;
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
        return super.update();
    }

    @Override
    public void draw(GL10 gl)
    {
        mesh.draw(gl);
    }

    @Override
    protected boolean hasCollided(Vector3 position) {
        return false;
    }
}
