package com.housecurling.com.Systems;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.housecurling.com.HouseCurling;

public class InputSystem extends InputAdapter {
    HouseCurling houseCurling;
    Vector2 initialPosition = new Vector2();
    Vector3 temp = new Vector3();

    Camera camera;

    boolean wasDragged = false;

    public InputSystem(HouseCurling houseCurling) {
        this.houseCurling = houseCurling;
        camera = houseCurling.renderingSystem.camera;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        temp.set(screenX, screenY, 0);
        camera.unproject(temp);
        initialPosition.set(temp.x, temp.y);
        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        wasDragged = true;
        return super.touchDragged(screenX, screenY, pointer);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(wasDragged) {
            temp.set(screenX, screenY, 0);
            camera.unproject(temp);

            houseCurling.physicSystem.wasDragged(new Vector2(initialPosition.x, initialPosition.y), new Vector2(temp.x, temp.y));
        }
        return super.touchUp(screenX, screenY, pointer, button);
    }
}
