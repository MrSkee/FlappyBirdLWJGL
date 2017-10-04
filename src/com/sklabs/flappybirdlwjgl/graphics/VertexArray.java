/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sklabs.flappybirdlwjgl.graphics;

import com.sklabs.flappybirdlwjgl.utils.BufferUtils;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class VertexArray {
    
    private int mVAO, mVBO, mIBO, mTCO;
    private int mCount;
    
    public VertexArray(float[] pVertices, byte[] pIndeces, float[] pTextureCoordinates) {
        mCount = pIndeces.length;
        
        mVAO = glGenVertexArrays();
        glBindVertexArray(mVAO);
        
        mVBO = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, mVBO);
        glBufferData(GL_ARRAY_BUFFER, BufferUtils.createFloatBuffer(pVertices), GL_STATIC_DRAW);
        glVertexAttribPointer(0, )
    }
}
