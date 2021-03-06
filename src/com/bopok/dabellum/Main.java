package com.bopok.dabellum;


import com.bopok.dabellum.field.Field;
import com.bopok.dabellum.graphics.Shader;
import com.bopok.dabellum.input.Input;
import com.bopok.dabellum.math.Matrix4f;
import com.bopok.dabellum.util.ShaderUtils;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;


import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.system.MemoryUtil.*;


public class Main implements Runnable {

    private int width = 1280;
    private int height = 720;

    private boolean running = false;
    private Thread thread;

    private long window;

    private Field field;

    public void start() {
        running = true;
        thread = new Thread(this, "Display");
        thread.start();
    }

    private void init() {

        GLFWErrorCallback.createPrint(System.err);

        if(!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        window = glfwCreateWindow(width, height, "DaBellum", NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(window, (vidMode.width() - width) /2, (vidMode.height() - height) / 2);
        glfwSetKeyCallback(window, new Input());
        glfwMakeContextCurrent(window);
        glfwShowWindow(window);

        GL.createCapabilities();
        glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        glEnable(GL_DEPTH_TEST);
        glActiveTexture(GL_TEXTURE1);

        Shader.loadAll();

        Matrix4f pr_matrix = Matrix4f.orthographic(-640.0f, 640.0f, -640.0f * 9.0f / 16.0f, 640.0f * 9.0f / 16.0f, -1.0f, 1.0f);
        Shader.BASIC.setUniformMat4f("pr_matrix", pr_matrix);
        Shader.BASIC.setUniform1i("tex", 1);

        Shader.HINF.setUniformMat4f("pr_matrix", pr_matrix);
        Shader.HINF.setUniform1i("tex", 1);

        field = new Field();

    }

    public void run() {
        init();

        long lastTime = System.nanoTime();
        double delta = 0.0;
        double ns = 1000000000.0 / 60.0;
        long timer = System.currentTimeMillis();
        int updates = 0;
        int frames = 0;
        while(running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
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
        }
        glfwDestroyWindow(window);
        glfwTerminate();

    }

    private void update() {

        glfwPollEvents();
        field.update();
    }

    private void render() {


        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        field.render();
        int i = glGetError();
        if(i != GL_NO_ERROR)
            System.out.print(i);
        glfwSwapBuffers(window);

        if (glfwWindowShouldClose(window)) {
            running = false;
        }

    }

    public static void main(String[] args) {

        new Main().start();

    }

}