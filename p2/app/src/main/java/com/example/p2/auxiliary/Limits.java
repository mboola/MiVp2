package com.example.p2.auxiliary;

public class Limits
{
    private final static float minX = 10f;
    private final static float minY = 10f;
    private final static float farZ = -10f;
    private final static float maxX = 10f;
    private final static float maxY = -10f;
    private final static float nearZ = 0f;

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
