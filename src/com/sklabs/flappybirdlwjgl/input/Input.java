/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sklabs.flappybirdlwjgl.input;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

/**
 *
 * @author kees18
 */
public class Input extends GLFWKeyCallback {
    
    public static boolean[] gKeys = new boolean[65536];

    public void invoke(long pWindow, int pKey, int pScancode, int pAction, int pMods) {
        gKeys[pKey] = pAction == GLFW.GLFW_RELEASE ? false : true;
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public static boolean isKeyDown(int pKeycode) {
        return gKeys[pKeycode];
    }
    
}
