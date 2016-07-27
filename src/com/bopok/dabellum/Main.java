package com.bopok.dabellum;


import com.bopok.dabellum.input.Input;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;


public class Main implements Runnable {

    private int width = 1280;
    private int height = 720;

    private Thread thread;
    private boolean running = false;

    private long window;

    public void start() {
        running = true;
        thread = new Thread(this, "Game");
        thread.start();
    }

    public void init() {
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");

        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        window = glfwCreateWindow(width, height, "DaBellum", NULL, NULL);

        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(window, (vidmode.width() - width) / 2, (vidmode.height() - height) / 2);

        glfwSetKeyCallback(window, new Input());

        glfwMakeContextCurrent(window);

        glfwShowWindow(window);
    }

    public void run() {
        init();
        while (running) {
            update();
            render();

            if (glfwWindowShouldClose(window) == true) {
                running = false;
            }
        }
    }

    private void update() {

        glfwPollEvents();
        if (Input.keys[GLFW_KEY_SPACE]) {
            System.out.println("works");
        }
    }

    private void render() {

        GL.createCapabilities();

        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        new Display().display();

        glfwSwapBuffers(window);


    }

    public static void main(String[] args) {

        System.out.println("hello");
        new Main().start();

    }

}