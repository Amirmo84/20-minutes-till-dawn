package com.TillDawn.Controllers.GameControllers;

import com.TillDawn.Models.Enums.Paths;
import com.TillDawn.Models.Player;
import com.TillDawn.TillDawn;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

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
//        backgroundX = playerController.getPlayer().getPosX();
//        backgroundY = playerController.getPlayer().getPosY();
//        TillDawn.getTillDawn().getBatch().draw(backgroundTexture, backgroundX, backgroundY);
//        Player player = playerController.getPlayer();
//
//        // Clamp camera to not show outside the background
//        float cameraX = MathUtils.clamp(player.getPosX(), viewport.getWorldWidth() / 2f, backgroundTexture.getWidth() - viewport.getWorldWidth() / 2f);
//        float cameraY = MathUtils.clamp(player.getPosY(), viewport.getWorldHeight() / 2f, backgroundTexture.getHeight() - viewport.getWorldHeight() / 2f);
//        camera.position.set(cameraX, cameraY, 0);
//        camera.update();
//
//        TillDawn.getTillDawn().getBatch().setProjectionMatrix(camera.combined);
//
//        TillDawn.getTillDawn().getBatch().draw(backgroundTexture, 0, 0);
//
//        player.getPlayerSprite().setPosition(player.getPosX(), player.getPosY());
////        player.getPlayerSprite().draw(TillDawn.getTillDawn().getBatch());
        TillDawn.getTillDawn().getBatch().draw(backgroundTexture, 0, 0);
    }

    public Texture getBackgroundTexture() {
        return backgroundTexture;
    }
}
