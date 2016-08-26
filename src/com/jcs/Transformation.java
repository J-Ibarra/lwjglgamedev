package com.jcs;

import org.joml.Matrix4f;

/**
 * Created by Jcs on 26/8/2016.
 */
public class Transformation {

    Matrix4f projection;
    Matrix4f view;
    Matrix4f model;

    public Transformation() {
        projection = new Matrix4f();
        view = new Matrix4f();
        model = new Matrix4f();
    }

    public Matrix4f getProjection() {
        return projection;
    }

    public Matrix4f getView() {
        return view;
    }

    public Matrix4f getModel() {
        return model;
    }
}
