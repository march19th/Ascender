package com.ascender.game.world;

import com.ascender.game.gameobjects.Background;
import com.ascender.game.gameobjects.Barrier;
import com.ascender.game.gameobjects.Ghost;
import com.ascender.game.gameobjects.Ground;
import com.ascender.game.gameobjects.Protagonist;
import com.ascender.game.gameobjects.ScrollHandler;
import com.ascender.game.gameobjects.Slime;
import com.ascender.game.gameobjects.Treasure;
import com.ascender.game.helpers.AssetLoader;
import com.ascender.game.helpers.Constants;
import com.ascender.game.helpers.Settings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import java.util.Iterator;

public class GameRenderer {

    private GameWorld myWorld;
    private OrthographicCamera cam;
    private ShapeRenderer shapeRenderer;

    private SpriteBatch batch;

    private int midPointY;
    private int gameHeight;

    //Game objects
    public static Protagonist protagonist;
    private ScrollHandler scroller;
    private Ground frontGround, backGround;
    private Barrier barrier1, barrier2, barrier3;
    private Slime slime;
    private Ghost ghost;
    private Background background1, background2;
    private Treasure apple;

    //Game assets
    private TextureRegion background;
    private TextureRegion bg, grass, ground;
    private Animation proAnimation;
    private TextureRegion proRight, proMid, proLeft;
    private TextureRegion skullUp, skullDown, bar;
    private Animation slimeAnimation;
    private TextureRegion slime1, slime2, slime3, slime4;
    private Animation ghostAnimation;
    private TextureRegion appleSprite;
    private TextureRegion pause;

    TextureRegion fireball;


    public GameRenderer(GameWorld world, int gameHeight, int midPointY) {
        myWorld = world;

        // The word "this" refers to this instance.
        // We are setting the instance variables' values to be that of the
        // parameters passed in from GameScreen.
//        this.gameHeight = gameHeight;
//        this.midPointY = midPointY;

        cam = new OrthographicCamera(Constants.APP_WIDTH, Constants.APP_HEIGHT);
        cam.setToOrtho(false, Constants.APP_WIDTH, Constants.APP_HEIGHT);
        //cam.position.set(Constants.APP_WIDTH / 2f, Constants.APP_HEIGHT / 2f, 1);

        batch = new SpriteBatch();
        //Attach batch to camera
        batch.setProjectionMatrix(cam.combined);
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(cam.combined);

        //helper methods
        initGameObjects();
        initAssets();
    }
    public void render(float runTime) {

        // Fill the entire screen with black, to prevent potential flickering.
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        cam.update();
        batch.setProjectionMatrix(cam.combined);
        drawBackground();
        drawGround();
        drawBarriers();
        batch.enableBlending();
        drawSkulls();
        renderProtagonist(runTime);

        renderFireballs();
        renderSlime(runTime);
        renderGhost(runTime);

        renderApples();
        renderScore();
        renderAmmo();

        renderDoubleJump();

        renderHelp();

    }

    private void renderScore() {
        batch.begin();
        String score = myWorld.getScore() + "";
        //drawing shadow
        AssetLoader.shadow.draw(batch, "" + myWorld.getScore(), (70)
        - (3 * score.length()), 40);
        // Draw text
        AssetLoader.font.draw(batch, "" + myWorld.getScore(), (136 / 2)
                - (3 * score.length() - 1), 39);
        batch.end();
    }
    private void renderDoubleJump() {
        batch.begin();
        batch.enableBlending();
        for (Rectangle jumpDrop : Protagonist.jumpDrops) {
            batch.draw(fireball, jumpDrop.x, jumpDrop.y, 0, 0, Constants.FIREBALL_WIDTH, Constants.FIREBALL_HEIGHT, 1, 1, 0);
            batch.draw(fireball, jumpDrop.x - Constants.FIREBALL_WIDTH, jumpDrop.y, 0, 0, Constants.FIREBALL_WIDTH, Constants.FIREBALL_HEIGHT, 1, 1, 0);

        }
        batch.end();

        Iterator<Rectangle> iter = Protagonist.jumpDrops.iterator();
        while (iter.hasNext()) {
            Rectangle jumpDrop = iter.next();
            jumpDrop.y -= 400 * Gdx.graphics.getDeltaTime();
        }
    }

