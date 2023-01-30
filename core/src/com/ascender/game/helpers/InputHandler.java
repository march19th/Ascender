package com.ascender.game.helpers;


import com.ascender.game.Ascender;
import com.ascender.game.gameobjects.Protagonist;
import com.ascender.game.screens.MainMenuScreen;
import com.ascender.game.world.GameWorld;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;

public class InputHandler extends Stage {

    private static final int VIEWPORT_WIDTH = Constants.APP_WIDTH;
    private static final int VIEWPORT_HEIGHT = Constants.APP_HEIGHT;

    private OrthographicCamera camera;
    private Vector3 touchPoint;
    private Rectangle screenRightSide;
    private Rectangle screenBottomLeft;

    Ascender game;
    Protagonist protag;
    GameWorld world;

    public InputHandler(final Ascender game, Protagonist protag, GameWorld world) {
        super(new ScalingViewport(Scaling.stretch, VIEWPORT_WIDTH, VIEWPORT_HEIGHT,
                new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT)));
        this.game = game;
        this.protag = protag;
        this.world = world;
        setupTouchControlAreas();
    }
    private void setupTouchControlAreas() {
        touchPoint = new Vector3();
        screenBottomLeft = new Rectangle(0, 0, getCamera().viewportWidth / 2, getCamera().viewportHeight);
        screenRightSide = new Rectangle(getCamera().viewportWidth / 2, 0, getCamera().viewportWidth / 2,
                getCamera().viewportHeight);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {

        // Need to get the actual coordinates
        translateScreenToWorldCoordinates(x, y);


        if (rightSideTouched(touchPoint.x, touchPoint.y)) {
            if (protag.isAlive()) {
                protag.jump();
            }
            if (world.isGameOVer()) {
                game.setScreen(new MainMenuScreen(game));
            }
            if (world.isReady()) {
                world.start();
            }

        } else if (bottomLeftTouched(touchPoint.x, touchPoint.y)) {
            if (protag.isAlive()) {
                protag.fireball();
            }
            if (world.isGameOVer()) {
                world.restart();
            }
            if (world.isReady()) {
                world.start();
            }
        }



        return super.touchDown(x, y, pointer, button);
    }

    /**
     * Helper function to get the actual coordinates in my world
     * @param x
     * @param y
     */
    private void translateScreenToWorldCoordinates(int x, int y) {
        getCamera().unproject(touchPoint.set(x, y, 0));
    }

    private boolean rightSideTouched(float x, float y) {
        return screenRightSide.contains(x, y);
    }

    private boolean bottomLeftTouched(float x, float y) {
        return screenBottomLeft.contains(x, y);
    }
}
