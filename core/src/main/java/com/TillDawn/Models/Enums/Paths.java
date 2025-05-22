package com.TillDawn.Models.Enums;

public enum Paths {
    SKIN("skin/pixthulhu-ui.json"),
    MAINMENUBACKIMAGE("BackGround/mainMenubackgroundImage.png"),
    AVATAR("profiles/"),
    BULLET("Images/Sprite/Icon_Bullet_Storm.png"),
    WEAPON("Images/Sprite/SMGStill.png"),
    CURSOR("Images/Sprite/T_CursorSprite.png"),
    MAINSONG("Songs/track1.mp3"),
    GRAYSCALE("GrayScale/"),
    SETTINGSBACKGROUND("BackGround/SettingsMenu.png"),
    SONG2("Songs/track2.mp3"),
    GAMEBACKGROUND("BackGround/background.png"),
    IDLE0("Hero/")
    ;

    private final String path;

    Paths(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
