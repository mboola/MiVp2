package com.example.p2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomEntityFactory implements IEntityFactory
{
    private int updatesToNextEntity;
    private final int maxUpdates;
    private List<IEntitySpawner> spawners;
    public RandomEntityFactory()
    {
        this.maxUpdates = 100;
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
            entities.addAll(spawners.get(random.nextInt(spawners.size())).spawn());
        }
        else
            updatesToNextEntity--;
    }
}
