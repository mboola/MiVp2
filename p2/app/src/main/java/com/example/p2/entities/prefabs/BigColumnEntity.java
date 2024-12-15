package com.example.p2.entities.prefabs;

import com.example.p2.auxiliary.GraphicStorage;
import com.example.p2.auxiliary.Time;
import com.example.p2.auxiliary.Vector3;
import com.example.p2.entities.Entity;

import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

public class BigColumnEntity extends Entity
{
    private final Vector3 rotation;
    private final float scale;
    private boolean falling = false;
    private float radius = 1f;
    private float tryToFall = 1f;
    public BigColumnEntity(Vector3 position, Vector3 rotation)
    {
        scale = 2.5f;
        this.position = position;
        this.rotation = rotation;
        mesh = GraphicStorage.getMesh("static", "static_texture");
    }

    @Override
    public void update()
    {
        if (falling)
        {
            if (rotation.x < 90)
            {
                rotation.x += Time.deltaTime * 100;
            }
        }
        else
        {
            if (tryToFall <= 0)
            {
                // random possibility of having a random possibility of falling
                Random random = new Random();
                if (random.nextInt(100) > 50)
                    falling = true;
                else
                    tryToFall = 1;
            }
            else
                tryToFall -= Time.deltaTime;
        }
        super.update();
    }

    @Override
    public void draw(GL10 gl)
    {
        if (isDead())
            return;
        gl.glPushMatrix();
        gl.glTranslatef(position.x, position.y, position.z);
        if (rotation.x != 0)
            gl.glRotatef(rotation.x, 1, 0, 0);
        if (rotation.y != 0)
            gl.glRotatef(rotation.y, 0, 1, 0);
        if (rotation.z != 0)
            gl.glRotatef(rotation.z, 0, 0, 1);
        gl.glScalef(scale, scale, scale);
        mesh.draw(gl);
        gl.glPopMatrix();
    }

    @Override
    public boolean hasCollided(Vector3 entityPosition)
    {
        if (falling)
            return false;

        float dist_x = entityPosition.x - position.x;
        float dist_y = entityPosition.y - position.y;
        float dist_z = entityPosition.z - position.z;

        float dist_sqrd = dist_x * dist_x + dist_y * dist_y + dist_z + dist_z;
        if (dist_sqrd <= radius * radius)
            return true;
        return false;
    }

    @Override
    public void hasBeenHit()
    {
        if (!falling)
            falling = true;
    }
}