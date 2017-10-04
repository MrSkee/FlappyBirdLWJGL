/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sklabs.flappybirdlwjgl.utils;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

public class ShaderUtils {
    
    private ShaderUtils() {
        
    }
    
    public static int load(String pVertPath, String pFragPath) {
        String vert = FileUtils.loadAsString(pVertPath);
        String frag = FileUtils.loadAsString(pFragPath);
        return create(vert, frag);
    }
    
    public static int create(String pVert, String pFrag) {
        int program = glCreateProgram();
        int vertID = glCreateShader(GL_VERTEX_SHADER);
        int fragID = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(vertID, pVert);
        glShaderSource(fragID, pFrag);
        
        glCompileShader(vertID);
        if (glGetShaderi(vertID, GL_COMPILE_STATUS) == GL_FALSE) {
            System.err.println("Failed to compile vertex shader!");
            System.err.println(glGetShaderInfoLog(vertID));
            return -1;
        }
        
        glCompileShader(fragID);
        if (glGetShaderi(fragID, GL_COMPILE_STATUS) == GL_FALSE) {
            System.err.println("Failed to compile fragment shader!");
            System.err.println(glGetShaderInfoLog(fragID));
            return -1;
        }
        
        glAttachShader(program, vertID);
        glAttachShader(program, fragID);
        glLinkProgram(program);
        glValidateProgram(program);
        
        glDeleteShader(vertID);
        glDeleteShader(fragID);
        
        return program;
    }
}
