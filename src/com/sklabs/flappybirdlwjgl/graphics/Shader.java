/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sklabs.flappybirdlwjgl.graphics;

import com.sklabs.flappybirdlwjgl.maths.Matrix4f;
import com.sklabs.flappybirdlwjgl.maths.Vec3f;
import com.sklabs.flappybirdlwjgl.utils.ShaderUtils;
import java.util.HashMap;
import java.util.Map;
import static org.lwjgl.opengl.GL20.*;

public class Shader {
    
    private final boolean IS_USABLE;
    
    public static final int VERTEX_ATTRIB = 0;
    public static final int TEXCOORD_ATTRIB = 1;
    
    public static Shader mBG, mBIRD, mPIPE;
    
    private boolean mEnabled = false;
    
    private final int mID;
    private Map<String, Integer> mLocationCache = new HashMap<String, Integer>();
    
    public Shader(String pVertPath, String pFragPath) {
        mID = ShaderUtils.load(pVertPath, pFragPath);
        if (mID == -1) {
            IS_USABLE = false;
            System.err.println("Invalid Shader!");
        } else {
            IS_USABLE = true;
        }
    }
    
    public static void loadAll() {
        mBG = new Shader("shaders/BG.vert", "shaders/BG.frag");
        mBIRD = new Shader("shaders/Bird.vert", "shaders/Bird.frag");    
        mPIPE = new Shader("shaders/Pipe.vert", "shaders/Pipe.frag");
    }
    
    public int getUniform(String pName) {
        if (mLocationCache.containsKey(pName)) {
            return mLocationCache.get(pName);
        }
        int result = glGetUniformLocation(mID, pName);
        
        if (result == -1) {
            System.err.println("Could not find uniform variable '" + pName + "'!");
        }
        else {
            mLocationCache.put(pName, result);
        }
        return result;
    }
    
    public void setUniform1i(String pName, int pValue) {
        if (!mEnabled) {
            enable();
        }
        glUniform1i(getUniform(pName), pValue);
    }
    
    public void setUniform1f(String pName, float pValue) {
        if (!mEnabled) {
            enable();
        }
        glUniform1f(getUniform(pName), pValue);
    }
    
    public void setUniform2f(String pName, float pX, float pY) {
        if (!mEnabled) {
            enable();
        }
        glUniform2f(getUniform(pName), pX, pY);
    }
    
    public void setUniform3f(String pName, Vec3f pVector) {
        if (!mEnabled) {
            enable();
        }
        glUniform3f(getUniform(pName), pVector.mX, pVector.mY, pVector.mZ);
    }
    
    public void setUniformMat4f(String pName, Matrix4f pMatrix) {
        if (!mEnabled) {
            enable();
        }
        glUniformMatrix4fv(getUniform(pName), false, pMatrix.toFloatBuffer());
    }
    
    public void enable() {
        if (IS_USABLE) {
            glUseProgram(mID);
            mEnabled = true;
        }
    }
    
    public void disable() {
        glUseProgram(0);
        mEnabled = false;
    }
}
