package com.ascender.game;

import com.ascender.game.helpers.AssetLoader;
import com.ascender.game.helpers.Settings;
import com.ascender.game.screens.MainMenuScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Ascender extends Game {

	public SpriteBatch batch;

	@Override
	public void create () {
		Gdx.app.log("Ascender", "created");
		batch = new SpriteBatch();
		AssetLoader.load();
		Settings.load();
		setScreen(new MainMenuScreen(this));
	}

	@Override
	public void dispose() {
		AssetLoader.dispose();
	}

}
