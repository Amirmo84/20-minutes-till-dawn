package com.TillDawn.Controllers;

import com.TillDawn.Models.App;
import com.TillDawn.Models.Enums.Paths;
import com.TillDawn.Models.GameAssetManager;
import com.TillDawn.Models.Result;
import com.TillDawn.Models.User;
import com.TillDawn.TillDawn;
import com.TillDawn.Views.LoginMenu;
import com.TillDawn.Views.MainMenu;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import java.util.Random;

public class SignUpMenuController {
    private final TillDawn game = TillDawn.getTillDawn();

    private final Skin skin = GameAssetManager.getManager().getSkin();

    private Image avatar;

    private final Label username = new Label("Username: ", skin);
    private final Label password = new Label("Password: ", skin);
    private Label menuTitle = new Label("Sign Up Menu", skin);
    private Label resultLabel = new Label("", skin);
    private final Label avatarTitle = new Label("Your avatar: ", skin);

    private TextField registerField = new TextField("", skin);
    private TextField passwordField = new TextField("", skin);
    private TextField security = new TextField("What is your favourite animal?", skin);

    private final TextButton registerButton = new TextButton("Sign Up", skin);
    private final TextButton backButton = new TextButton("Back", skin);
    private final TextButton login = new TextButton("Already have an account?", skin);

    private Table table = new Table(skin);

    private String avatarPath;

    public SignUpMenuController() {
        table.setFillParent(true);
        table.center();
        handleRandomAvatar();

        resultLabel.setFontScale(1.5f);
        menuTitle.setFontScale(2);
        table.add(menuTitle);
        table.row().pad(10, 0, 10, 0);
        table.add(username);
        table.row().pad(10, 0, 10, 0);
        table.add(registerField).width(400).height(80);
        table.row().pad(10, 0, 10, 0);
        table.add(password);
        table.row().pad(10, 0, 10, 0);
        table.add(passwordField).width(400).height(80);
        table.row();
        table.add(security).width(450);
        table.row().pad(10, 0, 10, 0);
        table.add(resultLabel);
        table.row().pad(10, 0, 10, 0);
        table.add(registerButton).height(100);
        table.row();
        table.add(backButton).height(100);
        table.row();
        table.add(login);

        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');
    }

    public void addAvatar(Stage stage){
        stage.addActor(avatarTitle);
        stage.addActor(avatar);
        avatarTitle.setPosition((float) Gdx.graphics.getWidth() / 2 + 320, (float) Gdx.graphics.getHeight() / 2 + 10, Align.center);
        avatar.setPosition((float) Gdx.graphics.getWidth() / 2 + 500, (float) Gdx.graphics.getHeight() / 2 + 10, Align.center);
    }

    private void handleRandomAvatar(){
        String path = Paths.AVATAR.getPath();
        Random random = new Random();
        int index = random.nextInt(4) + 1;
        avatarPath = String.format("%s%d.png", path, index);
        avatar = new Image(new Texture(Gdx.files.internal(avatarPath)));
    }

    private void handleBack(){
        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                handleRandomAvatar();
                game.getScreen().dispose();
                game.setScreen(new MainMenu());
            }
        });
    }

    private void handleSignUp(){
        registerButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String username = registerField.getText();
                String password = passwordField.getText();

                if (username == null || username.isEmpty()){
                    resultLabel.setText("Username can't be empty!");
                    return;
                }
                if (User.getUserByUserName(username) != null){
                    resultLabel.setText("Username exists! choose another one!");
                    return;
                }
                if (password == null || password.isEmpty() || !isPasswordStrong(password)){
                    resultLabel.setText("Password is weak!");
                    return;
                }
                User user = new User(username, password, security.getText(), avatarPath);
                App.getApp().getUsers().add(user);
                App.getApp().setLoggedInUser(user);
                resultLabel.setText("");
                passwordField.setText("");
                registerField.setText("");
                handleRandomAvatar();
                game.getScreen().dispose();
                game.setScreen(new MainMenu());
            }
        });
    }

    private void handleLogin(){
        login.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.getScreen().dispose();
                game.setScreen(new LoginMenu());
            }
        });
    }

    public void handleButtons(){
        handleSignUp();
        handleBack();
        handleLogin();
    }

    public Table getTable() {
        return table;
    }

    public boolean isPasswordStrong(String password){
        if (password.length() < 8)
            return false;
        boolean hasUpper = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        String specialChars = "@#$%&*)(_";

        for (char c : password.toCharArray()){
            if (Character.isUpperCase(c))
                hasUpper = true;
            else if (Character.isDigit(c))
                hasDigit = true;
            else if (specialChars.indexOf(c) >= 0)
                hasSpecial = true;
        }

        return hasSpecial && hasDigit && hasUpper;
    }
}
