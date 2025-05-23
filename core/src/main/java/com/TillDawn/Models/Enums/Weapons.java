package com.TillDawn.Models.Enums;

import com.badlogic.gdx.utils.Array;

public enum Weapons {
    REVOLVER("Revolver", 20, 1, 1, 6),
    SHOTGUN("Shotgun", 10, 4, 1, 2),
    SMG("Smg", 8, 1, 2, 24);
    private final String name;
    private final int damage;
    private final int projectile;
    private final int timeReload;
    private final int ammoMax;

    Weapons(String name, int damage, int projectile, int timeReload, int ammoMax) {
        this.name = name;
        this.damage = damage;
        this.projectile = projectile;
        this.timeReload = timeReload;
        this.ammoMax = ammoMax;
    }

    public String getName() {
        return name;
    }

    public int getDamage() {
        return damage;
    }

    public int getProjectile() {
        return projectile;
    }

    public int getTimeReload() {
        return timeReload;
    }

    public int getAmmoMax() {
        return ammoMax;
    }

    public static Array<String> weaponsNames(){
        Array<String> names = new Array<>();
        for (Weapons weapons : Weapons.values()){
            names.add(weapons.getName());
        }
        return names;
    }

    public static Weapons getWeaponByName(String name) {
        for (Weapons h : Weapons.values()) {
            if (h.getName().equals(name)) {
                return h;
            }
        }
        return null;
    }
}
