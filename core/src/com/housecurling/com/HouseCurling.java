package com.housecurling.com;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.housecurling.com.Systems.RenderingSystem;

public class HouseCurling extends ApplicationAdapter { ;

	RenderingSystem renderingSystem;

	@Override
	public void create () {
		renderingSystem = new RenderingSystem(this);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		renderingSystem.act();
		renderingSystem.draw();

	}
	
	@Override
	public void dispose () {
		renderingSystem.dispose();
	}
}
