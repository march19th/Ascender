package com.ascender.game.gameobjects;

public class Background extends Scrollable{
    public Background(float x, float y, int width, int height, float scrollSpeed) {
        super(x, y, width, height, scrollSpeed);
    }

    public void onRestart(float x, float speed) {
        velocity.x = speed;
        reset(x);
    }
}
