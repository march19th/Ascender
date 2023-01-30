package com.ascender.game.screens;

import com.ascender.game.Ascender;
import com.ascender.game.helpers.AssetLoader;
import com.ascender.game.helpers.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class MainMenuScreen extends ScreenAdapter {
    Ascender game;
    OrthographicCamera camera;
    Rectangle playButton;
    Rectangle helpButton;
    Rectangle highScoresButton;
    Vector3 touchPoint;
    Rectangle soundButton;
    private ShapeRenderer renderer;

    public MainMenuScreen (Ascender game) {
        this.game = game;
        renderer = new ShapeRenderer();


        camera = new OrthographicCamera(Constants.APP_WIDTH, Constants.APP_HEIGHT);
        camera.setToOrtho(false, Constants.APP_WIDTH, Constants.APP_HEIGHT);

        //camera.position.set(320 / 2, 480 / 2, 0);
        //camera.position.set(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 0);
        game.batch.setProjectionMatrix(camera.combined);
        renderer.setProjectionMatrix(camera.combined);

        soundButton = new Rectangle(0, 0, 64, 64);
        playButton = new Rectangle((Constants.APP_WIDTH / 2f) - (50), 170, 80, 36);
        highScoresButton = new Rectangle((Constants.APP_WIDTH / 2f) - (100), 117, 200, 36);
        helpButton = new Rectangle(160 - 150, 200 - 18 - 36, 300, 36);
        touchPoint = new Vector3();
    }

    public void update () {

        if (Gdx.input.justTouched()) {
            camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (playButton.contains(touchPoint.x, touchPoint.y)) {
                //Assets.playSound(Assets.clickSound);
                game.setScreen(new GameScreen(game));
                return;
            }
            if (highScoresButton.contains(touchPoint.x, touchPoint.y)) {
                //Assets.playSound(Assets.clickSound);
                game.setScreen(new HighScoresScreen(game));
                return;
            }
            if (helpButton.contains(touchPoint.x, touchPoint.y)) {
//                Assets.playSound(Assets.clickSound);
//                game.setScreen(new HelpScreen(game));
                return;
            }
            if (soundButton.contains(touchPoint.x, touchPoint.y)) {
//                Assets.playSound(Assets.clickSound);
//                Settings.soundEnabled = !Settings.soundEnabled;
//                if (Settings.soundEnabled)
//                    Assets.music.play();
//                else
//                    Assets.music.pause();
            }
        }
        game.batch.begin();
        game.batch.draw(AssetLoader.background, 0, 0, Constants.APP_WIDTH,
                Constants.APP_HEIGHT);
        //drawing title
//        AssetLoader.shadow.draw(game.batch, "ASCENDER", (Constants.APP_WIDTH / 2f) - (300), 400);
//        AssetLoader.font.draw(game.batch, "ASCENDER", (Constants.APP_WIDTH / 2f)
//                - (300 - 1), 399);
        AssetLoader.shadow.draw(game.batch, "ASCENDER", (Constants.APP_WIDTH / 2f) - (300), 400);
        AssetLoader.font.draw(game.batch, "ASCENDER", (Constants.APP_WIDTH / 2f)
                - (300 - 1), 399);
        //draw play button
        AssetLoader.shadow.draw(game.batch, "PLAY", (Constants.APP_WIDTH / 2f) - (50), 200);
        AssetLoader.font.draw(game.batch, "PLAY", (Constants.APP_WIDTH / 2f)
                - (50 - 1), 199);
        //draw highscores button
        AssetLoader.shadow.draw(game.batch, "HIGHSCORES", (Constants.APP_WIDTH / 2f) - (100), 150);
        AssetLoader.font.draw(game.batch, "HIGHSCORES", (Constants.APP_WIDTH / 2f)
                - (100 - 1), 150-1);
        //draw help button
//        AssetLoader.shadow.draw(game.batch, "HELP", (Constants.APP_WIDTH / 2f) - (50), 100);
//        AssetLoader.font.draw(game.batch, "HELP", (Constants.APP_WIDTH / 2f)
//                - (50 - 1), 100-1);
        AssetLoader.font.getData().setScale(.5f,.5f);
        AssetLoader.shadow.getData().setScale(.5f,.5f);
        game.batch.end();
    }
    public void draw () {

        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.RED);
        renderer.rect((Constants.APP_WIDTH / 2f) - (50), 170, 80, 36);
        //highscores button
        renderer.rect((Constants.APP_WIDTH / 2f) - (100), 117, 200, 36);
        renderer.end();
    }

    @Override
    public void render (float delta) {
        update();
        //draw();
    }

    @Override
    public void pause () {
        //Settings.save();
    }
}
