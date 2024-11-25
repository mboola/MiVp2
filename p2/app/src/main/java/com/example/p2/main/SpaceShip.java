package com.example.p2.main;

import com.example.p2.auxiliary.GraphicStorage;
import com.example.p2.auxiliary.Limits;
import com.example.p2.auxiliary.Vector3;
import com.example.p2.entities.Entity;

import javax.microedition.khronos.opengles.GL10;

public class SpaceShip extends Entity
{
    private enum AnimationState {
        LEFT_BOTTOM, GOING_RIGHT, RIGHT_BOTTOM, GOING_LEFT
    }
    private AnimationState currentAnimationState;
    private final int maxFrames = 60;
    private int currentFrames = 30;
    private float maxInclination = 2.5f;
    private float rotation = 0;
    public SpaceShip(Vector3 position)
    {
        this.position = position;
        currentAnimationState = AnimationState.GOING_LEFT;
        mesh = GraphicStorage.getMesh("armwing", "armwing_texture");
    }

    public void move(float x, float y, float z)
    {
        float final_x = position.x + x;
        float final_y = position.y + y;
        if (final_x < Limits.getMaxX() && final_x > Limits.getMinX())
            position.x = final_x;
        if (final_y < Limits.getMaxY() && final_y > Limits.getMinY())
            position.y = final_y;
    }

    public boolean update()
    {
        // Here we update the state
        currentFrames++;
        if (currentFrames >= maxFrames)
        {
            switch (currentAnimationState)
            {
                case GOING_LEFT:
                    currentFrames = 40;
                    currentAnimationState = AnimationState.LEFT_BOTTOM;
                    break;
                case LEFT_BOTTOM:
                    currentFrames = 0;
                    currentAnimationState = AnimationState.GOING_RIGHT;
                    break;
                case GOING_RIGHT:
                    currentFrames = 40;
                    currentAnimationState = AnimationState.RIGHT_BOTTOM;
                    break;
                case RIGHT_BOTTOM:
                    currentFrames = 0;
                    currentAnimationState = AnimationState.GOING_LEFT;
                    break;
            }
        }
        // Here update animationModifier
        switch (currentAnimationState)
        {
            case GOING_LEFT:
                rotation = ((float) (maxFrames - currentFrames) / (float) maxFrames) * maxInclination * 2 - maxInclination;
                break;
            case LEFT_BOTTOM:
                rotation = -maxInclination;
                break;
            case GOING_RIGHT:
                rotation = ((float) currentFrames / (float) maxFrames) * maxInclination * 2 - maxInclination;
                break;
            case RIGHT_BOTTOM:
                rotation = maxInclination;
                break;
        }
        return false;
    }

    public void draw(GL10 gl)
    {
        gl.glPushMatrix();
        gl.glTranslatef(position.x, position.y, position.z);
        gl.glRotatef(rotation, 0, 0, 1);
        mesh.draw(gl);
        gl.glPopMatrix();
    }

    @Override
    protected boolean hasCollided(Vector3 position) {
        return false;
    }
}
