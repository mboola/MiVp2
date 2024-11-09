package com.example.p2;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;

import java.io.InputStream;

public class MyOpenGLRenderer implements Renderer {

	//
	private EntityController entityController;
	private BackgroundController background;
	private SpaceShip spaceShip;
	private Context context;
	public MyOpenGLRenderer(Context context)
	{
		this.context = context;
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// Image Background color
		gl.glClearColor(1.0f, 1.0f, 1.0f, 0.5f);

		//Create the objects used in the scene
		//entityController = new EntityController(0, 10);
		//background = new BackgroundController(gl);
		//background.loadTexture(gl, context, R.raw.background);
		spaceShip = new SpaceShip(new Vector3(0, 0, -2));
	}

	/*
	 *	This is the update of the program.
	 */
	@Override
	public void onDrawFrame(GL10 gl)
	{
		handleInput();
		drawElements(gl);
	}

	private void handleInput()
	{

	}

	private void drawElements(GL10 gl)
	{
		// Clears the screen and depth buffer.
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		gl.glLoadIdentity();

		spaceShip.draw(gl);

		//entityController.update(gl);
		//background.draw(gl);
	}

	@Override //???????
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// Define the Viewport
		gl.glViewport(0, 0, width, height);
		// Select the projection matrix
		gl.glMatrixMode(GL10.GL_PROJECTION);
		// Reset the projection matrix
		gl.glLoadIdentity();
		// Calculate the aspect ratio of the window
		GLU.gluPerspective(gl, 60.0f, (float) width / (float) height, 0.1f, 1000.0f);
		
		// Select the modelview matrix
		gl.glMatrixMode(GL10.GL_MODELVIEW);	
	}


}
