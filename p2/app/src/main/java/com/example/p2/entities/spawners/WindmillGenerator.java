package com.example.p2.entities.spawners;

import com.example.p2.auxiliary.Limits;
import com.example.p2.auxiliary.Vector3;
import com.example.p2.entities.IEntity;
import com.example.p2.entities.prefabs.WindmillEntity;

import java.util.ArrayList;
import java.util.List;

public class WindmillGenerator implements EntityGenerator
{
    public List<IEntity> spawn(Vector3 position, Vector3 rotation) {
        List<IEntity> entities = new ArrayList<>();
        entities.add(new WindmillEntity(position, rotation));
        return entities;
    }
}
