package com.ascender.game.screens;

import com.ascender.game.Ascender;
import com.ascender.game.helpers.AssetLoader;
import com.ascender.game.helpers.Constants;
import com.ascender.game.helpers.Settings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class HighScoresScreen extends ScreenAdapter {
    Ascender game;
    OrthographicCamera camera;
    Rectangle backButton;
    Vector3 touchPoint;
    String[] highScores;
    float xOffset = 50;
    private ShapeRenderer renderer;

    public HighScoresScreen (Ascender game) {
        this.game = game;
        renderer = new ShapeRenderer();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.APP_WIDTH, Constants.APP_HEIGHT);
        camera.position.set(320 / 2, 480 / 2, 0);
        backButton = new Rectangle(30, 24, 85, 40);
        touchPoint = new Vector3();
        highScores = new String[5];
        for (int i = 0; i < 5; i++) {
            highScores[i] = i + 1 + ". " + Settings.highscores[i];
        }
    }
    public void update () {
        if (Gdx.input.justTouched()) {
            camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (backButton.contains(touchPoint.x, touchPoint.y)) {
                game.setScreen(new MainMenuScreen(game));
                return;
            }
        }
    }

    public void draw() {
        GL20 gl = Gdx.gl;
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(AssetLoader.background, 0, 0, Constants.APP_WIDTH,
                Constants.APP_HEIGHT);
        AssetLoader.shadow.draw(game.batch, "HIGHSCORES", (Constants.APP_WIDTH / 2f) - (100), 400);
        AssetLoader.font.draw(game.batch, "HIGHSCORES", (Constants.APP_WIDTH / 2f) - (100 - 1), 399);
        AssetLoader.shadow.draw(game.batch, "BACK", 30, 60);
        AssetLoader.font.draw(game.batch, "BACK", 30, 60);

        float y = 230;
        for (int i = 4; i >= 0; i--) {
            AssetLoader.font.draw(game.batch, highScores[i], xOffset, y);
            y += AssetLoader.font.getLineHeight();
        }
        game.batch.end();
//        renderer.begin(ShapeRenderer.ShapeType.Line);
//        renderer.setColor(Color.RED);
//        renderer.rect(30, 24, 85, 40);
        renderer.end();
    }

    @Override
    public void render (float delta) {
        update();
        draw();
    }
}
