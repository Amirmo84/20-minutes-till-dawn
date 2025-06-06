package com.TillDawn.Controllers.MenuControllers;

import com.TillDawn.Models.App;
import com.TillDawn.Models.Enums.Language;
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
    private final Skin skin = GameAssetManager.getManager().getSkin();
    private final Image image = GameAssetManager.getManager().getSettingsImage();

    private final Label menuTitle;
    private final Label change;
    private Label resultLabel;
    private Label musicLabel;
    private Label musicVolume;

    private final TextButton back;
    private final TextButton muteSFX;
    private final TextButton changeControllers;
    private final TextButton changeReload;
    private final TextButton changeAutoAim;
    private final TextButton autoReload;
    private final TextButton greyScale;
    private final TextButton music1;
    private final TextButton music2;
    private final SelectBox<String> languageSelect;

    private Slider volumeSlider = new Slider(0f, 1f, 0.01f, false, skin);
    private Table table = new Table(skin);

    public SettingsController() {
        User user = App.getApp().getLoggedInUser();
        
        // Initialize language selection
        languageSelect = new SelectBox<>(skin);
        String[] languages = new String[Language.values().length];
        for (int i = 0; i < Language.values().length; i++) {
            languages[i] = Language.values()[i].getDisplayName();
        }
        languageSelect.setItems(languages);
        languageSelect.setSelected(user.getLanguage().getDisplayName());

        // Initialize all UI elements with translations
        menuTitle = new Label(user.translate("menu.settings"), skin);
        change = new Label(user.translate("settings.volume"), skin);
        resultLabel = new Label("", skin);
        musicLabel = new Label("", skin);
        musicVolume = new Label("", skin);

        back = new TextButton(user.translate("button.back"), skin);
        muteSFX = new TextButton(user.getSfxManager().isSfxEnabled() ? 
            user.translate("settings.mute_sfx") : 
            user.translate("settings.unmute_sfx"), skin);
        changeControllers = new TextButton(user.translate("settings.controls"), skin);
        changeReload = new TextButton(user.translate("button.change") + " " + user.translate("settings.reload"), skin);
        changeAutoAim = new TextButton(user.translate("button.change") + " " + user.translate("settings.auto_aim"), skin);
        autoReload = new TextButton(user.isReloadAuto() ? 
            user.translate("settings.untoggle_auto_reload") : 
            user.translate("settings.toggle_auto_reload"), skin);
        greyScale = new TextButton(user.isGray() ? 
            user.translate("settings.ungray_scale") : 
            user.translate("settings.gray_scale"), skin);
        music1 = new TextButton(user.translate("settings.music") + " 1", skin);
        music2 = new TextButton(user.translate("settings.music") + " 2", skin);

        // Setup table layout
        setupTableLayout();
    }

    private void setupTableLayout() {
        User user = App.getApp().getLoggedInUser();
        
        table.addActor(image);
        image.setPosition(0, 0);
        image.setSize((float) Gdx.graphics.getWidth(), (float) Gdx.graphics.getHeight());
        table.setFillParent(true);
        table.center();

        volumeSlider.setValue(game.getMusicManager().getVolume());

        menuTitle.setFontScale(1.5f);
        table.add(menuTitle).row();
        
        // Language selection
        Label label = new Label(user.translate("settings.language"), skin);
        table.addActor(label);
        label.setPosition(80, (float) Gdx.graphics.getHeight() - 100);
//        table.add(new Label(user.translate("settings.language"), skin)).padTop(20).row();
        table.addActor(languageSelect);
        languageSelect.setWidth(200);
        languageSelect.setPosition(35, (float) Gdx.graphics.getHeight() - 200);
//        table.add(languageSelect).width(200).row();
        
        // Volume controls
        table.add(change).padTop(20).row();
        table.add(volumeSlider).width(400);
        table.add(musicVolume).row();
        
        // Music selection
        Table musicTable = new Table();
        musicTable.add(music1).height(100).padRight(20);
        musicTable.add(music2).height(100);
        table.add(musicTable).padTop(30).row();
        table.add(musicLabel).row();
        
        // Sound controls
        table.add(muteSFX).height(100).row();
        
        // Game controls
        Table controlsTable = new Table();
        changeControllers.getLabel().setFontScale(0.8f);
        changeAutoAim.getLabel().setFontScale(0.8f);
        changeReload.getLabel().setFontScale(0.8f);
        controlsTable.add(changeControllers).height(100).padRight(20);
        controlsTable.add(changeReload).height(100).padRight(20);
        controlsTable.add(changeAutoAim).height(100);
        table.add(controlsTable).row();
        table.add(resultLabel).row();
        
        // Additional settings
        table.add(autoReload).height(100).row();
        table.add(greyScale).height(100).row();
        
        // Back button
        table.add(back).height(100);
    }

    private void handleLanguageChange() {
        languageSelect.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                User user = App.getApp().getLoggedInUser();
                Language newLanguage = Language.fromDisplayName(languageSelect.getSelected());
                user.setLanguage(newLanguage);
                updateAllButtonStates();
                game.getScreen().dispose();
                game.setScreen(new SettingsView());
            }
        });
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
                if (user.getSfxManager().isSfxEnabled()) {
                    user.getSfxManager().setSfxEnabled(false);
                    muteSFX.setText(user.translate("settings.unmute_sfx"));
                } else {
                    user.getSfxManager().setSfxEnabled(true);
                    muteSFX.setText(user.translate("settings.mute_sfx"));
                }
