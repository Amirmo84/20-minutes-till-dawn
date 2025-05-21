package com.TillDawn.Controllers;

import com.TillDawn.Models.GameAssetManager;
import com.badlogic.gdx.scenes.scene2d.ui.*;

public class ProfileMenuController {
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

    public Table getTable() {
        return table;
    }
}
