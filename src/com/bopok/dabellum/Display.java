package com.bopok.dabellum;

import static org.lwjgl.opengl.GL11.*;



public class Display {

    public void display() {


        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, 1280, 720, 0, -1, 1);
        glMatrixMode(GL_MODELVIEW);

        glColor3f(0.0f, 1.0f, 1.0f);

        glBegin(GL_QUADS);
        glVertex2f(0, 0);
        glVertex2f(0, 360);
        glVertex2f(1280, 360);
        glVertex2f(1280, 0);
        glEnd();


    }



}
