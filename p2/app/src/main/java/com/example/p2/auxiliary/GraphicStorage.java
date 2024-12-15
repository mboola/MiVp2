package com.example.p2.auxiliary;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.example.p2.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class GraphicStorage
{
    private static Map<String, Bitmap> textures;
    private static Map<String, Mesh> meshes;
    public static void Initialize(Context context)
    {
        // Here initialize all the textures and meshes
        textures = new HashMap<String, Bitmap>();
        meshes = new HashMap<String, Mesh>();
        initializeTextures(context);
        initializeMeshes(context);
    }

    private static void initializeTextures(Context context)
    {
        initializeTexture(context, "background", R.raw.kenshi_background);
        initializeTexture(context, "base_texture", R.raw.base_texture);

        // Entities textures
        initializeTexture(context, "projectile_texture", R.raw.projectile_texture);
        initializeTexture(context, "armwing_texture", R.raw.armwing_texture);
        initializeTexture(context, "windmill_texture", R.raw.windmill_texture);
        initializeTexture(context, "windmill_structure_texture", R.raw.windmill_struct_texture);
        initializeTexture(context, "door_texture", R.raw.armwing_texture); // TODO : change this
        initializeTexture(context, "door_structure_texture", R.raw.static_texture);
        initializeTexture(context, "static_texture", R.raw.static_texture);

        // Hud textures
        initializeTexture(context, "shield_text", R.raw.shield_text);
        initializeTexture(context, "container", R.raw.container);
        initializeTexture(context, "shield_bar", R.raw.shield_bar);
        initializeTexture(context, "energy_bar", R.raw.energy_bar);
    }

    private static void initializeMeshes(Context context)
    {
        initializeMesh(context, "dots", R.raw.dots);
        initializeMesh(context, "static", R.raw.static1);

        // Entities meshes
        initializeMesh(context, "armwing_mesh", R.raw.armwing);
        initializeMesh(context, "door_mesh", R.raw.door);
        initializeMesh(context, "door_structure_mesh", R.raw.door_structure);
        initializeMesh(context, "windmill_mesh", R.raw.windmill);
        initializeMesh(context, "windmill_structure_mesh", R.raw.windmill_structure);

    }

    /*
     *  Searches in resources/raw a texture named "filenameID" and generates a bitmap.
     *  Stores it inside a dictionary with the key textureID.
     *  Throws NotFoundException if it does not find it. Not handled bc it needs to break.
     */
    private static void initializeTexture(Context context, String textureID, int filenameId)
    {
        InputStream istream = context.getResources().openRawResource(filenameId);

        Bitmap bitmap;
        try {
            // Read and decode input as bitmap
            bitmap = BitmapFactory.decodeStream(istream);
            textures.put(textureID, bitmap);
        } finally {
            try {
                istream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void initializeMesh(Context context, String meshID, int filenameId)
    {
        try {
            Mesh mesh = MeshFactory.createMesh(context, filenameId);
            meshes.put(meshID, mesh);
        }
        catch (IOException e) {
            System.out.println("File " + filenameId + " could not be loaded:");
            e.printStackTrace();
        }
    }

    public static Bitmap getTextureBitmap(String id)
    {
        if (!textures.containsKey(id)) System.out.println("Texture " + id + " not found in textures.");
        return textures.get(id);
    }

    public static Mesh getMesh(String idMesh, String idTexture)
    {
        if (!meshes.containsKey(idMesh)) System.out.println("Texture " + idMesh + " not found in meshes.");
        return meshes.get(idMesh).copy(idTexture);
    }
}
