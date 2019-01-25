package com.housecurling.com;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.housecurling.com.Systems.PhysicSystem;
import com.housecurling.com.Systems.ShapeRenderingSystem;

public class HouseCurling extends ApplicationAdapter { ;

	Engine engine;

	ShapeRenderingSystem renderingSystem;
	PhysicSystem physicSystem;

	@Override
	public void create () {
		engine = new Engine();
		physicSystem = new PhysicSystem(this);
		engine.addSystem(physicSystem);
		renderingSystem = new ShapeRenderingSystem(this);
		engine.addSystem(renderingSystem);
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
