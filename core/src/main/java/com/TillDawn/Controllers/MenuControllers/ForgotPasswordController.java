package com.TillDawn.Controllers.MenuControllers;

import com.TillDawn.Controllers.ControllersManager;
import com.TillDawn.Models.App;
import com.TillDawn.Models.GameAssetManager;
import com.TillDawn.TillDawn;
import com.TillDawn.Views.LoginMenu;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class ForgotPasswordController {
    private TillDawn game = TillDawn.getTillDawn();

    private final Skin skin = GameAssetManager.getManager().getSkin();

    private final Label answer = new Label("Enter your security answer:", skin);
    private final Label password = new Label("New password:", skin);
    private Label resultLabel = new Label("", skin);

    private TextField answerField = new TextField("", skin);
    private TextField newPassword = new TextField("", skin);

    private final TextButton back = new TextButton("Back", skin);
    private final TextButton change = new TextButton("Change password", skin);

    private Table table = new Table(skin);

    public ForgotPasswordController() {
        table.setFillParent(true);
        table.center();

        resultLabel.setFontScale(1.5f);

        table.add(answer).row();
        table.add(answerField).width(200).row();
        table.row();
        table.add(password).row();
        table.add(newPassword).width(400).row();
        table.row().pad(10, 0, 20, 0);
        table.add(resultLabel).row();
        table.row().pad(10, 0, 10, 0);
        table.add(change).row();
        table.add(back);

        newPassword.setPasswordMode(true);
        newPassword.setPasswordCharacter('*');
    }

    private void handleBack(){
        back.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.getScreen().dispose();
                game.setScreen(new LoginMenu());
            }
        });
    }

    private void handleChange(){
        change.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String answerText = answerField.getText();

                if (!App.getApp().getTempUser().getAnswer().equals(answerText)){
                    resultLabel.setText("Wrong answer! Please try again!");
                    return;
                }

                if (newPassword.getText() == "" || !ControllersManager.signUpMenuController.isPasswordStrong(newPassword.getText())){
                    resultLabel.setText("Weak Password!");
                    return;
                }

                App.getApp().getTempUser().setPassword(newPassword.getText());
                resultLabel.setFontScale(1);
                resultLabel.setText("Password changed successfully! Please enter back button to go to the login menu!");
            }
        });
    }

    public void handleButtons(){
        handleBack();
        handleChange();
    }

    public Table getTable() {
        return table;
    }
}
