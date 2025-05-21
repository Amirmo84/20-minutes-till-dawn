package com.TillDawn.Models.Enums;

public enum Paths {
    SKIN("skin/pixthulhu-ui.json"),
    MAINMENUBACKIMAGE("BackGround/mainMenubackgroundImage.png"),
    AVATAR("profiles/"),
    BULLET("Images/Sprite/Icon_Bullet_Storm.png"),
    WEAPON("Images/Sprite/SMGStill.png"),
    CURSOR("Images/Sprite/T_CursorSprite.png")
    ;

    private final String path;

    Paths(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
