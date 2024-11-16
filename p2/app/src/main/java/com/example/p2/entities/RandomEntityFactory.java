package com.example.p2.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomEntityFactory implements IEntityFactory
{
    private int updatesToNextEntity;
    private final int maxUpdates;
    private final List<IEntitySpawner> spawners;
    public RandomEntityFactory()
    {
        this.maxUpdates = 1000;
        updatesToNextEntity = 0;
        // initialize collection of entities
        spawners = new ArrayList<IEntitySpawner>();
        spawners.add(new BasicEntitySpawner());
    }
    public void generateEntities(List<IEntity> entities)
    {
        if (updatesToNextEntity <= 0)
        {
            updatesToNextEntity = maxUpdates;
            Random random = new Random();
            // Add to entities a new set of entities
            System.out.println("Spawned entity");
            entities.addAll(spawners.get(random.nextInt(spawners.size())).spawn());
        }
        else
            updatesToNextEntity--;
    }
}
