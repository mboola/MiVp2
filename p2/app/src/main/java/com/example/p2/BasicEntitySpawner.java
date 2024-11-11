package com.example.p2;

import java.util.ArrayList;
import java.util.List;

public class BasicEntitySpawner implements IEntitySpawner
{
    public BasicEntitySpawner(){}
    public List<IEntity> spawn()
    {
        List<IEntity> entities = new ArrayList<>();
        Mesh mesh = TextureStorage.getMesh("");
        Integer texture = TextureStorage.getTexture("");
        entities.add(new StaticEntity(new Vector3(0, 0, 0)));
        return entities;
    }
}
