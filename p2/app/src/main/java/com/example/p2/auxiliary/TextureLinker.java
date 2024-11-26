package com.example.p2.auxiliary;

import android.graphics.Bitmap;
import android.opengl.GLUtils;
import javax.microedition.khronos.opengles.GL10;

/*
 *  Used to control the textures inside the GRAM so there is enough space
 *  to display all the textures needed by the meshes when necessary.
 */
public class TextureLinker
{
    private static int[] loadedTexturesGPU; // Identifiers inside the GPU to access textures
    private static String[] texturesUsed; // My texture id used to identify Android Studio textures
    private static int currIndex;

    /*
     *  Initializes the array of identifiers used to access the textures loaded into the GPU.
     */
    public static void Initialize(GL10 gl)
    {
        int[] maxTextureUnits = new int[1];
        // Im not sure if this returns the max or not, but there must be a max somewhere and I want to
        // control it so the program does not brake.
        //gl.glGetIntegerv(GL10.GL_MAX_TEXTURE_UNITS, maxTextureUnits, 0);
        maxTextureUnits[0] = 10;
        loadedTexturesGPU = new int[maxTextureUnits[0]];
        texturesUsed = new String[maxTextureUnits[0]];
        for (int i = 0; i < maxTextureUnits[0]; i++)
            texturesUsed[i] = null;
        gl.glGenTextures(maxTextureUnits[0], loadedTexturesGPU, 0); // Generate texture-ID array
        currIndex = 0;
    }

    /*
     *  This function checks if the textureID to draw is already loaded.
     *  If it isn't, it loads the texture. Returns the ID of the GPU of the texture to draw.
     */
    public static int getTextureID(GL10 gl, String textureID)
    {
        // Here we first check if the textureID is being used in the GPU
        int i = 0;
        while (i < texturesUsed.length && !textureID.equals(texturesUsed[i]))
            i++;
        if (i < texturesUsed.length)
            return (loadedTexturesGPU[i]);

        // Selects an ID of the GPU
        gl.glBindTexture(GL10.GL_TEXTURE_2D, loadedTexturesGPU[currIndex]);
        // Filters to determine how the texture is sampled
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);

        // Load the bitmap into the texture
        Bitmap bitmap = GraphicStorage.getTextureBitmap(textureID);
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);

        // Sets the texture used in the current position.
        texturesUsed[currIndex] = textureID;
        int currentTextureID = loadedTexturesGPU[currIndex];

        // Update current index using Round Robin
        if (currIndex + 1 == loadedTexturesGPU.length)
            currIndex = 0;
        else
            currIndex++;
        return currentTextureID;
    }
}
