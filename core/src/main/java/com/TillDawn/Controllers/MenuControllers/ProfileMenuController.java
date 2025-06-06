package com.TillDawn.Controllers.MenuControllers;

import com.TillDawn.Controllers.ControllersManager;
import com.TillDawn.Models.App;
import com.TillDawn.Models.Enums.Language;
import com.TillDawn.Models.GameAssetManager;
import com.TillDawn.Models.User;
import com.TillDawn.TillDawn;
import com.TillDawn.Views.ChangeAvatarMenu;
import com.TillDawn.Views.MainMenu;
import com.TillDawn.Views.ProfileMenu;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class ProfileMenuController {
    private TillDawn game = TillDawn.getTillDawn();
    private final Skin skin = GameAssetManager.getManager().getSkin();
    private Language currentLanguage;

    private TextField newUserName;
    private TextField newPassWord;

    private final TextButton changeButtonUsername;
    private final TextButton changeButtonPassword;
    private final TextButton deleteAccount;
    private final TextButton avatarButton;
    private final TextButton backButton;

    private final Image image;

    private final Label changeUserName;
    private final Label changePassword;
    private final Label errorLabelUsername;
    private final Label errorLabelPassword;

    private Image avatar;
    private Table table = new Table(skin);

    public ProfileMenuController() {
        image = GameAssetManager.getManager().getOthersBackGround();
        table.addActor(image);
        image.setPosition(0, 0);
        image.setSize((float) Gdx.graphics.getWidth(), (float) Gdx.graphics.getHeight());
        User user = App.getApp().getLoggedInUser();
        currentLanguage = (user != null) ? user.getLanguage() : Language.ENGLISH;

        // Initialize UI components with translations
        newUserName = new TextField("", skin);
        newPassWord = new TextField("", skin);

        changeButtonUsername = new TextButton(currentLanguage.get("profile.change_username"), skin);
        changeButtonPassword = new TextButton(currentLanguage.get("profile.change_password"), skin);
        deleteAccount = new TextButton(currentLanguage.get("profile.delete_account"), skin);
        avatarButton = new TextButton(currentLanguage.get("profile.change_avatar"), skin);
        backButton = new TextButton(currentLanguage.get("button.back"), skin);

        changeUserName = new Label(currentLanguage.get("profile.enter_username"), skin);
        changePassword = new Label(currentLanguage.get("profile.enter_password"), skin);
        errorLabelUsername = new Label("", skin);
        errorLabelPassword = new Label("", skin);

        setupUI();
    }

    private void setupUI() {
        table.setFillParent(true);
        table.center();

        table.add(changeUserName).fillX().expandX().pad(0, 800, 0, 800);
        table.row().pad(10, 0, 10, 0);
        table.add(newUserName).fillX().expandX().pad(0, 800, 0, 800);
        table.row().pad(10, 0, 10, 0);
        table.add(errorLabelUsername).fillX().expandX().pad(0, 800, 0, 800);
        table.row().pad(10, 0, 10, 0);
        table.add(changeButtonUsername).fillX().expandX().pad(0, 800, 0, 800);
        table.row().pad(10, 0, 10, 0);
        table.add(changePassword).fillX().expandX().pad(0, 800, 0, 800);
        table.row().pad(10, 0, 10, 0);
        table.add(newPassWord).fillX().expandX().pad(0, 800, 0, 800);
        table.row().pad(20, 0, 10, 0);
        table.add(errorLabelPassword).fillX().expandX().pad(0, 800, 0, 800);
        table.row().pad(20, 0, 10, 0);
        table.add(changeButtonPassword).fillX().expandX().pad(0, 800, 0, 800);
        table.row().pad(20, 0, 10, 0);
        table.add(avatarButton).fillX().expandX().pad(0, 800, 0, 800);
        table.row().pad(20, 0, 10, 0);
        table.add(deleteAccount).fillX().expandX().pad(0, 800, 0, 800);
        table.row().pad(20, 0, 10, 0);
        table.add(backButton).fillX().expandX().pad(0, 800, 0, 800);
    }

    private void handleUsername() {
        changeButtonUsername.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String username = newUserName.getText();
                if (username == null || username.isEmpty()) {
                    errorLabelUsername.setText(currentLanguage.get("profile.error.empty_username"));
                    return;
                }

                User user = User.getUserByUserName(username);
                if (user != null) {
                    errorLabelUsername.setText(currentLanguage.get("profile.error.username_taken"));
                    return;
                }

                User currentUser = App.getApp().getLoggedInUser();
                if (currentUser != null) {
                    currentUser.setUsername(username);
                    App.getApp().setLoggedInUser(currentUser);
                    errorLabelUsername.setText(currentLanguage.get("profile.success.username_changed"));
                }

                game.getScreen().dispose();
                game.setScreen(new ProfileMenu());
            }
        });
    }

    private void handlePassword() {
        changeButtonPassword.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String password = newPassWord.getText();
                User currentUser = App.getApp().getLoggedInUser();

                if (password == null || password.isEmpty() || 
                    !ControllersManager.signUpMenuController.isPasswordStrong(password) ||
                    (currentUser != null && password.equals(currentUser.getPassword()))) {
                    errorLabelPassword.setText(currentLanguage.get("profile.error.invalid_password"));
                    return;
                }

                if (currentUser != null) {
                    currentUser.setPassword(password);
                    App.getApp().setLoggedInUser(currentUser);
                    errorLabelPassword.setText(currentLanguage.get("profile.success.password_changed"));
                }

                newPassWord.setText("");
                game.getScreen().dispose();
                game.setScreen(new ProfileMenu());
            }
        });
    }

    private void handleDelete() {
        deleteAccount.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                User currentUser = App.getApp().getLoggedInUser();
                if (currentUser != null) {
                    App.getApp().removeUser(currentUser);
                    App.getApp().setLoggedInUser(null);
                }
                game.getScreen().dispose();
                game.setScreen(new MainMenu());
            }
        });
    }

    private void handleBack() {
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.getScreen().dispose();
                game.setScreen(new MainMenu());
            }
        });
    }

    private void handleAvatar() {
        avatarButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.getScreen().dispose();
                game.setScreen(new ChangeAvatarMenu());
            }
        });
    }

    public void handleButtons() {
        handleDelete();
        handlePassword();
        handleUsername();
        handleBack();
        handleAvatar();
    }

    public Table getTable() {
        return table;
    }
}
