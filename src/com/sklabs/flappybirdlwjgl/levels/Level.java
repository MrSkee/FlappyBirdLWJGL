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
import java.util.Random;

/**
 *
 * @author kees18
 */
public class Level {
    
    private VertexArray mBackground, mFade;
    private Texture mBgTexture;
    
    private int mXScroll = 0;
    private int mMap = 0;
    
    private Bird mBird;
    
    private Pipe[] mPipes = new Pipe[5 * 2];
    private int mIndex = 0;
    private float OFFSET = 5.0f;
    private boolean mControl = true;
    
    private Random mRandom = new Random();
    
    private float mTime = 0;
    
    
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
        
        mFade = new VertexArray(6);
        mBackground = new VertexArray(vertices, indices, tcs);
        mBgTexture = new Texture("res/bg.jpeg");
        
        mBird = new Bird();
        
        createPipes();
    }
    
    private void createPipes() {
        Pipe.create();
        for (int i = 0; i < mPipes.length; i += 2) {
            mPipes[i] = new Pipe(OFFSET + mIndex * 3.0f, mRandom.nextFloat() * 4.0f);
            mPipes[i + 1] = new Pipe(mPipes[i].getX(), mPipes[i].getY() - 11.5f);
            mIndex += 2;
        }
    }
    
    private void updatePipes() {
        mPipes[mIndex % 10] = new Pipe(OFFSET + mIndex * 3.0f, mRandom.nextFloat() * 4.0f);
        mPipes[(mIndex + 1) % 10] = new Pipe(mPipes[mIndex % 10].getX(), mPipes[mIndex % 10].getY() - 11.5f);
        mIndex += 2;
    }
    
    public void update() {
        if (mControl) {
            mXScroll--;
            if (-mXScroll % 335 == 0) {
                mMap++;
            }

            if (-mXScroll > 250 && -mXScroll % 120 == 0) {
                updatePipes();
            }
        }
        
        mBird.update();
        
        if (mControl && collision()) {
            mBird.fall();
            mControl = false;
        }
        
        mTime += 0.01f;
    }
    
    private void renderPipes() {
        Shader.mPIPE.enable();
        Shader.mPIPE.setUniformMat4f("vw_matrix", Matrix4f.translate(new Vec3f(mXScroll * 0.05f, 0.0f, 0.0f)));
        Pipe.getTexture().bind();
        Pipe.getMesh().bind();
        
        for (int i = 0; i < mPipes.length; i++) {
            Shader.mPIPE.setUniformMat4f("ml_matrix", mPipes[i].getModelMatrix());
            Shader.mPIPE.setUniform1i("top", (i % 2 == 0) ? 1 : 0);
            Pipe.getMesh().draw();
        }
        Pipe.getMesh().unbind();
        Pipe.getTexture().unbind();
    }
    
    private boolean collision() {
        for (int i = 0; i < mPipes.length; i++) {
            float bx = -mXScroll * 0.05f;
            float by = mBird.getY();
            float px = mPipes[i].getX();
            float py = mPipes[i].getY();
            
            float bx0 = bx - mBird.getSize() / 2.0f;
            float bx1 = bx + mBird.getSize() / 2.0f;           
            float by0 = by - mBird.getSize() / 2.0f;           
            float by1 = by + mBird.getSize() / 2.0f;
            
            float px0 = px;
            float px1 = px + Pipe.getWidth();
            float py0 = py;
            float py1 = py + Pipe.getHeight();
            
            if (bx1 > px0 && bx0 < px1) {
                if (by1 > py0 && by0 < py1) {
                    return true;
                }
            }
        }
        return false;
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
        
        Shader.mFADE.enable();
        Shader.mFADE.setUniform1f("time", mTime);
        mFade.render();
        Shader.mFADE.disable();
    }
}
