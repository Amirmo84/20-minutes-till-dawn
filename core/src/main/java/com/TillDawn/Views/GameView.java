package com.TillDawn.Views;

import com.TillDawn.Controllers.GameControllers.PlayerController;
import com.TillDawn.Models.*;
import com.TillDawn.Models.Enums.Paths;
import com.TillDawn.TillDawn;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.Iterator;

public class GameView implements Screen, InputProcessor {
    private TillDawn tillDawn = TillDawn.getTillDawn();
    private Stage stage;

    private final float screenWidth = Gdx.graphics.getWidth();
    private final float screenHeight = Gdx.graphics.getHeight();

    private Skin skin = GameAssetManager.getManager().getSkin();
    private User user = App.getApp().getLoggedInUser();
    private Game game;
    private Player player;
    private PlayerController playerController;
    private float backMp = 1.5f;
    private Timer timer;
    private float tentaclesTime = 0;
    private float eyeBatsTime = 0;
    private float reloadStateTime = 0;
    private boolean isPaused = false;
    private boolean isLose = false;
    private BitmapFont font;
    private Texture lightMask = new Texture(Gdx.files.internal("light_mask.png"));
    private Sprite lightSprite = new Sprite(lightMask);


    @Override
    public void show() {
        Gdx.graphics.setCursor(GameAssetManager.getManager().getNewCursor());
        Gdx.input.setInputProcessor(this);
        this.stage = new Stage(new ScreenViewport());
        user = App.getApp().getLoggedInUser();
        game = user.getCurrentGame();
        game.setBackHeight(getBackgroundHeight());
        game.setBackWidth(getBackgroundWidth());
        player = game.getPlayer();
        playerController = new PlayerController(player, game, this);
        game.initializeTrees(screenWidth, screenHeight, 50);
        timer = new Timer();
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                if (game.getElderBoss() == null) {
                    spawnBoss();
                }
                this.cancel();
            }
        }, game.getMaxTime() / 2, 2);
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(Paths.FONT.getPath()));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 24; // size in pixels
        font = generator.generateFont(parameter);
        generator.dispose();
        lightSprite.setSize(200, 200);
