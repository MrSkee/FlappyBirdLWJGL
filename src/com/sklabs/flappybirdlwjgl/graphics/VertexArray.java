/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sklabs.flappybirdlwjgl.graphics;

import com.sklabs.flappybirdlwjgl.utils.BufferUtils;
import static com.sklabs.flappybirdlwjgl.graphics.Shader.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class VertexArray {
    
    /*
    *   mVAO - Vertex Array Object
    *   mVBO - Vertex Buffer Object
    *   mIBO - Indices(indexes) Buffer Object
    *   mTBO - Texture Coordinates Buffer Object
    */
    private int mVAO, mVBO, mIBO, mTBO;
    private int mCount;
    
    public VertexArray(int pCount) {
        this.mCount = pCount;
        mVAO = glGenVertexArrays();
    }
    
    public VertexArray(float[] pVertices, byte[] pIndices, float[] pTextureCoordinates) {
        mCount = pIndices.length;
        
        mVAO = glGenVertexArrays();
        glBindVertexArray(mVAO);
        
        mVBO = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, mVBO);
        glBufferData(GL_ARRAY_BUFFER, BufferUtils.createFloatBuffer(pVertices), GL_STATIC_DRAW);
        glVertexAttribPointer(Shader.VERTEX_ATTRIB, 3, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(Shader.VERTEX_ATTRIB);
        
        mTBO = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, mTBO);
        glBufferData(GL_ARRAY_BUFFER, BufferUtils.createFloatBuffer(pTextureCoordinates), GL_STATIC_DRAW);
        glVertexAttribPointer(Shader.TEXCOORD_ATTRIB, 2, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(Shader.TEXCOORD_ATTRIB);
        
        mIBO = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, mIBO);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, BufferUtils.createByteBuffer(pIndices), GL_STATIC_DRAW);
        
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }
    
    public void bind() {
        glBindVertexArray(mVAO);
        if (mIBO > 0)
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, mIBO);
    }
    
    public void unbind() {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }
    
    public void draw() {
        if (mIBO > 0)
            glDrawElements(GL_TRIANGLES, mCount, GL_UNSIGNED_BYTE, 0);
        else
            glDrawArrays(GL_TRIANGLES, 0, mCount);
    }
    
    public void render() {
        bind();
        draw();
    }
}
