package com.example.p2.main.scene;

import com.example.p2.auxiliary.GraphicStorage;
import com.example.p2.auxiliary.Limits;
import com.example.p2.auxiliary.Vector3;
import com.example.p2.entities.Entity;
import com.example.p2.entities.IEntity;
import com.example.p2.entities.prefabs.ProjectileEntity;
import com.example.p2.auxiliary.SpaceShipAnimation;

import javax.microedition.khronos.opengles.GL10;

public class SpaceShip extends Entity
{
    private CameraView camera;
    private SpaceShipAnimation animation;
    private Vector3 rotation;

    // Seconds passed without input that makes the ship return to original position
    private final float delayTilReturn = 1f;
    private float timeSinceLastInput;
    private final float delayDecrementer = 0.01f;
    private final float rotationDecrementer = 0.5f;
    private final float maxRotation = 20f;
    private final float rotationMultiplier = 20f;
    private float health = 100;
    private float energy = 100;
    private int minEnergyToShoot = 5;
    private boolean isInvencible = false;
    private float invencibleCooldown;

    public SpaceShip(Vector3 position, CameraView camera)
    {
        this.position = position;
        this.camera = camera;
        rotation = new Vector3(0,0,0);
        animation = new SpaceShipAnimation();
        timeSinceLastInput = 0;
        mesh = GraphicStorage.getMesh("armwing_mesh", "armwing_texture");
    }

    // TODO : change how rotation of ship gets calculated

    public void translate(float x, float y, float z)
    {
        timeSinceLastInput = delayTilReturn;

        // Update position if inside limits
        float final_x = position.x + x;
        if (final_x < Limits.getMaxX() && final_x > Limits.getMinX())
            position.x = final_x;
        float final_y = position.y + y;
        if (final_y < Limits.getMaxY() && final_y > Limits.getMinY())
            position.y = final_y;

        // Update rotation of spaceship
        float final_rotation_x = rotation.x + x * -rotationMultiplier;
        if (final_rotation_x < maxRotation && final_rotation_x > -maxRotation)
            rotation.x = final_rotation_x;
        float final_rotation_y = rotation.y + y * rotationMultiplier;
        if (final_rotation_y < maxRotation && final_rotation_y > -maxRotation)
            rotation.y = final_rotation_y;

        // Update ONLY position of camera
        camera.updatePosition(position.x, position.y);
    }

    public void update()
    {
        animation.update();

        if (energy < 100)
            energy += 0.1f;

        if (!isInvencible)
        {
            if (SceneRenderer.getRenderer().getEntityController().checkCollisions(position))
            {
                isInvencible = true;
                invencibleCooldown = 3;
                health -= 12.5f;
                if (health <= 0)
                    hasBeenHit();
            }
        }
        else
        {
            System.out.println(invencibleCooldown);

            invencibleCooldown -= 0.1f;
            if (invencibleCooldown < 0)
                isInvencible = false;
        }

        if (timeSinceLastInput > 0)
        {
            timeSinceLastInput -= delayDecrementer;
            return ;
        }

        if (rotation.x > 0)
        {
            rotation.x -= rotationDecrementer;
            if (rotation.x < 0)
                rotation.x = 0;
        }
        else if (rotation.x < 0)
        {
            rotation.x += rotationDecrementer;
            if (rotation.x > 0)
                rotation.x = 0;
        }
        if (rotation.y > 0)
        {
            rotation.y -= rotationDecrementer;
            if (rotation.y < 0)
                rotation.y = 0;
        }
        else if (rotation.y < 0)
        {
            rotation.y += rotationDecrementer;
            if (rotation.y > 0)
                rotation.y = 0;
        }
    }

    @Override
    public void draw(GL10 gl)
    {
        gl.glPushMatrix();
        gl.glTranslatef(position.x, position.y, position.z);
        if (rotation.x != 0)
            gl.glRotatef(rotation.x, 0, 1, 0);
        if (rotation.y != 0)
            gl.glRotatef(rotation.y, 1, 0, 0);
        animation.draw(gl);
        mesh.draw(gl);
        gl.glPopMatrix();
    }

    public void shoot()
    {
        // If we have enough energy
        if (energy < minEnergyToShoot)
            return ;

        energy -= 20;
        if (energy < 0)
            energy = 0;

        // Create a new IEntity projectile with the direction of the spaceship
        float direction_x = 0;
        if (rotation.x != 0)
            direction_x = (float)Math.tan(Math.toRadians(rotation.x));
        float direction_y = 0;
        if (rotation.y != 0)
            direction_y = (float)Math.tan(Math.toRadians(rotation.y));
        float direction_z = 1;
        if (rotation.z != 0)
            direction_z = (float)Math.tan(Math.toRadians(rotation.z));

        IEntity projectile = new ProjectileEntity(new Vector3(position.x, position.y, position.z), new Vector3(direction_x, direction_y, direction_z));
        SceneRenderer.getRenderer().getEntityController().addEntity(projectile);
    }

    public float getCurrentEnergy()
    {
        return energy;
    }

    public float getCurrentHealth() {
        return health;
    }
}
