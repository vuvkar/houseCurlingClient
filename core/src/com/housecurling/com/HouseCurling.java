package com.housecurling.com;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.housecurling.com.Network.CurlingNetwork;
import com.housecurling.com.Systems.InputSystem;
import com.housecurling.com.Systems.PhysicSystem;
import com.housecurling.com.Systems.ShapeRenderingSystem;
import com.housecurling.com.Systems.TextureRenderingSystem;
import com.housecurling.com.Ui.*;

public class HouseCurling extends ApplicationAdapter { ;

	Engine engine;

	public ShapeRenderingSystem renderingSystem;
	public PhysicSystem physicSystem;
	public InputSystem inputSystem;
	public TextureRenderingSystem textureRenderingSystem;

	public SpriteBatch batch;
	public Camera camera;
	
	public GameOver over;
	public MainMenu menu;

	@Override
	public void create () {
		float aspect = (float) Gdx.graphics.getHeight() / Gdx.graphics.getWidth();
		this.camera = new OrthographicCamera(Constants.WORLD_WIDTH, Constants.WORLD_WIDTH * aspect);
		this.batch = new SpriteBatch();
		batch.setProjectionMatrix(camera.combined);
		engine = new Engine();
		physicSystem = new PhysicSystem(this);
		this.menu = new MainMenu(this);
		this.over = new GameOver(this);
		engine.addSystem(physicSystem);
		renderingSystem = new ShapeRenderingSystem(this);
		engine.addSystem(renderingSystem);
		inputSystem = new InputSystem(this);
		textureRenderingSystem = new TextureRenderingSystem(this);
		Gdx.input.setInputProcessor(inputSystem);

		over.show();
		menu.show();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		textureRenderingSystem.drawBackground();
		switch(physicSystem.state){
			case 0:
				menu.render(Gdx.graphics.getDeltaTime());
				break;
			case 1:
				engine.update(Gdx.graphics.getDeltaTime());
				textureRenderingSystem.drawHouses();
				break;
			case 2:
			case 3:
				over.render(Gdx.graphics.getDeltaTime() );
				break;
		}

		
	}
	
	@Override
	public void dispose () {
		renderingSystem.dispose();
	}
}
