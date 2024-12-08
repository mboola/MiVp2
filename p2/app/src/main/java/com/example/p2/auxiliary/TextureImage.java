package com.example.p2.auxiliary;

import javax.microedition.khronos.opengles.GL10;

public class TextureImage
{
    // Mesh will always be a quad
    private Mesh mesh;
    private Vector3 position;
    private Vector3 scale;
    public TextureImage(String textureID, Vector3 position, Vector3 scale)
    {
        mesh = MeshFactory.createMesh(textureID);
        this.position = position;
        this.scale = scale;
    }

    public void draw(GL10 gl)
    {
        gl.glPushMatrix();
        gl.glTranslatef(position.x, position.y, position.z);
        gl.glScalef(scale.x, scale.y, scale.z);
        mesh.draw(gl);
        gl.glPopMatrix();
    }
}
