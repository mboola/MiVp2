package com.example.p2.entities;

import com.example.p2.auxiliary.Mesh;
import com.example.p2.auxiliary.Vector3;

/*
 *  Entity is basically everything that has a mesh and a texture.
 */
public abstract class Entity implements IEntity
{
    protected Vector3 position;
    protected Mesh mesh;
    protected int idTexture;
    abstract protected boolean hasCollided(Vector3 position);
}
