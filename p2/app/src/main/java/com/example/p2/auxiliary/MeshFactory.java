package com.example.p2.auxiliary;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;

public class MeshFactory {

    private static float[] vertices = { // Vertices for a face
            -1.0f, -1.0f, 0.0f,  // 0. left-bottom-front
            1.0f, -1.0f, 0.0f,  // 1. right-bottom-front
            -1.0f,  1.0f, 0.0f,  // 2. left-top-front
            1.0f,  1.0f, 0.0f   // 3. right-top-front
    };

    private static float[] uvs = { // Texture coords for the above face (NEW)
            0.0f, 1.0f,  // A. left-bottom (NEW)
            1.0f, 1.0f,  // B. right-bottom (NEW)
            0.0f, 0.0f,  // C. left-top (NEW)
            1.0f, 0.0f   // D. right-top (NEW)
    };
    public static Mesh createMesh(String textureID)
    {
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder()); // Use native byte order
        FloatBuffer vertexBuffer = vbb.asFloatBuffer(); // Convert from byte to float
        vertexBuffer.put(vertices);         // Copy data into buffer
        vertexBuffer.position(0);           // Rewind

        // Setup texture-coords-array buffer, in float. An float has 4 bytes (NEW)
        ByteBuffer tbb = ByteBuffer.allocateDirect(uvs.length * 4);
        tbb.order(ByteOrder.nativeOrder());
        FloatBuffer textureBuffer = tbb.asFloatBuffer();
        textureBuffer.put(uvs);
        textureBuffer.position(0);

        return new Mesh(vertexBuffer, null, null, textureBuffer, 2, textureID);
    }
    public static Mesh createMesh(Context context, int filenameId) throws IOException
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

        return new Mesh(vertexBuffer, normalBuffer, indexBuffer, textureCoordBuffer, numFaceIndexs);
    }
}
