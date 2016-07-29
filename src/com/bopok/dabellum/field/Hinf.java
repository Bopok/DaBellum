package com.bopok.dabellum.field;


import com.bopok.dabellum.graphics.Shader;
import com.bopok.dabellum.graphics.Texture;
import com.bopok.dabellum.graphics.VertexArray;
import com.bopok.dabellum.input.Input;
import com.bopok.dabellum.math.Matrix4f;
import com.bopok.dabellum.math.Vector3f;
import org.lwjgl.glfw.GLFW;

public class Hinf {

    private float SIZE = 50.0f;
    private VertexArray mesh;
    private Texture texture;

    private Vector3f position = new Vector3f();
    private float delta = 0.0f;

    public Hinf(){
        float[] vertices = new float[] {
                -SIZE / 2.0f, -SIZE / 2.0f, 0.1f,
                -SIZE / 2.0f,  SIZE / 2.0f, 0.1f,
                 SIZE / 2.0f,  SIZE / 2.0f, 0.1f,
                 SIZE / 2.0f, -SIZE / 2.0f, 0.1f
        };

        byte[] indices = new byte[] {
                0, 1, 2,
                2, 3, 0
        };

        float[] tcs = new float[] {
                0, 1,
                0, 0,
                1, 0,
                1, 1
        };

        mesh = new VertexArray(vertices, indices, tcs);
        texture = new Texture("res/hinf.png");
    }

    public void update(){
        if(Input.keys[GLFW.GLFW_KEY_W])
            position.y += 3.0f;
        if(Input.keys[GLFW.GLFW_KEY_S])
            position.y -= 3.0f;
        if(Input.keys[GLFW.GLFW_KEY_A])
            position.x -= 3.0f;
        if(Input.keys[GLFW.GLFW_KEY_D])
            position.x += 3.0f;
    }

    public void render() {
        texture.bind();
        Shader.HINF.enable();
        Shader.HINF.setUniformMat4f("ml_matrix", Matrix4f.translate(position));
        mesh.render();
        Shader.HINF.disable();
        texture.unbind();
    }
}
