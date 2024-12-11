package com.example.p2.main;

import android.opengl.GLU;
import android.view.KeyEvent;

import com.example.p2.auxiliary.Limits;
import com.example.p2.auxiliary.Vector3;

import javax.microedition.khronos.opengles.GL10;

/*
 *  This class is used to change the values of the camera. It will be called at draw to
 *  correctly set the view of the camera.
 */
public class CameraView
{
    private final Vector3 baseEye = new Vector3(0, 0.5f, 0.5f);
    private final Vector3 baseCenter = new Vector3(0, 0.5f, -5);
    private final Vector3 baseUp = new Vector3(0, 1, 0);
    private final Vector3 modificationEye = new Vector3(0,0,0);
    private final Vector3 modificationCenter = new Vector3(0, 0, 0);
    private final Vector3 modificationUp = new Vector3(0, 0, 0);
    private boolean followsShip;
    private final float delayTilReturn = 1f;
    private float timeSinceLastInput;
    private final float delayDecrementer = 0.01f;
    private final float rotationDecrementer = 0.005f;

    public CameraView()
    {
        followsShip = true;
        timeSinceLastInput = 0;
    }

    public void updatePosition(float x, float y)
    {
        timeSinceLastInput = delayTilReturn;
        if (x > 2)
            setCameraX(x - 1);
        else if (x < -2)
            setCameraX(x + 1);
        else
            setCameraX(x / 2);
        if (y > 2f)
            setCameraY(y - 1);
        else if (y > 0.5f)
            setCameraY(y / 2);
        else
            setCameraY(0);
    }

    private void setCameraX(float x)
    {
        modificationEye.x = x;
        modificationCenter.x = x;
    }

    private void setCameraY(float y)
    {
        modificationEye.y = y;
        modificationCenter.y = y;
    }

    public void updateRotation(float rotationX, float rotationY)
    {
        if (rotationX > 0)
        {
            if (modificationUp.x > -0.15f)
                modificationUp.x -= 0.01f;
            else
                modificationUp.x = -0.15f;
        }
        else if (rotationX < 0)
        {
            if (modificationUp.x < 0.15f)
                modificationUp.x += 0.01f;
            else
                modificationUp.x = 0.15f;
        }
    }

    public void update()
    {
        if (timeSinceLastInput > 0)
        {
            timeSinceLastInput -= delayDecrementer;
            return ;
        }

        if (modificationUp.x > 0)
        {
            modificationUp.x -= rotationDecrementer;
            if (modificationUp.x < 0)
                modificationUp.x = 0;
        }
        else if (modificationUp.x < 0)
        {
            modificationUp.x += rotationDecrementer;
            if (modificationUp.x > 0)
                modificationUp.x = 0;
        }
    }

    public void draw(GL10 gl)
    {
        if (followsShip)
            GLU.gluLookAt(gl, baseEye.x + modificationEye.x, baseEye.y + modificationEye.y, baseEye.z + modificationEye.z,
                    baseCenter.x + modificationCenter.x, baseCenter.y + modificationCenter.y, baseCenter.z + modificationCenter.z,
                    baseUp.x + modificationUp.x, baseUp.y + modificationUp.y, baseUp.z + modificationUp.z);
        else
        {
            GLU.gluLookAt(gl,
                    -10, 10, -5,
                    0, 0, -5,
                    0, 1, 0);
        }
    }

    public void changeView()
    {
        followsShip = !followsShip;
    }
}
