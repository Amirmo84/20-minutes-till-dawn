package com.TillDawn.Controllers.MenuControllers;

import com.TillDawn.Models.GameAssetManager;
import com.TillDawn.TillDawn;
import com.TillDawn.Views.SettingsView;
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

    private final TextButton signIn = new TextButton("Sign in", skin);
    private final TextButton settings = new TextButton("Settings", skin);
    private final TextButton profile = new TextButton("Profile menu", skin);
    private final TextButton preGameMenu = new TextButton("Pre-game menu", skin);
    private final TextButton leaderboard = new TextButton("Leaderboard", skin);
    private final TextButton hint = new TextButton("Hint menu", skin);
    private final TextButton logout = new TextButton("Log out", skin);
    private final TextButton exit = new TextButton("Exit", skin);

    public MainMenuController() {
        table.addActor(image);
        image.setPosition(0, 0);
        image.setSize((float) Gdx.graphics.getWidth(), (float) Gdx.graphics.getHeight());
        table.setFillParent(true);
        table.center();
        handleFontScales();
        table.addActor(signIn);
        signIn.setPosition(10, (float) Gdx.graphics.getHeight() - signIn.getHeight() - 10);
        table.addActor(settings);
        settings.setPosition(10, (float) Gdx.graphics.getHeight() - settings.getHeight() - 110);
        table.addActor(profile);
        profile.setPosition(10, (float) Gdx.graphics.getHeight() - profile.getHeight() - 210);
        table.addActor(preGameMenu);
        preGameMenu.setPosition(10, (float) Gdx.graphics.getHeight() - preGameMenu.getHeight() - 310);
        table.addActor(leaderboard);
        leaderboard.setPosition(10, (float) Gdx.graphics.getHeight() - leaderboard.getHeight() - 410);
        table.addActor(hint);
        hint.setPosition(10, (float) Gdx.graphics.getHeight() - hint.getHeight() - 510);
        table.addActor(logout);
        logout.setPosition(10, 10);
        table.add(exit);
    }

    private void handleFontScales(){
        signIn.getLabel().setFontScale(0.8f);
        settings.getLabel().setFontScale(0.8f);
        profile.getLabel().setFontScale(0.8f);
        profile.setWidth(300);
        preGameMenu.getLabel().setFontScale(0.8f);
        preGameMenu.setWidth(350);
        leaderboard.getLabel().setFontScale(0.8f);
        leaderboard.setWidth(300);
        hint.getLabel().setFontScale(0.8f);
        logout.getLabel().setFontScale(0.8f);
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

    private void handleSettings(){
        settings.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.getScreen().dispose();
                game.setScreen(new SettingsView());
            }
        });
    }

    private void handleExit(){
        exit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
    }

    public void handleButtons(){
        handleSignInButton();
        handleSettings();
        handleExit();
    }
}
