package com.example.p2.main;

import com.example.p2.Square;
import com.example.p2.auxiliary.Vector3;

import javax.microedition.khronos.opengles.GL10;

public class SpaceShip
{
    private Vector3 position;
    private Square square;
    public SpaceShip(Vector3 position)
    {
        this.position = position;
        square = new Square();
    }

    public void move(float x, float y, float z)
    {
        position.translate(x, y, z);
    }
    public void draw(GL10 gl)
    {
        square.draw(gl, position.x, position.y, position.z);
    }
}
