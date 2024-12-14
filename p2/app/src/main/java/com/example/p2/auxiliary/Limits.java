package com.example.p2.auxiliary;

import java.util.Random;

public class Limits
{
    private final static float minX = -7;
    private final static float minY = -0.2f;
    private final static float farZ = -15;
    private final static float maxX = 7;
    private final static float maxY = 2.8f;
    private final static float nearZ = 1;

    public static boolean outOfLimits(Vector3 position)
    {
        if (position.z < farZ || position.z > nearZ)
            return true;
        return false;
    }

    public static float getFarZ()
    {
        return farZ;
    }
    public static float getMaxX()
    {
        return maxX;
    }
    public static float getMinX()
    {
        return minX;
    }
    public static float getMaxY()
    {
        return maxY;
    }
    public static float getMinY()
    {
        return minY;
    }

    public static float getRandX()
    {
        Random random = new Random();
        int randomNumber = random.nextInt(1000) + 1; // Generates a number in [1, 1000]
        return ((float) randomNumber / 1000) * maxX * 2 - maxX;
    }

    public static float getSpawnY()
    {
        return minY;
    }

    public static float getRandDegree()
    {
        Random random = new Random();
        int randomNumber = random.nextInt(360); // Generates a number in [0, 359]
        return (float) randomNumber;
    }
}
