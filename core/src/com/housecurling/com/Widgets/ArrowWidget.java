package com.housecurling.com.Widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.housecurling.com.Constants;

import java.util.concurrent.CopyOnWriteArrayList;

public class ArrowWidget extends Image {
    public Vector2 target;
    public Body body;

    public ArrowWidget(Body body) {
        super(new Texture("rawAssets/arrow.png"));
        target = new Vector2();
        this.body = body;
       // setScale(Constants.IN_GAME_SCALE);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        this.setPosition(body.getPosition().x * Gdx.graphics.getWidth() / Constants.WORLD_WIDTH, body.getPosition().y / Constants.IN_GAME_SCALE + getStage().getCamera().viewportHeight / 2f - this.getHeight() / 2);
       // setOrigin(0, getHeight() / 2);
       // setRotation(target.angle(body.getPosition()));

    }
}
