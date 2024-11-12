package com.example.p2.auxiliary;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Mesh
{
    // Our vertex buffer.
    private final FloatBuffer vertexBuffer;

    // Our normal buffer.
    private final FloatBuffer normalBuffer;

    // Our index buffer.
    private final ShortBuffer indexBuffer;

    // Our texture buffer.
    private final FloatBuffer textureCoordBuffer;
    private final int numFaceIndexs;
    private boolean textureEnabled;
    private int textureID;

    public Mesh(FloatBuffer vertexBuffer, FloatBuffer normalBuffer,
                ShortBuffer indexBuffer, FloatBuffer textureCoordBuffer,
                int numFaceIndexs)
    {
        this.vertexBuffer = vertexBuffer;
        this.normalBuffer = normalBuffer;
        this.indexBuffer = indexBuffer;
        this.textureCoordBuffer = textureCoordBuffer;
        this.numFaceIndexs = numFaceIndexs;
        textureEnabled = false;
    }

    public Mesh(FloatBuffer vertexBuffer, FloatBuffer normalBuffer,
                ShortBuffer indexBuffer, FloatBuffer textureCoordBuffer,
                int numFaceIndexs, int textureID)
    {
        this.vertexBuffer = vertexBuffer;
        this.normalBuffer = normalBuffer;
        this.indexBuffer = indexBuffer;
        this.textureCoordBuffer = textureCoordBuffer;
        this.numFaceIndexs = numFaceIndexs;
        this.textureID = textureID;
        textureEnabled = true;
    }

    public void draw(GL10 gl)
    {
        gl.glColor4f(1,1,1,0);

        if (textureEnabled)
            gl.glBindTexture(GL10.GL_TEXTURE_2D, TextureLinker.getTextureID(gl, textureID));

        gl.glFrontFace(GL10.GL_CCW);    // Front face in counter-clockwise orientation
        gl.glEnable(GL10.GL_CULL_FACE); // Enable cull face
        gl.glCullFace(GL10.GL_BACK);    // Cull the back face (don't display)
        gl.glEnable(GL10.GL_TEXTURE_2D);  // Enable texture

        // Enabled the vertices buffer for writing and to be used during
        // rendering.
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        // Specifies the location and data format of an array of vertex
        // coordinates to use when rendering.
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);

        if(textureEnabled)
            gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

        if (normalBuffer != null)
            gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);


        if(textureEnabled && textureCoordBuffer != null)
            gl.glTexCoordPointer(2, GL10.GL_FLOAT,0, textureCoordBuffer);
        if (normalBuffer != null)
            gl.glNormalPointer(GL10.GL_FLOAT, 0, normalBuffer);
        if (indexBuffer != null)
            gl.glDrawElements(GL10.GL_TRIANGLES, numFaceIndexs, GL10.GL_UNSIGNED_SHORT, indexBuffer);
        else
            gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);

        // Disable the vertices buffer.
        if(textureEnabled)
            gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);

        if (normalBuffer != null)
            gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);

        gl.glDisable(GL10.GL_CULL_FACE);
        gl.glDisable(GL10.GL_TEXTURE_2D);
    }

    public Mesh copy()
    {
        return new Mesh(vertexBuffer, normalBuffer, indexBuffer, textureCoordBuffer, numFaceIndexs);
    }
}
