/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sklabs.flappybirdlwjgl.maths;

/**
 *
 * @author kees18
 */
public class Vec3f {
    
    public float mX, mY, mZ;
    
    public Vec3f() {
        mX = 0.0f;
        mY = 0.0f;
        mZ = 0.0f;
    }
    
    public Vec3f(float pFloat) {
        mX = pFloat;
        mY = pFloat;
        mZ = pFloat;
    }
    
    public Vec3f(float pX, float pY, float pZ) {
        mX = pX;
        mY = pY;
        mZ = pZ;
    }
}
