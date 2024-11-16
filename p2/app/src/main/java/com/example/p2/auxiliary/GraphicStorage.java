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
        initializeTexture(context, "armwing_texture", R.raw.armwing_texture);
        initializeTexture(context, "static1_texture", R.raw.static1_texture);
    }

    private static void setMeshes(Context context)
    {
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
        String line;
        String[] tmp,ftmp;

        ArrayList<Float> vlist = new ArrayList<Float>();
        ArrayList<Float> tlist = new ArrayList<Float>();
        ArrayList<Float> nlist = new ArrayList<Float>();
        ArrayList<Integer> vindex = new ArrayList<Integer>();
        ArrayList<Integer> tindex = new ArrayList<Integer>();
        ArrayList<Integer> nindex = new ArrayList<Integer>();

        int numFaceIndexs = 0;

        try {
            InputStream is = context.getResources().openRawResource(filenameId);
            BufferedReader inb = new BufferedReader(new InputStreamReader(is), 1024);
            while ((line = inb.readLine()) != null) {
                tmp = line.split(" ");
                if (tmp[0].equalsIgnoreCase("v"))
                {
                    for (int i = 1; i < 4; i++) {
                        vlist.add(Float.parseFloat(tmp[i]));
                    }
                }
                if (tmp[0].equalsIgnoreCase("vn"))
                {
                    for (int i = 1; i < 4; i++) {
                        nlist.add(Float.parseFloat(tmp[i]));
                    }
                }
                if (tmp[0].equalsIgnoreCase("vt"))
                {
                    for (int i = 1; i < 3; i++) {
                        tlist.add(Float.parseFloat(tmp[i]));
                    }
                }
                if (tmp[0].equalsIgnoreCase("f"))
                {
                    for (int i = 1; i < 4; i++) {
                        ftmp = tmp[i].split("/");

                        vindex.add(Integer.parseInt(ftmp[0]) - 1);
                        if (!tlist.isEmpty())
                            tindex.add(Integer.parseInt(ftmp[1]) - 1);
                        if (!nlist.isEmpty())
                            nindex.add(Integer.parseInt(ftmp[2]) - 1);

                        numFaceIndexs++;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        ByteBuffer vbb = ByteBuffer.allocateDirect(vindex.size() * 4 * 3);
        vbb.order(ByteOrder.nativeOrder());
        FloatBuffer vertexBuffer = vbb.asFloatBuffer();

        for (int j = 0; j < vindex.size(); j++) {
            vertexBuffer.put(vlist.get( vindex.get(j)*3 ));
            vertexBuffer.put(vlist.get( vindex.get(j)*3+1 ));
            vertexBuffer.put(vlist.get( vindex.get(j)*3+2 ));
        }
        vertexBuffer.position(0);

        FloatBuffer textureCoordBuffer = null;

        if (!tindex.isEmpty())  {
            ByteBuffer vtbb = ByteBuffer.allocateDirect(tindex.size() * 4 * 2);
            vtbb.order(ByteOrder.nativeOrder());
            textureCoordBuffer = vtbb.asFloatBuffer();

            for (int j = 0; j < tindex.size(); j++) {
                textureCoordBuffer.put(tlist.get( tindex.get(j)*2 ));
                textureCoordBuffer.put(tlist.get( tindex.get(j)*2+1 ));
            }
            textureCoordBuffer.position(0);
        }

        FloatBuffer normalBuffer = null;

        if(!nindex.isEmpty()) {
            ByteBuffer nbb = ByteBuffer.allocateDirect(nindex.size() * 4 * 3);
            nbb.order(ByteOrder.nativeOrder());
            normalBuffer = nbb.asFloatBuffer();

            for (int j = 0; j < nindex.size(); j++) {
                normalBuffer.put(nlist.get( nindex.get(j)*3 ));
                normalBuffer.put(nlist.get( nindex.get(j)*3+1 ));
                normalBuffer.put(nlist.get( nindex.get(j)*3+2 ));
            }
            normalBuffer.position(0);
        }

        ShortBuffer indexBuffer;

        ByteBuffer ibb = ByteBuffer.allocateDirect(numFaceIndexs * 2);
        ibb.order(ByteOrder.nativeOrder());
        indexBuffer = ibb.asShortBuffer();

        for (short j = 0; j < numFaceIndexs; j++) {
            indexBuffer.put(j);
        }
        indexBuffer.position(0);

        Mesh mesh = new Mesh(vertexBuffer, normalBuffer, indexBuffer, textureCoordBuffer, numFaceIndexs);

        meshes.put(meshID, mesh);
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
