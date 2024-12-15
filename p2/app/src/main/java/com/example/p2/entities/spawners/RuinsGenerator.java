package com.example.p2.entities.spawners;

import com.example.p2.auxiliary.Limits;
import com.example.p2.auxiliary.Vector3;
import com.example.p2.entities.IEntity;
import com.example.p2.entities.prefabs.ColumnEntity;

import java.util.ArrayList;
import java.util.List;

public class RuinsGenerator implements EntityGenerator
{
    public List<IEntity> spawn(Vector3 position, Vector3 rotation) {
        List<IEntity> entities = new ArrayList<>();

        entities.add(new ColumnEntity(new Vector3(0,0, Limits.getFarZ()), new Vector3(0,0,0)));

        entities.add(new ColumnEntity(new Vector3(-1,0, Limits.getFarZ() + 1), new Vector3(0,45,0)));
        entities.add(new ColumnEntity(new Vector3(1,0, Limits.getFarZ() + 1), new Vector3(0,-45,0)));

        entities.add(new ColumnEntity(new Vector3(-2f, 0, Limits.getFarZ() + 2f), new Vector3(0,-90,0)));
        entities.add(new ColumnEntity(new Vector3(2f, 0, Limits.getFarZ() + 2f), new Vector3(0,90,0)));

        entities.add(new ColumnEntity(new Vector3(-1, 0, Limits.getFarZ() + 3), new Vector3(0,-135,0)));
        entities.add(new ColumnEntity(new Vector3(1, 0, Limits.getFarZ() + 3), new Vector3(0,135,0)));

        entities.add(new ColumnEntity(new Vector3(0,0,Limits.getFarZ() + 4), new Vector3(0,180,0)));

        return entities;
    }
}
