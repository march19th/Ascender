package com.ascender.game.gameobjects;

import com.ascender.game.helpers.Constants;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Slime extends Scrollable{

    private Rectangle slimeRec;

    public Slime(float x, float y, int width, int height, float scrollSpeed) {
        super(x, y, width, height, scrollSpeed);
        slimeRec = new Rectangle();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        slimeRec.set(position.x + 16, position.y, Constants.SLIME_WIDTH/2 - 5, Constants.SLIME_HEIGHT/2);
    }

    public void onRestart(float x, float speed) {
        velocity.x = speed;
        reset(x);
    }

    @Override
    public void reset(float newX) {
        // Call the reset method in the superclass (Scrollable)
        velocity.x = MathUtils.random(-200, 50) + Constants.SLIME_SPEED;
        super.reset(newX);
    }

    public Rectangle getSlimeRec() {
        return slimeRec;
    }

    public boolean collides(Protagonist protag) {
        if (position.x < protag.getX() + protag.getWidth()) {
            return (Intersector.overlaps(protag.getDetection(), slimeRec));
        }
        return false;
    }
}
