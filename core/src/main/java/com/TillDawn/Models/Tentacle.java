package com.TillDawn.Models;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Tentacle {
    private Animation<TextureRegion> animation;
    private float x;
    private float y;
    private float width;
    private float height;
    private Rectangle rectangle;
    private float hp = 25;
    private float stateTime = 0;
    private Color color = null;

    public Tentacle(float x, float y) {
        this.animation = GameAssetManager.getManager().getTentacleAnimation();
        this.x = x;
        this.y = y;
        width = GameAssetManager.getManager().getTentacleFrames()[0].getRegionWidth();
        height = GameAssetManager.getManager().getTentacleFrames()[0].getRegionHeight();
        rectangle = new Rectangle(x, y, width, height);
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

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public float getHp() {
        return hp;
    }

    public void setHp(float hp) {
        this.hp = hp;
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
