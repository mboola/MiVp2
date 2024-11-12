package com.example.p2.auxiliary;

import android.opengl.GLUtils;
import javax.microedition.khronos.opengles.GL10;

/*
 *  This class stores all textures used to render in VRAM.
 *  It has an array of textures with an index so it can
 */
public class TextureLinker
{
    // Store all bitmaps
    private static int[] texturesGPUIDs;
    private static int[] currentUsedTexturesIDs;
    private static int currIndex;
    public static void Initialize(GL10 gl)
    {
        int[] maxTextureUnits = new int[1];
        gl.glGetIntegerv(GL10.GL_MAX_TEXTURE_UNITS, maxTextureUnits, 0);
        texturesGPUIDs = new int[maxTextureUnits[0]];
        currentUsedTexturesIDs = new int[maxTextureUnits[0]];
        for (int i = 0; i < maxTextureUnits[0]; i++)
            currentUsedTexturesIDs[i] = -1;
        gl.glGenTextures(maxTextureUnits[0], texturesGPUIDs, 0); // Generate texture-ID array
        currIndex = 0;
    }

    public static int getTextureID(GL10 gl, int textureID)
    {
        // Here we first check if the textureID is being used in the GPU
        int i = 0;
        while (i < currentUsedTexturesIDs.length && currentUsedTexturesIDs[i] != textureID)
            i++;
        if (i < currentUsedTexturesIDs.length)
            return (texturesGPUIDs[i]);

        System.out.println("enter");
        gl.glBindTexture(GL10.GL_TEXTURE_2D, texturesGPUIDs[currIndex]);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, GraphicStorage.getTextureBitmap(textureID), 0);
        currentUsedTexturesIDs[currIndex] = textureID;
        i = texturesGPUIDs[currIndex];

        // If not it means we gotta add it to the map and get a new
        // position in the array to load the texture
        if (currIndex + 1 == texturesGPUIDs.length)
            currIndex = 0;
        else
            currIndex++;
        return i;
    }
}
