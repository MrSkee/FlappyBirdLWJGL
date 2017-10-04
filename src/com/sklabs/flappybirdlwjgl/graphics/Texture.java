/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sklabs.flappybirdlwjgl.graphics;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import com.sklabs.flappybirdlwjgl.utils.BufferUtils;

import static org.lwjgl.opengl.GL11.*;

public class Texture {
    
    private int mWidth, mHeight;
    private int mTexture;
    
    public Texture(String pPath) {
        mTexture = load(pPath);
    }
    
    private int load(String pPath) {
        int[] pixels = null;
        try {
            BufferedImage image = ImageIO.read(new FileInputStream(pPath));
            mWidth = image.getWidth();
            mHeight = image.getHeight();
            pixels = new int[mWidth * mHeight];
            image.getRGB(0, 0, mWidth, mHeight, pixels, 0, mWidth);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        int[] data = new int[mWidth * mHeight];
        for (int i = 0; i < mWidth * mHeight; i++) {
            int a = (pixels[i] & 0xff000000) >> 24;
            int r = (pixels[i] & 0xff0000) >> 16;
            int g = (pixels[i] & 0xff00) >> 8;
            int b = (pixels[i] & 0xff);
            
            data[i] = a << 24 | b << 16 | g << 8 | r;
        }
        
        int result = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, result);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        // GL_LINEAR linearly interpolates pixels - anti-aliasing
        // GL_NEAREST snaps to nearest pixel
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);        
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, mWidth, mHeight, 0, GL_RGBA, GL_UNSIGNED_BYTE, BufferUtils.createIntBuffer(data));
        glBindTexture(GL_TEXTURE_2D, 0);
        return result;
    }
    
    public void bind() {
        glBindTexture(GL_TEXTURE_2D, mTexture);
    }
    
    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }
}
