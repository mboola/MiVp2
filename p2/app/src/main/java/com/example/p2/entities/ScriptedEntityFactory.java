package com.example.p2.entities;

import com.example.p2.auxiliary.Limits;
import com.example.p2.auxiliary.Time;
import com.example.p2.auxiliary.Vector3;
import com.example.p2.entities.spawners.DoorGenerator;
import com.example.p2.entities.spawners.RuinsGenerator;
import com.example.p2.entities.spawners.WindmillGenerator;

import java.util.List;

public class ScriptedEntityFactory implements IEntityFactory
{
    private int spawnRange = 0;
    private float intervalBetweenSpawns = 1;
    private final int totalRange = 3;
    DoorGenerator doorGenerator;
    WindmillGenerator windmillGenerator;
    RuinsGenerator ruinsGenerator;
    public ScriptedEntityFactory()
    {
        doorGenerator = new DoorGenerator();
        windmillGenerator = new WindmillGenerator();
        ruinsGenerator = new RuinsGenerator();
    }
    public void generateEntities(List<IEntity> entities)
    {
        if (intervalBetweenSpawns > 0)
        {
            intervalBetweenSpawns -= Time.deltaTime;
            return;
        }

        switch (spawnRange % totalRange)
        {
            case 0:
                intervalBetweenSpawns = 1;
                entities.addAll(doorGenerator.spawn(
                        new Vector3(0, 0, Limits.getFarZ()),
                        new Vector3(0, 0, 0)));
                break;
            case 1:
                intervalBetweenSpawns = 2;
                entities.addAll(windmillGenerator.spawn(
                        new Vector3(-2, 0, Limits.getFarZ()),
                        new Vector3(0, -45, 0)));
                entities.addAll(windmillGenerator.spawn(
                        new Vector3(2, 0, Limits.getFarZ()),
                        new Vector3(0, 225, 0)));
                break;
            case 2:
                intervalBetweenSpawns = 1;
                entities.addAll(ruinsGenerator.spawn(
                        new Vector3(0, 0, Limits.getFarZ()),
                        new Vector3(0, 0, 0)));
                break;
        }
        spawnRange++;
    }
}
