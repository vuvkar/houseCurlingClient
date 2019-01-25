package com.housecurling.com.Components;

import com.badlogic.ashley.core.Component;

public class RadiusComponent implements Component {
    private float radius;

    public RadiusComponent(float radius) {
        this.radius = radius;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }
}
