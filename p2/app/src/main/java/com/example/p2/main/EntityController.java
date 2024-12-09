package com.example.p2.main;

import com.example.p2.entities.IEntity;
import com.example.p2.entities.IEntityFactory;
import com.example.p2.entities.RandomEntityFactory;

import java.util.Iterator;
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

        Iterator<IEntity> iterator = entities.iterator();
        while (iterator.hasNext()) {
            IEntity entity = iterator.next();
            if (entity.update()) {
                iterator.remove();
            }
        }
    }
    public void draw(GL10 gl)
    {
        for (IEntity entity : entities)
        {
            entity.draw(gl);
        }
    }

    public void addEntity(IEntity entity)
    {
        entities.add(entity);
    }
}
