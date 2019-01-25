package com.housecurling.com.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.housecurling.com.Constants;
import com.housecurling.com.HouseCurling;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = Constants.SCREEN_HEIGHT;
		config.width = Constants.SCREEN_WIDTH;

		new LwjglApplication(new HouseCurling(), config);
	}
}