//        lightSprite.setPosition(Gdx.graphics.getWidth() / 2f + 5f, Gdx.graphics.getHeight() / 2f + 10f);
        lightSprite.setOriginCenter();
    }

    public void renderTrees() {
        for (Tree tree : game.getTrees()) {
            TextureRegion currentFrame = tree.getAnimation().getKeyFrame(tree.getStateTime(), true);
            tillDawn.getBatch().setColor(1, 1, 1, 0.5f);
            tillDawn.getBatch().draw(currentFrame, player.getGridPos().getX() + tree.getX(),
                    player.getGridPos().getY() + tree.getY(), tree.getWidth(), tree.getHeight());
            tillDawn.getBatch().setColor(1, 1, 1, 1);
            tree.getRectangle().setX(player.getGridPos().getX() + tree.getX());
            tree.getRectangle().setY(player.getGridPos().getY() + tree.getY());
        }
    }

    class WinView implements Screen{
        @Override
        public void show() {
            player.getUser().getSfxManager().setSound(GameAssetManager.getManager().getYouWinSound());
            player.getUser().getSfxManager().play();
            ScreenUtils.clear(0, 0, 0, 1);
            timer.clear();
            user.setScore(user.getScore() + game.getTimeGone() * player.getKills());
            user.setKills(user.getKills() + player.getKills());
            user.setMaxTimeLived(Math.max(user.getMaxTimeLived(), game.getTimeGone()));
            createWinScreen();
        }

        @Override
        public void render(float delta) {

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

        }
    }

    @Override
    public void render(float delta) {
//        ScreenUtils.clear(0.1f, 0.1f, 0.1f, 1);
        if (player.getHp() <= 0 || isLose){
            lose();
            return;
        }
        if (game.getTimeGone() >= game.getMaxTime()){
            win();
//            tillDawn.setScreen(new WinView());
            return;
        }
        if (isPaused){
            pause();
            return;
        }

        game.updateItemsTime(delta);

        if (player.isHurt()) {
            player.setHurtTime(player.getHurtTime() + delta);
            if (player.getHurtTime() > 1f) {
                player.setHurtTime(0);
                player.setHurt(false);
            }
        }
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        if (player.isLevelUp()){
            printAbilitiesMenu();
            return;
        }

        if (user.isGray())
            tillDawn.getBatch().setShader(tillDawn.getGrayscaleShader());
        else
            tillDawn.getBatch().setShader(tillDawn.getLightShader());
        checkElderBounds();
        tillDawn.getBatch().begin();
        //TODO render

        tillDawn.getBatch().draw(GameAssetManager.getManager().getGameBackGround(), 0, 0, screenWidth, screenHeight);
        renderStats();
        timeUpdate(delta);
        playerController.update(delta);
        playerController.handlePlayerInput();
        playerController.render(tillDawn.getBatch());
        renderTrees();
        renderTentacles();
        renderEyeBats();
        renderXps();
        renderExplosions(delta);
        renderElderBoss();
//        renderReloadAnimation(delta);

//        tillDawn.getBatch().setBlendFunction(GL20.GL_DST_COLOR, GL20.GL_ZERO);
//        lightSprite.setPosition(
//                Gdx.graphics.getWidth() / 2f - lightSprite.getWidth() / 2f + 5f,
//                Gdx.graphics.getHeight() / 2f - lightSprite.getHeight() / 2f + 10f
//        );
//        lightSprite.draw(tillDawn.getBatch());

        tillDawn.getBatch().end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.GRAY);
        shapeRenderer.rect(20, Gdx.graphics.getHeight() - 45,
                Gdx.graphics.getWidth() - 40, 40);

        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(20, Gdx.graphics.getHeight() - 45,
                (Gdx.graphics.getWidth() - 40) * ((float) player.getXp() / (float) (player.getLevel() * 20)), 40);
        shapeRenderer.end();
        renderElderBounds();

        tillDawn.getBatch().begin();
        font.draw(tillDawn.getBatch(), "LEVEL: " + player.getLevel(),
                Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() - 15);
        tillDawn.getBatch().end();
    }

    private void spawnTentacle(float time){
        if (tentaclesTime >= 3) {
            tentaclesTime = 0;
            int count = (int) Math.ceil(time / 50);
            for (int i = 0; i < count; i++) {
                float xPos = (float) (Math.random() * (getBackgroundWidth() - 30f));
                float yPos = (float) (Math.random() * (getBackgroundHeight() - 30f));
                Tentacle tentacle = new Tentacle(xPos, yPos);

                game.getTentacles().add(tentacle);
            }
        }
    }

    private void updateTentacles() {
        int index = -1;
        for (Tentacle b : game.getTentacles()) {
            Vector2 direction = new Vector2(
                    b.getX() + player.getGridPos().getX() - Gdx.graphics.getWidth() / 2f,
                    b.getY() + player.getGridPos().getY() - Gdx.graphics.getHeight() / 2f
            ).nor();

            b.setX(b.getX() - direction.x);
            b.setY(b.getY() - direction.y);
            b.getRectangle().setX(b.getX() + player.getGridPos().getX() - direction.x);
            b.getRectangle().setY(b.getY() + player.getGridPos().getY() - direction.y);
            if (b.getRectangle().overlaps(playerController.getPlayerRectangle())) {
                index = game.getTentacles().indexOf(b);
            }
        }
        if (index != -1) {
            game.getTentacles().remove(index);
            player.setHp(player.getHp() - .3f);
        }
    }

    private void renderTentacles() {
        for (Tentacle t : game.getTentacles()) {
            TextureRegion currentFrame = t.getAnimation().getKeyFrame(t.getStateTime(), true);
            if (t.getColor() != null) {
                tillDawn.getBatch().draw(GameAssetManager.getManager().getDamage(), player.getGridPos().getX() + t.getX(),
                        player.getGridPos().getY() + t.getY(), t.getWidth(), t.getHeight());
            } else
                tillDawn.getBatch().draw(currentFrame, player.getGridPos().getX() + t.getX(),
                        player.getGridPos().getY() + t.getY(), t.getWidth(), t.getHeight());
            t.getRectangle().setX(player.getGridPos().getX() + t.getX());
            t.getRectangle().setY(player.getGridPos().getY() + t.getY());
        }
    }

    private void renderEyeBats() {
        for (EyeBat e : game.getEyeBats()) {
            TextureRegion currentFrame = e.getAnimation().getKeyFrame(e.getStateTime(), true);
            if (e.getColor() != null) {
                tillDawn.getBatch().draw(GameAssetManager.getManager().getDamage(), player.getGridPos().getX() + e.getX(),
                        player.getGridPos().getY() + e.getY(), e.getWidth(), e.getHeight());
            } else
                tillDawn.getBatch().draw(currentFrame, player.getGridPos().getX() + e.getX(),
                        player.getGridPos().getY() + e.getY(), e.getWidth(), e.getHeight());
            e.getRectangle().setX(player.getGridPos().getX() + e.getX());
            e.getRectangle().setY(player.getGridPos().getY() + e.getY());
            renderEyBatBullets(e);
        }
    }

    public void renderEyBatBullets(EyeBat eyeBat) {
        int index = -1;
        for (OppBullet b : eyeBat.getBullets()) {
            b.getSprite().draw(TillDawn.getTillDawn().getBatch());
            b.getDirection().add(
                    screenWidth / 2f - player.getGridPos().getX(),
                    screenHeight / 2f - player.getGridPos().getY()
            ).nor();
            Vector2 direction = b.getDirection();

            b.getSprite().setX(b.getSprite().getX() + direction.x * 5);
            b.getSprite().setY(b.getSprite().getY() + direction.y * 5);
            b.getRectangle().setX(b.getSprite().getX() + direction.x * 5);
            b.getRectangle().setY(b.getSprite().getY() + direction.y * 5);
            if (b.getRectangle().overlaps(playerController.getPlayerRectangle())) {
                index = eyeBat.getBullets().indexOf(b);
                player.setHp(player.getHp() - .2f);
            }
        }
        if (index != -1) {
            eyeBat.getBullets().remove(index);
        }
    }

