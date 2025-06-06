package com.TillDawn.Controllers.MenuControllers;

import com.TillDawn.Models.App;
import com.TillDawn.Models.Enums.Language;
import com.TillDawn.Models.GameAssetManager;
import com.TillDawn.Models.User;
import com.TillDawn.TillDawn;
import com.TillDawn.Views.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;

public class MainMenuController {
    private final TillDawn game = TillDawn.getTillDawn();
    private final Skin skin = GameAssetManager.getManager().getSkin();
    private final Image backgroundImage = GameAssetManager.getManager().getImage();
    private Language currentLanguage;
    private Table table = new Table(skin);

    // Buttons
    private final TextButton signIn;
    private final TextButton settings;
    private final TextButton profile;
    private final TextButton preGameMenu;
    private final TextButton leaderboard;
    private final TextButton hint;
    private final TextButton logout;
    private final TextButton exit;
    private final TextButton resume;

    // User info components
    private final Image userAvatar;
    private final Label usernameLabel;
    private final Label scoreLabel;
    private final Table userInfoTable;

    public MainMenuController() {
        User user = App.getApp().getLoggedInUser();
        currentLanguage = (user != null) ? user.getLanguage() : Language.ENGLISH;

        // Initialize buttons with translations
        signIn = new TextButton(currentLanguage.get("button.login"), skin);
        settings = new TextButton(currentLanguage.get("button.settings"), skin);
        profile = new TextButton(currentLanguage.get("button.profile"), skin);
        preGameMenu = new TextButton(currentLanguage.get("menu.pregame"), skin);
        leaderboard = new TextButton(currentLanguage.get("menu.leaderboard"), skin);
        hint = new TextButton(currentLanguage.get("menu.hint"), skin);
        logout = new TextButton(currentLanguage.get("button.logout"), skin);
        exit = new TextButton(currentLanguage.get("button.exit"), skin);
        resume = new TextButton(currentLanguage.get("button.resume"), skin);

        // Initialize user info components
        userAvatar = new Image();
        usernameLabel = new Label(currentLanguage.get("label.username"), skin);
        scoreLabel = new Label(currentLanguage.get("label.score"), skin);
        userInfoTable = new Table(skin);

        setupUI();
    }

    private void setupUI() {
        table.clear();
        
        table.addActor(backgroundImage);
        backgroundImage.setPosition(0, 0);
        backgroundImage.setSize((float) Gdx.graphics.getWidth(), (float) Gdx.graphics.getHeight());
        table.setFillParent(true);
        table.center();
        handleFontScales();

        // Set up user info table
        userInfoTable.defaults().pad(5);
        userAvatar.setScaling(Scaling.fit);

        // Add buttons based on user state
        refreshUIState();
    }

    private void refreshUIState() {
        // Remove all buttons and user info first
        signIn.remove();
        settings.remove();
        profile.remove();
        preGameMenu.remove();
        leaderboard.remove();
        hint.remove();
        logout.remove();
        exit.remove();
        userInfoTable.remove();
        resume.remove();

        // Check if user is logged in
        User user = App.getApp().getLoggedInUser();
        boolean isLoggedIn = user != null && !user.getUsername().isEmpty();

        if (!isLoggedIn) {
            table.addActor(signIn);
            signIn.setPosition(10, (float) Gdx.graphics.getHeight() - signIn.getHeight() - 10);
        } else {
            // Update and show user info
            updateUserInfo();
            table.addActor(userInfoTable);
            
            // Position user info down and left from the top-right corner
            float userInfoX = Gdx.graphics.getWidth() - userInfoTable.getWidth() - 180;
            float userInfoY = Gdx.graphics.getHeight() - userInfoTable.getHeight() - 200;
            
            userInfoTable.setPosition(userInfoX, userInfoY);
            
            // Adjust menu buttons
            table.addActor(profile);
            profile.setPosition(10, (float) Gdx.graphics.getHeight() - profile.getHeight() - 10);
            
            table.addActor(settings);
            settings.setPosition(10, (float) Gdx.graphics.getHeight() - settings.getHeight() - 110);
            
            table.addActor(preGameMenu);
            preGameMenu.setPosition(10, (float) Gdx.graphics.getHeight() - preGameMenu.getHeight() - 210);
            
            table.addActor(leaderboard);
            leaderboard.setPosition(10, (float) Gdx.graphics.getHeight() - leaderboard.getHeight() - 310);
            
            table.addActor(hint);
            hint.setPosition(10, (float) Gdx.graphics.getHeight() - hint.getHeight() - 410);
            
            table.addActor(logout);
            logout.setPosition(10, 10);
        }

        table.add(exit).row();
        if (isLoggedIn && user.getCurrentGame() != null) {
            table.add(resume);
        }
    }

