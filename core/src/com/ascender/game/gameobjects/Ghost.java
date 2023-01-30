package com.ascender.game.gameobjects;

import com.ascender.game.helpers.Constants;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Ghost extends Scrollable{

    private Rectangle ghostCirc;

    public Ghost(float x, float y, int width, int height, float scrollSpeed) {
        super(x, y, width, height, scrollSpeed);
        ghostCirc = new Rectangle();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        ghostCirc.set(position.x + 18, position.y + 12, Constants.GHOST_WIDTH / 2f - 5, Constants.GHOST_HEIGHT / 2);
    }

    @Override
    public void reset(float newX) {
        velocity.x = Constants.GHOST_SPEED + MathUtils.random(-200, 50);
        super.reset(Constants.GHOST_X + 500);
    }

    public Rectangle getghostCirc() {
        return ghostCirc;
    }

    public boolean collides(Protagonist protag) {
        if (position.x < protag.getX() + protag.getWidth()) {
            return (Intersector.overlaps(protag.getDetection(), ghostCirc));
        }
        return false;
    }

    public void onRestart(float x, float speed) {
        velocity.x = speed;
        reset(x);
    }
}
