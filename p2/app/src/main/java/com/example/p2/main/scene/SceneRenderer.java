package com.example.p2.main.scene;


import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import android.view.KeyEvent;

import com.example.p2.auxiliary.Light;
import com.example.p2.auxiliary.Limits;
import com.example.p2.auxiliary.TextureLinker;
import com.example.p2.auxiliary.Time;
import com.example.p2.auxiliary.Vector3;
import com.example.p2.main.controllers.EntityController;
import com.example.p2.main.controllers.MovementController;

import java.nio.FloatBuffer;

public class SceneRenderer implements Renderer
{
	private CameraView camera;
	private Background background;
	private EntityController entityController;
	private SpaceShip spaceShip;
	private Hud hud;
	private MovementController movementController;
	private boolean gamePaused = false;

	private Light light;
	private float dayTime;
	private boolean isDay;
	private boolean graphicsInitialized = false;

	private float startTime = 0;

	private int height;
	private int width;

	// SingletonPattern
	private static SceneRenderer instance = null;

	private SceneRenderer()
	{
		camera = new CameraView();

		// Create the objects used in the scene
		background = new Background(new Vector3(0, 0, Limits.getFarZ()), 30);
		entityController = new EntityController();
		spaceShip = new SpaceShip(new Vector3(0, 0, -1.8f), camera);

		// Controller that defines the movement of the ship and the camera
		movementController = new MovementController(spaceShip, camera);

		hud = new Hud();
		dayTime = 1;
		isDay = true;
	}

	public static SceneRenderer getRenderer() {
		if (instance == null)
			instance = new SceneRenderer();
		return instance;
	}

	public EntityController getEntityController() {
		return entityController;
	}

	public CameraView getCamera() {
		return camera;
	}

	public SpaceShip getSpaceShip() {
		return spaceShip;
	}

	public void keyPushed(int keyCode) {
		if (keyCode == KeyEvent.KEYCODE_SPACE) {
			gamePaused = !gamePaused;
		}
		else if (keyCode == KeyEvent.KEYCODE_C) {
			getCamera().changeView();
		}
		else if (!gamePaused) {
			movementController.setLastKeyPushed(keyCode);
		}
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
		long endTime = System.nanoTime();

		Time.deltaTime = (endTime - startTime) / 1000000000;

		if (!graphicsInitialized)
			return ;
		if (!gamePaused)
			updateEntities();

		//Set default ambience light
		gl.glLightModelfv(GL10.GL_LIGHT_MODEL_AMBIENT, new float[]{dayTime, dayTime, dayTime, 0}, 0);

		drawEntities(gl);

		startTime = System.nanoTime();
	}

	/*
	 *	Update the logic of the game.
	 */
	private void updateEntities()
	{
		movementController.handleKeyPushed();

		camera.update();

		// Update all entities: collided into something or else
		entityController.update();

		// Check collisions
		spaceShip.update();

		Vector3 position = spaceShip.getPosition();
		light.setPosition(new float[]{position.x, position.y, position.z - 1, 0.0f});
		light.setSpecularColor(new float[]{1f, 1f, 1f});
		light.setDiffuseColor(new float[]{1f, 1f, 1f});

		// Move cubes
		background.update();

		if (isDay)
		{
			dayTime += 0.001f;
			if (dayTime >= 1)
				isDay = !isDay;
		}
		else
		{
			dayTime -= 0.001f;
			if (dayTime <= 0.3f)
				isDay = !isDay;
		}
	}

	/*
	 *	Draw the objects of the scene.
	 */
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
	 *	Called when the app starts or when the app goes background
	 * 	and then comes back and needs to be recreated.
	 */
	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{
		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);  // Set color's clear-value to black

		gl.glClearDepthf(1.0f);            // Set depth's clear-value to farthest
		gl.glEnable(GL10.GL_DEPTH_TEST);   // Enables depth-buffer for hidden surface removal
		gl.glDepthFunc(GL10.GL_LEQUAL);    // The type of depth testing to do
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);  // nice perspective view
		gl.glShadeModel(GL10.GL_SMOOTH);   // Enable smooth shading of color
		gl.glDisable(GL10.GL_DITHER);      // Disable dithering for better performance

		//Set default ambience light
		gl.glLightModelfv(GL10.GL_LIGHT_MODEL_AMBIENT, new float[]{0, 0, 0, 0}, 0);

		//Enable Lights
		gl.glEnable(GL10.GL_LIGHTING);

		// Enable Normalize
		gl.glEnable(GL10.GL_NORMALIZE);

		gl.glEnable(GL10.GL_FOG);
		gl.glFogf(GL10.GL_FOG_MODE, GL10.GL_LINEAR);
		gl.glFogfv(GL10.GL_FOG_COLOR, FloatBuffer.wrap(new float[] {1, 1f, 1, 1}));
		gl.glFogf(GL10.GL_FOG_START,  0);
		gl.glFogf(GL10.GL_FOG_END,    1000);
		gl.glFogf(GL10.GL_FOG_DENSITY, 0.5f);
		gl.glHint(GL10.GL_FOG_HINT, GL10.GL_NICEST);
		gl.glEnable(GL10.GL_FOG);

		// Lights initializations
		light = new Light(gl, GL10.GL_LIGHT0);

		// Game initializations
		TextureLinker.Initialize(gl);
		graphicsInitialized = true;
	}

}