    private void updateUserInfo() {
        userInfoTable.clear();
        User user = App.getApp().getLoggedInUser();
        if (user == null) return;

        // Create a vertical layout
        Table infoLayout = new Table();
        infoLayout.defaults().pad(5);
        
        // Update avatar
        try {
            Texture avatarTexture;
            String avatarPath = user.getAvatarPath();
            
            if (avatarPath.startsWith("profiles/")) {
                avatarTexture = new Texture(Gdx.files.internal(avatarPath));
            } else {
                avatarTexture = new Texture(Gdx.files.local(avatarPath));
            }
            
            userAvatar.setDrawable(new TextureRegionDrawable(new TextureRegion(avatarTexture)));
        } catch (Exception e) {
            System.err.println("Error loading avatar: " + e.getMessage());
            userAvatar.setDrawable(skin.getDrawable("default-avatar"));
        }

        usernameLabel.setText(currentLanguage.get("label.username") + " " + user.getUsername());
        scoreLabel.setText(currentLanguage.get("label.score") + " " + String.format("%.1f", user.getScore()));

        // Add components vertically with center alignment
        infoLayout.add(userAvatar).size(150, 150).padBottom(5).row();
        infoLayout.add(usernameLabel).padBottom(2).center().row();
        infoLayout.add(scoreLabel).center();

        userInfoTable.add(infoLayout);
    }

    private void handleFontScales() {
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
        usernameLabel.setFontScale(0.9f);
        scoreLabel.setFontScale(0.9f);
    }

    public Table getTable() {
        return table;
    }

    public void refreshUI() {
        User user = App.getApp().getLoggedInUser();
        currentLanguage = (user != null) ? user.getLanguage() : Language.ENGLISH;
        
        // Update button texts
        signIn.setText(currentLanguage.get("button.login"));
        settings.setText(currentLanguage.get("button.settings"));
        profile.setText(currentLanguage.get("button.profile"));
        preGameMenu.setText(currentLanguage.get("menu.pregame"));
        leaderboard.setText(currentLanguage.get("menu.leaderboard"));
        hint.setText(currentLanguage.get("menu.hint"));
        logout.setText(currentLanguage.get("button.logout"));
        exit.setText(currentLanguage.get("button.exit"));
        resume.setText(currentLanguage.get("button.resume"));
        
        refreshUIState();
    }

    private void handleSignInButton() {
        signIn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.getScreen().dispose();
                game.setScreen(new SignUpMenu());
            }
        });
    }

    private void handleSettings() {
        settings.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.getScreen().dispose();
                game.setScreen(new SettingsView());
            }
        });
    }

    private void handleExit() {
        exit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
    }

    private void handlePregame() {
        preGameMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.getScreen().dispose();
                game.setScreen(new PreGameMenu());
            }
        });
    }

    private void handleHint() {
        hint.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.getScreen().dispose();
                game.setScreen(new HintMenuView());
            }
        });
    }

    private void handleLeaderBoard() {
        leaderboard.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.getScreen().dispose();
                game.setScreen(new LeaderBoardMenu());
            }
        });
    }

    private void handleProfile() {
        profile.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.getScreen().dispose();
                game.setScreen(new ProfileMenu());
            }
        });
    }

    private void handleLogout() {
        logout.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                App.getApp().setLoggedInUser(null);
                refreshUIState();
            }
        });
    }

    private void handleResume(){
        resume.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.getScreen().dispose();
                game.setScreen(new GameView());
            }
        });
    }

    public void handleButtons() {
        handleSignInButton();
        handleSettings();
        handleExit();
        handlePregame();
        handleHint();
        handleLeaderBoard();
        handleProfile();
        handleLogout();
        handleResume();
    }
}
