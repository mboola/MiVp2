package com.example.p2.auxiliary;

import javax.microedition.khronos.opengles.GL10;

public class TextureImage
{
    // Mesh will always be a quad
    private Mesh mesh;
    private Vector3 position;
    private Vector3 scale;
    private Vector3 customScale;
    public TextureImage(String textureID, Vector3 position, Vector3 scale)
    {
        mesh = MeshFactory.createMesh(textureID);
        this.position = position;
        this.scale = scale;
        customScale = new Vector3(1, 1 ,1);
    }

    public void draw(GL10 gl)
    {
        gl.glPushMatrix();
        gl.glTranslatef(position.x, position.y, position.z);
        gl.glScalef(scale.x, scale.y, scale.z);
        gl.glScalef(customScale.x, customScale.y, customScale.z);
        mesh.draw(gl);
        gl.glPopMatrix();
    }

    // This is not ideal, but whatever, it gets the job done
    public void setCustomScale(Vector3 customScale)
    {
        this.customScale = customScale;
    }
}
