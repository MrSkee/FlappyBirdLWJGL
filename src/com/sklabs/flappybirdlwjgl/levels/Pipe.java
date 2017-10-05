package com.sklabs.flappybirdlwjgl.levels;

import com.sklabs.flappybirdlwjgl.graphics.Texture;
import com.sklabs.flappybirdlwjgl.graphics.VertexArray;
import com.sklabs.flappybirdlwjgl.maths.Matrix4f;
import com.sklabs.flappybirdlwjgl.maths.Vec3f;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kees18
 */
public class Pipe {
    
    private Vec3f mPosition = new Vec3f();
    private Matrix4f ml_matrix;                             // Model Matrix
    
    private static float mWidth = 1.5f, mHeight = 8.0f;
    private static Texture mTexture;
    private static VertexArray mMesh;
    
    public static void create() {
        float[] vertices = new float[] {
            0.0f, 0.0f, 0.1f,
            0.0f, mHeight, 0.1f,
            mWidth, mHeight, 0.1f,
            mWidth, 0.0f, 0.1f,                   
        };
        // First triangle has 0 1 2
        // Second triangle has 2 3 0
        byte[] indices = new byte[] {
            0, 1, 2,
            2, 3, 0
        };
        
        // Texture Coordinates
        float[] tcs = new float[] {
            0, 1,
            0, 0,
            1, 0,
            1, 1
        };
        
        mMesh = new VertexArray(vertices, indices, tcs);
        mTexture = new Texture("res/pipe.png");
    }
    
    public Pipe(float pX, float pY) {
        mPosition.mX = pX;
        mPosition.mY = pY;
        ml_matrix = Matrix4f.translate(mPosition);
    }
    
    public float getX() {
        return mPosition.mX;
    }
    
    public float getY() {
        return mPosition.mY;
    }
    
    public Matrix4f getModelMatrix() {
        return ml_matrix;
    }
    
    public static VertexArray getMesh() {
        return mMesh;
    }
    
    public static Texture getTexture() {
        return mTexture;
    }
    
    public static float getWidth() {
        return mWidth;
    }
    
    public static float getHeight() {
        return mHeight;
    }
}
