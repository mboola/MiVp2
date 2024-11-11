package com.example.p2;

public class ProjectileIEntity implements IEntity
{
    private Vector3 position;
    public ProjectileIEntity(Vector3 position)
    {
        this.position = position;
    }
    public boolean update() {
        return false;
    }
}
