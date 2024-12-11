package com.example.p2.main;

import android.view.KeyEvent;

// In reality is more like an actionController more or less
// something in between...something weird
public class MovementController
{
    private final SpaceShip ship;
    private final CameraView camera;
    private final float movementModifier;
    private final float rotationModifier;
    public MovementController(SpaceShip ship, CameraView camera)
    {
        this.ship = ship;
        this.camera = camera;
        movementModifier = 0.2f;
        rotationModifier = 2f;
    }

    public void keyPushed(int keyCode)
    {
        switch (keyCode) {
            case KeyEvent.KEYCODE_W:
                ship.translate(0, movementModifier, 0);
                camera.updateRotation(0, -rotationModifier);
                break;
            case KeyEvent.KEYCODE_S:
                ship.translate(0, -movementModifier, 0);
                camera.updateRotation(0, rotationModifier);
                break;
            case KeyEvent.KEYCODE_A:
                ship.translate(-movementModifier, 0, 0);
                camera.updateRotation(-rotationModifier, 0);
                break;
            case KeyEvent.KEYCODE_D:
                ship.translate(movementModifier, 0, 0);
                camera.updateRotation(rotationModifier, 0);
                break;
            case KeyEvent.KEYCODE_ENTER:
                ship.shoot();
                // entityController.addEntity(spaceShip.shoot());
                break;
        }
    }
}
