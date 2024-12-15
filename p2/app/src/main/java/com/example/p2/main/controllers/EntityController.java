package com.example.p2.main.controllers;

import com.example.p2.auxiliary.Vector3;
import com.example.p2.entities.IEntity;
import com.example.p2.entities.IEntityFactory;
import com.example.p2.entities.ScriptedEntityFactory;

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
        //entityFactory = new RandomEntityFactory();
        entityFactory = new ScriptedEntityFactory();
    }

    // Updates all entities inside controller
    public void update()
    {
        // Checks if new entities must be created
        entityFactory.generateEntities(entities);

        for (IEntity entity : entities) {
            entity.update();
        }


        entities.removeIf(IEntity::isDead);
    }
    public void draw(GL10 gl)
    {
        for (IEntity entity : entities)
        {
            entity.draw(gl);
        }
    }

    public boolean checkCollisions(Vector3 position)
    {
        for (IEntity entityToCollide : entities)
        {
            if (entityToCollide.hasCollided(position))
            {
                entityToCollide.hasBeenHit();
                return true;
            }
        }
        return false;
    }

    public void addEntity(IEntity entity)
    {
        entities.add(entity);
    }
}
