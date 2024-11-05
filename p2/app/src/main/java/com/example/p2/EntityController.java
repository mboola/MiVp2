package com.example.p2;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.microedition.khronos.opengles.GL10;

/*
 *  Creates entities and moves them.
 */
public class EntityController {
    private int min;
    private int max;
    private EntityFactory entityFactory;
    private List<Entity> entities;
    public EntityController(int min, int max)
    {
        this.min = min;
        this.max = max;
        entities = new LinkedList<>();
        entityFactory = new EntityFactory();
    }

    // Updates all entities inside controller
    public void update(GL10 gl)
    {
        // Checks if new entities must be created
        // TODO : Create new entities with entityFactory

        // Updates all entities of entities
        for (Entity entity : entities)
        {
            if (entity.update())
                entities.remove(entity);
        }
    }
}
