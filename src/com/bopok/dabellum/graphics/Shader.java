package com.bopok.dabellum.graphics;

import com.bopok.dabellum.math.Matrix4f;
import com.bopok.dabellum.math.Vector3f;
import com.bopok.dabellum.util.ShaderUtils;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL20.*;

public class Shader {

    public static final int VERTEX = 0;
    public static final int TCOORD = 1;

    private int ID;

    private Map<String, Integer> locationCache = new HashMap<String, Integer>();


    public static Shader BASIC;

    private boolean enabled = false;

    private Shader(String vertex, String fragment) {

        ID = ShaderUtils.load(vertex, fragment);
    }

    public static void loadAll() {
        BASIC = new Shader("shaders/shader.vert", "shaders/shader.frag");
    }

    public int getUniform(String name) {
        if (locationCache.containsKey(name))
            return locationCache.get(name);

        int result = glGetUniformLocation(ID, name);
        if(result == -1)
            System.err.println("Could not find uniform variable '" + name + "'!");
        else
            locationCache.put(name, result);
        return result;
    }

    public void setUniform1i(String name, int value) {
        glUniform1i(getUniform(name), value);
    }

    public void setUniform3f(String name, Vector3f vector) {
        glUniform3f(getUniform(name), vector.x, vector.y, vector.z);
    }

    public void setUniformMat4f(String name, Matrix4f matrix) {
        if(!enabled) enable();
        glUniformMatrix4fv(getUniform(name), false, matrix.toFloatBuffer());
    }

    public void enable() {
        glUseProgram(ID);
        enabled = true;
    }

    public void disable() {
        glUseProgram(0);
        enabled = false;
    }

}
