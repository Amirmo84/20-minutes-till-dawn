package com.TillDawn.Views;

import com.TillDawn.Controllers.ScreenController;
import com.TillDawn.Models.Enums.Paths;
import com.TillDawn.Models.GameAssetManager;
import com.TillDawn.Models.Result;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class SignUpMenu implements Screen {
    private Stage stage;

    private Table table;

    private Label menuTitle;
    private Label resultLabel;

    private TextField registerField;
    private TextField passwordField;

    private final TextButton registerButton;;

    public SignUpMenu(Skin skin) {
        this.table = new Table(skin);

        this.menuTitle = new Label("Sign Up Menu", skin);
        this.resultLabel = new Label("", skin);

        this.registerField = new TextField("Enter your username", skin);
        this.passwordField = new TextField("Enter your password", skin);
        this.registerButton = new TextButton("Sign Up", skin);

        this.passwordField.setPasswordMode(true);
        this.passwordField.setPasswordCharacter('*');

        addListener(skin);
    }

    public void addListener(Skin skin){
        registerButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Result result = ScreenController.signUpMenuController.signUp(registerField.getText(), passwordField.getText());
                resultLabel.setText(result.getMessage());
            }
        });
    }

    @Override
    public void show() {
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Skin skin = GameAssetManager.getManager().getSkin();
        menuTitle.setFontScale(2.5f);
        resultLabel.setFontScale(1.8f);

        menuTitle.setPosition(800, 1000);
        resultLabel.setPosition(900,930);

        stage.addActor(menuTitle);
        stage.addActor(resultLabel);

        table.setFillParent(true);
        table.center();

        table.add(registerField).width(400).height(100);
        table.row().padTop(20);
        table.add(passwordField).width(400).height(100);
        table.row().padTop(70);
        table.add(registerButton).colspan(2).width(450);
        table.row().padTop(20);

        stage.addActor(table);
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0.1f, 0.1f, 0.1f, 1);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        this.stage.dispose();
    }
}
