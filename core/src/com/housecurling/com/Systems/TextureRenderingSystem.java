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
        batch.draw(background, -camera.viewportWidth / 2, -camera.viewportHeight / 2, background.getWidth() / 2, background.getHeight() / 2, camera.viewportWidth, camera.viewportHeight, 1, 1, 0);
        batch.end();
    }

    public void drawHouses() {
        batch.begin();
        Array<Body> houses = houseCurling.physicSystem.houses;

        float scl2 = Constants.UI_SCALE * camera.viewportWidth /Constants.SCREEN_WIDTH;

        for(Body body: houses) {
            batch.draw(house, body.getPosition().x - Constants.HOUSE_SIZE / 2, body.getPosition().y - Constants.HOUSE_SIZE / 2, 0, 0, house.getWidth(), house.getHeight(), scl2, scl2, 0);
        }
        batch.end();
    }

}
