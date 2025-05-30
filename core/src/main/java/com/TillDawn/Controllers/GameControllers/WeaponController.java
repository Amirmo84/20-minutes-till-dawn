package com.TillDawn.Controllers.GameControllers;

import com.TillDawn.Models.*;
import com.TillDawn.TillDawn;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Timer;

import java.util.ArrayList;

public class WeaponController {
    private Texture texture;
    private float originX, originY;
    private float rotation;
    private float x;
    private float y;
    private Player player;
    private Game game;
    private ArrayList<Bullet> bullets = new ArrayList<>();

    public WeaponController(Player player, Game game) {
        this.player = player;
        this.game = game;
        x = Gdx.graphics.getWidth() / 2f;
        y = Gdx.graphics.getHeight() / 2f;
        texture = GameAssetManager.getManager().getWeapon();
        originX = 0f;
        originY = 0f;
    }

    public void update(float delta) {
        Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);

        rotation = 2 * (float) Math.PI - (float) Math.toDegrees(Math.atan2(
                mousePos.y - y,
                mousePos.x - x
        ));
        ArrayList<Bullet> bulletsToRemove = new ArrayList<>(bullets);
        for (int i = 0; i < bullets.size(); i++) {
            if (isBulletOffScreen(bullets.get(i))) {
                if (i >= bullets.size())
                    bulletsToRemove.remove(i);
            }
        }
        bullets = bulletsToRemove;
    }

    public void render(SpriteBatch batch) {
        batch.draw(
                texture,
                x - originX + 15f,
                y - originY + 10f,
                originX,
                originY,
                25f,
                25f,
                1.5f,
                1.5f,
                rotation,
                0,
                0,
                texture.getWidth(),
                texture.getHeight(),
                false,
                false
        );
        updateBullets(batch);
    }

    public void handleShot(int i1, int i2) {
        if (player.isReloading()) {
            return;
        }
        player.setAmmo(Math.max(player.getAmmo() - 1, 0));
        if (player.getAmmo() == 0) {
            if (player.getUser().isReloadAuto()) {
                player.setReloading(true);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        player.setReloading(false);
                        player.setAmmo(player.getWeapon().getAmmoMax() + player.getMaxAmmoAddition());
                        this.cancel();
                    }
                }, player.getWeapon().getTimeReload(), 2);
            }
        }
        player.getUser().getSfxManager().setSound(GameAssetManager.getManager().getShootSound());
        player.getUser().getSfxManager().play();
        for (int i = 0; i < player.getWeapon().getProjectile() + player.getProjectileAddition(); i++) {
            bullets.add(new Bullet(i1, i2, x, y + i * 20f));
        }
    }

    public void updateBullets(SpriteBatch batch) {
        ArrayList<Integer> bulletsIndexToRemove = new ArrayList<>();
        for (Bullet b : bullets) {
            b.getSprite().draw(batch);
            b.getSprite().setX(b.getSprite().getX() - b.getDirection().x * 5);
            b.getSprite().setY(b.getSprite().getY() + b.getDirection().y * 5);
            b.getRectangle().setX(b.getSprite().getX() - b.getDirection().x * 5);
            b.getRectangle().setY(b.getSprite().getY() + b.getDirection().y * 5);

            ArrayList<Integer> tentacleIndexToRemove = new ArrayList<>();
            ArrayList<Integer> eyBatIndexToRemove = new ArrayList<>();
            for (Tentacle t : game.getTentacles()) {
                if (t.getRectangle().overlaps(b.getRectangle())) {
                    bulletsIndexToRemove.add(bullets.indexOf(b));
                    t.setHp(t.getHp() - b.getDamage() * player.getDamageMp());
                    Vector2 direction = b.getDirection();
                    t.setX(t.getX() - direction.x * 10);
                    t.setY(t.getY() + direction.y * 10);
                    t.getRectangle().setX(t.getRectangle().getX() - direction.x * 10);
                    t.getRectangle().setY(t.getRectangle().getY() + direction.y * 10);
                    Color shadowColor = new Color(1, 0, 0, 1);
                    t.setColor(shadowColor);
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            t.setColor(null);
                            this.cancel();
                        }
                    } , .2f , 2);
                    if (t.getHp() <= 0) {
                        player.setKills(player.getKills() + 1);
                        game.getExplosions().add(new Explosion(t.getX(), t.getY()));
                        player.getUser().getSfxManager().setSound(GameAssetManager.getManager().getExplosionSound());
                        player.getUser().getSfxManager().play();
                        tentacleIndexToRemove.add(game.getTentacles().indexOf(t));
                    }
                }
            }
            for (EyeBat t : game.getEyeBats()) {
                if (t.getRectangle().overlaps(b.getRectangle())) {
                    bulletsIndexToRemove.add(bullets.indexOf(b));
                    Vector2 direction = b.getDirection();
                    t.setHp(t.getHp() - b.getDamage() * player.getDamageMp());
                    t.setX(t.getX() - direction.x * 5);
                    t.setY(t.getY() + direction.y * 5);
                    t.getRectangle().setX(t.getRectangle().getX() - direction.x * 5);
                    t.getRectangle().setY(t.getRectangle().getY() + direction.y * 5);
                    Color shadowColor = new Color(1, 0, 0, 0.5f);
                    t.setColor(shadowColor);
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            t.setColor(null);
                            this.cancel();
                        }
                    } , .2f , 2);
                    if (t.getHp() <= 0) {
                        player.setKills(player.getKills() + 1);
                        game.getExplosions().add(new Explosion(t.getX(), t.getY()));
                        player.getUser().getSfxManager().setSound(GameAssetManager.getManager().getExplosionSound());
                        player.getUser().getSfxManager().play();
                        eyBatIndexToRemove.add(game.getEyeBats().indexOf(t));
                    }
                }
            }
            if (game.getElderBoss() != null) {
                ElderBoss boss = game.getElderBoss();
                if (boss.getRectangle().overlaps(b.getRectangle())) {
                    bulletsIndexToRemove.add(bullets.indexOf(b));
                    boss.setHp(boss.getHp() - b.getDamage() * player.getDamageMp());
                    Vector2 direction = b.getDirection();
                    boss.setHp(boss.getHp() - b.getDamage());
                    boss.setX(b.getX() - direction.x * 5);
                    boss.setY(b.getY() + direction.y * 5);
                    boss.getRectangle().setX(boss.getRectangle().getX() - direction.x * 5);
                    boss.getRectangle().setY(boss.getRectangle().getY() + direction.y * 5);
                    if (boss.getHp() <= 0) {
                        player.setKills(player.getKills() + 1);
                        game.setElderBoss(null);
                    }
                }
            }
            for (Integer i : tentacleIndexToRemove) {
                try {
                    Tentacle t = game.getTentacles().get(i);
                    XpDrop xp = new XpDrop(t.getX(), t.getY());
                    game.getXpDrops().add(xp);
                    game.getTentacles().remove(i.intValue());
                } catch (Exception e) {
                }
            }
            for (Integer i : eyBatIndexToRemove) {
                try {
                    EyeBat e = game.getEyeBats().get(i);
                    XpDrop xp = new XpDrop(e.getX(), e.getY());
                    game.getXpDrops().add(xp);
                    game.getEyeBats().remove(i.intValue());
                } catch (Exception e) {
                }
            }
        }
        for (Integer i : bulletsIndexToRemove) {
            try {
                bullets.remove(i.intValue());
            } catch (IndexOutOfBoundsException e) {

            }
        }
    }

    private boolean isBulletOffScreen(Bullet bullet) {
        float x = bullet.getSprite().getX();
        float y = bullet.getSprite().getY();
        float width = bullet.getSprite().getWidth();
        float height = bullet.getSprite().getHeight();

        return x + width < 0 ||
                x > Gdx.graphics.getWidth() ||
                y + height < 0 ||
                y > Gdx.graphics.getHeight();
    }

    public void dispose() {
        texture.dispose();
    }

    public Texture getTexture() {
        return texture;
    }
}
