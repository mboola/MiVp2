package com.example.p2.main;

import javax.microedition.khronos.opengles.GL10;

/*
 *  This animation rotates the ship and translates it vertically at different frequencies.
 */
// TODO : maybe change to composite?
public class SpaceShipAnimation
{
    private enum AnimationState {
        LEFT_BOTTOM, GOING_RIGHT, RIGHT_BOTTOM, GOING_LEFT, TOP, BOTTOM
    }
    private AnimationState rotationCurrentAnimationState;
    private final int rotationMaxFrames = 60;
    private int rotationCurrentFrames = 30;
    private final float maxRotation = 2.5f;
    private float rotation = 0;
    private AnimationState translationCurrentAnimationState;
    private final int translationMaxFrames = 50;
    private int translationCurrentFrames = 25;
    private final float maxTranslation = 0.1f;
    private float translation = 0;
    public SpaceShipAnimation()
    {
        rotationCurrentAnimationState = AnimationState.GOING_LEFT;
        translationCurrentAnimationState = AnimationState.TOP;
    }

    private void updateRotation()
    {
        // Here we update the state
        rotationCurrentFrames++;
        if (rotationCurrentFrames >= rotationMaxFrames)
        {
            switch (rotationCurrentAnimationState)
            {
                case GOING_LEFT:
                    rotationCurrentFrames = 45;
                    rotationCurrentAnimationState = AnimationState.LEFT_BOTTOM;
                    break;
                case LEFT_BOTTOM:
                    rotationCurrentFrames = 0;
                    rotationCurrentAnimationState = AnimationState.GOING_RIGHT;
                    break;
                case GOING_RIGHT:
                    rotationCurrentFrames = 45;
                    rotationCurrentAnimationState = AnimationState.RIGHT_BOTTOM;
                    break;
                case RIGHT_BOTTOM:
                    rotationCurrentFrames = 0;
                    rotationCurrentAnimationState = AnimationState.GOING_LEFT;
                    break;
            }
        }
        // Here update animationModifier
        switch (rotationCurrentAnimationState)
        {
            case GOING_LEFT:
                rotation = ((float) (rotationMaxFrames - rotationCurrentFrames) / (float) rotationMaxFrames) * maxRotation * 2 - maxRotation;
                break;
            case LEFT_BOTTOM:
                rotation = -maxRotation;
                break;
            case GOING_RIGHT:
                rotation = ((float) rotationCurrentFrames / (float) rotationMaxFrames) * maxRotation * 2 - maxRotation;
                break;
            case RIGHT_BOTTOM:
                rotation = maxRotation;
                break;
        }
    }

    private void updateTranslation()
    {
        translationCurrentFrames++;
        if (translationCurrentFrames >= translationMaxFrames)
        {
            switch (translationCurrentAnimationState)
            {
                case TOP:
                    translationCurrentFrames = 0;
                    translationCurrentAnimationState = AnimationState.BOTTOM;
                    break;
                case BOTTOM:
                    translationCurrentFrames = 0;
                    translationCurrentAnimationState = AnimationState.TOP;
                    break;
            }
        }

        // Here update animationModifier
        switch (translationCurrentAnimationState)
        {
            case TOP:
                translation = ((float) (translationMaxFrames - translationCurrentFrames) / (float) translationMaxFrames) * maxTranslation * 2 - maxTranslation;
                break;
            case BOTTOM:
                translation = ((float) translationCurrentFrames / (float) translationMaxFrames) * maxTranslation * 2 - maxTranslation;
                break;
        }
    }

    public boolean update()
    {
        updateRotation();
        updateTranslation();
        return false;
    }

    public void draw(GL10 gl)
    {
        gl.glTranslatef(0, translation, 0);
        gl.glRotatef(rotation, 0, 0, 1);
    }
}
