package com.TillDawn.Controllers;

import com.TillDawn.Models.GameAssetManager;
import com.TillDawn.TillDawn;
import com.TillDawn.Views.SignUpMenu;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MainMenuController {
    private final TillDawn game = TillDawn.getTillDawn();

    private final Skin skin  = GameAssetManager.getManager().getSkin();;
    private final Image image  = GameAssetManager.getManager().getImage();;
    private Table table = new Table(skin);

    private final TextButton signIn = new TextButton("Sign in", skin);;
    private final TextButton settings = new TextButton("Settings", skin);;

    public MainMenuController() {
        table.addActor(image);
        image.setPosition(0, 0);
        image.setSize((float) Gdx.graphics.getWidth(), (float) Gdx.graphics.getHeight());
        table.setFillParent(true);
        table.addActor(signIn);
        signIn.setPosition(10, (float) Gdx.graphics.getHeight() - signIn.getHeight() - 10);
        table.addActor(settings);
        settings.setPosition(10, (float) Gdx.graphics.getHeight() - settings.getHeight() - 110);
    }

    public Table getTable() {
        return table;
    }

    private void handleSignInButton(){
        signIn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.getScreen().dispose();
                game.setScreen(new SignUpMenu());
            }
        });
    }

    public void handleButtons(){
        handleSignInButton();
    }
}
