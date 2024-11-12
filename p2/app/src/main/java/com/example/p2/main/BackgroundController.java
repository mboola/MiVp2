package com.example.p2.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

import com.example.p2.auxiliary.GraphicStorage;
import com.example.p2.auxiliary.Mesh;
import com.example.p2.auxiliary.Vector3;

import java.io.IOException;
import java.io.InputStream;
import java.nio.FloatBuffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.microedition.khronos.opengles.GL10;

public class BackgroundController
{
    private float[] vertices = { // Vertices for a face
            -1.0f, -1.0f, 0.0f,  // 0. left-bottom-front
            1.0f, -1.0f, 0.0f,  // 1. right-bottom-front
            -1.0f,  1.0f, 0.0f,  // 2. left-top-front
            1.0f,  1.0f, 0.0f   // 3. right-top-front
    };

    float[] uvs = { // Texture coords for the above face (NEW)
            0.0f, 1.0f,  // A. left-bottom (NEW)
            1.0f, 1.0f,  // B. right-bottom (NEW)
            0.0f, 0.0f,  // C. left-top (NEW)
            1.0f, 0.0f   // D. right-top (NEW)
    };

    private final Mesh backgroundMesh;
    private Vector3 position;
    private int size;

    public BackgroundController(Vector3 position, int size)
    {
        this.position = position;
        this.size = size;

        // Setup vertex-array buffer. Vertices in float. An float has 4 bytes
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

        backgroundMesh = new Mesh(vertexBuffer, null, null, textureBuffer, 2, GraphicStorage.getTexture("background"));
    }

    public void draw(GL10 gl)
    {
        gl.glPushMatrix();
        gl.glScalef(size, size, 1);
        gl.glTranslatef(position.x, position.y, position.z);
        backgroundMesh.draw(gl);
        gl.glPopMatrix();
    }
}
