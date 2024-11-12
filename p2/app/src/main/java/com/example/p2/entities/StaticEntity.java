package com.example.p2.entities;

import com.example.p2.auxiliary.Vector3;

public class StaticEntity extends Entity
{
    public StaticEntity(Vector3 position)
    {
        this.position = position;
    }
    public boolean update() {
        return false;
    }
}
