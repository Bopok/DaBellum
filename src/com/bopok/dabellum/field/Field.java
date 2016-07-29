package com.bopok.dabellum.field;

import com.bopok.dabellum.graphics.Shader;
import com.bopok.dabellum.graphics.Texture;
import com.bopok.dabellum.graphics.VertexArray;

public class Field {

    private VertexArray field;
    private Texture fieldTex;

    public Field () {
        float[] vertices = new float[] {
                -10.0f, -10.0f, 0.0f,
                -10.0f,  10.0f, 0.0f,
                 10.0f,  10.0f, 0.0f,
                 10.0f, -10.0f, 0.0f
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

        field = new VertexArray(vertices, indices, tcs);
        fieldTex = new Texture("res/field.png");
    }

    public void render() {
        fieldTex.bind();
        Shader.BASIC.enable();
        field.render();
        Shader.BASIC.disable();
        fieldTex.unbind();
    }

}
