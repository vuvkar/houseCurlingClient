package com.housecurling.com.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
    ShapeRenderer shapeRenderer;
    Rectangle rectangle;

    BotsSystem botsSystem;

    Body userHouse;

    Array<Body> houses;
    float radius = Constants.CIRCLE_INITIAL_RADIUS;

    int countStep = 0;

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        createWorld();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        world.step(1/60f, 10, 5);
        checkForPlaces();
        float circleDelta = deltaTime / Constants.GAME_TIMEOUT;
        radius -= circleDelta;
        if ( radius < 0)
        {
            radius = 0 ;
        }
        float temp = Constants.CIRCLE_INITIAL_RADIUS / 3;

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl20.glLineWidth(5);

        debugRenderer.render(world, houseCurling.camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setProjectionMatrix(houseCurling.camera.combined);
            if( radius < 3 * temp && radius > 2 * temp )
        {
            shapeRenderer.setColor(0.7f, 0.9f, 0, 0.8f);
        }
        else
        {
            if ( radius > temp && radius < 2 * temp )
            {
                shapeRenderer.setColor(0.7f, 0.5f, 0, 0.8f);
            }
            else
            {
                shapeRenderer.setColor(0.7f, 0.2f, 0, 0.8f);
            }
        }
        shapeRenderer.circle(0, 0, radius, 100);

        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1f, 0.9f, 0f, 0.2f);
        shapeRenderer.circle(0, 0, radius, 100);
        shapeRenderer.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);
        if(countStep % Constants.BOT_SYSTEM_FRAMESTEP == 0) {
           botsSystem.botsAction();
    }
        countStep++;
        countStep %= Constants.BOT_SYSTEM_FRAMESTEP;
    }

    private void checkForPlaces() {
        for(Body house: houses) {
            Vector2 position = house.getWorldCenter();
            if(Math.pow(position.x, 2) + Math.pow(position.y, 2) > Math.pow(radius, 2)) {
                if ( house == userHouse )
                {
                    gameLost();
                }
                else
                {
                    deleteHouse(house);
                }
            }
            if ( houses.size == 1)
            {
                gameWin();
            }
        }
    }

    private void gameLost() {

    }


    private void gameWin() {

    }
    private void deleteHouse(Body house) {
        world.destroyBody(house);
        botsSystem.removeBot(house);
        this.houses.removeValue(house, true);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
    }

    public void wasDragged(Vector2 initialPosition, Vector2 finalPosition) {
        Vector2 vector2 = new Vector2(initialPosition);
        if(doesCollide(vector2, userHouse)) {
            initialPosition.sub(finalPosition);
            applyImpulse(userHouse, initialPosition);
        }
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
            float randx = MathUtils.random(-3 * HOUSE_SIZE, 3 * HOUSE_SIZE);
            float randy = MathUtils.random(-3 * HOUSE_SIZE, 3 * HOUSE_SIZE);
            for ( int j = 0; j < i; j++ )
            {
                if ( randx == houses.get(j).getPosition().x && randy == houses.get(j).getPosition().y )
                {
                    randx = MathUtils.random(-3 * HOUSE_SIZE, 3 * HOUSE_SIZE);
                    randy = MathUtils.random(-3 * HOUSE_SIZE, 3 * HOUSE_SIZE);
                    j--;
                }
            }
            bodyDef.position.set( randx , randy );

            Body body = world.createBody(bodyDef);

            PolygonShape polygonShape = new PolygonShape();
            polygonShape.setAsBox(HOUSE_SIZE / 2, HOUSE_SIZE / 2);

            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = polygonShape;
            fixtureDef.density = 5f;
            fixtureDef.restitution = 1f;

            body.createFixture(fixtureDef);
            body.setLinearDamping(.6f);

            polygonShape.dispose();

            botsSystem.addBot(body);
            this.houses.add(body);
        }
        userHouse = houses.get(0);
    }

    public void applyImpulse(Body body, Vector2 dir) {
        if ( dir.dst( body.getWorldCenter() ) > Constants.MAX_VECTOR_LENGTH )
        {
            dir.scl(Constants.MAX_VECTOR_LENGTH / dir.len());
            body.applyLinearImpulse( dir, body.getWorldCenter(), true);
        }
        else
        {
            body.applyLinearImpulse( dir, body.getWorldCenter(), true);
        }
    }

    public PhysicSystem(HouseCurling houseCurling){
        super(Family.all(PositionComponent.class, VelocityComponent.class).get());
        this.houseCurling = houseCurling;
        this.houses = new Array<Body>();
    }

    private void createWorld() {
        world = new World(new Vector2(0, 0), false);
        debugRenderer = new Box2DDebugRenderer();
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
        rectangle = new Rectangle();
        botsSystem = new BotsSystem(this);
        //createCircle(100, Constants.CIRCLE_INITIAL_RADIUS, new Vector2());
        createHouses();
    }


}
