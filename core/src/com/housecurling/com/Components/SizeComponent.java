package com.housecurling.com.Components;

import com.badlogic.ashley.core.Component;

public class SizeComponent implements Component {
    private float width;
    private float height;

    public void setFrom(SizeComponent size) {
        this.width = size.width;
        this.height = size.height;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public SizeComponent(float width, float height) {
        this.width = width;
        this.height = height;
    }

}
