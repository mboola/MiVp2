package com.example.p2;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

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
