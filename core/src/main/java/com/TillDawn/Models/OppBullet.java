package com.TillDawn.Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class OppBullet {
    private Texture texture;
    private Sprite sprite;
    private Rectangle rectangle;
    private float damage;
    private float x;
    private float y;
    private Vector2 direction;

    public OppBullet(float x, float y, float x1, float y1) {
        this.damage = .1f;
        this.x = x;
        this.y = y;
        texture = GameAssetManager.getManager().getOppBullet();
        sprite = new Sprite(texture);
        sprite.setSize(20, 20);
        sprite.setX(x);
        sprite.setY(y);
        rectangle = new Rectangle(x, y, 20, 20);

        direction = new Vector2(
                x,
                y
        ).nor();
//        direction = new Vector2(
//                x1 - x,
//                y1 - y
//        ).nor();
//        direction = new Vector2(
//                Gdx.graphics.getWidth() / 2f - x1,
//                Gdx.graphics.getHeight() / 2f - y1
//        ).nor();
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Vector2 getDirection() {
        return direction;
    }

    public void setDirection(Vector2 direction) {
        this.direction = direction;
    }
}
