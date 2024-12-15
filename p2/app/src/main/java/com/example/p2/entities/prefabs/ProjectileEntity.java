package com.example.p2.entities.prefabs;

import com.example.p2.auxiliary.GraphicStorage;
import com.example.p2.auxiliary.Limits;
import com.example.p2.auxiliary.MeshFactory;
import com.example.p2.auxiliary.Vector3;
import com.example.p2.entities.Entity;
import com.example.p2.main.scene.SceneRenderer;

import javax.microedition.khronos.opengles.GL10;

public class ProjectileEntity extends Entity
{
    private float[] uvs = { // Texture coords for the above face (NEW)
            0.0f, 1.0f,  // A. left-bottom (NEW)
            0.5f, 1.0f,  // B. right-bottom (NEW)
            0.0f, 0.0f,  // C. left-top (NEW)
            0.5f, 0.0f   // D. right-top (NEW)
    };

    private int frames;
    private final int maxFrames = 15;
    private float velMult = 0.05f;
    private Vector3 direction;

    public ProjectileEntity(Vector3 position, Vector3 direction)
    {
        this.position = position;
        this.direction = direction;
        mesh = MeshFactory.createMesh("projectile_texture");
        mesh.setUVs(uvs);
        frames = 0;
    }

    @Override
    public void update()
    {
        // Update UVs
        if (frames > maxFrames)
        {
            // Change UVs
            uvs[0] += 0.5f;
            uvs[2] += 0.5f;
            uvs[4] += 0.5f;
            uvs[6] += 0.5f;
            mesh.setUVs(uvs);
            frames = 0;
        }
        frames++;
        position.x -= (direction.x * velMult);
        position.y -= (direction.y * -velMult);
        position.z -= (direction.z * velMult);
        boolean outOfLimits = Limits.outOfLimits(position);
        if (!outOfLimits)
        {
            if (SceneRenderer.getRenderer().getEntityController().checkCollisions(position))
                hasBeenHit();
        }
    }

    @Override
    public void draw(GL10 gl)
    {
        gl.glDisable(GL10.GL_LIGHTING);
        gl.glPushMatrix();
        gl.glTranslatef(position.x, position.y, position.z);
        gl.glScalef(0.1f, 0.1f, 1);
        mesh.draw(gl);
        gl.glPopMatrix();
        gl.glEnable(GL10.GL_LIGHTING);
    }

}
