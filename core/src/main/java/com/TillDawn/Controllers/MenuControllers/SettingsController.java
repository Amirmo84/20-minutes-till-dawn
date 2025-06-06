package com.TillDawn.Controllers.MenuControllers;

import com.TillDawn.Models.App;
import com.TillDawn.Models.Enums.Paths;
import com.TillDawn.Models.GameAssetManager;
import com.TillDawn.Models.User;
import com.TillDawn.TillDawn;
import com.TillDawn.Views.MainMenu;
import com.TillDawn.Views.SettingsView;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
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
    private Label musicLabel = new Label("", skin);
    private Label musicVolume = new Label("", skin);

    private final TextButton back = new TextButton("Back", skin);
    private final TextButton muteSFX = new TextButton("Mute SFX", skin);
    private final TextButton changeControllers = new TextButton("Change movement", skin);
    private final TextButton changeReload = new TextButton("Change reload", skin);
    private final TextButton changeAutoAim = new TextButton("Change auto aim", skin);
    private final TextButton autoReload = new TextButton("Toggle auto reload", skin);
    private final TextButton greyScale = new TextButton("Toggle gray scale", skin);
    private final TextButton music1 = new TextButton("Music 1", skin);
    private final TextButton music2 = new TextButton("Music 2", skin);

    private Slider volumeSlider = new Slider(0f, 1f, 0.01f, false, skin);

    private Table table = new Table(skin);

//    private User user = App.getApp().getLoggedInUser();

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
        table.add(volumeSlider).width(400);
        table.add(musicVolume).row();
        table.row().padTop(30);
        Table musicTable = new Table();
        musicTable.add(music1).height(100).padRight(20);
        musicTable.add(music2).height(100);
        table.add(musicTable).row();
        table.add(musicLabel).row();
        table.add(muteSFX).height(100).row();
        Table subTable = new Table();
        changeControllers.getLabel().setFontScale(0.8f);
        changeAutoAim.getLabel().setFontScale(0.8f);
        changeReload.getLabel().setFontScale(0.8f);
        subTable.add(changeControllers).height(100).padRight(20);
        subTable.add(changeReload).height(100).padRight(20);
        subTable.add(changeAutoAim).height(100);
        table.add(subTable).row();
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
//                musicVolume.setText(String.format("%d%%", (int) volume * 100));
//                game.getScreen().dispose();
//                game.setScreen(new SettingsView());
            }
        });
    }

    private void handleMute(){
        muteSFX.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                User user = App.getApp().getLoggedInUser();
                if (muteSFX.getText().toString().equals("Mute SFX")){
                    user.getSfxManager().setSfxEnabled(false);
                    muteSFX.setText("Unmute SFX");
                } else {
                    user.getSfxManager().setSfxEnabled(true);
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
                User user = App.getApp().getLoggedInUser();
                if (user.isControlDefault()){
                    user.setControlDefault(false);
                    resultLabel.setText("You can now move with arrows!");
                    user.getKeyManagement().changeMovement();
                } else {
                    user.setControlDefault(true);
                    resultLabel.setText("You can now move with w-a-s-d!");
                    user.getKeyManagement().changeMovement();
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
                User user = App.getApp().getLoggedInUser();
                if (!user.isReloadAuto()){
                    user.setReloadAuto(true);
                    autoReload.setText("Untoggle auto reload");
                } else {
                    user.setReloadAuto(false);
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
                User user = App.getApp().getLoggedInUser();
                if (!user.isGray()){
                    user.setGray(true);
                    greyScale.setText("Untoggle grey scale");
                } else {
                    user.setGray(false);
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
                musicLabel.setText("");
                game.getScreen().dispose();
                game.setScreen(new MainMenu());
            }
        });
    }

    private void handleMusic1(){
        music1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Music music = Gdx.audio.newMusic(Gdx.files.internal(Paths.MAINSONG.getPath()));
                if (!game.getMusicManager().getPath().equals(Paths.MAINSONG.getPath())){
                    game.getMusicManager().play(music, true);
                    musicLabel.setText("Music 1 is now playing!");
                    game.getMusicManager().setPath(Paths.MAINSONG.getPath());
                }else
                    musicLabel.setText("Music 1 is already playing!");
                game.getScreen().dispose();
                game.setScreen(new SettingsView());
            }
        });
    }

    private void handleMusic2(){
        music2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Music music = Gdx.audio.newMusic(Gdx.files.internal(Paths.SONG2.getPath()));
                if (!game.getMusicManager().getPath().equals(Paths.SONG2.getPath())){
                    game.getMusicManager().play(music, true);
                    musicLabel.setText("Music 2 is now playing!");
                    game.getMusicManager().setPath(Paths.SONG2.getPath());
                } else
                    musicLabel.setText("Music 2 is already playing!");
                game.getScreen().dispose();
                game.setScreen(new SettingsView());
            }
        });
    }

    private void handleChangeReload(){
        changeReload.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (App.getApp().getLoggedInUser().getKeyManagement().getReloadButton() == Input.Keys.R) {
                    resultLabel.setText("You can now reload with m");
                    App.getApp().getLoggedInUser().getKeyManagement().setReloadButton(Input.Keys.M);
                } else {
                    resultLabel.setText("You can now reload with r");
                    App.getApp().getLoggedInUser().getKeyManagement().setReloadButton(Input.Keys.R);
                }
                game.getScreen().dispose();
                game.setScreen(new SettingsView());
            }
        });
    }

    private void handleChangeAutoAim(){
        changeAutoAim.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (App.getApp().getLoggedInUser().getKeyManagement().getAutoAimButton() == Input.Keys.SPACE){
                    resultLabel.setText("You can now auto aim with P!");
                    App.getApp().getLoggedInUser().getKeyManagement().setAutoAimButton(Input.Keys.P);
                } else {
                    resultLabel.setText("You can now auto aim with space!");
                    App.getApp().getLoggedInUser().getKeyManagement().setAutoAimButton(Input.Keys.SPACE);
                }
                game.getScreen().dispose();
                game.setScreen(new SettingsView());
            }
        });
    }

    public void handleBasedOnUser(){
        User user = App.getApp().getLoggedInUser();
        if (user.isGray()){
            greyScale.setText("Untoggle grey scale");
        } else {
            greyScale.setText("Toggle grey scale");
        }
        if (user.getSfxManager().isSfxEnabled()){
            muteSFX.setText("Mute SFX");
        } else {
            muteSFX.setText("Unute SFX");
        }
        if (user.isReloadAuto()){
            autoReload.setText("Untoggle auto reload");
        } else {
            autoReload.setText("Toggle auto reload");
        }
    }

    public void handleButtons(){
        handleSlider();
        handleMute();
        handleBack();
        handleControllers();
        handleReload();
        handleGreyScale();
        handleMusic1();
        handleMusic2();
        handleChangeAutoAim();
        handleChangeReload();
    }

    public Table getTable() {
        return table;
    }
}
