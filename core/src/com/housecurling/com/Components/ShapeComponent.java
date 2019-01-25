package com.housecurling.com.Components;

import com.badlogic.ashley.core.Component;
import com.housecurling.com.Enums.GameShape;

public class ShapeComponent implements Component {
    GameShape gameShape;

    public GameShape getGameShape() {
        return gameShape;
    }

    public void setGameShape(GameShape gameShape) {
        this.gameShape = gameShape;
    }

    public ShapeComponent(GameShape gameShape) {
        this.gameShape = gameShape;
    }
}
