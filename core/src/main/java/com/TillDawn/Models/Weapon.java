package com.TillDawn.Models;

import com.TillDawn.Models.Enums.Paths;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Weapon {
    private final Texture texture = new Texture(Paths.WEAPON.getPath());
    private Sprite sprite = new Sprite(texture);
    private int ammo;

    public Weapon(int amount){
        sprite.setX((float) Gdx.graphics.getWidth() / 2 );
        sprite.setY((float) Gdx.graphics.getHeight() / 2);
        sprite.setSize(50,50);
        this.ammo = amount;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public int getAmmo() {
        return ammo;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
