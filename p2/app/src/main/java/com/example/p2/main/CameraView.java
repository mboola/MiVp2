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
    private final Vector3 outsideEye;
    private final Vector3 outsideCenter;
    private final Vector3 outsideUp;
    private final Vector3 baseEye;
    private final Vector3 baseCenter;
    private final Vector3 baseUp;
    private final Vector3 modificationEye;
    private final Vector3 modificationCenter;
    private final Vector3 modificationUp;
    private boolean followsShip = true;
    private int lastKey;
    private int framesKeyPulsed;

    public CameraView()
    {
        lastKey = -1;
        framesKeyPulsed = -1;
        outsideEye = new Vector3(-10, 10, -5);
        outsideCenter = new Vector3(0, 0, -5);
        outsideUp = new Vector3(0, 1, 0);

        baseEye = new Vector3(0, 0.5f, 0.5f);
        baseCenter = new Vector3(0, 0.5f, -5);
        baseUp = new Vector3(0, 1, 0);

        modificationEye = new Vector3(0,0,0);
        modificationCenter = new Vector3(0, 0, 0);
        modificationUp = new Vector3(0, 0, 0);
    }

    public void setLastKey(int lastKey)
    {
        this.lastKey = lastKey;
        framesKeyPulsed = 0;
    }

    public void update()
    {
        if (framesKeyPulsed == -1)
        {
            // If a movement key is not being pressed and camera has an inclination
            // return slowly to neutral camera (lineally)
            if (modificationUp.x > 0)
            {
                modificationUp.x -= 0.005f;
                if (modificationUp.x < 0)
                    modificationUp.x = 0;
            }
            else if (modificationUp.x < 0)
            {
                modificationUp.x += 0.005f;
                if (modificationUp.x > 0)
                    modificationUp.x = 0;
            }
            return ;
        }
        else
        {
            framesKeyPulsed++;
            if (framesKeyPulsed >= 30)
                framesKeyPulsed = -1;
        }

        switch (lastKey) {
            case KeyEvent.KEYCODE_W:
                break;
            case KeyEvent.KEYCODE_S:
                break;
            case KeyEvent.KEYCODE_A: // Left
                if (modificationUp.x > -0.15f)
                    modificationUp.x -= 0.01f;
                else
                    modificationUp.x = -0.15f;
                break;
            case KeyEvent.KEYCODE_D:
                if (modificationUp.x < 0.15f)
                    modificationUp.x += 0.01f;
                else
                    modificationUp.x = 0.15f;
                break;
        }
    }

    public void setCameraX(float x)
    {
        modificationEye.x = x;
        modificationCenter.x = x;
    }

    public void setCameraY(float y)
    {
        modificationEye.y = y;
        modificationCenter.y = y;
    }

    public void draw(GL10 gl)
    {
        if (followsShip)
            GLU.gluLookAt(gl, baseEye.x + modificationEye.x, baseEye.y + modificationEye.y, baseEye.z + modificationEye.z,
                    baseCenter.x + modificationCenter.x, baseCenter.y + modificationCenter.y, baseCenter.z + modificationCenter.z,
                    baseUp.x + modificationUp.x, baseUp.y + modificationUp.y, baseUp.z + modificationUp.z);
        else
            GLU.gluLookAt(gl, outsideEye.x, outsideEye.y, outsideEye.z, outsideCenter.x, outsideCenter.y, outsideCenter.z, outsideUp.x, outsideUp.y, outsideUp.z);
    }

    public void changeView()
    {
        followsShip = !followsShip;
    }
}
