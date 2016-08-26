package com.jcs;

import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFWVidMode;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * Created by Jcs on 26/8/2016.
 */
public class DummyGame extends GameEngine {

    public String tittle = "Game Engine";
    int width = 640;
    int height = 480;

    Shader shader;

    Mesh mesh;

    @Override
    public void init() throws Exception {
        shader = new Shader("shaders/shader.vs", "shaders/shader.fs");

        float[] vertices = new float[]{
                -0.5f, 0.5f, 0.0f,
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f,
                0.5f, 0.5f, 0.0f,
        };

        int[] indices = new int[]{
                0, 1, 3, 3, 1, 2,
        };

        float[] colours = new float[]{
                0.5f, 0.0f, 0.0f,
                0.0f, 0.5f, 0.0f,
                0.0f, 0.0f, 0.5f,
                0.0f, 0.5f, 0.5f,
        };

        mesh = new Mesh(vertices, colours, indices);

        projection = new Matrix4f().orthoSymmetric(width, height, -1, 1).scale(32);

        shader.bind();
        float[] data = new float[16];
        glUniformMatrix4fv(shader.getUniformLocation("mProjection"), false, projection.get(data));
        shader.unbind();
    }

    @Override
    public void config(long win) throws Exception {
        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(win, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window, true); // We will detect this in our rendering loop
        });

        glfwSetWindowSizeCallback(win, ((window, width, height) -> {
            this.width = width;
            this.height = height;
            glViewport(0, 0, width, height);
        }));
    }

    @Override
    public void update(float delta) throws Exception {

    }

    Matrix4f projection;

    @Override
    public void render() throws Exception {

        shader.bind();

        // Bind to the VAO
        glBindVertexArray(mesh.getVaoId());
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        // Draw the vertices
        glDrawElements(GL_TRIANGLES, mesh.getVertexCount(), GL_UNSIGNED_INT, 0);

        // Restore state
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);

        shader.unbind();
    }

    @Override
    public void oneSecond(int ups, int fps) throws Exception {
        glfwSetWindowTitle(WINDOW, tittle + " || ups: " + ups + ", fps: " + fps);
    }

    @Override
    public void destroy() throws Exception {
        shader.cleanUp();
        mesh.cleanUp();
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
