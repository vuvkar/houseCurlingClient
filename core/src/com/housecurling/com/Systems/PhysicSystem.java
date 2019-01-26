package com.housecurling.com.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.housecurling.com.Components.*;
import com.housecurling.com.Constants;
import com.housecurling.com.Entities.GameEntity;
import com.housecurling.com.Enums.GameObject;
import com.housecurling.com.Enums.GameShape;
import com.housecurling.com.HouseCurling;
import java.lang.*;

public class PhysicSystem extends IteratingSystem {
    private final HouseCurling houseCurling;
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);
    private ComponentMapper<SizeComponent> sm = ComponentMapper.getFor(SizeComponent.class);

    World world;
    Box2DDebugRenderer debugRenderer;

    Array<Entity> houses;

    //private Entity circle;

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        createWorld();
        ImmutableArray<Entity> circleEntities = engine.getEntitiesFor(Family.all(RadiusComponent.class).get());

//        if(circleEntities.size() != 1) {
//            throw new GdxRuntimeException("Current circles can't be more or less then one");
//        }

//        circle = circleEntities.first();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        world.step(1/60f, 6, 2);
        debugRenderer.render(world, houseCurling.renderingSystem.camera.combined);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
//        if(entity instanceof GameEntity) {
//            GameObject type = ((GameEntity) entity).getType();
//            if(type.equals(GameObject.HOUSE)) {
//                actHouse((GameEntity) entity);
//            }
//        }
    }

    public void createCircle(int lineNumber) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0, 0);

        Body body = world.createBody(bodyDef);

       // PolygonRegion =

        PolygonShape polygonShape = new PolygonShape();
        

        polygonShape.set( createCirclePoints( lineNumber , 50 ) );
        polygonShape.setRadius(50);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f; // Make it bounce a little bit

        Fixture fixture = body.createFixture(fixtureDef);

        polygonShape.dispose();
    }

    private Vector2[] createCirclePoints( int n , int r ) {
        Vector2[] vertexes = new Vector2[n];
        double coef = 1;
        for ( int i=0; i < n ; i++ )
        {
            double radian = coef * Math.PI * 2;
            double x = r + Math.cos(radian) * r;
            double y = r + Math.sin(radian) * r;
            vertexes[i] = new Vector2( x , y );
            coef -= 1/n;
        }
        return vertexes;
    }

    private void createHouses() {
        for(int i = 0; i < Constants.HOUSE_COUNT; i++) {
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.DynamicBody;
            bodyDef.position.set(MathUtils.random(-300, 300), MathUtils.random(- 300, 300));

            Body body = world.createBody(bodyDef);

            PolygonShape polygonShape = new PolygonShape();
            polygonShape.setAsBox(25, 25);

            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = polygonShape;
            fixtureDef.density = 0.5f;
            fixtureDef.friction = 0.4f;
            fixtureDef.restitution = 0.6f; // Make it bounce a little bit

            Fixture fixture = body.createFixture(fixtureDef);

            polygonShape.dispose();

           // this.houses.add(house);
        }
    }

    public void wasDragged(Fixture body, VelocityComponent velocityComponent, PositionComponent positionComponent) {
    }

    public PhysicSystem(HouseCurling houseCurling){
        super(Family.all(PositionComponent.class, VelocityComponent.class).get());
        this.houseCurling = houseCurling;
        this.houses = new Array<Entity>();
    }

    private void createWorld() {
        world = new World(new Vector2(0, 0), true);
        debugRenderer = new Box2DDebugRenderer();
        //createCircle(10);
        createHouses();
    }


}
