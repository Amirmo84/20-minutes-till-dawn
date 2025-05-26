package com.TillDawn.Views;

import com.TillDawn.Controllers.ControllersManager;
import com.TillDawn.Models.App;
import com.TillDawn.Models.GameAssetManager;
import com.TillDawn.Models.SFXManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class SignUpMenu implements Screen {
    private Stage stage;

    @Override
    public void show() {
        this.stage = new Stage(new ScreenViewport());
//        InputMultiplexer multiplexer = new InputMultiplexer();
//        multiplexer.addProcessor(App.getApp().getLoggedInUser().getSfxManager().setSound(GameAssetManager.getManager().getClickSound()));
//        multiplexer.addProcessor(stage);
//        Gdx.input.setInputProcessor(multiplexer);
        Gdx.input.setInputProcessor(stage);
        stage.addActor(ControllersManager.signUpMenuController.getTable());
        ControllersManager.signUpMenuController.addAvatar(stage);
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0.1f, 0.1f, 0.1f, 1);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        ControllersManager.signUpMenuController.handleButtons();
    }

    @Override
    public void resize(int i, int i1) {
        stage.getViewport().update(i, i1, true);
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