    private void renderHelp() {
        // handling right before game start
        if (myWorld.isReady()) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(Color.BLUE);
            //screen divider
            shapeRenderer.line(Constants.APP_WIDTH/2f, Constants.APP_HEIGHT , Constants.APP_WIDTH/2f
                    , 0 );
            shapeRenderer.end();
            batch.begin();
            AssetLoader.shadow.draw(batch, "tap here \nto shoot", 150, 300);
            AssetLoader.font.draw(batch, "tap here \nto shoot", 150 - 1, 300 - 1);
            AssetLoader.shadow.draw(batch, "tap here \nto jump", 500, 300);
            AssetLoader.font.draw(batch, "tap here \nto jump", 500 - 1, 300 - 1);
            batch.end();
        }
        // handling when game ends helper stuff
        if (myWorld.isGameOVer()) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(Color.BLUE);
            //screen divider
            shapeRenderer.line(Constants.APP_WIDTH/2f, Constants.APP_HEIGHT , Constants.APP_WIDTH/2f
                    , 0 );
            shapeRenderer.end();
            batch.begin();
            if (myWorld.getScore() >= Settings.highscores[0]) {
                AssetLoader.shadow.draw(batch, "NEW HIGHSCORE!!!", 250, 400);
                AssetLoader.font.draw(batch, "NEW HIGHSCORE!!!", 250 - 1, 400 - 1);
            }
            AssetLoader.shadow.draw(batch, "tap here \nto restart", 150, 300);
            AssetLoader.font.draw(batch, "tap here \nto restart", 150 - 1, 300 - 1);
            AssetLoader.shadow.draw(batch, "tap here \nto go to \nmain menu", 500, 300);
            AssetLoader.font.draw(batch, "tap here \nto go to \nmain menu", 500 - 1, 300 - 1);
            batch.end();
        }
    }

    private void renderAmmo() {
        batch.begin();
        batch.draw(fireball, 780, 430, 0, 0, Constants.FIREBALL_WIDTH, Constants.FIREBALL_HEIGHT, 1,1, 90);
        String ammo = myWorld.getAmmo() + "";
        AssetLoader.shadow.draw(batch, "" + myWorld.getAmmo(), (720)
                - (3 * ammo.length()), 455);
        // Draw text
        AssetLoader.font.draw(batch, "" + myWorld.getAmmo(), (720)
                - (3 * ammo.length() - 1), 454);
        batch.end();
    }

    private void renderFireballs() {
        batch.begin();
        batch.enableBlending();
        for (Rectangle raindrop : Protagonist.fireballs) {
            batch.draw(fireball, raindrop.x, raindrop.y, 0, 0, Constants.FIREBALL_WIDTH, Constants.FIREBALL_HEIGHT, 1, 1, 90);
        }
        batch.end();

        Iterator<Rectangle> iter = Protagonist.fireballs.iterator();
        while (iter.hasNext()) {
            Rectangle raindrop = iter.next();
            raindrop.x += 400 * Gdx.graphics.getDeltaTime();
            if (raindrop.overlaps(slime.getSlimeRec())) {
                iter.remove();
                //kills slime
                slime.reset(1000);
                AssetLoader.death.play();
            }
            if (raindrop.overlaps(ghost.getghostCirc())) {
                iter.remove();
                ghost.reset(1000);
                AssetLoader.death.play();
            }
            if (raindrop.x + 64 > Constants.APP_WIDTH) {
                iter.remove();
            }
        }
    }

    private void renderApples() {
        batch.begin();
        batch.draw(appleSprite, apple.getX(), apple.getY(),
                apple.getWidth(), apple.getHeight());
        batch.end();
    }


    private void renderProtagonist(float runTime) {
        batch.begin();
        if (!protagonist.run()) {
            batch.draw(proRight, protagonist.getX(), protagonist.getY(),
                    protagonist.getWidth() / 2.0f, protagonist.getHeight() / 2.0f,
                    protagonist.getWidth(), protagonist.getHeight(), 1, 1, protagonist.getRotation());

        } else {
            batch.draw((TextureRegion) proAnimation.getKeyFrame(runTime), protagonist.getX(),
                    protagonist.getY(), protagonist.getWidth() / 2.0f,
                    protagonist.getHeight() / 2.0f, protagonist.getWidth(), protagonist.getHeight(),
                    1, 1, protagonist.getRotation());
        }
        batch.end();


    }

    private void renderSlime(float runTime) {
        batch.begin();
        batch.draw((TextureRegion) slimeAnimation.getKeyFrame(runTime), slime.getX(), slime.getY(),
                slime.getWidth(), slime.getHeight());
        batch.end();
    }

    private void renderGhost(float runTime) {
        batch.begin();
        batch.draw((TextureRegion) ghostAnimation.getKeyFrame(runTime), ghost.getX(), ghost.getY(),
                ghost.getWidth(), ghost.getHeight());
        batch.end();
    }


    private void renderDetection() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.circle( protagonist.getDetection().x,
                 protagonist.getDetection().y,
                protagonist.getDetection().radius);
        shapeRenderer.end();
    }

    private void renderBarrier() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(barrier1.getBarrier().x, barrier1.getBarrier().y
                , barrier1.getBarrier().width, barrier1.getHeight());
        shapeRenderer.rect(barrier2.getBarrier().x, barrier2.getBarrier().y
                , barrier2.getBarrier().width,
                barrier2.getHeight());
        shapeRenderer.rect(barrier3.getBarrier().x, barrier3.getBarrier().y
                , barrier3.getBarrier().width,
                barrier3.getHeight());
        shapeRenderer.rect(slime.getX()+16, slime.getY() , slime.getWidth()/2 - 5, slime.getHeight()/2);
        shapeRenderer.rect(ghost.getX() + 18, ghost.getY() + 12
                , ghost.getWidth() / 2 - 5, ghost.getHeight()/2);
        shapeRenderer.rect(apple.getX() + 30, apple.getY() + 29, apple.getWidth() /2 - 15, apple.getHeight() / 3);
        shapeRenderer.end();
    }

    private void drawBackground() {
        batch.begin();
        batch.draw(AssetLoader.background, background1.getX(), background1.getY(), Constants.APP_WIDTH,
                Constants.APP_HEIGHT);
        batch.draw(AssetLoader.background, background2.getX(), background2.getY(), Constants.APP_WIDTH,
                Constants.APP_HEIGHT);
        batch.end();
    }

    private void drawGround() {
        batch.begin();
        batch.draw(ground, frontGround.getX(), frontGround.getY(), frontGround.getWidth(),
                frontGround.getHeight());
        batch.draw(ground, backGround.getX(), backGround.getY(),
                backGround.getWidth(), backGround.getHeight());
        batch.end();
    }

    private void drawSkulls() {
        // Temporary code! Sorry about the mess :)
        // We will fix this when we finish the Pipe class.
        batch.begin();
        batch.draw(skullUp, barrier1.getX() - 1,
                barrier1.getY() + barrier1.getHeight() - 14, Constants.TOPPER_WIDTH, Constants.TOPPER_HEIGHT);

        batch.draw(skullUp, barrier2.getX() - 1,
                barrier2.getY() + barrier2.getHeight() - 14, Constants.TOPPER_WIDTH, Constants.TOPPER_HEIGHT);

        batch.draw(skullUp, barrier3.getX() - 1,
                barrier3.getY() + barrier3.getHeight() - 14, Constants.TOPPER_WIDTH, Constants.TOPPER_HEIGHT);

        batch.end();
    }

    private void drawBarriers() {
        batch.begin();
        batch.draw(bar, barrier1.getX(), barrier1.getY(), barrier1.getWidth(),
                barrier1.getHeight());
        batch.draw(bar, barrier2.getX(), barrier2.getY(), barrier2.getWidth(),
                barrier2.getHeight());
        batch.draw(bar, barrier3.getX(), barrier3.getY(), barrier3.getWidth(),
                barrier3.getHeight());
        batch.end();
    }

    private void initGameObjects() {
        protagonist = myWorld.getProtag();
        scroller = myWorld.getScroller();
        frontGround = scroller.getFrontGround();
        backGround = scroller.getBackGround();
        barrier1 = scroller.getEnemy1();
        barrier2 = scroller.getEnemy2();
        barrier3 = scroller.getEnemy3();
        background1 = scroller.getBackground1();
        background2 = scroller.getBackground2();
        slime = scroller.getSlime();
        ghost = scroller.getGhost();
        apple = scroller.getApple();
    }

    private void initAssets() {
        background = AssetLoader.background;
        proAnimation = AssetLoader.proAnimation;
        proMid = AssetLoader.proMid;
        proLeft = AssetLoader.proLeft;
        proRight = AssetLoader.proRight;
        skullUp = AssetLoader.skullUp;
        skullDown = AssetLoader.skullDown;
        bar = AssetLoader.bar;

        grass = AssetLoader.grass;
        ground = AssetLoader.ground;

        fireball = AssetLoader.fireball;

        slimeAnimation = AssetLoader.slimeAnimation;
        slime1 = AssetLoader.slime1;
        slime2 = AssetLoader.slime2;
        slime3 = AssetLoader.slime3;
        slime4 = AssetLoader.slime4;

        ghostAnimation = AssetLoader.ghostAnimation;

        appleSprite = AssetLoader.apple;

        pause = AssetLoader.pause;
    }

}
