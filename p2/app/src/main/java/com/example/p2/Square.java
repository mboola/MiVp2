package com.example.p2;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Square {
    private final float vertices[] = {-0.5f, -0.5f, 0.0f,
            -0.5f,  0.5f, 0.0f,
            0.5f,  0.5f, 0.0f,
            0.5f, -0.5f, 0.0f};
    private final short faces[] = { 0, 1, 2, 0, 2, 3 };

    // Our vertex buffer.
    private final FloatBuffer vertexBuffer;

    // Our index buffer.
    private final ShortBuffer indexBuffer;

    public Square() {
        //Move the vertices list into a buffer
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        vertexBuffer = vbb.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);

        //Move the faces list into a buffer
        ByteBuffer ibb = ByteBuffer.allocateDirect(faces.length * 2);
        ibb.order(ByteOrder.nativeOrder());
        indexBuffer = ibb.asShortBuffer();
        indexBuffer.put(faces);
        indexBuffer.position(0);
    }

    public void draw(GL10 gl, float x, float y, float z)
    {
        gl.glPushMatrix();

        //gl.glRotatef(0.0f, 0.0f, 0.0f, 1.0f);
        gl.glTranslatef( x, y, z);
        gl.glColor4f(0,0,1,0);

        // Enabled the vertices buffer for writing and to be used during rendering.
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        // Specifies the location and data format of an array of vertex
        // coordinates to use when rendering.
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);

        gl.glDrawElements(GL10.GL_TRIANGLES, faces.length,
                GL10.GL_UNSIGNED_SHORT, indexBuffer);

        // Disable the vertices buffer.
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glPopMatrix();
    }
}
