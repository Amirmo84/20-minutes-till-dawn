package com.TillDawn.Controllers;

import com.TillDawn.Models.App;
import com.TillDawn.Models.GameAssetManager;
import com.TillDawn.Models.Result;
import com.TillDawn.Models.User;
import com.TillDawn.TillDawn;
import com.TillDawn.Views.MainMenu;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class SignUpMenuController {
    private final TillDawn game = TillDawn.getTillDawn();

    private final Skin skin = GameAssetManager.getManager().getSkin();

    private final Label username = new Label("Username: ", skin);
    private final Label password = new Label("Password: ", skin);
    private Label menuTitle = new Label("Sign Up Menu", skin);
    private Label resultLabel = new Label("", skin);

    private TextField registerField = new TextField("", skin);
    private TextField passwordField = new TextField("", skin);

    private final TextButton registerButton = new TextButton("Sign Up", skin);
    private final TextButton showOrHide = new TextButton("Show", skin);

    private Table table = new Table(skin);

    public SignUpMenuController() {
        table.setFillParent(true);
        menuTitle.setFontScale(2);
        menuTitle.setPosition((float) Gdx.graphics.getWidth() /2 - menuTitle.getWidth() / 2, 1000);
        table.row().pad(10, 0, 10, 0);
        table.add(username);
        table.row().pad(10, 0, 10, 0);
        table.add(registerField).width(400).height(80);
        table.row().pad(10, 0, 10, 0);
        table.add(password);
        table.row().pad(10, 0, 10, 0);
        table.add(passwordField).width(400).height(80);
        table.row().pad(20, 0, 10, 0);
        table.add(resultLabel);
        table.row().pad(20, 0, 10, 0);
        table.add(registerButton);
//        table.row().pad(20, 0, 10, 0);
//        table.add(backButton).fillX().expandX().pad(0 , 800 , 0 , 800);


        registerField.setWidth(100);
        passwordField.setWidth(100);

        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');
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
                User user = new User(username, password);
                App.getApp().getUsers().add(user);
                resultLabel.setText("");
                passwordField.setText("");
                registerField.setText("");
                //TODO goto another menu
                game.getScreen().dispose();
                game.setScreen(new MainMenu());
            }
        });
    }

    private void handleShow(){
        showOrHide.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (showOrHide.getText().toString().equals("Show")) {
                    passwordField.setPasswordMode(false);
                    showOrHide.setText("Hide");
                }
                else {
                    passwordField.setPasswordMode(true);
                    passwordField.setPasswordCharacter('*');
                    showOrHide.setText("Show");
                }
            }
        });
    }

    public void handleButtons(){
        handleSignUp();
        handleShow();
    }

    public Table getTable() {
        return table;
    }

    private boolean isPasswordStrong(String password){
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
