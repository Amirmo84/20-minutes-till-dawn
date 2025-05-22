package com.TillDawn.Views;

import com.TillDawn.Controllers.GameControllers.GameController;
import com.TillDawn.Models.GameAssetManager;
import com.TillDawn.TillDawn;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameView implements Screen, InputProcessor {
    private Stage stage;
    private TillDawn game = TillDawn.getTillDawn();
    private GameController controller;

    public GameView(GameController controller) {
        this.controller = controller;
        controller.setView(this);
    }

    @Override
    public void show() {
        this.stage = new Stage(new ScreenViewport());
        Gdx.graphics.setCursor(GameAssetManager.getManager().getNewCursor());
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.1f, 0.1f, 0.1f, 1);
        if (game.isGray())
            game.getBatch().setShader(game.getGrayscaleShader());
        game.getBatch().begin();
        controller.updateGame();
        game.getBatch().end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

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

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (game.isControlDefault())
            controller.getWeaponController().handleWeaponShoot(screenX, screenY);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (!game.isControlDefault())
            controller.getWeaponController().handleWeaponShoot(screenX, screenY);
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        controller.getWeaponController().handleWeaponRotation(screenX, screenY);
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
