package com.housecurling.com.Systems;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.housecurling.com.Constants;
import com.housecurling.com.HouseCurling;

public class TextureRenderingSystem {
    HouseCurling houseCurling;
    SpriteBatch batch;
    Camera camera;

    Sprite background;
    Sprite[] houseSprites;
    //ArrowWidget arrowWidget;

    boolean isVisible = true;

    public TextureRenderingSystem(HouseCurling houseCurling) {
        this.houseCurling = houseCurling;
        this.batch = houseCurling.batch;
        this.camera = houseCurling.camera;
       // this.arrowWidget = new ArrowWidget();

        background = new Sprite(new Texture("backgrounds/background.png"));
        houseSprites = new Sprite[Constants.HOUSE_COUNT];
        for(int i = 0; i < Constants.HOUSE_COUNT; i++) {
            Sprite house = new Sprite(new Texture("rawAssets/" + i + ".png"));
            houseSprites[i] = house;
        }
    }

    public void drawBackground() {
        batch.draw(background, -camera.viewportWidth / 2, -camera.viewportHeight / 2, background.getWidth() / 2, background.getHeight() / 2, camera.viewportWidth, camera.viewportHeight, 1, 1, 0);
    }

    public void drawHouses() {
        Array<Body> houses = houseCurling.physicSystem.houses;

        int i = 0;
        for(Body body: houses) {
            Sprite house = houseSprites[i];
            house.setOrigin(0,0);
            house.setCenter(house.getWidth()/2, house.getHeight()/2);
            Vector2 worldPoint = body.getWorldPoint(new Vector2(- Constants.HOUSE_SIZE / 2, - Constants.HOUSE_SIZE / 2));
            house.setPosition(worldPoint.x, worldPoint.y);
            house.setRotation((float)Math.toDegrees(body.getAngle()));
            house.setScale(Constants.IN_GAME_SCALE);
            house.draw(batch);
            i++;
        }
    }

    public void drawArrow() {
//        if(isVisible) {
//            arrowWidget.render(batch, houseCurling.physicSystem.userHouse);
//        }
    }

}