//                updateAllButtonStates();
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
                if (user.isControlDefault()) {
                    user.setControlDefault(false);
                    resultLabel.setText(user.translate("settings.controls.arrows"));
                    changeControllers.setText(user.translate("settings.controls") + " (←↑→↓)");
                    user.getKeyManagement().changeMovement();
                } else {
                    user.setControlDefault(true);
                    resultLabel.setText(user.translate("settings.controls.default"));
                    changeControllers.setText(user.translate("settings.controls") + " (WASD)");
                    user.getKeyManagement().changeMovement();
                }
                updateAllButtonStates();
            }
        });
    }

    private void handleReload(){
        autoReload.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                User user = App.getApp().getLoggedInUser();
                if (!user.isReloadAuto()) {
                    user.setReloadAuto(true);
                    autoReload.setText(user.translate("settings.untoggle_auto_reload") + " ✓");
                } else {
                    user.setReloadAuto(false);
                    autoReload.setText(user.translate("settings.toggle_auto_reload"));
                }
//                updateAllButtonStates();
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
                if (!user.isGray()) {
                    user.setGray(true);
                    greyScale.setText(user.translate("settings.ungray_scale") + " ✓");
                } else {
                    user.setGray(false);
                    greyScale.setText(user.translate("settings.gray_scale"));
                }
//                updateAllButtonStates();
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

    private void handleMusic1() {
        music1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                User user = App.getApp().getLoggedInUser();
                Music music = Gdx.audio.newMusic(Gdx.files.internal(Paths.MAINSONG.getPath()));
                if (!game.getMusicManager().getPath().equals(Paths.MAINSONG.getPath())) {
                    game.getMusicManager().play(music, true);
                    musicLabel.setText(user.translate("settings.music.playing").replace("{0}", "1"));
                    game.getMusicManager().setPath(Paths.MAINSONG.getPath());
                    updateMusicButtonStates();
                } else {
                    musicLabel.setText(user.translate("settings.music.already_playing").replace("{0}", "1"));
                }
            }
        });
    }

    private void handleMusic2() {
        music2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                User user = App.getApp().getLoggedInUser();
                Music music = Gdx.audio.newMusic(Gdx.files.internal(Paths.SONG2.getPath()));
                if (!game.getMusicManager().getPath().equals(Paths.SONG2.getPath())) {
                    game.getMusicManager().play(music, true);
                    musicLabel.setText(user.translate("settings.music.playing").replace("{0}", "2"));
                    game.getMusicManager().setPath(Paths.SONG2.getPath());
                    updateMusicButtonStates();
                } else {
                    musicLabel.setText(user.translate("settings.music.already_playing").replace("{0}", "2"));
                }
            }
        });
    }

    private void handleChangeReload() {
        changeReload.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                User user = App.getApp().getLoggedInUser();
                if (App.getApp().getLoggedInUser().getKeyManagement().getReloadButton() == Input.Keys.R) {
                    resultLabel.setText(user.translate("settings.controls.reload").replace("{0}", "M"));
                    changeReload.setText(user.translate("button.change") + " " + user.translate("settings.reload") + " (M)");
                    App.getApp().getLoggedInUser().getKeyManagement().setReloadButton(Input.Keys.M);
                } else {
                    resultLabel.setText(user.translate("settings.controls.reload").replace("{0}", "R"));
                    changeReload.setText(user.translate("button.change") + " " + user.translate("settings.reload") + " (R)");
                    App.getApp().getLoggedInUser().getKeyManagement().setReloadButton(Input.Keys.R);
                }
                updateAllButtonStates();
            }
        });
    }

    private void handleChangeAutoAim() {
        changeAutoAim.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                User user = App.getApp().getLoggedInUser();
                if (App.getApp().getLoggedInUser().getKeyManagement().getAutoAimButton() == Input.Keys.SPACE) {
                    resultLabel.setText(user.translate("settings.controls.auto_aim").replace("{0}", "P"));
                    changeAutoAim.setText(user.translate("button.change") + " " + user.translate("settings.auto_aim") + " (P)");
                    App.getApp().getLoggedInUser().getKeyManagement().setAutoAimButton(Input.Keys.P);
                } else {
                    resultLabel.setText(user.translate("settings.controls.auto_aim").replace("{0}", "SPACE"));
                    changeAutoAim.setText(user.translate("button.change") + " " + user.translate("settings.auto_aim") + " (SPACE)");
                    App.getApp().getLoggedInUser().getKeyManagement().setAutoAimButton(Input.Keys.SPACE);
                }
                updateAllButtonStates();
            }
        });
    }

    private void updateMusicButtonStates() {
        User user = App.getApp().getLoggedInUser();
        if (game.getMusicManager().getPath().equals(Paths.MAINSONG.getPath())) {
            music1.setText(user.translate("settings.music") + " 1 ✓");
            music2.setText(user.translate("settings.music") + " 2");
        } else if (game.getMusicManager().getPath().equals(Paths.SONG2.getPath())) {
            music1.setText(user.translate("settings.music") + " 1");
            music2.setText(user.translate("settings.music") + " 2 ✓");
        } else {
            music1.setText(user.translate("settings.music") + " 1");
            music2.setText(user.translate("settings.music") + " 2");
        }
    }

    private void updateAllButtonStates() {
        User user = App.getApp().getLoggedInUser();
        
        // Update toggle buttons
        greyScale.setText(user.isGray() ? 
            user.translate("settings.ungray_scale") + " ✓" : 
            user.translate("settings.gray_scale"));
            
        muteSFX.setText(user.getSfxManager().isSfxEnabled() ? 
            user.translate("settings.mute_sfx") + " ✓" : 
            user.translate("settings.unmute_sfx"));
            
        autoReload.setText(user.isReloadAuto() ? 
            user.translate("settings.untoggle_auto_reload") + " ✓" : 
            user.translate("settings.toggle_auto_reload"));

        // Update control buttons
        changeControllers.setText(user.translate("settings.controls"));
        changeReload.setText(user.translate("button.change") + " " + user.translate("settings.reload"));
        changeAutoAim.setText(user.translate("button.change") + " " + user.translate("settings.auto_aim"));
        
        // Update back button
        back.setText(user.translate("button.back"));
        
        // Update labels
        menuTitle.setText(user.translate("menu.settings"));
        change.setText(user.translate("settings.volume"));
        
        updateMusicButtonStates();
    }

    public void handleBasedOnUser() {
        updateAllButtonStates();
    }

    public void handleButtons() {
        handleLanguageChange();
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