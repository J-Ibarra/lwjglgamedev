package com.jcs;

import org.lwjgl.glfw.GLFWVidMode;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * Created by Jcs on 26/8/2016.
 */
public class DummyGame extends GameEngine {

    public String tittle = "Game Engine";
    int width = 640;
    int height = 480;


    @Override
    public void init() throws Exception {

    }

    private int direction = 0;

    private float color = 0.0f;


    @Override
    public void config(long win) throws Exception {
        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(win, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window, true); // We will detect this in our rendering loop

            if (key == GLFW_KEY_UP && action != GLFW_RELEASE)
                direction = 1;

            if (key == GLFW_KEY_DOWN && action != GLFW_RELEASE)
                direction = -1;

            if ((key == GLFW_KEY_UP || key == GLFW_KEY_DOWN) && action == GLFW_RELEASE)
                direction = 0;


        });
    }

    @Override
    public void update(float delta) throws Exception {
        color += direction * delta;
        if (color > 1) {
            color = 1.0f;
        } else if (color < 0) {
            color = 0.0f;
        }
    }

    @Override
    public void render() throws Exception {

        glClearColor(color, color, color, 1.0f);

    }

    @Override
    public void oneSecond(int ups, int fps) throws Exception {
        glfwSetWindowTitle(WINDOW, tittle + " || ups: " + ups + ", fps: " + fps);
    }

    @Override
    public void destroy() throws Exception {

    }


    @Override
    public void initGLFW() {
        // Configure our window
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        // Create the window
        WINDOW = glfwCreateWindow(width, height, tittle, NULL, NULL);
        if (WINDOW == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        // Get the resolution of the primary monitor
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        // Center our window
        glfwSetWindowPos(
                WINDOW,
                (vidmode.width() - width) / 2,
                (vidmode.height() - height) / 2
        );

        // Make the OpenGL context current
        glfwMakeContextCurrent(WINDOW);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(WINDOW);
    }


    public static void main(String[] args) {
        new DummyGame().start();
    }
}
