package com.TillDawn.Controllers.GameControllers;

import com.TillDawn.Models.GameAssetManager;
import com.TillDawn.Models.Player;
import com.TillDawn.TillDawn;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;

public class PlayerController {
    private Player player;

    public PlayerController(Player player) {
        this.player = player;
    }

    public void update(){
        player.getPlayerSprite().draw(TillDawn.getTillDawn().getBatch());

        if(player.isPlayerIdle()){
            idleAnimation();
        }

        handlePlayerInput();
    }

    public void handlePlayerInput(){
        if (Gdx.input.isKeyPressed(Input.Keys.W)){
            player.setPosY(player.getPosY() - player.getSpeed());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)){
            player.setPosX(player.getPosX() - player.getSpeed());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)){
            player.setPosY(player.getPosY() + player.getSpeed());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)){
            player.setPosX(player.getPosX() + player.getSpeed());
            player.getPlayerSprite().flip(true, false);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            Gdx.app.exit();
        }
    }


    public void idleAnimation(){
        Animation<Texture> animation = GameAssetManager.getManager().getIdle_frames();

        player.getPlayerSprite().setRegion(animation.getKeyFrame(player.getTime()));

        if (!animation.isAnimationFinished(player.getTime())) {
            player.setTime(player.getTime() + Gdx.graphics.getDeltaTime());
        }
        else {
            player.setTime(0);
        }

        animation.setPlayMode(Animation.PlayMode.LOOP);
    }

    public Player getPlayer() {
        return player;
    }
}
