package com.jcs;

import org.lwjgl.glfw.GLFWErrorCallback;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.opengl.GL11.*;

/**
 * Created by Jcs on 26/8/2016.
 */
public abstract class GameEngine {

    private boolean running = false;

    public long WINDOW = -1;


    public void loop() throws Exception {

        int ups, fps;
        ups = fps = 0;

        glfwSetTime(0);
        double lastTime = glfwGetTime();
        double lastTimer = glfwGetTime();

        while (running) {
            double currentTime = glfwGetTime();
            float deltaTime = (float) (currentTime - lastTime);
            lastTime = currentTime;

            glfwPollEvents(); //Processes all pending events.
            ups++;
            update(deltaTime);

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            render();
            fps++;
            glfwSwapBuffers(WINDOW); // swap the color buffers

            if (glfwGetTime() - lastTimer > 1) {
                oneSecond(ups, fps);
                ups = fps = 0;
                lastTimer += 1;
            }

            if (glfwWindowShouldClose(WINDOW)) {
                running = false;
            }
        }

        destroy();
    }


    public void run() {
        try {
            // Setup an error callback. The default implementation
            // will print the error message in System.err.
            GLFWErrorCallback.createPrint(System.err).set();

            // Initialize GLFW. Most GLFW functions will not work before doing this.
            if (!glfwInit())
                throw new IllegalStateException("Unable to initialize GLFW");

            initGLFW();
            init();
            config(WINDOW);

            createCapabilities();
            loop();

            // Free the window callbacks and destroy the window
            glfwFreeCallbacks(WINDOW);
            glfwDestroyWindow(WINDOW);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Terminate GLFW and free the error callback
            glfwTerminate();
            glfwSetErrorCallback(null).free();
        }
    }

    public void start() {
        running = true;
        run();
    }

    public abstract void initGLFW();

    public abstract void config(long win) throws Exception;

    public abstract void init() throws Exception;

    public abstract void update(float delta) throws Exception;

    public abstract void render() throws Exception;

    public abstract void oneSecond(int ups, int fps) throws Exception;

    public abstract void destroy() throws Exception;


}
