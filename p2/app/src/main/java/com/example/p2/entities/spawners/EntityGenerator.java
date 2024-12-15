package com.example.p2.entities.spawners;

import com.example.p2.auxiliary.Vector3;
import com.example.p2.entities.IEntity;

import java.util.List;
import java.util.Vector;

/*
 *  Interface used to spawn a list of IEntities
 */
public interface EntityGenerator
{
    List<IEntity> spawn(Vector3 position, Vector3 rotation);
}
