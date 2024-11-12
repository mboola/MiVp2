package com.example.p2.auxiliary;

public class Vector3
{
    public float x;
    public float y;
    public float z;

    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void translate(float x, float y, float z)
    {
        this.x += x;
        this.y += y;
        this.z += z;
    }
}
