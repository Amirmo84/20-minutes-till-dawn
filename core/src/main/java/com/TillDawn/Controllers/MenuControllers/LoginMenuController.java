package com.TillDawn.Controllers.MenuControllers;

import com.TillDawn.Models.App;
import com.TillDawn.Models.GameAssetManager;
import com.TillDawn.Models.User;
import com.TillDawn.TillDawn;
import com.TillDawn.Views.ForgotPasswordView;
import com.TillDawn.Views.MainMenu;
import com.TillDawn.Views.SignUpMenu;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class LoginMenuController {
    private TillDawn game = TillDawn.getTillDawn();

    private Skin skin = GameAssetManager.getManager().getSkin();

    private final Label menuTitle = new Label("Login menu", skin);
    private final Label username = new Label("Username: ", skin);
    private final Label password = new Label("Password: ", skin);
    private Label resultLabel = new Label("", skin);

    private TextField registerField = new TextField("", skin);
    private TextField passwordField = new TextField("", skin);

    private final TextButton loginButton = new TextButton("Log in", skin);
    private final TextButton asGuestButton = new TextButton("Enter as guest", skin);
    private final TextButton forgotPassword = new TextButton("Forgot password", skin);
    private final TextButton back = new TextButton("Back", skin);

    private Table table = new Table(skin);

    public LoginMenuController() {
        table.setFillParent(true);
        table.center();

        resultLabel.setFontScale(1.5f);
        menuTitle.setFontScale(2);
        table.add(menuTitle);
        table.row().pad(10, 0, 10, 0);
        table.add(username).row();
        table.add(registerField).width(400).row();
        table.add(password).row();
        table.add(passwordField).width(400).row();
        table.row().pad(10, 0 , 10, 0);
        table.add(resultLabel).row();
        table.row().pad(10, 0 , 10, 0);
        table.add(loginButton).height(100).row();
        table.add(asGuestButton).height(100).row();
        table.add(forgotPassword).height(100);
    }

    private void handleLogin(){
        loginButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String username = registerField.getText();
                String password = passwordField.getText();

                User user = User.getUserByUserName(username);

                if (user == null){
                    resultLabel.setText("Such username doesn't exist!");
                    return;
                }
                if (!user.getPassword().equals(password))
                    resultLabel.setText("Invalid password!");
                App.getApp().setLoggedInUser(user);
                registerField.setText("");
                passwordField.setText("");
                game.getScreen().dispose();
                game.setScreen(new MainMenu());
            }
        });
    }

    private void handleAsGuest(){
        asGuestButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                registerField.setText("");
                passwordField.setText("");
                game.getScreen().dispose();
                game.setScreen(new MainMenu());
            }
        });
    }

    private void handleForgot(){
        forgotPassword.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String username = registerField.getText();

                User user = User.getUserByUserName(username);
                if (user == null){
                    resultLabel.setText("Such username doesn't exist!");
                    return;
                }
                App.getApp().setTempUser(user);
                registerField.setText("");
                passwordField.setText("");
                game.getScreen().dispose();
                game.setScreen(new ForgotPasswordView());
            }
        });
    }

    private void handleBack(){
        back.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                registerField.setText("");
                passwordField.setText("");
                game.getScreen().dispose();
                game.setScreen(new SignUpMenu());
            }
        });
    }

    public void handleButtons(){
        handleLogin();
        handleAsGuest();
        handleForgot();
        handleBack();
    }

    public Table getTable() {
        return table;
    }
}
