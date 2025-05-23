package com.TillDawn.Models.Enums;

import com.badlogic.gdx.utils.Array;


public enum Heroes {
    SHANA("Shana", 4, 4),
    DIAMOND("Diamond", 7, 1),
    SCARLETT("Scarlett", 3, 5),
    LILITH("Lilith", 5, 3),
    DASHER("Dasher", 2, 10),
    ;

    private final String name;
    private final int hp;
    private final int speed;

    Heroes(String name, int hp, int speed) {
        this.name = name;
        this.hp = hp;
        this.speed = speed;
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public int getSpeed() {
        return speed;
    }

    public static Array<String> heroesNames(){
        Array<String> names = new Array<>();
        for (Heroes heroes : Heroes.values()){
            names.add(heroes.getName());
        }
        return names;
    }

    public static Heroes getHeroByName(String name) {
        for (Heroes h : Heroes.values()) {
            if (h.getName().equals(name)) {
                return h;
            }
        }
        return null;
    }
}
