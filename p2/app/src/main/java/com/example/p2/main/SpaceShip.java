package com.example.p2.main;

import com.example.p2.auxiliary.GraphicStorage;
import com.example.p2.auxiliary.Limits;
import com.example.p2.auxiliary.Vector3;
import com.example.p2.entities.Entity;

import javax.microedition.khronos.opengles.GL10;

public class SpaceShip extends Entity
{
    private SpaceShipAnimation animation;
    public SpaceShip(Vector3 position)
    {
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
        if (final_y < Limits.getMaxY() && final_y > Limits.getMinY())
            position.y = final_y;
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
