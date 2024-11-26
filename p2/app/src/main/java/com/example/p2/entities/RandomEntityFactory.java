package com.example.p2.entities;

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
    }
    public void generateEntities(List<IEntity> entities)
    {
        if (updatesToNextEntity <= 0)
        {
            updatesToNextEntity = maxUpdates;
            Random random = new Random();
            // Add to entities a new set of entities
            List<IEntity> newEntities = spawners.get(random.nextInt(spawners.size())).spawn();
            for (IEntity newEntity: newEntities) {
                entities.add(newEntity);
            }
        }
        else
            updatesToNextEntity--;
    }
}
