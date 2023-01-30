package com.ascender.game.world;

import com.ascender.game.gameobjects.Protagonist;
import com.ascender.game.gameobjects.ScrollHandler;
import com.ascender.game.helpers.AssetLoader;
import com.ascender.game.helpers.Constants;
import com.ascender.game.helpers.Settings;


public class GameWorld {

    private Protagonist protag;
    private ScrollHandler scroller;
    private int ammo = 2;
    private float score;
    private GameState currState;

    public enum GameState {
        READY, RUNNING, GAMEOVER
    }

    public GameWorld() {
        currState = GameState.READY;
        protag = new Protagonist(this, Constants.PROTAG_X, Constants.PROTAG_Y, Constants.PROTAG_WIDTH, Constants.PROTAG_HEIGHT);
        scroller = new ScrollHandler(this, Constants.GROUND_Y);
        score = 0;
    }

    public void update(float delta) {
        switch (currState) {
            case READY:
                updateReady(delta);
                break;
            case RUNNING:
                updateRunning(delta);
                break;
            default:
                updateGameOver(delta);
                break;
        }

    }

    private void updateReady(float delta) {
    }

    private void updateGameOver(float delta) {
        this.addScore(0);
        protag.update(delta);
    }

    private void updateRunning(float delta) {
        this.addScore(delta * Constants.SCORE_MULTIPLIER);
        if (delta > .15f) {
            delta = .15f;
        }
        protag.update(delta);
        scroller.update(delta);
        if (scroller.collides(protag) && protag.isAlive()) {
            //Clean up on game over
            scroller.stop();
            AssetLoader.death.play(1, 0.2f, 0.3f);
            protag.die();
            Settings.addScore((int)score);
            Settings.save();
            currState = GameState.GAMEOVER;
        }
        scroller.snatchApple(protag);


    }

    public Protagonist getProtag() {
        return protag;
    }

    public ScrollHandler getScroller() {
        return scroller;
    }

    public int getScore() {
        return (int)Math.floor(score);
    }

    public void addScore(float increment) {
        score += increment;
    }

    public int getAmmo() {
        return ammo;
    }

    public void addAmmo(int increment) {
        ammo += increment;
    }

    public void reduceAmmo(int increment) {
        ammo -= increment;
    }
    public void start() {
        currState = GameState.RUNNING;
    }
    public boolean isReady() {
        return currState == GameState.READY;
    }
    public boolean isRunning() {
        return currState == GameState.RUNNING;
    }

    public void restart() {
        score = 0;
        ammo = 2;
        protag.onRestart();
        scroller.onRestart();
        currState = GameState.READY;
    }

    public boolean isGameOVer() {
        return currState == GameState.GAMEOVER;
    }
}
