package com.TillDawn.Controllers.GameControllers;

import com.TillDawn.Models.*;
import com.TillDawn.TillDawn;
import com.TillDawn.Views.GameView;
import com.TillDawn.Views.SettingsView;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Timer;

public class PlayerController {
    private Animation<TextureRegion> animation;
    private float stateTime;
    private float x, y;
    private float width, height;
    private Player player;
    private Game game;
    private Rectangle playerRectangle;
    private WeaponController weaponController;
    private GameView gameView;

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public Rectangle getPlayerRectangle() {
        return playerRectangle;
    }

    public PlayerController(Player player, Game game, GameView gameView) {
        this.player = player;
        this.game = game;
        this.animation = player.getAnimation();
        this.x = Gdx.graphics.getWidth() / 2f;
        this.y = Gdx.graphics.getHeight() / 2f;
        this.width = player.getWidth();
        this.height = player.getHeight();
        this.playerRectangle = player.getRectangle();
        this.weaponController = new WeaponController(player, game);
        this.stateTime = 0f;
        this.gameView = gameView;
    }

    public WeaponController getWeaponController() {
        return this.weaponController;
    }

    public void update(float delta) {
        stateTime += delta;
        TillDawn.getTillDawn().getLightShader().setUniformf("u_lightPos", Gdx.graphics.getWidth() / 2f + 700, Gdx.graphics.getHeight() / 2f + 550);

        TillDawn.getTillDawn().getLightShader().setUniformf("u_radius", 250f);
        weaponController.update(delta);
    }

    public void render(SpriteBatch batch) {
//        TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
//        batch.draw(currentFrame, x, y, width, height);
        player.getPlayerSprite().draw(batch);
        idleAnimation();
        weaponController.render(batch);
    }

    public void idleAnimation(){

        player.getPlayerSprite().setRegion(animation.getKeyFrame(player.getTime()));

        if (!animation.isAnimationFinished(player.getTime())) {
            player.setTime(player.getTime() + Gdx.graphics.getDeltaTime());
        }
        else {
            player.setTime(0);
        }

        animation.setPlayMode(Animation.PlayMode.LOOP);
    }

