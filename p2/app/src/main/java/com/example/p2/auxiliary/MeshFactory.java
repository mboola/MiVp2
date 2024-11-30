package com.example.p2.auxiliary;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
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

    /*
     *  Used to create a Quad with the textureID. Used in the background.
     */
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

    /*
     *  Used to create a mesh located in a filenameId.
     */
    public static Mesh createMesh(Context context, int filenameId) throws IOException
    {
        InputStream inputStream = context.getResources().openRawResource(filenameId);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        ArrayList<Float> vertices = new ArrayList<Float>();
        ArrayList<Float> uvs = new ArrayList<Float>();
        ArrayList<Float> normals = new ArrayList<Float>();

        ArrayList<Integer> vertexIndices  = new ArrayList<Integer>();
        ArrayList<Integer> uvIndices  = new ArrayList<Integer>();
        ArrayList<Integer> normalIndices  = new ArrayList<Integer>();

        int numFaceIndexs = 0;

        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(" ");
            switch (parts[0])
            {
                case "v": // Vertex coordinates
                    vertices.add(Float.parseFloat(parts[1]));
                    vertices.add(Float.parseFloat(parts[2]));
                    vertices.add(Float.parseFloat(parts[3]));
                    break;
                case "vt": // Texture coordinates
                    uvs.add(Float.parseFloat(parts[1]));
                    uvs.add(1f - Float.parseFloat(parts[2]));
                    break;
                case "vn": // Normals
                    normals.add(Float.parseFloat(parts[1]));
                    normals.add(Float.parseFloat(parts[2]));
                    normals.add(Float.parseFloat(parts[3]));
                    break;
                case "f": // Faces
                    for (int i = 1; i < 4; i++) {
                        String[] indicesStr = parts[i].split("/");
                        vertexIndices.add(Integer.parseInt(indicesStr[0]) - 1);
                        uvIndices.add(Integer.parseInt(indicesStr[1]) - 1);
                        normalIndices.add(Integer.parseInt(indicesStr[2]) - 1);
                        numFaceIndexs++;
                    }
                    break;
            }
        }
        reader.close();

        // Prepare buffers
        FloatBuffer vertexBuffer = ByteBuffer.allocateDirect(vertexIndices.size() * 4 * 3)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        for (int j = 0; j < vertexIndices.size(); j++) {
            vertexBuffer.put(vertices.get( vertexIndices.get(j)*3 ));
            vertexBuffer.put(vertices.get( vertexIndices.get(j)*3+1 ));
            vertexBuffer.put(vertices.get( vertexIndices.get(j)*3+2 ));
        }
        vertexBuffer.position(0);

        FloatBuffer texcoordBuffer = null;
        if (!uvs.isEmpty()) {
            texcoordBuffer = ByteBuffer.allocateDirect(uvIndices.size() * 4 * 2)
                    .order(ByteOrder.nativeOrder())
                    .asFloatBuffer();
            for (int j = 0; j < uvIndices.size(); j++) {
                texcoordBuffer.put(uvs.get( uvIndices.get(j)*2 ));
                texcoordBuffer.put(uvs.get( uvIndices.get(j)*2+1 ));
            }
            texcoordBuffer.position(0);
        }

        FloatBuffer normalBuffer = null;
        if(!normals.isEmpty()) {
            ByteBuffer nbb = ByteBuffer.allocateDirect(normalIndices.size() * 4 * 3);
            nbb.order(ByteOrder.nativeOrder());
            normalBuffer = nbb.asFloatBuffer();

            for (int j = 0; j < normalIndices.size(); j++) {
                normalBuffer.put(normals.get( normalIndices.get(j)*3 ));
                normalBuffer.put(normals.get( normalIndices.get(j)*3+1 ));
                normalBuffer.put(normals.get( normalIndices.get(j)*3+2 ));
            }
            normalBuffer.position(0);
        }

        ByteBuffer ibb = ByteBuffer.allocateDirect(numFaceIndexs * 4);
        ibb.order(ByteOrder.nativeOrder());
        IntBuffer indexBuffer = ibb.asIntBuffer();

        for (int j = 0; j < numFaceIndexs; j++) {
            indexBuffer.put(j);
        }
        indexBuffer.position(0);

        return new Mesh(vertexBuffer, normalBuffer, indexBuffer, texcoordBuffer, numFaceIndexs);
    }
}
