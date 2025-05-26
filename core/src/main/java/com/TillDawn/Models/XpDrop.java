package com.TillDawn.Models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class XpDrop {
    private Texture texture;
    private Rectangle rectangle;
    private float x;
    private float y; //TODO: add gridPos here
    private float width;
    private float height;

    public XpDrop(float x, float y) {
        this.x = x;
        this.y = y;
        this.texture = GameAssetManager.getManager().getXpDrop();
        this.width = texture.getWidth() * 2;
        this.height = texture.getHeight() * 2;
        this.rectangle = new Rectangle(x , y, width , height);
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
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
}
