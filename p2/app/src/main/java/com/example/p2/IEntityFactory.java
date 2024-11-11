package com.example.p2;

import java.util.List;

/*
 *  Defined with the possibility of implementing the strategy pattern
 *  Random or scripted.
 */
public interface IEntityFactory
{
    public void generateEntities(List<IEntity> entities);
}
