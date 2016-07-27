package com.bopok.dabellum;

import org.lwjgl.opengl.GL11;



public class Display {

    public void display() {



        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, 1280, 720, 0, -1, 1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);

        GL11.glColor3f(0.0f, 0.0f, 1.0f);

        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(0, 0);
        GL11.glVertex2f(0, 360);
        GL11.glVertex2f(1280, 360);
        GL11.glVertex2f(1280, 0);
        GL11.glEnd();


    }



}
