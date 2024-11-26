package com.example.p2.entities;

import com.example.p2.auxiliary.GraphicStorage;
import com.example.p2.auxiliary.Limits;
import com.example.p2.auxiliary.Mesh;
import com.example.p2.auxiliary.Vector3;

import java.util.ArrayList;
import java.util.List;

public class DoorGenerator implements EntityGenerator
{
    public List<IEntity> spawn() {
        System.out.println("Spawned door");
        List<IEntity> entities = new ArrayList<>();
        Mesh doorStructure = GraphicStorage.getMesh("door_structure", "static1_texture");
        Mesh door = GraphicStorage.getMesh("door", "armwing_texture");
        float randomX = Limits.getRandX();
        entities.add(new StaticEntity(new Vector3(randomX, Limits.getSpawnY(), Limits.getFarZ()), doorStructure));
        entities.add(new DynamicEntity(new Vector3(randomX, Limits.getSpawnY(), Limits.getFarZ()), door));
        return entities;
    }
}
