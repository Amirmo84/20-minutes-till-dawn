package com.TillDawn.Models.Enums;

public enum Paths {
    SKIN("skin/pixthulhu-ui.json"),
    MAINMENUBACKIMAGE("BackGround/mainMenubackgroundImage.png"),
    AVATAR("profiles/")
    ;

    private final String path;

    Paths(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
