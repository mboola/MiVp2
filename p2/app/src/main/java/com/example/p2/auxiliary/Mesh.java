package com.example.p2.auxiliary;

import static android.opengl.GLES10.GL_BLEND;
import static android.opengl.GLES10.GL_COLOR_MATERIAL;
import static android.opengl.GLES10.GL_ONE_MINUS_SRC_ALPHA;
import static android.opengl.GLES10.GL_SRC_ALPHA;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Mesh
{
    // Our vertex buffer.
    private final FloatBuffer vertexBuffer;

    // Our normal buffer.
    private final FloatBuffer normalBuffer;

    // Our index buffer.
    private final IntBuffer indexBuffer;

    // Our texture buffer.
    private FloatBuffer textureCoordBuffer;
    private final int numFaceIndexs;
    private boolean textureEnabled;
    private String textureID;

    public Mesh(FloatBuffer vertexBuffer, FloatBuffer normalBuffer,
                IntBuffer indexBuffer, FloatBuffer textureCoordBuffer,
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
               IntBuffer indexBuffer, FloatBuffer textureCoordBuffer,
                int numFaceIndexs, String textureID)
    {
        this.vertexBuffer = vertexBuffer;
        this.normalBuffer = normalBuffer;
        this.indexBuffer = indexBuffer;
        this.textureCoordBuffer = textureCoordBuffer;
        this.numFaceIndexs = numFaceIndexs;
        this.textureID = textureID;
        if (textureCoordBuffer != null)
            textureEnabled = true;
    }

    public void draw(GL10 gl)
    {
        gl.glColor4f(1,1,1,1);

        if (textureEnabled)
        {
            gl.glBindTexture(GL10.GL_TEXTURE_2D, TextureLinker.getTextureID(gl, textureID));
            gl.glEnable(GL10.GL_TEXTURE_2D);  // Enable texture
        }

        gl.glFrontFace(GL10.GL_CCW);    // Front face in counter-clockwise orientation
        gl.glEnable(GL10.GL_CULL_FACE); // Enable cull face
        gl.glCullFace(GL10.GL_BACK);    // Cull the back face (don't display)

        // Enabled the vertices buffer for writing and to be used during rendering.
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        // Specifies the location and data format of an array of vertex coordinates to use when rendering.
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);

        if(textureEnabled)
        {
            gl.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
            gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
            gl.glTexCoordPointer(2, GL10.GL_FLOAT,0, textureCoordBuffer);
        }

        if (normalBuffer != null)
        {
            gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
            gl.glNormalPointer(GL10.GL_FLOAT, 0, normalBuffer);
        }
        if (indexBuffer != null)
            gl.glDrawElements(GL10.GL_TRIANGLES, numFaceIndexs, GLES20.GL_UNSIGNED_INT, indexBuffer);
        else
            gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);

        // Disable the vertices buffer.
        if(textureEnabled)
        {
            gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
            gl.glDisable(GL10.GL_TEXTURE_2D);
        }
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);

        if (normalBuffer != null)
            gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);

        gl.glDisable(GL10.GL_CULL_FACE);
    }

    public void setUVs(float[] uvs)
    {
        ByteBuffer tbb = ByteBuffer.allocateDirect(uvs.length * 4);
        tbb.order(ByteOrder.nativeOrder());
        textureCoordBuffer = tbb.asFloatBuffer();
        textureCoordBuffer.put(uvs);
        textureCoordBuffer.position(0);
    }

    public Mesh copy(String newTexture)
    {
        return new Mesh(vertexBuffer, normalBuffer, indexBuffer, textureCoordBuffer, numFaceIndexs, newTexture);
    }
}
