package com.ascender.game.gameobjects;

import com.ascender.game.helpers.Constants;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;


public class Treasure extends Scrollable{

    private Rectangle treasureRec;

    public Treasure(float x, float y, int width, int height, float scrollSpeed) {
        super(x, y, width, height, scrollSpeed);
        treasureRec = new Rectangle();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        treasureRec.set(position.x, position.y, Constants.TREASURE_WIDTH / 2f, Constants.TREASURE_HEIGHT / 2f);
    }

    @Override
    public void reset(float newX) {
        // Call the reset method in the superclass (Scrollable)
        super.reset(newX);
    }

    public Rectangle getTreasureRec() {
        return treasureRec;
    }

    public boolean snatchApple(Protagonist protag) {
        if (position.x < protag.getX() + protag.getWidth()) {
            return (Intersector.overlaps(protag.getDetection(), treasureRec));
        }
        return false;
    }

    public void onRestart(float x, float speed) {
        velocity.x = speed;
        reset(x);
    }

}
