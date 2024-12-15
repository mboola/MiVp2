package com.example.p2.entities.spawners;

import com.example.p2.auxiliary.Vector3;
import com.example.p2.entities.IEntity;
import com.example.p2.entities.prefabs.BigColumnEntity;

import java.util.ArrayList;
import java.util.List;

public class BigColumnGenerator implements EntityGenerator
{
    public BigColumnGenerator(){}
    public List<IEntity> spawn(Vector3 position, Vector3 rotation)
    {
        List<IEntity> entities = new ArrayList<>();
        entities.add(new BigColumnEntity(position, new Vector3(0,0,0)));
        return entities;
    }
}