package com.housecurling.com.Systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.housecurling.com.HouseCurling;

public class RenderingSystem extends IteratingSystem {
    HouseCurling houseCurling;
    SpriteBatch batch;
    Camera camera;

    PhysicSystem physicSystem;

    public RenderingSystem(HouseCurling houseCurling) {
        super(Family.all().get());
        this.houseCurling = houseCurling;
        this.batch = new SpriteBatch();
        this.camera = new PerspectiveCamera();

    }

    public void act() {
        float delta = Gdx.graphics.getDeltaTime();
        physicSystem.act(delta);
    }

    public void draw() {
        physicSystem.drawDebug(batch);
    }

    public void dispose() {

    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

    }
}
