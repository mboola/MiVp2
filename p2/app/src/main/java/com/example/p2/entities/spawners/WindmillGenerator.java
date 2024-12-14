package com.example.p2.entities.spawners;

import com.example.p2.auxiliary.Limits;
import com.example.p2.auxiliary.Vector3;
import com.example.p2.entities.EntityGenerator;
import com.example.p2.entities.IEntity;
import com.example.p2.entities.prefabs.WindmillEntity;

import java.util.ArrayList;
import java.util.List;

public class WindmillGenerator implements EntityGenerator
{
    public List<IEntity> spawn() {
        System.out.println("Windmill windmill");
        List<IEntity> entities = new ArrayList<>();
        entities.add(new WindmillEntity(new Vector3(Limits.getRandX(), Limits.getMinY(), Limits.getFarZ()), new Vector3(0, Limits.getRandDegree(), 0)));
        return entities;
    }
}
