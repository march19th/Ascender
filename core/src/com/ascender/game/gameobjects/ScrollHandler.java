package com.ascender.game.gameobjects;

import com.ascender.game.helpers.AssetLoader;
import com.ascender.game.helpers.Constants;
import com.ascender.game.world.GameWorld;
import com.badlogic.gdx.math.MathUtils;

public class ScrollHandler {
    // ScrollHandler will create all five objects that we need.
    private Ground frontGround, backGround;
    private Barrier barrier1, barrier2, barrier3;
    private Slime slime;
    private Ghost ghost;
    private Treasure apple;
    private Background background1, background2;
    private GameWorld world;

    public ScrollHandler(GameWorld world, float yPos) {
        this.world = world;

        frontGround = new Ground(Constants.GROUND_X, yPos, Constants.GROUND_WIDTH,
                Constants.GROUND_HEIGHT, Constants.BARRIER_SPEED);
        backGround = new Ground(frontGround.getTailX(), yPos, Constants.GROUND_WIDTH,
                Constants.GROUND_HEIGHT, Constants.BARRIER_SPEED);

        barrier1 = new Barrier(Constants.BARRIER_X, Constants.BARRIER_Y, Constants.BARRIER_WIDTH, Constants.BARRIER_HEIGHT, Constants.BARRIER_SPEED);
        barrier2 = new Barrier(barrier1.getTailX() + Constants.BARRIER_GAP, Constants.BARRIER_Y, Constants.BARRIER_WIDTH, Constants.BARRIER_HEIGHT, Constants.BARRIER_SPEED);
        barrier3 = new Barrier(barrier2.getTailX() + Constants.BARRIER_GAP, Constants.BARRIER_Y, Constants.BARRIER_WIDTH, Constants.BARRIER_HEIGHT, Constants.BARRIER_SPEED);

        slime = new Slime(Constants.SLIME_X, Constants.SLIME_Y, Constants.SLIME_WIDTH, Constants.SLIME_HEIGHT, Constants.SLIME_SPEED);

        ghost = new Ghost(Constants.GHOST_X, Constants.GHOST_Y, Constants.GHOST_WIDTH,
                Constants.GHOST_HEIGHT, Constants.GHOST_SPEED);

        apple = new Treasure(Constants.TREASURE_X, Constants.TREASURE_Y, Constants.TREASURE_WIDTH,
                Constants.TREASURE_HEIGHT, Constants.TREASURE_SPEED);

        background1 = new Background(0, 0, Constants.APP_WIDTH, Constants.APP_HEIGHT, Constants.BACKGROUND_SPEED);
        background2 = new Background(background1.getTailX(), 0, Constants.APP_WIDTH, Constants.APP_HEIGHT, Constants.BACKGROUND_SPEED);
    }

    public void update(float delta) {
        frontGround.update(delta);
        backGround.update(delta);
        barrier1.update(delta);
        barrier2.update(delta);
        barrier3.update(delta);
        background1.update(delta);
        background2.update(delta);
        slime.update(delta);
        ghost.update(delta);
        apple.update(delta);

        //check if any enemies have scrolled out and reset
        if (barrier1.isScrolledLeft()) {
            barrier1.reset(barrier3.getTailX() + MathUtils.random(200, 1000));
        } else if (barrier2.isScrolledLeft()) {
            barrier2.reset(barrier1.getTailX() + MathUtils.random(200, 1000));
        } else if (barrier3.isScrolledLeft()) {
            barrier3.reset(barrier2.getTailX() + MathUtils.random(200, 1000));
        }

        //Check for ground
        if (frontGround.isScrolledLeft()) {
            frontGround.reset(backGround.getTailX());
        } else if (backGround.isScrolledLeft()) {
            backGround.reset(frontGround.getTailX());
        }

        //check background
        if (background1.isScrolledLeft()) {
            background1.reset(background2.getTailX());
        } else if (background2.isScrolledLeft()) {
            background2.reset(background1.getTailX());
        }

        //check slime
        if (slime.isScrolledLeft()) {
            slime.reset(Constants.SLIME_X  - 200 + MathUtils.random(200, 500));
        }

        //check ghost
        if (ghost.isScrolledLeft()) {
            ghost.reset(Constants.GHOST_X - 200 + MathUtils.random(300, 600));
        }

        //check apple
        if (apple.isScrolledLeft()) {
            apple.reset(Constants.TREASURE_X + MathUtils.random(200, 1000));
        }
    }

    public void stop() {
        frontGround.stop();
        backGround.stop();
        barrier1.stop();
        barrier2.stop();
        barrier3.stop();
        slime.stop();
        ghost.stop();
        background1.stop();
        background2.stop();
        apple.stop();
    }

    public void onRestart() {
        frontGround.onRestart(Constants.GROUND_X, Constants.BARRIER_SPEED);
        backGround.onRestart(frontGround.getTailX(), Constants.BARRIER_SPEED);
        barrier1.onRestart(Constants.BARRIER_X, Constants.BARRIER_SPEED);
        barrier2.onRestart(barrier1.getTailX() + Constants.BARRIER_GAP, Constants.BARRIER_SPEED);
        barrier3.onRestart(barrier2.getTailX() + Constants.BARRIER_GAP, Constants.BARRIER_SPEED);
        slime.onRestart(Constants.SLIME_X, Constants.SLIME_SPEED);
        ghost.onRestart(Constants.GHOST_X, Constants.GHOST_SPEED);
        background1.onRestart(Constants.GROUND_X, Constants.BACKGROUND_SPEED);
        background2.onRestart(background1.getTailX(), Constants.BACKGROUND_SPEED);
        apple.onRestart(Constants.TREASURE_X, Constants.TREASURE_SPEED);
    }

    public boolean collides(Protagonist protag) {
        return (barrier1.collides(protag) || barrier2.collides(protag) || barrier3.collides(protag) ||
                slime.collides(protag) || ghost.collides(protag));
    }
    public boolean snatchApple(Protagonist protag) {
        if (apple.snatchApple(protag)) {
            apple.isScrolledLeft = true;
            world.addAmmo(Constants.TREASURE_POINTS);
            AssetLoader.points.play();
        }
        return (apple.snatchApple(protag));
    }

    // The getters for our five instance variables
    public Ground getFrontGround() {
        return frontGround;
    }

    public Ground getBackGround() {
        return backGround;
    }

    public Barrier getEnemy1() {
        return barrier1;
    }

    public Barrier getEnemy2() {
        return barrier2;
    }

    public Barrier getEnemy3() {
        return barrier3;
    }

    public Background getBackground1() {
        return background1;
    }

    public Background getBackground2() {
        return background2;
    }

    public Slime getSlime() {
        return slime;
    }

    public Ghost getGhost(){
        return ghost;
    }

    public Treasure getApple() {
        return apple;
    }
}