    public void handlePlayerInput() {
        float mp = 1.5f;
        float newX = player.getGridPos().getX();
        float newY = player.getGridPos().getY();

        if (Gdx.input.isKeyPressed(player.getUser().getKeyManagment().getUp())) {
            float y = player.getGridPos().getY();
            newY = (y - player.getSpeed());
            checkCollisionTree(newX, newY);
        }
        if (Gdx.input.isKeyPressed(player.getUser().getKeyManagment().getDown())) {
            float y = player.getGridPos().getY();
            newY = (y + player.getSpeed());
            checkCollisionTree(newX, newY);
        }
        if (Gdx.input.isKeyPressed(player.getUser().getKeyManagment().getRight())) {
            float x = player.getGridPos().getX();
            newX = (x - player.getSpeed() * mp);
            checkCollisionTree(newX, newY);
        }
        if (Gdx.input.isKeyPressed(player.getUser().getKeyManagment().getLeft())) {
            float x = player.getGridPos().getX();
            newX = (x + player.getSpeed() * mp);
            player.getPlayerSprite().flip(true, false);
            checkCollisionTree(newX, newY);
        }
        if (Gdx.input.isKeyJustPressed(player.getUser().getKeyManagment().getAutoAimButton())) {
            autoAim();
        }
        if(Gdx.input.isKeyJustPressed(player.getUser().getKeyManagment().getReloadButton())) {
            player.setReloading(true);
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    player.setReloading(false);
                    player.setAmmo(player.getWeapon().getAmmoMax() + player.getMaxAmmoAddition());
                    this.cancel();
                }
            } , player.getWeapon().getTimeReload() , 2);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            gameView.setPaused(!gameView.isPaused());
            return;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)){
            TillDawn.getTillDawn().getScreen().dispose();
            TillDawn.getTillDawn().setScreen(new SettingsView());
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            float time = Math.min(game.getMaxTime(), game.getTimeGone() + 60);
            game.setTimeGone(time);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            player.setLevel(player.getLevel() + 1);
            player.getUser().getSfxManager().setSound(GameAssetManager.getManager().getLevelUp());
            player.getUser().getSfxManager().play();
            player.setLevelUp(true);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
            player.setHp(player.getHp() + 0.1f);
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)) {
            gameView.spawnBoss();
        }
         else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_5)) {
            player.setMaxAmmoAddition(player.getMaxAmmoAddition() + player.getWeapon().getAmmoMax());
        }
        player.getGridPos().setX(newX);
        player.getGridPos().setY(newY);
    }

    private void checkCollisionTree(float x, float y){
        for (Tree tree : game.getTrees()){
            Rectangle rectangle = new Rectangle(x + tree.getX(), y + tree.getY(), tree.getWidth(), tree.getHeight());
            if (rectangle.overlaps(playerRectangle)){
                player.setHp(player.getHp() - tree.getDamage());
            }
        }
    }

    public void autoAim() {
        //TODO: tof
        Tentacle target = null;
        double distance = Double.POSITIVE_INFINITY;
        for (Tentacle t : game.getTentacles()) {
            double d = (player.getGridPos().getX() + t.getX() - x) * (player.getGridPos().getX() + t.getX() - x)
                    + (player.getGridPos().getY() + t.getY() - y) * (player.getGridPos().getY() + t.getY() - y);
            if (d < distance) {
                distance = d;
                target = t;
            }
        }
        EyeBat eye = null;
        for (EyeBat t : game.getEyeBats()) {
            double d = (player.getGridPos().getX() + t.getX() - x) * (player.getGridPos().getX() + t.getX() - x)
                    + (player.getGridPos().getY() + t.getY() - y) * (player.getGridPos().getY() + t.getY() - y);
            if (d < distance) {
                distance = d;
                target = null;
                eye = t;
            }
        }
        ElderBoss h = null;
        if (game.getElderBoss() != null) {
            ElderBoss boss = game.getElderBoss();
            double d = (player.getGridPos().getX() + boss.getX() - x) * (player.getGridPos().getX() + boss.getX() - x)
                    + (player.getGridPos().getY() + boss.getY() - y) * (player.getGridPos().getY() + boss.getY() - y);
            if (d < distance) {
                h = boss;
                target = null;
                eye = null;
            }
        }
        if (target != null) {
            Gdx.input.setCursorPosition((int) (player.getGridPos().getX() + target.getX()),
                    Gdx.graphics.getHeight() - (int) (player.getGridPos().getY() + target.getY()));
            weaponController.handleShot((int) (player.getGridPos().getX() + target.getX()),
                    Gdx.graphics.getHeight() - (int) (player.getGridPos().getY() + target.getY()));
        }
        if (eye != null) {
            Gdx.input.setCursorPosition((int) (player.getGridPos().getX() + eye.getX()),
                    Gdx.graphics.getHeight() - (int) (player.getGridPos().getY() + eye.getY()));
            weaponController.handleShot((int) (player.getGridPos().getX() + eye.getX()),
                    Gdx.graphics.getHeight() - (int) (player.getGridPos().getY() + eye.getY()));
        }
        if (h != null) {
            Gdx.input.setCursorPosition((int) (player.getGridPos().getX() + h.getX()),
                    Gdx.graphics.getHeight() - (int) (player.getGridPos().getY() + h.getY()));
            weaponController.handleShot((int) (player.getGridPos().getX() + h.getX()),
                    Gdx.graphics.getHeight() - (int) (player.getGridPos().getY() + h.getY()));
        }
    }

//    private void handleOutOfBounds() {
//        if (x < player.getGridPos().getX()) {
//            player.getGridPos().setX(x);
//        }
//        if (x > player.getGridPos().getX() + gameView.getBackgroundWidth() - width) {
//            player.getGridPos().setX(x - gameView.getBackgroundWidth() + width);
//        }
//        if (y < player.getGridPos().getY()) {
//            player.getGridPos().setY(y);
//        }
//        if (y > player.getGridPos().getY() + gameView.getBackgroundHeight() - height) {
//            player.getGridPos().setY(y - gameView.getBackgroundHeight() + height);
//        }
//    }
//
//    public boolean isPlayerXOutOfBounds() {
//        return x < player.getGridPos().getX() || x > player.getGridPos().getX() + gameView.getBackgroundWidth();
//    }
//
//    public boolean isPlayerYOutOfBounds() {
//        return y < player.getGridPos().getY() || y > player.getGridPos().getY() + gameView.getBackgroundHeight();
//    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }
}
