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
    IDLE0("Hero/"),
    TREE("Images/Sprite/T_TreeMonster_"),
    DROP("Images/Sprite/T_EyeBat_EM.png"),
    TENTACLE("Images/Sprite/BrainMonster_"),
    DAMAGE("Images/Sprite/ShoggothWindup.png"),
    EYEBAT("Images/Sprite/T_EyeBat_"),
    OPPBULLET("Images/Sprite/T_BatGun_SS_2.png"),
    SHOOT("AudioClip/Big_Weapon_Whoosh.wav"),
    EXPLOSION("Images/Sprite/ExplosionFX_"),
    EXPLOSIONSOUND("AudioClip/Explosion_Blood_01.wav"),
    RELOAD("Images/Sprite/RevolverReload_"),
    FONT("Font/ChevyRay - Lantern.ttf"),
    CLICK("AudioClip/click.wav"),
    ELDERBOSS("Images/Sprite/T_HasturBoss_"),
    WIN("AudioClip/YouWin.wav"),
    LOSE("AudioClip/YouLose.wav"),
    LevelUp("AudioClip/Powerup.wav"),
    PregameBackground("BackGround/featured_com.Flanne.MinutesTillDawn.roguelike.shooting.gp_-1.jpg"),
    OthersBackground("BackGround/20-minutes-till-dawn-huwun.png"),
    RELOADSOUND("AudioClip/Weapon_Shotgun_Reload.wav")
    ;

    private final String path;

    Paths(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
