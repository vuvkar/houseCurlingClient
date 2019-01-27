package com.housecurling.com.Systems;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.housecurling.com.HouseCurling;
import com.housecurling.com.Widgets.ArrowWidget;

public class InGameUI {
    private HouseCurling houseCurling;
    Stage stage;
    ArrowWidget arrowWidget;

    public InGameUI(HouseCurling houseCurling) {
        this.houseCurling = houseCurling;
        stage = new Stage();
        //arrowWidget = new ArrowWidget(houseCurling.physicSystem.userHouse);

       // stage.addActor(arrowWidget);
    }

    public void act() {
        stage.act();
    }

    public void draw() {
        stage.draw();
    }
}
