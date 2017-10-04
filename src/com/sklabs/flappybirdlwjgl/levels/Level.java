/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sklabs.flappybirdlwjgl.levels;

import com.sklabs.flappybirdlwjgl.graphics.Shader;
import com.sklabs.flappybirdlwjgl.graphics.Texture;
import com.sklabs.flappybirdlwjgl.graphics.VertexArray;

/**
 *
 * @author kees18
 */
public class Level {
    
    private VertexArray mBackground;
    private Texture mBgTexture;
    
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
    }
    
    public void render() {
        mBgTexture.bind();
        Shader.mBG.enable();
        mBackground.render();
        Shader.mBG.disable();
        mBgTexture.unbind();
    }
}