//    private void renderReloadAnimation(float delta){
//        if (player.getAmmo() != player.getWeapon().getAmmoMax() && player.isReloading()){
//            TextureRegion currentFrame = GameAssetManager.getManager().getReloadAnimation().getKeyFrame(reloadStateTime, false);
//            reloadStateTime += delta;
////            tillDawn.getBatch().draw(currentFrame, screenWidth / 2 + 15f, screenHeight / 2 + 10f);
//            tillDawn.getBatch().draw(currentFrame, playerController.getWeaponController().getTexture()., screenHeight / 2 + 10f,
//                    0, 0,
//            25f, 25f,
//            1.5f, 1.5f,
//                    0);
//        }
//    }

    private void updateEyeBats(float delta) {
        int index = -1;
        for (EyeBat b : game.getEyeBats()) {
            Vector2 direction = new Vector2(
                    b.getX() + player.getGridPos().getX() - Gdx.graphics.getWidth() / 2f,
                    b.getY() + player.getGridPos().getY() - Gdx.graphics.getHeight() / 2f
            ).nor();

            b.setX(b.getX() - direction.x);
            b.setY(b.getY() - direction.y);
            b.getRectangle().setX(b.getX() + player.getGridPos().getX() - direction.x);
            b.getRectangle().setY(b.getY() + player.getGridPos().getY() - direction.y);
            if (b.getRectangle().overlaps(playerController.getPlayerRectangle())) {
                index = game.getEyeBats().indexOf(b);
            }
            b.setShotTime(b.getShotTime() + delta);
            if (b.getShotTime() > 5) {
                b.setShotTime(0);
                b.getBullets().add(new OppBullet(player.getGridPos().getX() + b.getX(),
                        player.getGridPos().getY() + b.getY()));
            }
        }
        if (index != -1) {
            game.getEyeBats().remove(index);
            player.setHp(player.getHp() - .3f);
        }
    }

    private void spawnEyeBats(float time) {
        if (eyeBatsTime >= 10) {
            eyeBatsTime = 0;
            int count = (int) Math.ceil((4 * time - game.getMaxTime() + 30f) / 90f);
            for (int i = 0; i < count; i++) {
                float xPos = (float) (Math.random() * (getBackgroundWidth() - 30f));
                float yPos = (float) (Math.random() * (getBackgroundHeight() - 30f));
                EyeBat eyeBat = new EyeBat(xPos, yPos);

                game.getEyeBats().add(eyeBat);
            }
        }
    }

    public void updateXps() {
        int index = -1;
        for (XpDrop e : game.getXpDrops()) {
            if (e.getRectangle().overlaps(playerController.getPlayerRectangle())) {
                index = game.getXpDrops().indexOf(e);
                player.setXp(player.getXp() + 3);
            }
        }
        if (index != -1) {
            game.getXpDrops().remove(index);
        }
    }

    public void renderXps() {
        for (XpDrop e : game.getXpDrops()) {
            tillDawn.getBatch().draw(e.getTexture(), player.getGridPos().getX() + e.getX(),
                    player.getGridPos().getY() + e.getY(), e.getWidth(), e.getHeight());
            e.getRectangle().setX(player.getGridPos().getX() + e.getX());
            e.getRectangle().setY(player.getGridPos().getY() + e.getY());
        }
    }

    public void renderExplosions(float delta) {
        for (Explosion explosion : game.getExplosions()) {
            TextureRegion currentFrame = explosion.getAnimation()
                    .getKeyFrame(explosion.getStateTime(), false);
            tillDawn.getBatch().draw(currentFrame, player.getGridPos().getX() + explosion.getX(),
                    player.getGridPos().getY() + explosion.getY(),
                    explosion.getWidth(), explosion.getHeight());
        }
        Iterator<Explosion> iter = game.getExplosions().iterator();
        while (iter.hasNext()) {
            Explosion explosion1 = iter.next();
            if (explosion1.getAnimation().isAnimationFinished(explosion1.getStateTime())) {
                iter.remove();
            }
        }
    }

    private void renderStats(){
        SpriteBatch batch = TillDawn.getTillDawn().getBatch();

        font.draw(batch,
                "HP: " + String.format("%.1f", player.getHp()) + "/" + player.getMaxHp(),
                20,
                Gdx.graphics.getHeight() - 50);

        font.draw(batch,
                "XP: " + player.getXp(),
                20,
                Gdx.graphics.getHeight() - 80);
        font.draw(batch,
                "KILLS: " + player.getKills(),
                20,
                Gdx.graphics.getHeight() - 110);


        font.draw(batch,
                "AMMO: " + player.getAmmo(),
                20,
                Gdx.graphics.getHeight() - 140);

        if (player.isReloading()) {
            font.draw(batch,
                    "RELOAD_GUN",
                    screenWidth / 2f,
                    Gdx.graphics.getHeight() - 50);
        }

        float time = game.getMaxTime() - game.getTimeGone();
        int min = (int) time / 60;
        int sec = (int) (time % 60);
        font.draw(batch,
                min + " : " + sec,
                Gdx.graphics.getWidth() - 80,
                Gdx.graphics.getHeight() - 80);
    }

    public void spawnBoss(){
        float posX = (int) (Math.random() * screenWidth) + player.getGridPos().getX();
        float posY = (int) (Math.random() * screenHeight) + player.getGridPos().getY();
        ElderBoss boss = new ElderBoss(posX, posY);
        game.setElderBoss(boss);
    }

    private void renderElderBoss() {
        if (game.getElderBoss() != null) {
            ElderBoss boss = game.getElderBoss();
            TextureRegion currentFrame = boss.getAnimation().getKeyFrame(boss.getStateTime(), true);
            tillDawn.getBatch().draw(currentFrame, player.getGridPos().getX() + boss.getX(),
                    player.getGridPos().getY() + boss.getY(), boss.getWidth(), boss.getHeight());
            boss.getRectangle().setX(player.getGridPos().getX() + boss.getX());
            boss.getRectangle().setY(player.getGridPos().getY() + boss.getY());
        }
    }

    private void renderElderBounds() {
        if (game.getElderBoss() != null) {
            ElderBoss boss = game.getElderBoss();
            ShapeRenderer shapeRenderer = new ShapeRenderer();

            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(Color.YELLOW);
            shapeRenderer.rect(boss.getX() + player.getGridPos().getX() - boss.getBounds().getWidth() / 2f,
                    boss.getY() + player.getGridPos().getY() - boss.getBounds().getHeight() / 2f,
                    boss.getBounds().getWidth(), boss.getBounds().getHeight());
            shapeRenderer.end();
        }
    }

    private void checkElderBounds() {
        if (game.getElderBoss() != null) {
            ElderBoss boss = game.getElderBoss();
            float x = Gdx.graphics.getWidth() / 2f;
            float y = Gdx.graphics.getHeight() / 2f;
            if (boss.getX() + player.getGridPos().getX() + boss.getBounds().getWidth() / 2f
                    - playerController.getWidth() / 2f <= x) {
                player.getGridPos().setX(player.getGridPos().getX() + 80f);
                player.setHp(player.getHp() - .1f);
            } else if (boss.getX() + player.getGridPos().getX() - boss.getBounds().getWidth() / 2f
                    + playerController.getWidth() / 2f >= x) {
                player.getGridPos().setX(player.getGridPos().getX() - 80f);
                player.setHp(player.getHp() - .1f);
            } else if (boss.getY() + player.getGridPos().getY() + boss.getBounds().getHeight() / 2f
                    - playerController.getHeight() / 2f - 50f <= y) {
                player.getGridPos().setY(player.getGridPos().getY() + 80f);
                player.setHp(player.getHp() - .1f);
            } else if (boss.getY() + player.getGridPos().getY() - boss.getBounds().getHeight() / 2f
                    + playerController.getHeight() / 2f - 50f >= y) {
                player.getGridPos().setY(player.getGridPos().getY() - 80f);
                player.setHp(player.getHp() - .1f);
            }
        }
    }

    private void updateElderBoss(float delta) {
        if (game.getElderBoss() != null) {
            ElderBoss boss = game.getElderBoss();
            boss.setWalkStateTime(boss.getWalkStateTime() + delta);
            if (boss.getWalkStateTime() >= 5) {
                boss.setWalkStateTime(0);
                Vector2 direction = new Vector2(
                        boss.getX() + player.getGridPos().getX() - Gdx.graphics.getWidth() / 2f,
                        boss.getY() + player.getGridPos().getY() - Gdx.graphics.getHeight() / 2f
                ).nor();

                boss.setX(boss.getX() - 200 * direction.x);
                boss.setY(boss.getY() - 200 * direction.y);
                boss.getRectangle().setX(boss.getX() + player.getGridPos().getX() - 200 * direction.x);
                boss.getRectangle().setY(boss.getY() + player.getGridPos().getY() - 200 * direction.y);
//                if (boss.getRectangle().overlaps(playerController.getPlayerRectangle())) {
//                    player.setHp(player.getHp() - .5f);
//                }
                boss.getBounds().setWidth(boss.getBounds().getWidth() * .97f);
                boss.getBounds().setHeight(boss.getBounds().getHeight() * .97f);
            }
            if (boss.getRectangle().overlaps(playerController.getPlayerRectangle())) {
                player.setHp(player.getHp() - .5f);
            }
        }
    }

    private void timeUpdate(float delta){
        tentaclesTime += delta;
        eyeBatsTime += delta;
        game.modifyTimeGone(delta);

        spawnTentacle(game.getTimeGone());
        updateTentacles();

        spawnEyeBats(game.getTimeGone());
        updateEyeBats(delta);

        updateXps();

        updateElderBoss(delta);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
//        playerController.setX((float) width / 2);
//        playerController.setY((float) height / 2);
    }

    @Override
    public void pause() {
        stage.addActor(createCheatTable());
        stage.draw();
    }

    private Table createCheatTable() {
        //Not my idea

        Table table = new Table();
        table.setFillParent(true);

        Gdx.input.setInputProcessor(stage);

        Label cheatTitle = new Label("CHEAT_CODES", skin);
        table.add(cheatTitle).colspan(2).center().row();
        table.padTop(10).row();

        addCheatRow(table, "Key 1", "DECREASE_GAME_TIME");
        addCheatRow(table, "Key 2", "PLAYER_LEVEL_UP");
        addCheatRow(table, "Key 3", "INCREASE_HP");
        addCheatRow(table, "Key 4", "GO_TO_BOSS_FIGHT");
        addCheatRow(table, "Key 5", "INCREASE_PROJECTILE");

        table.add().height(20).row();
        Label abilitiesTitle = new Label("PLAYER_ABILITIES", skin);
        table.add(abilitiesTitle).colspan(2).center().row();

        for (Integer i : player.getAbilities()) {
            if (i.equals(1)) {
                addAbilityRow(table, "Vitality", "Increase Hp");

            } else if (i.equals(2)) {
                addAbilityRow(table, "Damager", "Increase Damage");

            } else if (i.equals(3)) {
                addAbilityRow(table, "Procrease", "Increase Projectile");

            } else if (i.equals(4)) {
                addAbilityRow(table, "Amocrease", "increase Ammo max");

            } else if (i.equals(5)) {
                addAbilityRow(table, "Speedy", "Increase player speed");

            }
        }

        table.add().height(30).row();

        TextButton resumeButton = new TextButton("RESUME", skin);
        TextButton quitButton = new TextButton("QUIT", skin);
        TextButton giveUpButton = new TextButton("GIVE_UP", skin);
        TextButton blackShader = new TextButton("WHITE_BLACK", skin);


        //TODO can remove this
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            isPaused = false;
            stage.clear();
            stage = new Stage(new ScreenViewport());
            setInputProccessor();
        }

        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                isPaused = false;
                stage.clear();
                stage = new Stage(new ScreenViewport());
                setInputProccessor();
            }
        });

        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
