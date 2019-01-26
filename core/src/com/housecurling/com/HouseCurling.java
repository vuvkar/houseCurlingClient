package com.housecurling.com;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.housecurling.com.Network.CurlingNetwork;
import com.housecurling.com.Systems.InputSystem;
import com.housecurling.com.Systems.PhysicSystem;
import com.housecurling.com.Systems.ShapeRenderingSystem;

public class HouseCurling extends ApplicationAdapter { ;

	Engine engine;

	public ShapeRenderingSystem renderingSystem;
	public PhysicSystem physicSystem;
	public InputSystem inputSystem;

	@Override
	public void create () {
		engine = new Engine();
		physicSystem = new PhysicSystem(this);
		engine.addSystem(physicSystem);
		renderingSystem = new ShapeRenderingSystem(this);
		engine.addSystem(renderingSystem);
		inputSystem = new InputSystem(this);
		Gdx.input.setInputProcessor(inputSystem);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		engine.update(Gdx.graphics.getDeltaTime());
	}
	
	@Override
	public void dispose () {
		renderingSystem.dispose();
	}
}
