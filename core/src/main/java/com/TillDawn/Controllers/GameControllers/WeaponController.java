package com.TillDawn.Controllers.GameControllers;

import com.TillDawn.Models.Bullet;
import com.TillDawn.Models.Weapon;
import com.TillDawn.TillDawn;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class WeaponController {
    private TillDawn game = TillDawn.getTillDawn();

    private Weapon weapon;
    private ArrayList<Bullet> bullets = new ArrayList<>();
//    private PlayerController

    public WeaponController(Weapon weapon) {
        this.weapon = weapon;
    }

    public void update(){
        weapon.getSprite().draw(game.getBatch());
        updateBullets();
    }

    public void handleWeaponRotation(int x, int y) {
        Sprite weaponSprite = weapon.getSprite();

        float weaponCenterX = (float) Gdx.graphics.getWidth() / 2;
        float weaponCenterY = (float) Gdx.graphics.getHeight() / 2;

        float angle = (float) Math.atan2(y - weaponCenterY, x - weaponCenterX);

        weaponSprite.setRotation((float) (MathUtils.PI - angle * MathUtils.radiansToDegrees));
    }

    public void handleWeaponShoot(int x, int y){
        bullets.add(new Bullet(x, y));

    }

    public void updateBullets() {
        for(Bullet b : bullets) {
            b.getSprite().draw(game.getBatch());
            Vector2 direction = new Vector2(
                    Gdx.graphics.getWidth()/2f - b.getX(),
                    Gdx.graphics.getHeight()/2f - b.getY()
            ).nor();

            b.getSprite().setX(b.getSprite().getX() - direction.x * 5);
            b.getSprite().setY(b.getSprite().getY() + direction.y * 5);
        }
    }

    public Weapon getWeapon() {
        return weapon;
    }
}
