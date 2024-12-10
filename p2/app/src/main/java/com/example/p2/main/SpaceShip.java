package com.example.p2.main;

import com.example.p2.auxiliary.GraphicStorage;
import com.example.p2.auxiliary.Limits;
import com.example.p2.auxiliary.Vector3;
import com.example.p2.entities.Entity;
import com.example.p2.entities.IEntity;
import com.example.p2.entities.ProjectileEntity;

import javax.microedition.khronos.opengles.GL10;

public class SpaceShip extends Entity
{
    private SpaceShipAnimation animation;
    private CameraView camera;
    private Vector3 lookingAt;
    private float rotationMultiplier = 20f;
    private float rotationDecreaser = 0.5f;
    private float maxRotation = 20f;
    public SpaceShip(Vector3 position, CameraView camera)
    {
        this.camera = camera;
        this.position = position;
        lookingAt = new Vector3(0,0, -5);
        animation = new SpaceShipAnimation();
        mesh = GraphicStorage.getMesh("armwing", "armwing_texture");
    }

    // TODO : change how rotation of ship gets calculated

    public void move(float x, float y, float z)
    {
        float final_x = position.x + x;
        if (lookingAt.x + (x * rotationMultiplier) <= maxRotation && lookingAt.x + (x * rotationMultiplier) >= -maxRotation)
            lookingAt.x += (x * -rotationMultiplier);
        if (lookingAt.y + (y * rotationMultiplier) <= maxRotation && lookingAt.y + (y * rotationMultiplier) >= -maxRotation)
            lookingAt.y += (y * rotationMultiplier);
        float final_y = position.y + y;
        if (final_x < Limits.getMaxX() && final_x > Limits.getMinX())
            position.x = final_x;
        if (position.x > 2)
            camera.setCameraX(final_x - 1f);
        else if (position.x < -2)
            camera.setCameraX(final_x + 1f);
        else
            camera.setCameraX(final_x /2);
        if (final_y < Limits.getMaxY() && final_y > Limits.getMinY())
            position.y = final_y;
        if (position.y > 2f)
            camera.setCameraY(final_y - 1);
        else if (position.y > 0.5f)
            camera.setCameraY(final_y / 2);
        else
            camera.setCameraY(0);
    }

    public Vector3 getPosition()
    {
        return position;
    }

    public boolean update()
    {
        if (lookingAt.x > 0)
        {
            lookingAt.x -= rotationDecreaser;
            if (lookingAt.x < 0)
                lookingAt.x = 0;
        }
        else if (lookingAt.x < 0)
        {
            lookingAt.x += rotationDecreaser;
            if (lookingAt.x > 0)
                lookingAt.x = 0;
        }
        if (lookingAt.y > 0)
        {
            lookingAt.y -= rotationDecreaser;
            if (lookingAt.y < 0)
                lookingAt.y = 0;
        }
        else if (lookingAt.y < 0)
        {
            lookingAt.y += rotationDecreaser;
            if (lookingAt.y > 0)
                lookingAt.y = 0;
        }
        animation.update();
        return false;
    }

    public void draw(GL10 gl)
    {
        gl.glPushMatrix();
        gl.glTranslatef(position.x, position.y, position.z);
        if (lookingAt.x != 0)
            gl.glRotatef(lookingAt.x, 0, 1, 0);
        if (lookingAt.y != 0)
            gl.glRotatef(lookingAt.y, 1, 0, 0);
        animation.draw(gl);
        mesh.draw(gl);
        gl.glPopMatrix();
    }

    public IEntity shoot()
    {
        // Create a new IEntity proyectile with the direction of the spaceship
        // TODO : hell naw change this
        if (lookingAt.x != 0)
        {
            if (lookingAt.y != 0)
                return new ProjectileEntity(new Vector3(position.x, position.y, position.z), new Vector3(lookingAt.x - position.x,lookingAt.y - position.y, 1));
            else
                return new ProjectileEntity(new Vector3(position.x, position.y, position.z), new Vector3(lookingAt.x - position.x,0, 1));
        }
        else if (lookingAt.y != 0)
            return new ProjectileEntity(new Vector3(position.x, position.y, position.z), new Vector3(0,lookingAt.y - position.y, 1));
        return new ProjectileEntity(new Vector3(position.x, position.y, position.z), new Vector3(0,0, 1));
    }

    @Override
    protected boolean hasCollided(Vector3 position) {
        return false;
    }
}
