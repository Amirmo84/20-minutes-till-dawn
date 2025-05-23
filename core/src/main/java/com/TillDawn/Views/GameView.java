package com.TillDawn.Views;

import com.TillDawn.Controllers.GameControllers.GameController;
import com.TillDawn.Models.GameAssetManager;
import com.TillDawn.TillDawn;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameView implements Screen, InputProcessor {
    private Stage stage;
    private TillDawn game = TillDawn.getTillDawn();
    private GameController controller;
    private OrthographicCamera camera;
    private FitViewport viewport;

    public GameView(GameController controller) {
        this.controller = controller;
        controller.setView(this);
    }

    @Override
    public void show() {
        Gdx.graphics.setCursor(GameAssetManager.getManager().getNewCursor());
        Gdx.input.setInputProcessor(this);
        camera = new OrthographicCamera();
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
        camera.update();
        this.stage = new Stage(viewport);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.1f, 0.1f, 0.1f, 1);
        if (game.isGray())
            game.getBatch().setShader(game.getGrayscaleShader());

        float playerX = controller.getPlayerController().getPlayer().getPosX();
        float playerY = controller.getPlayerController().getPlayer().getPosY();

// 2. Clamp camera to not go outside background (optional, if background size is known)
        float halfWidth = camera.viewportWidth / 2;
        float halfHeight = camera.viewportHeight / 2;
        int bgWidth = controller.getWorldController().getBackgroundTexture().getWidth();
        int bgHeight = controller.getWorldController().getBackgroundTexture().getHeight();

        camera.position.set(
                MathUtils.clamp(playerX, halfWidth, bgWidth - halfWidth),
                MathUtils.clamp(playerY, halfHeight, bgHeight - halfHeight),
                0
        );

// 3. Update and set camera
        camera.update();
        game.getBatch().setProjectionMatrix(camera.combined);
        game.getBatch().begin();
        controller.getWeaponController().getWeapon().getSprite().setPosition(playerX, playerY);
        controller.updateGame();
        game.getBatch().end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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
        Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
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

    public GameController getController() {
        return controller;
    }
}
