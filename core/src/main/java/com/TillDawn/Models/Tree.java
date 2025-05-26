package com.TillDawn.Models;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Tree {
    private Animation<TextureRegion> animation = GameAssetManager.getManager().getTreeAnimation();
    private Rectangle rectangle;
    private float damage = 0.1f; //TODO: change
    private float x;
    private float y; //TODO add gridPos here
    private float width;
    private float height;
    private float stateTime;

    public Tree(float x, float y) {
        this.x = x;
        this.y = y;
        width = GameAssetManager.getManager().getTreeFrames()[0].getRegionWidth() * 1.2f;
        height = GameAssetManager.getManager().getTreeFrames()[0].getRegionHeight() * 1.2f;
        rectangle = new Rectangle(x, y, width, height); //TODO: change it Later
    }

    public Animation<TextureRegion> getAnimation() {
        return animation;
    }

    public void setAnimation(Animation<TextureRegion> animation) {
        this.animation = animation;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
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

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getStateTime() {
        return stateTime;
    }

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }

    public void modifyStateTime(float time){
        this.stateTime += time;
    }
}
