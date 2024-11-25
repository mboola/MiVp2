package com.example.p2.main;

import android.opengl.GLU;

import com.example.p2.auxiliary.Vector3;

import javax.microedition.khronos.opengles.GL10;

/*
 *  This class is used to change the values of the camera. It will be called at draw to
 *  correctly set the view of the camera.
 */
public class CameraView
{
    private Vector3 eye;
    private Vector3 center;
    private Vector3 up;
    private boolean followsShip = false;

    public CameraView()
    {
        eye = new Vector3(-10, 10, -5);
        center = new Vector3(0, 0, -5);
        up = new Vector3(0, 1, 0);
    }

    public void draw(GL10 gl)
    {
        if (followsShip)
            GLU.gluLookAt(gl, eye.x, eye.y, eye.z, center.x, center.y, center.z, up.x, up.y, eye.z);
    }

    public void changeView()
    {
        followsShip = !followsShip;
    }
}
