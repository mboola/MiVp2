package com.example.p2.main;


import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import android.view.KeyEvent;

import com.example.p2.auxiliary.GraphicStorage;
import com.example.p2.auxiliary.Limits;
import com.example.p2.auxiliary.TextureLinker;
import com.example.p2.auxiliary.Vector3;

import java.util.LinkedList;
import java.util.Queue;

public class SceneRenderer implements Renderer
{
	private EntityController entityController;
	private Background background;
	private SpaceShip spaceShip;
	private CameraView camera;
	private Hud hud;
	private Queue<Integer> keysToHandle;
	private boolean gamePaused = false;
	private boolean graphicsInitialized = false;
	private int height;
	private int width;
	public SceneRenderer(Context context)
	{
		keysToHandle = new LinkedList<>();

		// Initialize all graphics (meshes and textures)
		GraphicStorage.Initialize(context);

		camera = new CameraView();
		// Create the objects used in the scene
		background = new Background(new Vector3(0, 0, Limits.getFarZ()), 30);
		entityController = new EntityController();
		spaceShip = new SpaceShip(new Vector3(0, 0, -1.8f), camera);
		hud = new Hud(this);
	}

	/*
	 *	Called when the app starts or when the app goes background
	 * 	and then comes back and needs to be recreated.
	 */
	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{
		// Image Background color
		gl.glClearColor(1.0f, 1.0f, 1.0f, 0f);
		TextureLinker.Initialize(gl);
		graphicsInitialized = true;
	}

	@Override //???????
	public void onSurfaceChanged(GL10 gl, int width, int height)
	{
		// Define the Viewport
		this.width=width;
		this.height=height;

		gl.glViewport(0, 0, width, height);
	}

	/*
	 *	This is the update of the program.
	 */
	@Override
	public void onDrawFrame(GL10 gl)
	{
		if (!graphicsInitialized)
			return ;
		handleInput();
		if (!gamePaused)
			updateEntities();
		drawEntities(gl);
	}

	private void handleInput()
	{
		int	key;

		while (!keysToHandle.isEmpty())
		{
			key = keysToHandle.remove();
			if (key == KeyEvent.KEYCODE_SPACE){
				gamePaused = !gamePaused;
			}
			else if (key == KeyEvent.KEYCODE_C) {
				camera.changeView();
			} else if (!gamePaused) {
				switch (key) {
					case KeyEvent.KEYCODE_W:
						camera.setLastKey(KeyEvent.KEYCODE_W);
						spaceShip.move(0, 0.2f, 0);
						break;
					case KeyEvent.KEYCODE_S:
						camera.setLastKey(KeyEvent.KEYCODE_S);
						spaceShip.move(0, -0.2f, 0);
						break;
					case KeyEvent.KEYCODE_A:
						camera.setLastKey(KeyEvent.KEYCODE_A);
						spaceShip.move(-0.2f, 0, 0);
						break;
					case KeyEvent.KEYCODE_D:
						camera.setLastKey(KeyEvent.KEYCODE_D);
						spaceShip.move(0.2f, 0, 0);
						break;
				}
			}
		}
	}

	private void updateEntities()
	{
		camera.update();

		entityController.update();
		spaceShip.update();
		background.update();
	}

	// Elements must be rendered in order from farthest to nearest.
	private void drawEntities(GL10 gl)
	{
		setPerspectiveProjection(gl);

		// Clears the screen and depth buffer.
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

		camera.draw(gl);

		gl.glPushMatrix();
		background.draw(gl);
		entityController.draw(gl);
		spaceShip.draw(gl);
		gl.glPopMatrix();

		hud.draw(gl);
	}

	private void setPerspectiveProjection(GL10 gl)
	{
		gl.glClearDepthf(1.0f);            // Set depth's clear-value to farthest
		gl.glEnable(GL10.GL_DEPTH_TEST);   // Enables depth-buffer for hidden surface removal
		gl.glDepthFunc(GL10.GL_LEQUAL);    // The type of depth testing to do
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);  // nice perspective view
		gl.glShadeModel(GL10.GL_SMOOTH);   // Enable smooth shading of color
		gl.glDisable(GL10.GL_DITHER);      // Disable dithering for better performance
		gl.glDepthMask(true);  // disable writes to Z-Buffer

		gl.glMatrixMode(GL10.GL_PROJECTION); // Select projection matrix
		gl.glLoadIdentity();                 // Reset projection matrix

		// Use perspective projection
		GLU.gluPerspective(gl, 60, (float) width / height, 0.1f, 100.f);

		gl.glMatrixMode(GL10.GL_MODELVIEW);  // Select model-view matrix
		gl.glLoadIdentity();                 // Reset
	}

	/*
	 *	Adds a new key to handle in the next update.
	 */
	public void addKey(int keyCode)
	{
		keysToHandle.add(keyCode);
	}

}