//                if (!user.isGuest())
//                    GameRepository.save(game);
                dispose();
                tillDawn.setScreen(new MainMenu());
            }
        });

        giveUpButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
//                if (!user.isGuest())
//                    GameRepository.save(game);
                isPaused = false;
                isLose = true;
                stage.clear();
            }
        });

        blackShader.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                isPaused = false;
                user.setGray(!user.isGray());
                stage.clear();
                stage = new Stage(new ScreenViewport());
                setInputProccessor();
            }
        });
        Table buttonTable = new Table();
        buttonTable.add(blackShader).width(500).height(100).colspan(5).row();
        buttonTable.defaults().pad(5).width(120).height(50);
        buttonTable.add(resumeButton).width(250).height(100);
        buttonTable.add(quitButton).width(250).height(100);
        buttonTable.add(giveUpButton).width(250).height(100);

        table.add(buttonTable).colspan(2).center();

        return table;
    }

    private void setInputProccessor(){
        Gdx.input.setInputProcessor(this);
    }

    private void addCheatRow(Table table, String key, String description) {
        table.add(new Label(key, skin)).left().width(80);
        table.add(new Label(description, skin)).left();
        table.row();
    }

    private void addAbilityRow(Table table, String ability, String value) {
        table.add(new Label(ability, skin)).left().width(150);
        table.add(new Label(value, skin)).left();
        table.row();
    }

    public void printAbilitiesMenu() {
        Table table = new Table();
        table.setFillParent(true);
        Gdx.input.setInputProcessor(stage);

        table.add(new Label("CHOOSE_ABILITY", skin, "title")).row();

        TextButton vitality = new TextButton("Vitality ", skin);
        TextButton damager = new TextButton("Damager ", skin);
        TextButton procrease = new TextButton("Procrease ", skin);
        TextButton amocrease = new TextButton("Amocrease ", skin);
        TextButton speedy = new TextButton("Speedy ", skin);

        vitality.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                player.setLevelUp(false);
                player.setMaxHp(player.getMaxHp() + 1);
                player.getAbilities().add(1);
                stage.clear();
                stage = new Stage(new ScreenViewport());
                setInputProccessor();
            }
        });
        damager.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                player.setLevelUp(false);
                player.setDamageMp(1.25f);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        player.setDamageMp(1f);
                        this.cancel();
                    }
                }, 10, 2);
                player.getAbilities().add(2);
                stage.clear();
                stage = new Stage(new ScreenViewport());
                setInputProccessor();
            }
        });
        procrease.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                player.setLevelUp(false);
                player.setProjectileAddition(1);
                stage.clear();
                stage = new Stage(new ScreenViewport());
                setInputProccessor();
            }
        });
        amocrease.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                player.setLevelUp(false);
                player.setMaxAmmoAddition(5);
                player.getAbilities().add(4);
                stage.clear();
                stage = new Stage(new ScreenViewport());
                setInputProccessor();
            }
        });
        speedy.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                player.setLevelUp(false);
                player.setSpeedMp(2);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        player.setSpeedMp(1f);
                        this.cancel();
                    }
                }, 10, 2);
                player.getAbilities().add(5);
                stage.clear();
                stage = new Stage(new ScreenViewport());
                setInputProccessor();
            }
        });

        table.add(vitality).colspan(2);
        table.add(damager).colspan(2).row();
        table.add(procrease).colspan(2);
        table.add(amocrease).colspan(2).row();
        table.add(speedy).colspan(2).row();
        stage.addActor(table);
        stage.draw();
    }

    public void lose() {
        ScreenUtils.clear(0, 0, 0, 1);
        timer.clear();
        user.setScore(user.getScore() + game.getTimeGone() * player.getKills());
        user.setKills(user.getKills() + player.getKills());
        user.setMaxTimeLived(Math.max(user.getMaxTimeLived(), game.getTimeGone()));
//        if (!user.isGuest()) {
//            GameRepository.save(game);
//        }
//        if (!user.isGuest())
//            UserRepository.save(user);
        game.setOver(true);
//        player.getUser().getSfxManager().setSound(GameAssetManager.getManager().getYouLoseSound());
//        player.getUser().getSfxManager().play();
        createLooseScreen();
    }

    public void win() {
        ScreenUtils.clear(0, 0, 0, 1);
        timer.clear();
        user.setScore(user.getScore() + game.getTimeGone() * player.getKills());
        user.setKills(user.getKills() + player.getKills());
        user.setMaxTimeLived(Math.max(user.getMaxTimeLived(), game.getTimeGone()));
        game.setOver(true);
//        if (!user.isGuest()) {
//            GameRepository.save(game);
//        }
//        if (!user.isGuest())
//            UserRepository.save(user);
//        player.getUser().getSfxManager().setSound(GameAssetManager.getManager().getYouWinSound());
//        player.getUser().getSfxManager().play();
        createWinScreen();
    }

    public void createLooseScreen() {
        Table table = new Table();
        table.setFillParent(true);
        Gdx.input.setInputProcessor(stage);
        stage.addActor(table);

        Label title = new Label("YOU_LOST", new Label.LabelStyle(new BitmapFont(), Color.RED));
        title.setFontScale(2f);

        Table statsTable = createStatsTable();

        Table buttonTable = createButtonTable();


        table.add(title).padBottom(40).row();
        table.add(statsTable).width(400).padBottom(40).row();
        table.add(buttonTable);

        stage.draw();

    }

    public void createWinScreen() {
        Table table = new Table();
        table.setFillParent(true);
        stage.clear();
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
        Label title = new Label("YOU_WON", new Label.LabelStyle(font, Color.GOLD));
        title.setFontScale(2f);

        Table statsTable = createStatsTable();

        Table buttonTable = createButtonTable();


        table.add(title).padBottom(40).row();
        table.add(statsTable).width(400).padBottom(40).row();
        table.add(buttonTable);

        stage.draw();

    }

    private Table createButtonTable() {
        Table buttonTable = new Table();
        buttonTable.defaults().pad(10).width(200).height(60);

        TextButton menuButton = new TextButton("MAIN_MENU", skin);
        TextButton replayButton = new TextButton("PLAY_AGAIN", skin);

        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                tillDawn.setScreen(new MainMenu());
            }
        });

        replayButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                tillDawn.setScreen(new PreGameMenu());
            }
        });

        buttonTable.add(replayButton).width(500).height(80).padRight(10);
        buttonTable.add(menuButton).width(500).height(80).padRight(10);

        return buttonTable;
    }

    private Table createStatsTable() {
        Table statsTable = new Table();
        statsTable.defaults().pad(10).left();

        statsTable.add(new Label("MISSION_REPORT", skin)).colspan(2).center().row();

        addStatRow(statsTable, "USERNAME" + " : ", player.getUser().getUsername(), "user-icon");
        addStatRow(statsTable, "ENEMY_DEFEATED" + " : ", String.valueOf(player.getKills()), "skull-icon");
        addStatRow(statsTable, "SURVIVAL_TIME" + " : ", formatTime(game.getTimeGone()), "clock-icon");
        addStatRow(statsTable, "TOTAL_SCORE" + " : ", String.valueOf(game.getTimeGone() * player.getKills()),
                "star-icon");

        return statsTable;
    }

    private String formatTime(float seconds) {
        int minutes = (int) (seconds / 60);
        int secs = (int) (seconds % 60);
        return String.format("%02d:%02d", minutes, secs);
    }


    private void addStatRow(Table table, String label, String value, String iconName) {
        table.add();
        table.add(new Label(label, skin)).padRight(100);
        table.add(new Label(value, skin)).row();
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
        playerController.getWeaponController().handleShot(screenX, screenY);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
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
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public float getBackgroundWidth(){
        return GameAssetManager.getManager().getGameBackGround().getWidth() / backMp;
    }

    public float getBackgroundHeight(){
        return GameAssetManager.getManager().getGameBackGround().getHeight() / backMp;
    }
}
