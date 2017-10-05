/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sklabs.flappybirdlwjgl;

import com.sklabs.flappybirdlwjgl.graphics.Shader;
import com.sklabs.flappybirdlwjgl.input.Input;
import com.sklabs.flappybirdlwjgl.levels.Level;
import com.sklabs.flappybirdlwjgl.maths.Matrix4f;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.WGLEXTSwapControl.wglSwapIntervalEXT;

/**
 *
 * @author kees18
 */
public class Main implements Runnable {

    private int gWidth = 1280;
    private int gHeight = 720;
    private String gTitle = "FlappyBirdLWJGL";
    
    private Thread gThread;
    private boolean gIsRunning = false;
    
    private long gWindow;
    
    private Level gLevel;
    
    public void start() {
        gIsRunning = true;
        gThread = new Thread(this, "Game");
        gThread.start();
    }
    
    private void init() {
        if (glfwInit() != true) {
            // TODO: Handle it
            return;
        }
        
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        gWindow = glfwCreateWindow(gWidth, gHeight, gTitle, NULL, NULL);
        
        if (gWindow == NULL) {
            // TODO: handle
            return;
        }
        
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(gWindow, (vidmode.width() - gWidth) / 2, (vidmode.height() - gHeight) / 2);
        
        glfwSetKeyCallback(gWindow, new Input());
        
        glfwMakeContextCurrent(gWindow);
        glfwShowWindow(gWindow);
        //GLContext.createFromCurrent();
        GL.createCapabilities();
        glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        glEnable(GL_DEPTH_TEST);
        glActiveTexture(GL_TEXTURE1);
        System.out.println("OpenGL: " + glGetString(GL_VERSION));
        Shader.loadAll();
        
        // Must bind shader before setting
        Matrix4f pr_matrix = Matrix4f.orthographic(-10.0f, 10.0f, -10.0f * 9.0f / 16.0f, 10.0f * 9.0f / 16.0f, -1.0f, 1.0f);
        Shader.mBG.setUniformMat4f("pr_matrix", pr_matrix);
        Shader.mBG.setUniform1i("tex", 1);
        
        Shader.mBIRD.setUniformMat4f("pr_matrix", pr_matrix);
        Shader.mBIRD.setUniform1i("tex", 1);
        
        Shader.mPIPE.setUniformMat4f("pr_matrix", pr_matrix);
        Shader.mPIPE.setUniform1i("tex", 1);
        
        gLevel = new Level();
        
        wglSwapIntervalEXT(0);
    }
    
    public void run() {
        init();
        
        long lastTime = System.nanoTime();
        double delta = 0.0;
        double ns = 1000000000.0 / 60.0;
        long timer = System.currentTimeMillis();
        int updates = 0;
        int frames = 0;
        while (gIsRunning) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            // Delta will reach 1 60 times per second
            if (delta >= 1.0) {
                update();
                updates++;
                delta--;
            }
            render();
            frames++;
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println(updates + " ups, " + frames + " fps");
                updates = 0;
                frames = 0;
            }
            
            if (glfwWindowShouldClose(gWindow) == true)
                gIsRunning = false;
        }
        
        glfwDestroyWindow(gWindow);
        glfwTerminate();
    }
    
    private void update() {
        glfwPollEvents();
        /*if (Input.gKeys[GLFW_KEY_SPACE]) {
            System.out.println("FLAP!");
        }*/
        gLevel.update();
    }
    
    private void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        gLevel.render();
        int error = glGetError();
        if (error != GL_NO_ERROR) {
            System.out.println(error);
        }
        glfwSwapBuffers(gWindow);
    }

    public static void main(String[] args) {
        new Main().start();

    }
}
