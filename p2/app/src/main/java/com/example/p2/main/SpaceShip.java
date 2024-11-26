package com.example.p2.main;

import com.example.p2.auxiliary.GraphicStorage;
import com.example.p2.auxiliary.Limits;
import com.example.p2.auxiliary.Vector3;
import com.example.p2.entities.Entity;

import javax.microedition.khronos.opengles.GL10;

public class SpaceShip extends Entity
{
    private SpaceShipAnimation animation;
    private CameraView camera;
    public SpaceShip(Vector3 position, CameraView camera)
    {
        this.camera = camera;
        this.position = position;
        animation = new SpaceShipAnimation();
        mesh = GraphicStorage.getMesh("armwing", "armwing_texture");
    }

    public void move(float x, float y, float z)
    {
        float final_x = position.x + x;
        float final_y = position.y + y;
        if (final_x < Limits.getMaxX() && final_x > Limits.getMinX())
            position.x = final_x;
        if (position.x > 2)
            camera.setCameraX(final_x - 1f);
        else if (position.x < -2)
            camera.setCameraX(final_x + 1f);
        else
            camera.setCameraX(final_x /2);
        if (final_y < Limits.getMaxY() && final_y > Limits.getMinY() - 0.45f)
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
        animation.update();
        return false;
    }

    public void draw(GL10 gl)
    {
        gl.glPushMatrix();
        gl.glTranslatef(position.x, position.y, position.z);
        animation.draw(gl);
        mesh.draw(gl);
        gl.glPopMatrix();
    }

    @Override
    protected boolean hasCollided(Vector3 position) {
        return false;
    }
}
