package com.TillDawn.Models;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Explosion {
    private Animation<TextureRegion> animation;
    private float x;
    private float y;
    private float width ;
    private float height ;
    private float stateTime = 0;
    private boolean isFinished = false;

    public Explosion(float x, float y) {
        this.x = x;
        this.y = y;
        this.animation = GameAssetManager.getManager().getExplosionAnimation();
        this.width = GameAssetManager.getManager().getExplosionFrames()[0].getRegionWidth();
        this.height = GameAssetManager.getManager().getExplosionFrames()[0].getRegionHeight();
    }

    public Animation<TextureRegion> getAnimation() {
        return animation;
    }

    public void setAnimation(Animation<TextureRegion> animation) {
        this.animation = animation;
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
