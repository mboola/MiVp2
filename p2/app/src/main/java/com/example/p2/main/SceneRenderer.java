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
import com.example.p2.entities.EntityController;

import java.util.LinkedList;
import java.util.Queue;

public class SceneRenderer implements Renderer
{
	private EntityController entityController;
	private Background background;
	private SpaceShip spaceShip;
	private CameraView camera;
	private Hud hud;
	private Context context;
	private Queue<Integer> keysToHandle;
	private boolean gamePaused = false;
	private boolean graphicsInitialized = false;
	public SceneRenderer(Context context)
	{
		this.context = context;
		keysToHandle = new LinkedList<>();

		// Initialize all graphics (meshes and textures)
		GraphicStorage.Initialize(context);

		// Create the objects used in the scene
		camera = new CameraView();
		background = new Background(new Vector3(0, 0.4f, Limits.getFarZ()), 20, 10);
		entityController = new EntityController();
		spaceShip = new SpaceShip(new Vector3(0, 1, -2));
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
				}
			}
		}
	}

	private void updateEntities()
	{
		entityController.update();
		spaceShip.update();
		background.update();
		hud.update();
	}

	// Elements must be rendered in order from farthest to nearest.
	private void drawEntities(GL10 gl)
	{
		// Clears the screen and depth buffer.
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

		gl.glLoadIdentity();

		camera.draw(gl);

		gl.glPushMatrix();
		background.draw(gl);
		entityController.draw(gl);
		spaceShip.draw(gl);
		gl.glPopMatrix();

		//hud.draw(gl);
	}

	/*
	 *	Adds a new key to handle in the next update.
	 */
	public void addKey(int keyCode)
	{
		keysToHandle.add(keyCode);
	}

}
