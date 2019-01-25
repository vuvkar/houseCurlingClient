package com.housecurling.com.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.housecurling.com.Components.*;
import com.housecurling.com.Entities.GameEntity;
import com.housecurling.com.Enums.GameObject;

public class PhysicSystem extends IteratingSystem {
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);
    private ComponentMapper<SizeComponent> sm = ComponentMapper.getFor(SizeComponent.class);

    private Entity circle;

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
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

    private void actHouse(GameEntity house) {
        //ToDo:: do calculations
    }

    PhysicSystem(){
        super(Family.all(PositionComponent.class, VelocityComponent.class).get());
    }
}
