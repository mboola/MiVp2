package com.example.p2.entities.spawn_managers;

import com.example.p2.entities.IEntity;

import java.util.List;

/*
 *  Defined with the possibility of implementing the strategy pattern
 *  Random or scripted.
 */
public interface IEntityFactory
{
    public void generateEntities(List<IEntity> entities);
}
