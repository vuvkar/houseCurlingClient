package com.housecurling.com.Systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.housecurling.com.Components.*;
import com.housecurling.com.Constants;
import com.housecurling.com.Entities.GameEntity;
import com.housecurling.com.Enums.GameObject;
import com.housecurling.com.HouseCurling;

public class ShapeRenderingSystem extends IteratingSystem {
    HouseCurling houseCurling;
    ShapeRenderer shapeRenderer;
    SpriteBatch batch;
    Camera camera;

    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<SizeComponent> sm = ComponentMapper.getFor(SizeComponent.class);
    private ComponentMapper<ShapeComponent> shm = ComponentMapper.getFor(ShapeComponent.class);
    private ComponentMapper<RadiusComponent> rm = ComponentMapper.getFor(RadiusComponent.class);

    public ShapeRenderingSystem(HouseCurling houseCurling) {
        super(Family.all(PositionComponent.class, ShapeComponent.class).get());
        this.houseCurling = houseCurling;
        this.batch = new SpriteBatch();
        float aspect = Gdx.graphics.getWidth() / Gdx.graphics.getHeight();

        this.camera = new OrthographicCamera(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT * aspect);
        this.shapeRenderer = new ShapeRenderer();

        camera.position.set(0,0,0);
    }

    @Override
    public void update(float deltaTime) {
        camera.update();
       // shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        super.update(deltaTime);
       // shapeRenderer.end();
    }

    public void dispose() {

    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
//        if(entity instanceof GameEntity) {
//            //if(((GameEntity) entity).getType().equals(GameObject.HOUSE)) {
//                ShapeComponent shapeComponent = shm.get(entity);
//                PositionComponent positionComponent = pm.get(entity);
//
//                switch (shapeComponent.getGameShape()) {
//                    case CIRCLE:
//                        RadiusComponent radiusComponent = rm.get(entity);
//                        shapeRenderer.circle(positionComponent.getX(), positionComponent.getY(), radiusComponent.getRadius(), 100);
//                        break;
//                    case SQUARE:
//                        SizeComponent sizeComponent = sm.get(entity);
//                        shapeRenderer.rect(positionComponent.getX(), positionComponent.getY(), sizeComponent.getWidth(), sizeComponent.getHeight());
//                        break;
//               // }
//            }
//        }
    }
}
