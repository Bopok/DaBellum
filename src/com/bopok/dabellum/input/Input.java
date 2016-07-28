package com.bopok.dabellum.input;

import org.lwjgl.glfw.GLFWKeyCallback;

import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class Input extends GLFWKeyCallback {

    public static boolean[] keys = new boolean[655356];
    @Override
    public void invoke(long window, int key, int scancode, int action, int mods) {
        keys[key] = action != GLFW_RELEASE;

    }

}