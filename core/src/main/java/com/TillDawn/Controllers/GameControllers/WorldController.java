package com.TillDawn.Controllers.GameControllers;

import com.TillDawn.Models.Enums.Paths;
import com.TillDawn.TillDawn;
import com.badlogic.gdx.graphics.Texture;

public class WorldController {
    private PlayerController playerController;
    private Texture backgroundTexture;
    private float backgroundX = 0;
    private float backgroundY = 0;

    public WorldController(PlayerController playerController) {
        this.backgroundTexture = new Texture(Paths.GAMEBACKGROUND.getPath());
        this.playerController = playerController;
    }

    public void update() {
        backgroundX = playerController.getPlayer().getPosX();
        backgroundY = playerController.getPlayer().getPosY();
        TillDawn.getTillDawn().getBatch().draw(backgroundTexture, backgroundX, backgroundY);
    }

}
