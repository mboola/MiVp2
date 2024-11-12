package com.example.p2.main;


import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import android.view.KeyEvent;

import com.example.p2.auxiliary.GraphicStorage;
import com.example.p2.auxiliary.TextureLinker;
import com.example.p2.auxiliary.Vector3;
import com.example.p2.entities.EntityController;

import java.util.LinkedList;
import java.util.Queue;

public class MyOpenGLRenderer implements Renderer {

	//
	private EntityController entityController;
	private BackgroundController background;
	private SpaceShip spaceShip;
	private Context context;
	private Queue<Integer> keysToHandle;
	public MyOpenGLRenderer(Context context)
	{
		this.context = context;
		keysToHandle = new LinkedList<>();
	}

	/*
	 *	Called when the app starts or when the app goes background
	 * 	and then comes back and needs to be recreated.
	 */
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// Image Background color
		gl.glClearColor(1.0f, 1.0f, 1.0f, 0f);
		TextureLinker.Initialize(gl);
		GraphicStorage.Initialize(context);

		//Create the objects used in the scene
		background = new BackgroundController(new Vector3(0, 0.4f, -1), 2);
		entityController = new EntityController();
		spaceShip = new SpaceShip(new Vector3(0, 0, -2));
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
		int	key;

		while (!keysToHandle.isEmpty())
		{
			key = keysToHandle.remove();
			switch (key)
			{
				case KeyEvent.KEYCODE_W:
					spaceShip.move(0, 0.2f, 0);
					break;
				case KeyEvent.KEYCODE_S:
					spaceShip.move(0, -0.2f, 0);
					break;
				case KeyEvent.KEYCODE_A:
					spaceShip.move(-0.2f, 0, 0);
					break;
				case KeyEvent.KEYCODE_D:
					spaceShip.move(0.2f, 0, 0);
					break;
				case KeyEvent.KEYCODE_SPACE:
					System.out.println("Works");
					break;
			}
		}
	}

	// Elements must be rendered in order from farthest to nearest.
	private void drawElements(GL10 gl)
	{
		// Clears the screen and depth buffer.
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		gl.glLoadIdentity();

		background.draw(gl);
//		entityController.update(gl);
		spaceShip.draw(gl);
	}

	/*
	 *	Adds a new key to handle in the next update.
	 */
	public void addKey(int keyCode)
	{
		keysToHandle.add(keyCode);
	}

}
