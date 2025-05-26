package com.TillDawn.Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Bullet {
    private Texture texture; //constant
    private Sprite sprite; //constant
    private int damage; //TODO: keep it here or not?
    private Rectangle rectangle;
    private int x;
    private int y;
    private Vector2 direction;

    public Bullet(int x, int y, float srcX, float srcY) {
        this.damage = 5;
        this.x = x;
        this.y = y;
        texture = GameAssetManager.getManager().getBullet();
        sprite = new Sprite(texture);
        sprite.setSize(20, 20);
//        sprite.setX(Gdx.graphics.getWidth() / 2f); //middle
//        sprite.setY(Gdx.graphics.getHeight() / 2f); //middle
        sprite.setPosition((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2); //TODO: handle Properly
        rectangle = new Rectangle(x, y, 20, 20);
        direction = new Vector2(srcX - x, srcY - y).nor(); // kiarash
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

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Vector2 getDirection() {
        return direction;
    }

    public void setDirection(Vector2 direction) {
        this.direction = direction;
    }
}
