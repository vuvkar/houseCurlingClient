package com.housecurling.com.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.housecurling.com.Components.*;
import com.housecurling.com.Constants;
import com.housecurling.com.Entities.GameEntity;
import com.housecurling.com.Enums.GameObject;
import com.housecurling.com.Enums.GameShape;
import com.housecurling.com.HouseCurling;

public class PhysicSystem extends IteratingSystem {
    private final HouseCurling houseCurling;
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);
    private ComponentMapper<SizeComponent> sm = ComponentMapper.getFor(SizeComponent.class);

    Array<Entity> houses;

    private Entity circle;

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        createWorld();
        ImmutableArray<Entity> circleEntities = engine.getEntitiesFor(Family.all(RadiusComponent.class).get());

        if(circleEntities.size() != 1) {
            throw new GdxRuntimeException("Current circles can't be more or less then one");
        }

        circle = circleEntities.first();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        if(entity instanceof GameEntity) {
            GameObject type = ((GameEntity) entity).getType();
            if(type.equals(GameObject.HOUSE)) {
                actHouse((GameEntity) entity);
            }
        }
    }

    public void createCircle() {
        GameEntity circle = new GameEntity(GameObject.CIRCLE);
        float radius = 400;
        circle.add(new PositionComponent(Constants.SCREEN_WIDTH / 2f, Constants.SCREEN_HEIGHT / 2f));
        circle.add(new ShapeComponent(GameShape.CIRCLE));
        circle.add(new RadiusComponent(radius));
        getEngine().addEntity(circle);

        this.circle = circle;
    }

    private void createHouses() {
        for(int i = 0; i < Constants.HOUSE_COUNT; i++) {
            GameEntity house = new GameEntity(GameObject.HOUSE);
            house.add(new SizeComponent(50, 50));
            PositionComponent positionComponent = pm.get(circle);
            house.add(new PositionComponent(MathUtils.random(positionComponent.getX() - 300, positionComponent.getX() + 300), MathUtils.random(positionComponent.getY() - 300, positionComponent.getY() + 300)));
            house.add(new ShapeComponent(GameShape.SQUARE));
            getEngine().addEntity(house);

            this.houses.add(house);
        }
    }

    private void actHouse(GameEntity house) {
        //ToDo:: do calculations
    }

    public PhysicSystem(HouseCurling houseCurling){
        super(Family.all(PositionComponent.class, VelocityComponent.class).get());
        this.houseCurling = houseCurling;
        this.houses = new Array<Entity>();
    }

    private void createWorld() {
        createCircle();
        createHouses();
    }


}
