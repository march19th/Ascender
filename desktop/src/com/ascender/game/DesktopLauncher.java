package com.ascender.game;

import com.ascender.game.helpers.Constants;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("Ascender");
		config.useVsync(true);
		config.setWindowedMode(Constants.APP_WIDTH, Constants.APP_HEIGHT);
		new Lwjgl3Application(new Ascender(), config);
	}
}