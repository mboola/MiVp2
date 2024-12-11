package com.example.p2.entities;

import com.example.p2.auxiliary.Vector3;

import javax.microedition.khronos.opengles.GL10;

public interface IEntity {
    public boolean update();
    public void draw(GL10 gl);
    public boolean hasCollided(Vector3 position);
    public void hit();
    public boolean isHit();
}
