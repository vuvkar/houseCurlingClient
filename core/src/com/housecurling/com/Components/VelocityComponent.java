package com.housecurling.com.Components;

import com.badlogic.ashley.core.Component;

public class VelocityComponent implements Component {
    private float x;
    private float y;

    public void setFrom(VelocityComponent velocityComponent) {
        this.x = velocityComponent.x;
        this.y = velocityComponent.y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public VelocityComponent(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
