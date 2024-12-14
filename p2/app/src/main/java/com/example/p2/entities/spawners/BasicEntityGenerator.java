package com.example.p2.entities.spawners;

import com.example.p2.auxiliary.Limits;
import com.example.p2.auxiliary.Mesh;
import com.example.p2.auxiliary.GraphicStorage;
import com.example.p2.auxiliary.Vector3;
import com.example.p2.entities.EntityGenerator;
import com.example.p2.entities.IEntity;
import com.example.p2.entities.StaticEntity;

import java.util.ArrayList;
import java.util.List;

// Spawns only one entity.
public class BasicEntityGenerator implements EntityGenerator
{
    public BasicEntityGenerator(){}
    public List<IEntity> spawn()
    {
        List<IEntity> entities = new ArrayList<>();
        Mesh mesh = GraphicStorage.getMesh("static", "static_texture");
        entities.add(new StaticEntity(new Vector3(Limits.getRandX(), Limits.getSpawnY(), Limits.getFarZ()), mesh));
        return entities;
    }
}
