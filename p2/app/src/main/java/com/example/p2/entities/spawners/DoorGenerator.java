package com.example.p2.entities.spawners;

import com.example.p2.auxiliary.Limits;
import com.example.p2.auxiliary.Vector3;
import com.example.p2.entities.EntityGenerator;
import com.example.p2.entities.IEntity;
import com.example.p2.entities.prefabs.DoorEntity;

import java.util.ArrayList;
import java.util.List;

public class DoorGenerator implements EntityGenerator
{
    public List<IEntity> spawn() {
        List<IEntity> entities = new ArrayList<>();
        entities.add(new DoorEntity(new Vector3(Limits.getRandX(), Limits.getMinY(), Limits.getFarZ())));
        return entities;
    }
}
