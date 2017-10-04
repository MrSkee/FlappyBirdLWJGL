/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sklabs.flappybirdlwjgl.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class BufferUtils {
    
    private BufferUtils() {
        
    }
    
    public static ByteBuffer createByteBuffer(byte[] pArray) {
        ByteBuffer result = ByteBuffer. allocateDirect(pArray.length).order(ByteOrder.nativeOrder());
        result.put(pArray).flip();
        return result;
    }
    
    public static FloatBuffer createFloatBuffer(float[] pArray) {
        FloatBuffer result = ByteBuffer. allocateDirect(pArray.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        result.put(pArray).flip();
        return result;
    }
    
    public static IntBuffer createIntBuffer(int[] pArray) {
        IntBuffer  result = ByteBuffer. allocateDirect(pArray.length * 4).order(ByteOrder.nativeOrder()).asIntBuffer();
        result.put(pArray).flip();
        return result;
    }
}
