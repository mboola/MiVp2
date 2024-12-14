package com.example.p2.entities.prefabs;

import com.example.p2.auxiliary.GraphicStorage;
import com.example.p2.auxiliary.Mesh;
import com.example.p2.auxiliary.Vector3;
import com.example.p2.entities.Entity;
import com.example.p2.entities.IEntity;
import com.example.p2.entities.StaticEntity;

import javax.microedition.khronos.opengles.GL10;

public class WindmillEntity extends Entity
{
    private IEntity windmill;
    private IEntity windmillStruct;
    private Vector3 rotation;
    private float windmillRotation = 0;
    public WindmillEntity(Vector3 position, Vector3 rotation)
    {
        this.rotation = rotation;
        this.position = position;
        Mesh windmillMesh = GraphicStorage.getMesh("windmill_mesh", "windmill_texture");
        Mesh windmillStructMesh = GraphicStorage.getMesh("windmill_structure_mesh", "windmill_structure_texture");
        windmill = new StaticEntity(new Vector3(0, 0, 0), windmillMesh);
        windmillStruct = new StaticEntity(new Vector3(0, 0, 0), windmillStructMesh);
    }

    @Override
    public void update()
    {
        windmillRotation += 1f;
        super.update();
    }

    @Override
    public void draw(GL10 gl)
    {
        gl.glPushMatrix();
        gl.glTranslatef(position.x, position.y, position.z);

        if (rotation.x != 0)
            gl.glRotatef(rotation.x, 1, 0, 0);
        if (rotation.y != 0)
            gl.glRotatef(rotation.y, 0, 1, 0);
        if (rotation.z != 0)
            gl.glRotatef(rotation.z, 0, 0, 1);

        windmillStruct.draw(gl);
        gl.glTranslatef(0.8f, 2.1f, 0);
        gl.glRotatef(windmillRotation, 1, 0, 0);
        windmill.draw(gl);
        gl.glPopMatrix();
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
