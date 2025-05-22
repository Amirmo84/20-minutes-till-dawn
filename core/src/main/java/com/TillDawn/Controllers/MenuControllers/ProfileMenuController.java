package com.TillDawn.Controllers.MenuControllers;

import com.TillDawn.Controllers.ControllersManager;
import com.TillDawn.Models.App;
import com.TillDawn.Models.GameAssetManager;
import com.TillDawn.Models.User;
import com.TillDawn.TillDawn;
import com.TillDawn.Views.MainMenu;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class ProfileMenuController {
    private TillDawn game = TillDawn.getTillDawn();

    private final Skin skin = GameAssetManager.getManager().getSkin();

    private TextField newUserName = new TextField("" ,  skin);
    private TextField newPassWord = new TextField("", skin);

    private final TextButton changeButtonUsername = new TextButton("Change Username", skin , "default");
    private final TextButton changeButtonPassword = new TextButton("Change Password", skin , "default");
    private final TextButton deleteAccount = new TextButton("Delete Account", skin , "default");
    private final TextButton avatarButton = new TextButton("Change Avatar", skin , "default");
    private final TextButton backButton = new TextButton("Back", skin , "default");

    private final Label changeUserName = new Label("change username: ", skin);
    private final Label changePassword = new Label("change password: ", skin);
    private final Label errorLabelUsername = new Label("", skin );
    private final Label errorLabelPassword = new Label("", skin );

    private Table table = new Table(skin);
    public ProfileMenuController() {
        table.setFillParent(true);
        table.center();

        table.add(changeUserName).fillX().expandX().pad(0 , 800 , 0 , 800);
        table.row().pad(10, 0, 10, 0);
        table.add(newUserName).fillX().expandX().pad(0 , 800 , 0 , 800);
        table.row().pad(10, 0, 10, 0);
        table.add(errorLabelUsername).fillX().expandX().pad(0 , 800 , 0 , 800);
        table.row().pad(10, 0, 10, 0);
        table.add(changeButtonUsername).fillX().expandX().pad(0 , 800 , 0 , 800);
        table.row().pad(10, 0, 10, 0);
        table.add(changePassword).fillX().expandX().pad(0 , 800 , 0 , 800);
        table.row().pad(10, 0, 10, 0);
        table.add(newPassWord).fillX().expandX().pad(0 , 800 , 0 , 800);
        table.row().pad(20, 0, 10, 0);
        table.add(errorLabelPassword).fillX().expandX().pad(0 , 800 , 0 , 800);
        table.row().pad(20, 0, 10, 0);
        table.add(changeButtonPassword).fillX().expandX().pad(0 , 800 , 0 , 800);
        table.row().pad(20, 0, 10, 0);
        table.add(avatarButton).fillX().expandX().pad(0 , 800 , 0 , 800);
        table.row().pad(20, 0, 10, 0);
        table.add(deleteAccount).fillX().expandX().pad(0 , 800 , 0 , 800);
        table.row().pad(20, 0, 10, 0);
        table.add(backButton).fillX().expandX().pad(0 , 800 , 0 , 800);
    }

    private void handleUsername(){
        changeButtonUsername.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String username = newUserName.getText();
                if (username == null || username.isEmpty()){
                    errorLabelUsername.setText("Username can't be empty!");
                    return;
                }

                User user = User.getUserByUserName(username);
                if (user != null){
                    errorLabelUsername.setText("Username is already taken!");
                    return;
                }

                App.getApp().getLoggedInUser().setUsername(username);
                errorLabelUsername.setText("Successful!");
            }
        });
    }

    private void handlePassword(){
        changeButtonPassword.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String password = newPassWord.getText();

                if (password == null || password.isEmpty() || !ControllersManager.signUpMenuController.isPasswordStrong(password)
                        || password.equals(App.getApp().getLoggedInUser().getPassword())){
                    errorLabelPassword.setText("Can't use this password!");
                    return;
                }

                App.getApp().getLoggedInUser().setPassword(password);
                errorLabelPassword.setText("Successful!");
            }
        });
    }

    private void handleDelete(){
        deleteAccount.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                App.getApp().getUsers().remove(App.getApp().getLoggedInUser());
                App.getApp().setLoggedInUser(null);
                game.getScreen().dispose();
                game.setScreen(new MainMenu());
            }
        });
    }

    private void changeAvatar(){
        avatarButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //TODO
            }
        });
    }

    private void handleBack(){
        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.getScreen().dispose();
                game.setScreen(new MainMenu());
            }
        });
    }

    public void handleButtons(){
        handleDelete();
        handlePassword();
        handleUsername();
        handleBack();
    }

    public Table getTable() {
        return table;
    }
}
