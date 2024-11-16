package com.example.p2.entities;

import com.example.p2.auxiliary.Vector3;

public class ProjectileEntity extends Entity
{
    public ProjectileEntity(Vector3 position)
    {
        this.position = position;
    }
    public boolean update() {
        return false;
    }

    @Override
    public void draw() {

    }
}
