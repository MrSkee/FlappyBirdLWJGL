/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sklabs.flappybirdlwjgl.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class FileUtils {

    private FileUtils() {
    }
    
    public static String loadAsString(String pFileName) {
        // StringBuilder is good for loops
        StringBuilder result = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(pFileName));
            String buffer = "";
            while ((buffer = reader.readLine()) != null) {
                result.append(buffer);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
