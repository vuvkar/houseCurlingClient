package com.housecurling.com.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.housecurling.com.Components.*;
import com.housecurling.com.Constants;
import com.housecurling.com.HouseCurling;
import java.lang.*;

import static com.housecurling.com.Constants.HOUSE_SIZE;

public class PhysicSystem extends IteratingSystem {
    private final HouseCurling houseCurling;
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);
    private ComponentMapper<SizeComponent> sm = ComponentMapper.getFor(SizeComponent.class);

    World world;
    Box2DDebugRenderer debugRenderer;
    Rectangle rectangle;

    BotsSystem botsSystem;

    Array<Body> houses;

    int countStep = 0;

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
        world.step(1/60f, 10, 5);
        debugRenderer.render(world, houseCurling.renderingSystem.camera.combined);
        if(countStep < Constants.BOT_SYSTEM_FRAMESTEP) {
            botsSystem.botsAction();
        } else {
            countStep = 0;
        }
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

    public void wasDragged(Vector2 initialPosition, Vector2 finalPosition) {
        Vector2 vector2 = new Vector2(initialPosition);
        for(Body body: houses) {
            System.out.println("body position " + body.getPosition() + "  :::  " + initialPosition);
            if(doesCollide(vector2, body)) {
                initialPosition.sub(finalPosition);
                applyImpulse(body, initialPosition);
                break;
            }
        }
        System.out.println("   :::::::::::     ");
    }

    private boolean doesCollide(Vector2 initialPosition, Body body) {
        rectangle.set(body.getPosition().x, body.getPosition().y, HOUSE_SIZE, HOUSE_SIZE);
        rectangle.setCenter(initialPosition);
        return rectangle.contains(initialPosition);
    }

    public void createCircle(int lineNumber, float radius, Vector2 centerCoordinate) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(-radius, -radius);

        Body body = world.createBody(bodyDef);

        ChainShape polygonShape = new ChainShape();

        polygonShape.createLoop( createCirclePoints( lineNumber , radius, centerCoordinate) );

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f; // Make it bounce a little bit

        Fixture fixture = body.createFixture(fixtureDef);

        polygonShape.dispose();
    }

    private Vector2[] createCirclePoints( int n , float r , Vector2 centerCoordinate) {
        Vector2[] vertexes = new Vector2[n];
        double coef = 1;
        for ( int i=0; i < n ; i++ )
        {
            double radian = coef * Math.PI * 2;
            double x = r + Math.cos(radian) * r;
            double y = r + Math.sin(radian) * r;
            vertexes[i] = new Vector2((float)x ,(float)y);
            coef -= 1f/n;
        }
        return vertexes;
    }

    private void createHouses() {
        for(int i = 0; i < Constants.HOUSE_COUNT; i++) {
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.DynamicBody;
            bodyDef.position.set(MathUtils.random(-3 * HOUSE_SIZE, 3 * HOUSE_SIZE), MathUtils.random(-3 * HOUSE_SIZE, 3 * HOUSE_SIZE));

            Body body = world.createBody(bodyDef);

            PolygonShape polygonShape = new PolygonShape();
            polygonShape.setAsBox(HOUSE_SIZE / 2, HOUSE_SIZE / 2);

            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = polygonShape;
            fixtureDef.density = 0.0f;
            fixtureDef.friction = 0.0f;
            fixtureDef.restitution = 0.0f;

            body.createFixture(fixtureDef);
            body.setLinearDamping(1);
            body.setFixedRotation(true);
            body.setGravityScale(0);
            body.setBullet(true);

            polygonShape.dispose();

            botsSystem.addBot(body);
            this.houses.add(body);
        }
    }

    public void applyImpulse(Body body, Vector2 dir) {
        body.applyLinearImpulse(dir, body.getWorldCenter(), true);
    }

    public PhysicSystem(HouseCurling houseCurling){
        super(Family.all(PositionComponent.class, VelocityComponent.class).get());
        this.houseCurling = houseCurling;
        this.houses = new Array<Body>();
    }

    private void createWorld() {
        world = new World(new Vector2(0, 0), false);
        debugRenderer = new Box2DDebugRenderer();
        rectangle = new Rectangle();
        botsSystem = new BotsSystem(this);
        createCircle(100, Constants.CIRCLE_INITIAL_RADIUS, new Vector2());
        createHouses();
    }


}
