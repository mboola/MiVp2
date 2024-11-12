package com.example.p2.auxiliary;

import java.util.List;

public class Limits
{
    private final static float minX = 10f;
    private final static float minY = 10f;
    private final static float minZ = 10f;
    private final static float maxX = 10f;
    private final static float maxY = 10f;
    private final static float maxZ = 10f;

    public static float[] getLimits()
    {
        float[] limits = {minX, minY, minZ, maxX, maxY, maxZ};
        return limits;
    }
}
