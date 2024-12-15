package com.example.p2.entities;

import com.example.p2.auxiliary.Limits;
import com.example.p2.auxiliary.Vector3;
import com.example.p2.entities.IEntity;
import com.example.p2.entities.IEntityFactory;
import com.example.p2.entities.spawners.BasicEntityGenerator;
import com.example.p2.entities.spawners.DoorGenerator;
import com.example.p2.entities.spawners.EntityGenerator;
import com.example.p2.entities.spawners.WindmillGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomEntityFactory implements IEntityFactory
{
    private int updatesToNextEntity;
    private final int maxUpdates;
    private final List<EntityGenerator> spawners;
    public RandomEntityFactory()
    {
        this.maxUpdates = 100;
        updatesToNextEntity = 0;
        // initialize collection of entities
        spawners = new ArrayList<EntityGenerator>();
        spawners.add(new BasicEntityGenerator());
        spawners.add(new DoorGenerator());
        spawners.add(new WindmillGenerator());
    }
    public void generateEntities(List<IEntity> entities)
    {
        if (updatesToNextEntity <= 0)
        {
            updatesToNextEntity = maxUpdates;
            Random random = new Random();
            // Add to entities a new set of entities
            List<IEntity> newEntities = spawners.get(random.nextInt(spawners.size()))
                    .spawn(new Vector3(Limits.getRandX(), Limits.getMinY(), Limits.getFarZ()),
                            new Vector3(Limits.getRandDegree(),0,0));
            for (IEntity newEntity: newEntities) {
                entities.add(newEntity);
            }
        }
        else
            updatesToNextEntity--;
    }
}
