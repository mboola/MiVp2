package com.example.p2.entities.prefabs;

import com.example.p2.auxiliary.GraphicStorage;
import com.example.p2.auxiliary.Mesh;
import com.example.p2.auxiliary.Vector3;
import com.example.p2.entities.Entity;
import com.example.p2.entities.IEntity;
import com.example.p2.entities.StaticEntity;

import javax.microedition.khronos.opengles.GL10;

public class DoorEntity extends Entity
{
    private IEntity door;
    private IEntity doorStruct;
    private boolean goingUp = true;
    private float maxHeight = 1f;
    private float radius = 2f;

    public DoorEntity(Vector3 position)
    {
        this.position = position;
        Mesh doorMesh = GraphicStorage.getMesh("door_mesh", "door_texture");
        Mesh doorStructureMesh = GraphicStorage.getMesh("door_structure_mesh", "door_structure_texture");
        door = new StaticEntity(new Vector3(0, 0, 0), doorMesh);
        doorStruct = new StaticEntity(new Vector3(0, 0, 0), doorStructureMesh);
    }

    @Override
    public boolean update()
    {
        updateDoorPosition();
        return super.update();
    }

    private void updateDoorPosition()
    {
        // Update door position
        Vector3 doorPosition = door.getPosition();
        if (goingUp)
        {
            doorPosition.y += 0.05f;
            if (doorPosition.y >= maxHeight)
                goingUp = false;
        }
        else
        {
            doorPosition.y -= 0.05f;
            if (doorPosition.y <= -maxHeight)
                goingUp = true;
        }
    }

    @Override
    public void draw(GL10 gl)
    {
        gl.glPushMatrix();
        gl.glTranslatef(position.x, position.y, position.z);
        door.draw(gl);
        doorStruct.draw(gl);
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
