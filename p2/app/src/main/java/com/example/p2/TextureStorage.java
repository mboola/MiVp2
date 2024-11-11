package com.example.p2;

import java.util.HashMap;
import java.util.Map;

public class TextureStorage
{
    private static Map<String, Integer> textures;
    private static Map<String, Mesh> meshes;
    public static void Initialize()
    {
        // Here initialize all the textures and meshes
        textures = new HashMap<>();
        meshes = new HashMap<>();
        textures.put("background", R.raw.background);
    }

    public static Integer getTexture(String id)
    {
        return textures.get(id);
    }

    public static Mesh getMesh(String id)
    {
        return meshes.get(id);
    }
}
