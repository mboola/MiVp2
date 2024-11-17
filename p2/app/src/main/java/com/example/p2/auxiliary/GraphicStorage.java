package com.example.p2.auxiliary;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.example.p2.R;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
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

        setTextures(context);
        setMeshes(context);
    }

    private static void setTextures(Context context)
    {
        initializeTexture(context, "background", R.raw.background);
        initializeTexture(context, "armwing_texture", R.raw.base_texture);
        initializeTexture(context, "static1_texture", R.raw.base_texture);
        initializeTexture(context, "base_texture", R.raw.base_texture);
    }

    private static void setMeshes(Context context)
    {
        initializeMesh(context, "dots", R.raw.dots);
        initializeMesh(context, "armwing", R.raw.armwing);
        initializeMesh(context, "static1", R.raw.static1);
    }

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
                // TODO Auto-generated catch block
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
        return textures.get(id);
    }

    public static Mesh getMesh(String idMesh, String idTexture)
    {
        return meshes.get(idMesh).copy(idTexture);
    }
}
