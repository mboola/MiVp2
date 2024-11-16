package com.example.p2.entities;

import com.example.p2.auxiliary.Mesh;
import com.example.p2.auxiliary.GraphicStorage;
import com.example.p2.auxiliary.Vector3;

import java.util.ArrayList;
import java.util.List;

// Spawns only one entity.
public class BasicEntitySpawner implements IEntitySpawner
{
    public BasicEntitySpawner(){}
    public List<IEntity> spawn()
    {
        List<IEntity> entities = new ArrayList<>();
        Mesh mesh = GraphicStorage.getMesh("armwing", "background");
        entities.add(new StaticEntity(new Vector3(0, 0, 0)));
        return entities;
    }
}
