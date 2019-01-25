package com.housecurling.com.Entities;

import com.badlogic.ashley.core.Entity;
import com.housecurling.com.Enums.GameObject;

public class GameEntity extends Entity {

    private GameObject type;

    public GameObject getType() {
        return type;
    }

    public void setType(GameObject type) {
        this.type = type;
    }

    public GameEntity(GameObject type) {
        this.type = type;
    }
}
