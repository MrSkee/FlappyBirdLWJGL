package com.sklabs.flappybirdlwjgl.maths;

import java.nio.FloatBuffer;
import com.sklabs.flappybirdlwjgl.utils.BufferUtils;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kees18
 */
public class Matrix4f {
    public static final int SIZE = 4 * 4;
    public float[] mElements = new float[SIZE];
    
    public Matrix4f() {
        
    }
    
    public static Matrix4f identity() {
        Matrix4f result = new Matrix4f();
        for (int i = 0; i < SIZE; i++) {
            result.mElements[i] = 0.0f;
        }
        result.mElements[0 + 0 * 4] = 1.0f;
        result.mElements[1 + 1 * 4] = 1.0f;
        result.mElements[2 + 2 * 4] = 1.0f;
        result.mElements[3 + 3 * 4] = 1.0f;        
        
        return result;
    }
    
    public static Matrix4f orthographic(float pLeft, float pRight, float pBottom, float pTop, float pNear, float pFar) {
        Matrix4f result = identity();
        
        result.mElements[0 + 0 * 4] = 2.0f / (pRight - pLeft);
        result.mElements[1 + 1 * 4] = 2.0f / (pTop - pBottom);
        result.mElements[2 + 2 * 4] = 2.0f / (pNear - pFar);
        result.mElements[3 + 3 * 4] = (pLeft + pRight) / (pLeft - pRight);
        result.mElements[4 + 4 * 4] = (pBottom + pTop) / (pBottom - pTop);
        result.mElements[5 + 5 * 4] = (pFar + pNear) / (pFar - pNear);        
        
        return result;
    }
    
    public static Matrix4f translate(Vec3f pVector) {
        Matrix4f result = identity();
        result.mElements[0 + 3 * 4] = pVector.x;
        result.mElements[1 + 3 * 4] = pVector.y;
        result.mElements[2 + 3 * 4] = pVector.z;
        return result;
    }
    
    public static Matrix4f rotate(float pAngle) {
        Matrix4f result = identity();
        float radAngle = (float) Math.toRadians(pAngle);
        float cos = (float) Math.cos(radAngle);
        float sin = (float) Math.sin(radAngle);
        
        result.mElements[0 + 0 * 4] = cos;
        result.mElements[1 + 0 * 4] = sin;

        result.mElements[0 + 1 * 4] = -sin;
        result.mElements[1 + 1 * 4] = cos;
        
        return result;
    }
    
    public Matrix4f multiply(Matrix4f pMatrix) {
        Matrix4f result = new Matrix4f();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                float sum = 0.0f;
                for (int k = 0; k < 4; k++) {
                sum += this.mElements[j + k * 4] * pMatrix.mElements[k + i * 4];
                }
                result.mElements[j + i * 4] = sum;
            }
        }
        return result;
    }
    
    public FloatBuffer toFloatBuffer() {
        return BufferUtils.createFloatBuffer(mElements);
}
