package com.TillDawn.Controllers.MenuControllers;

import com.TillDawn.Models.GameAssetManager;
import com.TillDawn.TillDawn;
import com.TillDawn.Views.MainMenu;
import com.TillDawn.Views.SettingsView;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class SettingsController {
    private TillDawn game = TillDawn.getTillDawn();
    private final Skin skin = GameAssetManager.getManager().getSkin();;
    private final Image image = GameAssetManager.getManager().getSettingsImage();

    private final Label menuTitle = new Label("Settings", skin);
    private final Label change = new Label("Change volume: ", skin);
    private Label resultLabel = new Label("", skin);

    private final TextButton back = new TextButton("Back", skin);
    private final TextButton muteSFX = new TextButton("Mute SFX", skin);
    private final TextButton changeControllers = new TextButton("Change controllers", skin);
    private final TextButton autoReload = new TextButton("Toggle auto reload", skin);
    private final TextButton greyScale = new TextButton("Toggle gray scale", skin);

    private Slider volumeSlider = new Slider(0f, 1f, 0.01f, false, skin);

    private Table table = new Table(skin);

    public SettingsController() {
        table.addActor(image);
        image.setPosition(0 ,0);
        image.setSize((float) Gdx.graphics.getWidth(), (float) Gdx.graphics.getHeight());
        table.setFillParent(true);
        table.center();

        volumeSlider.setValue(game.getMusicManager().getVolume());

        menuTitle.setFontScale(1.5f);
        table.add(menuTitle).row();
        table.row().padTop(20);
        table.add(change).row();
        table.add(volumeSlider).width(400).row();
        table.row().padTop(30);
        table.add(muteSFX).height(100).row();
        table.add(changeControllers).height(100).row();
        table.add(resultLabel).row();
        table.add(autoReload).height(100).row();
        table.add(greyScale).height(100).row();
        table.add(back).height(100);
    }

    private void handleSlider(){
        volumeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                float volume = volumeSlider.getValue();
                game.getMusicManager().setVolume(volume);
            }
        });
    }

    private void handleMute(){
        muteSFX.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (muteSFX.getText().toString().equals("Mute SFX")){
                    game.getSfxManager().setSfxEnabled(false);
                    muteSFX.setText("Unmute SFX");
                } else {
                    game.getSfxManager().setSfxEnabled(true);
                    muteSFX.setText("Mute SFX");
                }
                game.getScreen().dispose();
                game.setScreen(new SettingsView());
            }
        });
    }

    private void handleControllers(){
        changeControllers.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (game.isControlDefault()){
                    game.setControlDefault(false);
                    resultLabel.setText("You can now shoot using M!");
                } else {
                    game.setControlDefault(true);
                    resultLabel.setText("You can now shoot using touch down!");
                }
                game.getScreen().dispose();
                game.setScreen(new SettingsView());
            }
        });
    }

    private void handleReload(){
        autoReload.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //TODO handle it properly

                if (!game.isReloadAuto()){
                    game.setReloadAuto(true);
                    autoReload.setText("Untoggle auto reload");
                } else {
                    game.setReloadAuto(false);
                    autoReload.setText("Toggle auto reload");
                }
                game.getScreen().dispose();
                game.setScreen(new SettingsView());
            }
        });
    }

    private void handleGreyScale(){
        greyScale.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!game.isGray()){
                    game.setGray(true);
                    greyScale.setText("Untoggle grey scale");
                } else {
                    game.setGray(false);
                    greyScale.setText("Toggle grey scale");
                }
                game.getScreen().dispose();
                game.setScreen(new SettingsView());
            }
        });
    }

    private void handleBack(){
        back.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                resultLabel.setText("");
                game.getScreen().dispose();
                game.setScreen(new MainMenu());
            }
        });
    }

    public void handleButtons(){
        handleSlider();
        handleMute();
        handleBack();
        handleControllers();
        handleReload();
        handleGreyScale();
    }

    public Table getTable() {
        return table;
    }
}
