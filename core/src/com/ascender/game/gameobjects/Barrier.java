package com.ascender.game.gameobjects;

import com.ascender.game.helpers.Constants;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Barrier extends Scrollable{

    private Rectangle barrierRec;


    public Barrier(float x, float y, int width, int height, float scrollSpeed) {
        super(x, y, width, height, scrollSpeed);
        barrierRec = new Rectangle();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        barrierRec.set(position.x, position.y, this.getWidth(), this.getHeight());
    }

    public void onRestart(float x, float speed) {
        velocity.x = speed;
        reset(x);
    }

    @Override
    public void reset(float newX) {
        super.reset(newX);
        // Change the height to a random number
        height = MathUtils.random(50, Constants.MAX_HEIGHT);
    }

    public Rectangle getBarrier() {
        return barrierRec;
    }

    public boolean collides(Protagonist protag) {
        if (position.x < protag.getX() + protag.getWidth()) {
            return (Intersector.overlaps(protag.getDetection(), barrierRec));
        }
        return false;
    }
}
