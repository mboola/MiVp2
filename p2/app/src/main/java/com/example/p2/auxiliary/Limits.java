package com.example.p2.auxiliary;

public class Limits
{
    private final static float minX = -10;
    private final static float minY = 0;
    private final static float farZ = -10;
    private final static float maxX = 10;
    private final static float maxY = 10;
    private final static float nearZ = 0;

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
}
