package com.TillDawn.Models;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import java.beans.Transient;
import java.util.ArrayList;

public class EyeBat {
    private Animation<TextureRegion> animation;
    private Rectangle rectangle;
    private float x;
    private float y;
    private float width;
    private float height;
    private Color color = null;
    private ArrayList<OppBullet> bullets = new ArrayList<>();
    private float hp = 50;
    private float stateTime = 0;
    private float shotTime = 0;

    public EyeBat(float x, float y) {
        this.x = x;
        this.y = y;
        this.animation = GameAssetManager.getManager().getEyeBatAnimation();
        this.width = GameAssetManager.getManager().getEyeBatFrames()[0].getRegionWidth();
        this.height = GameAssetManager.getManager().getEyeBatFrames()[0].getRegionHeight();
        this.rectangle = new Rectangle(x, y, width, height);
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

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public ArrayList<OppBullet> getBullets() {
        return bullets;
    }

    public void setBullets(ArrayList<OppBullet> bullets) {
        this.bullets = bullets;
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

    public float getShotTime() {
        return shotTime;
    }

    public void setShotTime(float shotTime) {
        this.shotTime = shotTime;
    }

    public void modifyStateTime(float time){
        this.stateTime += time;
    }
}
