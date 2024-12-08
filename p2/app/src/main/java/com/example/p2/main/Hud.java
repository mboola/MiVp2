package com.example.p2.main;

import com.example.p2.auxiliary.TextureImage;
import com.example.p2.auxiliary.Vector3;

import javax.microedition.khronos.opengles.GL10;

public class Hud
{
    private SceneRenderer renderer;
    private TextureImage lifesCounter;
    private TextureImage shipImage;
    private TextureImage shildText;
    private TextureImage shieldContainer;
    private TextureImage shieldBar;
    private TextureImage energyContainer;
    private TextureImage energyBar;
    private TextureImage[] bombs;
    public Hud(SceneRenderer renderer)
    {
        this.renderer = renderer;
        //lifesCounter = new TextureImage("");
        //shipImage = new TextureImage("");
        shildText = new TextureImage("shield_text", new Vector3(-4,-2.25f,0), new Vector3(0.8f,0.35f,1));
        shieldContainer = new TextureImage("container", new Vector3(-3.75f,-3.25f,0), new Vector3(1f,0.5f,1));
        shieldBar = new TextureImage("shield_bar", new Vector3(-3.75f,-3.25f,0), new Vector3(1f,0.5f,1));

        energyContainer = new TextureImage("container", new Vector3(3.75f,-3.25f,0), new Vector3(1f,0.5f,1));
        energyBar = new TextureImage("energy_bar", new Vector3(3.75f,-3.25f,0), new Vector3(1f,0.5f,1));
    }

    private void setOrthographicProjection(GL10 gl)
    {
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrthof(-5,5,-4,4,-5,5);
        gl.glDepthMask(false);  // disable writes to Z-Buffer
        gl.glDisable(GL10.GL_DEPTH_TEST);  // disable depth-testing

        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public void draw(GL10 gl)
    {
        setOrthographicProjection(gl);
        //lifesCounter.draw(gl);
        //shipImage.draw(gl);
        shildText.draw(gl);
        shieldContainer.draw(gl);
        shieldBar.draw(gl);

        energyContainer.draw(gl);
        energyBar.draw(gl);
    }
}
