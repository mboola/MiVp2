package com.example.p2.entities;

import java.util.LinkedList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

/*
 *  Creates entities and moves them.
 */
public class EntityController {
    private IEntityFactory entityFactory;
    private List<IEntity> entities;
    public EntityController()
    {
        entities = new LinkedList<>();
        entityFactory = new RandomEntityFactory();
        //entityFactory = new ScriptedEntityFactory();
    }

    // Updates all entities inside controller
    public void update()
    {
        // Checks if new entities must be created
        entityFactory.generateEntities(entities);

        // Updates all entities of entities
        for (IEntity entity : entities)
        {
            if (entity.update())
                entities.remove(entity);
        }
    }
    public void draw(GL10 gl)
    {
        for (IEntity entity : entities)
        {
            entity.draw(gl);
        }
    }
}
