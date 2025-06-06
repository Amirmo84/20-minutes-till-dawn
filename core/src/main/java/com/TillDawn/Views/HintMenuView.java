package com.TillDawn.Views;

import com.TillDawn.Controllers.ControllersManager;
import com.TillDawn.Controllers.MenuControllers.HintMenuController;
import com.TillDawn.Models.App;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class HintMenuView implements Screen {
    private Stage stage;
    private HintMenuController hintMenuController = new HintMenuController();

    @Override
    public void show() {
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        ControllersManager.hintMenuController.setUser(App.getApp().getLoggedInUser());
        stage.addActor(hintMenuController.getTable());
        hintMenuController.handleButtons();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.1f, 0.1f, 0.1f, 1);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
