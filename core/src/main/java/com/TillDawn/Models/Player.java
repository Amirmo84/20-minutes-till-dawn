package com.TillDawn.Models;

import com.TillDawn.Models.Enums.Heroes;
import com.TillDawn.Models.Enums.Weapons;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import java.util.HashSet;

public class Player {
    private transient TextureRegion playerTexture;
    private final transient User user;
    private transient Sprite playerSprite;
    private Heroes hero;
    private Weapons weapon;
    private GridPos gridPos;
    private int currentAmmo;
    private int level = 1; //TODO: enum it
    private float hp;
    private int xp;
    private int kills = 0;
    private float hurt = 0f;
    private boolean isHurt = false;
    private float hurtTime = 0f;
    private boolean levelUp = false;
    private float damageMp = 1f;
    private int ammo;
    private int projectileAddition = 0;
    private int maxAmmoAddition = 0;
    private float speedMp = 1f;
    private float maxHp;
    private boolean isPlayerIdle = true;
    private boolean isPlayerRunning = false;
    private HashSet<Integer> abilities = new HashSet<Integer>();
    private transient Animation<TextureRegion> animation;
    private transient Rectangle rectangle;
    private boolean isReloading = false;
    private float width;
    private float height;
    private float time;
    private Color color = null;

    public Player(User user){
        this.user = user;
    }

    public TextureRegion getPlayerTexture() {
        return playerTexture;
    }

    public void setPlayerTexture(TextureRegion playerTexture) {
        this.playerTexture = playerTexture;
    }

    public User getUser() {
        return user;
    }

    public Sprite getPlayerSprite() {
        playerSprite.setPosition((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2);
        playerSprite.setSize(playerTexture.getRegionWidth() * 3, playerTexture.getRegionHeight() * 3);
        return playerSprite;
    }

    public void setPlayerSprite(Sprite playerSprite) {
        this.playerSprite = playerSprite;
    }

    public Heroes getHero() {
        return hero;
    }

    public void setHero(Heroes hero) {
        this.hero = hero;
    }

    public Weapons getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapons weapon) {
        this.weapon = weapon;
    }

    public GridPos getGridPos() {
        return gridPos;
    }

    public void setGridPos(GridPos gridPos) {
        this.gridPos = gridPos;
    }

    public int getCurrentAmmo() {
        return currentAmmo;
    }

    public void setCurrentAmmo(int currentAmmo) {
        this.currentAmmo = currentAmmo;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public float getHp() {
        return hp;
    }

    public void setHp(float hp) {
        if (hp < this.hp && isHurt) {
            return;
        }
        isHurt = true;
        this.hp = Math.min(Math.max(hp, 0), maxHp);
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        if (xp > level * 20) {
            user.getSfxManager().setSound(GameAssetManager.getManager().getLevelUp());
            user.getSfxManager().play();
            level++;
            levelUp = true;
            xp = xp - (level - 1) * 20;
        }
        this.xp = xp;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public float getHurt() {
        return hurt;
    }

    public void setHurt(float hurt) {
        this.hurt = hurt;
    }

    public boolean isHurt() {
        return isHurt;
    }

    public void setHurt(boolean hurt) {
        isHurt = hurt;
    }

    public boolean isLevelUp() {
        return levelUp;
    }

    public void setLevelUp(boolean levelUp) {
        this.levelUp = levelUp;
    }

    public float getDamageMp() {
        return damageMp;
    }

    public void setDamageMp(float damageMp) {
        this.damageMp = damageMp;
    }

    public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    public int getProjectileAddition() {
        return projectileAddition;
    }

    public void setProjectileAddition(int projectileAddition) {
        this.projectileAddition = projectileAddition;
    }

    public int getMaxAmmoAddition() {
        return maxAmmoAddition;
    }

    public void setMaxAmmoAddition(int maxAmmoAddition) {
        this.maxAmmoAddition = maxAmmoAddition;
    }

    public float getSpeed() {
        return hero.getSpeed() * speedMp;
    }

    public void setSpeedMp(float speedMp) {
        this.speedMp = speedMp;
    }

    public boolean isPlayerIdle() {
        return isPlayerIdle;
    }

    public void setPlayerIdle(boolean playerIdle) {
        isPlayerIdle = playerIdle;
    }

    public boolean isPlayerRunning() {
        return isPlayerRunning;
    }

    public void setPlayerRunning(boolean playerRunning) {
        isPlayerRunning = playerRunning;
    }

    public HashSet<Integer> getAbilities() {
        return abilities;
    }

    public void setAbilities(HashSet<Integer> abilities) {
        this.abilities = abilities;
    }

    public Animation<TextureRegion> getAnimation() {
        //to avoid conflict we had to patterns something like singleton for animation and recangle
        if (animation == null){
            animation = GameAssetManager.getManager().getIdle_frames();
            width = animation.getKeyFrame(0).getRegionWidth() * 3f;
            height = animation.getKeyFrame(0).getRegionHeight() * 3f;
        }
        return animation;
    }

    public void setAnimation(Animation<TextureRegion> animation) {
        this.animation = animation;
    }

    public Rectangle getRectangle() {
        if (rectangle == null){
            rectangle = new Rectangle(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, width, height);
        }
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public boolean isReloading() {
        return isReloading;
    }

    public void setReloading(boolean reloading) {
        isReloading = reloading;
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

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public float getHurtTime() {
        return hurtTime;
    }

    public void setHurtTime(float hurtTime) {
        this.hurtTime = hurtTime;
    }

    public float getMaxHp(){
        return maxHp;
    }

    public void setMaxHp(float maxHp) {
        this.maxHp = maxHp;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
