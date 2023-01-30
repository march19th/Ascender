package com.ascender.game.screens;

import com.ascender.game.Ascender;
import com.ascender.game.helpers.Constants;
import com.ascender.game.helpers.InputHandler;
import com.ascender.game.world.GameRenderer;
import com.ascender.game.world.GameWorld;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.sun.org.apache.bcel.internal.Const;

public class GameScreen implements Screen {
    Ascender game;
    private GameWorld world;
    private GameRenderer renderer;
    private float runTime;

    public GameScreen(Ascender game) {
        float screenWidth = Constants.APP_WIDTH;
        float screenHeight = Constants.APP_HEIGHT;
        float gameWidth = Constants.APP_WIDTH;
        float gameHeight = screenHeight / (screenWidth / gameWidth);

        int midPointY = (int) (gameHeight / 2);

        world = new GameWorld(); //initializes world
        renderer = new GameRenderer(world, (int) gameHeight, midPointY); //initializes renderer
        Gdx.input.setInputProcessor(new InputHandler(game, world.getProtag(), world));

        Gdx.app.log("GameScreen", "Attached");

    }
    @Override
    public void show() {
        Gdx.app.log("GameScreen", "show called");
    }

    @Override
    public void render(float delta) {
        runTime += delta;
        world.update(delta);
        renderer.render(runTime);

    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.log("GameScreen", "resizing");
    }

    @Override
    public void pause() {
        Gdx.app.log("GameScreen", "pause called");
    }

    @Override
    public void resume() {
        Gdx.app.log("GameScreen", "resume called");
    }

    @Override
    public void hide() {
        Gdx.app.log("GameScreen", "hide called");
    }

    @Override
    public void dispose() {

    }
}
