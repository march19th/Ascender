package com.ascender.game.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class AssetLoader {
    public static Texture texture;
    public static Texture items;
    public static TextureRegion pause;
    public static Texture orangeProtagonist;
    public static Texture slime;
    public static TextureRegion apple;
    public static TextureRegion background;
    public static Rectangle textureRegionBounds1;
    public static Rectangle textureRegionBounds2;

    public static TextureRegion ground;
    public static Rectangle groundBounds1;
    public static Rectangle groundBounds2;

    public static TextureRegion fireball;

    public static TextureRegion bg, grass;
    public static Animation slimeAnimation;
    public static Animation proAnimation;
    public static TextureRegion proRight, proMid, proLeft;

    public static TextureRegion slime1, slime2, slime3, slime4;

    public static Animation ghostAnimation;
    public static TextureRegion ghost1, ghost2;

    public static TextureRegion skullUp, skullDown, bar;

    public static Sound death, jump, points;

    public static BitmapFont font, shadow;

    public static void load() {

        texture = new Texture(Gdx.files.internal("data/texture.png"));
        texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        orangeProtagonist = new Texture(Gdx.files.internal("data/OrangeProtagonist.png"));


        background = new TextureRegion(new Texture(Gdx.files.internal("data/background.png")));
        textureRegionBounds1 = new Rectangle(0 - Constants.APP_WIDTH / 2, 0, Constants.APP_WIDTH, Constants.APP_HEIGHT);
        textureRegionBounds2 = new Rectangle(Constants.APP_WIDTH / 2, 0, Constants.APP_WIDTH, Constants.APP_HEIGHT);

        ground = new TextureRegion(new Texture(Gdx.files.internal("data/ground.png")));
//        groundBounds1 = new Rectangle(0 - Constants.APP_WIDTH / 2, 0, Constants.GROUND_WIDTH, Constants.GROUND_HEIGHT);
//        groundBounds2 = new Rectangle(Constants.APP_WIDTH / 2, 0, Constants.GROUND_WIDTH, Constants.GROUND_HEIGHT);

        fireball = new TextureRegion(new Texture(Gdx.files.internal("data/drop.png")));


        grass = new TextureRegion(texture, 0, 43, 143, 11);

        slime = new Texture(Gdx.files.internal("data/slime.png"));
        slime1 = new TextureRegion(slime, 0, 64, 64, 64);
        slime1.flip(true, false);
        slime2 = new TextureRegion(slime, 64, 64, 64, 64);
        slime2.flip(true, false);
        slime3 = new TextureRegion(slime, 128, 64, 64, 64);
        slime3.flip(true, false);
        slime4 = new TextureRegion(slime, 192, 64, 64, 64);
        slime4.flip(true, false);
        TextureRegion[] slimey = {slime1, slime2, slime3, slime4};
        slimeAnimation = new Animation(0.16f, slimey);
        slimeAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        ghost1 = new TextureRegion(new Texture(Gdx.files.internal("data/spooky1.png")),
                0, 0, 32, 32);
        ghost1.flip(true, false);
        ghost2 = new TextureRegion(new Texture(Gdx.files.internal("data/spooky2.png")),
                0, 0, 32, 32);
        ghost2.flip(true, false);
        TextureRegion[] ghosty = {ghost1, ghost2};
        ghostAnimation = new Animation(0.1f, ghosty);
        ghostAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        apple = new TextureRegion(new Texture(Gdx.files.internal("data/treasure.png")),
                0, 0, 64, 64);

        proRight = new TextureRegion(orangeProtagonist, 32, 96, 32, 32);

        proMid = new TextureRegion(orangeProtagonist, 64, 96, 32, 32);

        proLeft = new TextureRegion(orangeProtagonist, 96, 96, 32, 32);

        TextureRegion[] protag = { proRight, proMid, proLeft };
        proAnimation = new Animation(0.06f, protag);
        proAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        skullUp = new TextureRegion(texture, 192, 0, 24, 14);
        // Create by flipping existing skullUp
        skullDown = new TextureRegion(skullUp);
        skullDown.flip(false, true);

        bar = new TextureRegion(texture, 136, 16, 22, 3);
        //bar.flip(false, true);
        death = Gdx.audio.newSound(Gdx.files.internal("data/dead.wav"));
        jump = Gdx.audio.newSound(Gdx.files.internal("data/jump.wav"));
        points = Gdx.audio.newSound(Gdx.files.internal("data/point.wav"));

        font = new BitmapFont(Gdx.files.internal("data/text.fnt"));
        font.getData().setScale(.25f, .25f);
        shadow = new BitmapFont(Gdx.files.internal("data/shadow.fnt"));
        shadow.getData().setScale(.25f, .25f);

        //pause
        items = new Texture(Gdx.files.internal("data/items.png"));
        pause = new TextureRegion(items, 64, 64, 64, 64);
    }

    public static void dispose() {
        // We must dispose of the texture when we are finished.
        orangeProtagonist.dispose();
        slime.dispose();
        items.dispose();
        texture.dispose();
        death.dispose();
        jump.dispose();
        points.dispose();
        font.dispose();
        shadow.dispose();
    }
}
