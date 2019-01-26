package com.housecurling.com.Systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.housecurling.com.Constants;
import com.housecurling.com.HouseCurling;

import java.awt.*;

public class TextureRenderingSystem {
    HouseCurling houseCurling;
    SpriteBatch batch;
    Camera camera;

    Sprite background;
    Sprite house;

    public TextureRenderingSystem(HouseCurling houseCurling) {
        this.houseCurling = houseCurling;
        this.batch = houseCurling.batch;
        this.camera = houseCurling.camera;

        background = new Sprite(new Texture("backgrounds/background.png"));
        house = new Sprite(new Texture("rawAssets/house.png"));
    }

    public void drawBackground() {
        batch.begin();
        batch.draw(background, 0, 0, background.getWidth() / 2, background.getHeight() / 2,  Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), Constants.UI_SCALE, Constants.UI_SCALE,0);
        batch.end();
    }

    public void drawHouses() {
        batch.begin();
        Array<Body> houses = houseCurling.physicSystem.houses;

        for(Body body: houses) {
            batch.draw(house, body.getPosition().x, body.getPosition().y);
        }
        batch.end();
    }

}
