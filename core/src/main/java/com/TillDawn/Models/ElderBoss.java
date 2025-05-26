package com.TillDawn.Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class ElderBoss {
    private Animation<TextureRegion> animation;
    private Rectangle rectangle;
    private float x;
    private float y;
    private float width;
    private float height;
    private Rectangle bounds;
    private float stateTime = 0;
    private float hp = 400f;
    private float walkStateTime = 0;

    public ElderBoss(float x, float y) {
        this.x = x;
        this.y = y;
        this.animation = GameAssetManager.getManager().getBossAnimation();
        this.width = GameAssetManager.getManager().getBossFrames()[0].getRegionWidth();
        this.height = GameAssetManager.getManager().getBossFrames()[0].getRegionHeight();
        this.rectangle = new Rectangle(x, y, width, height);
        this.bounds = new Rectangle(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public float getStateTime() {
        return stateTime;
    }

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }

    public float getHp() {
        return hp;
    }

    public void setHp(float hp) {
        this.hp = hp;
    }

    public float getWalkStateTime() {
        return walkStateTime;
    }

    public void setWalkStateTime(float walkStateTime) {
        this.walkStateTime = walkStateTime;
    }

    public void modifyStateTime(float time){
        this.stateTime += time;
    }
}
