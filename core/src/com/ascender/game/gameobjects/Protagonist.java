package com.ascender.game.gameobjects;

import com.ascender.game.helpers.AssetLoader;
import com.ascender.game.helpers.Constants;
import com.ascender.game.world.GameWorld;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

//TODO: If protagonist is in the air they should shoot downwards diagonally
public class Protagonist {
    private Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration;

    private float rotation;
    private int width;
    private int height;

    public static Array<Rectangle> fireballs;
    public static Array<Rectangle> jumpDrops;
    public static Array<Circle> apples;

    private Circle detection;

    private boolean alive;

    private GameWorld world;

    public Protagonist(GameWorld world, float x, float y, int width, int height) {
        this.world = world;
        this.width = width;
        this.height = height;
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
        acceleration = new Vector2(0, Constants.PROTAG_ACCEL);
        fireballs = new Array<>();
        apples = new Array<>();
        jumpDrops = new Array<>();
        detection = new Circle();
        alive = true;
    }

    public void update(float delta) {

        velocity.add(acceleration.cpy().scl(delta));

        if (velocity.y < -400) {
            velocity.y = -400;
        }

        position.add(velocity.cpy().scl(delta));
        detection.set(position.x + 30, position.y + 30, 15f);


        //gravity
        if (position.y > 200) {
            acceleration.y = Constants.PROTAG_ACCEL - 100;
        }

        // ground handling
        if (position.y <= Constants.PROTAG_Y) {
            position.y = Constants.PROTAG_Y;
        }

        //death handling
        if (!isAlive()) {
            rotation += 480 * delta;
            if (rotation > 90) {
                rotation = 90;
            }
        }
    }

    public boolean run() {
        return position.y <= Constants.GROUND_HEIGHT + 10 && alive;
    }

    public void onRestart() {
        rotation = 0;
        position.y = Constants.PROTAG_Y;
        velocity.x = 0;
        velocity.y = 0;
        acceleration.x = 0;
        acceleration.y = Constants.PROTAG_ACCEL;
        alive = true;
    }

    public void jump() {
        if (alive && position.y > Constants.PROTAG_Y + 10 && world.getAmmo() >= 2) {
            doubleJump();
            AssetLoader.jump.play();
            velocity.y = Constants.PROTAG_JUMP;
        }
        if (alive && position.y == Constants.PROTAG_Y) {
            AssetLoader.jump.play();
            velocity.y = Constants.PROTAG_JUMP;
        }
    }

    public void doubleJump() {
        Rectangle jumpDrop = new Rectangle();
        jumpDrop.x = this.getX();
        jumpDrop.y = this.getY();
        jumpDrop.width = 1;
        jumpDrop.height = 64;
        if (world.getAmmo() > 0) {
            jumpDrops.add(jumpDrop);
        }
        world.reduceAmmo(2);
    }

    public void fireball() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = this.getX() + 50;
        raindrop.y = this.getY();
        raindrop.width = 1;
        raindrop.height = 64;
        if (world.getAmmo() > 0) {
            fireballs.add(raindrop);
            world.reduceAmmo(1);
        }

    }


    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getRotation() {
        return rotation;
    }

    public Circle getDetection() {
        return detection;
    }

    public boolean isAlive() {
        return alive;
    }

    public void die() {
        alive = false;
        velocity.y = 0;
    }
}
