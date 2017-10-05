/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sklabs.flappybirdlwjgl.levels;

import com.sklabs.flappybirdlwjgl.graphics.Shader;
import com.sklabs.flappybirdlwjgl.graphics.Texture;
import com.sklabs.flappybirdlwjgl.graphics.VertexArray;
import com.sklabs.flappybirdlwjgl.maths.Matrix4f;
import com.sklabs.flappybirdlwjgl.maths.Vec3f;

/**
 *
 * @author kees18
 */
public class Level {
    
    private VertexArray mBackground;
    private Texture mBgTexture;
    
    private int mXScroll = 0;
    private int mMap = 0;
    
    private Bird mBird;
    
    private Pipe[] mPipes = new Pipe[5 * 2];
    
    private int mIndex = 0;
    
    public Level() {
        float[] vertices = new float[] {
            -10.0f, -10.0f * 9.0f / 16.0f, 0.0f,
            -10.0f,  10.0f * 9.0f / 16.0f, 0.0f,
              0.0f,  10.0f * 9.0f / 16.0f, 0.0f,
              0.0f, -10.0f * 9.0f / 16.0f, 0.0f
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
        
        mBackground = new VertexArray(vertices, indices, tcs);
        mBgTexture = new Texture("res/bg.jpeg");
        
        mBird = new Bird();
        
        createPipes();
    }
    
    private void createPipes() {
        Pipe.create();
        for (int i = 0; i < mPipes.length; i += 2) {
            mPipes[i] = new Pipe(mIndex * 3.0f, 4.0f);
            mPipes[i + 1] = new Pipe(mPipes[i].getX(), mPipes[i].getY() - 10.0f);
            mIndex += 2;
        }
    }
    
    private void updatePipes() {
        //mPipes[]
    }
    
    public void update() {
        mXScroll--;
        if (-mXScroll % 335 == 0) {
            mMap++;
        }
        
        mBird.update();
    }
    
    private void renderPipes() {
        Shader.mPIPE.enable();
        Shader.mPIPE.setUniformMat4f("vw_matrix", Matrix4f.translate(new Vec3f(mXScroll * 0.03f, 0.0f, 0.0f)));
        Pipe.getTexture().bind();
        Pipe.getMesh().bind();
        
        for (int i = 0; i < mPipes.length; i++) {
            Shader.mPIPE.setUniformMat4f("ml_matrix", mPipes[i].getModelMatrix());
            Pipe.getMesh().draw();
        }
        Pipe.getMesh().unbind();
        Pipe.getTexture().unbind();
    }
    
    public void render() {
        mBgTexture.bind();
        Shader.mBG.enable();
        mBackground.bind();
        // Infinite scrolling background
        for (int i = mMap; i < mMap + 4; i++) {
            Shader.mBG.setUniformMat4f("vw_matrix", Matrix4f.translate(new Vec3f(i * 10 + mXScroll * 0.03f, 0.0f, 0.0f)));
            mBackground.draw();
        }
        Shader.mBG.disable();
        mBgTexture.unbind();
        
        renderPipes();
        mBird.render();
    }
}
