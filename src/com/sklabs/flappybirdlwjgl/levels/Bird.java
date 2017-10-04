/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sklabs.flappybirdlwjgl.levels;

import com.sklabs.flappybirdlwjgl.graphics.Shader;
import com.sklabs.flappybirdlwjgl.graphics.Texture;
import com.sklabs.flappybirdlwjgl.graphics.VertexArray;
import com.sklabs.flappybirdlwjgl.input.Input;
import com.sklabs.flappybirdlwjgl.maths.Matrix4f;
import com.sklabs.flappybirdlwjgl.maths.Vec3f;
import org.lwjgl.glfw.GLFW;

public class Bird {
    
    private float SIZE = 1.0f;
    private VertexArray mMesh;
    private Texture mTexture;
    
    private Vec3f mPosition = new Vec3f();
    private float pRot;
    private float pYDelta = 0.0f;
    
    public Bird() {
        float[] vertices = new float[] {
            -SIZE / 2.0f, -SIZE / 2.0f, 0.1f,
            -SIZE / 2.0f,  SIZE / 2.0f, 0.1f,
             SIZE / 2.0f,  SIZE / 2.0f, 0.1f,
             SIZE / 2.0f, -SIZE / 2.0f, 0.1f,            
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
        
        mMesh= new VertexArray(vertices, indices, tcs);
        mTexture = new Texture("res/bird.png");
    }
    
    public void update() {
        /*if (Input.gKeys[GLFW.GLFW_KEY_SPACE]) {
            mPosition.mY++;
        }*/
        if (Input.gKeys[GLFW.GLFW_KEY_UP]) {
            mPosition.mY += 0.1f;
        }
        if (Input.gKeys[GLFW.GLFW_KEY_DOWN]) {
            mPosition.mY -= 0.1f;
        }
        if (Input.gKeys[GLFW.GLFW_KEY_LEFT]) {
            mPosition.mX -= 0.1f;
        }
        if (Input.gKeys[GLFW.GLFW_KEY_RIGHT]) {
            mPosition.mX += 0.1f;
        }
    }
    
    public void render() {
        Shader.mBIRD.enable();
        Shader.mBIRD.setUniformMat4f("ml_matrix", Matrix4f.translate(mPosition));
        mTexture.bind();
        mMesh.render();
        Shader.mBIRD.disable();
    }
}
