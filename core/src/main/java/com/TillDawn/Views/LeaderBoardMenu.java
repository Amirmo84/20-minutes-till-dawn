package com.TillDawn.Views;

import com.TillDawn.Models.App;
import com.TillDawn.Models.Enums.Language;
import com.TillDawn.Models.GameAssetManager;
import com.TillDawn.Models.User;
import com.TillDawn.TillDawn;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;

public class LeaderBoardMenu implements Screen {
    private TillDawn main = TillDawn.getTillDawn();
    private Stage stage;
    private Skin skin = GameAssetManager.getManager().getSkin();
    private Table scoreTable;
    private String currentSort = "score";
    private ArrayList<User> users;
    private User user;
    private Language currentLanguage;

    public LeaderBoardMenu() {
        user = App.getApp().getLoggedInUser();
        currentLanguage = (user != null) ? user.getLanguage() : Language.ENGLISH;
        users = App.getApp().getUsers();
        setStage();
        createUI();
    }

    private void createUI() {
        Table mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.pad(20);
        stage.addActor(mainTable);
        mainTable.add(new Label(currentLanguage.get("menu.leaderboard"), skin)).colspan(3).padBottom(20).row();
        mainTable.add(createSortSelector()).colspan(3).padBottom(20).row();

        scoreTable = new Table();
        scoreTable.defaults().pad(5);

        ScrollPane scrollPane = new ScrollPane(scoreTable, skin);
        scrollPane.setScrollingDisabled(true, false);
        mainTable.add(scrollPane).colspan(3).grow().row();

        TextButton backButton = new TextButton(currentLanguage.get("button.back"), skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                main.setScreen(new MainMenu());
            }
        });
        mainTable.add(backButton).colspan(3).padTop(20).width(200).height(80);
        refreshScores();
    }

    private Table createSortSelector() {
        Table table = new Table();
        table.defaults().pad(5);

        table.add(new Label(currentLanguage.get("leaderboard.sort_by"), skin)).right().padRight(10);

        SelectBox<String> sortSelect = new SelectBox<>(skin);
        sortSelect.setItems(
            currentLanguage.get("leaderboard.sort.score"),
            currentLanguage.get("leaderboard.sort.kills"),
            currentLanguage.get("leaderboard.sort.time"),
            currentLanguage.get("leaderboard.sort.username")
        );
        sortSelect.setSelected(currentLanguage.get("leaderboard.sort.score"));
        sortSelect.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                switch (sortSelect.getSelectedIndex()) {
                    case 0:
                        currentSort = "score";
                        break;
                    case 1:
                        currentSort = "kills";
                        break;
                    case 2:
                        currentSort = "time";
                        break;
                    case 3:
                        currentSort = "username";
                        break;
                }
                refreshScores();
            }
        });

        table.add(sortSelect).width(200);
        return table;
    }

    private void refreshScores() {
        scoreTable.clear();

        scoreTable.add(new Label(currentLanguage.get("leaderboard.rank"), skin)).width(60);
        scoreTable.add(new Label(currentLanguage.get("leaderboard.username"), skin)).width(200);
        scoreTable.add(new Label(currentLanguage.get("leaderboard.score"), skin)).width(100);
        scoreTable.add(new Label(currentLanguage.get("leaderboard.kills"), skin)).width(100);
        scoreTable.add(new Label(currentLanguage.get("leaderboard.time_alive"), skin)).width(120);
        scoreTable.row();

        sortScores();

        for (int i = 0; i < Math.min(users.size(), 10); i++) {
            User player = users.get(i);

            Color rowColor = Color.WHITE;
            if (i == 0) rowColor = Color.GOLD;
            else if (i == 1) rowColor = Color.GRAY;
            else if (i == 2) rowColor = new Color(0.8f, 0.5f, 0.2f, 1f);

            if(player.getUsername().equals(user != null ? user.getUsername() : "")) {
                rowColor = Color.GREEN;
            }

            Label rankLabel = new Label((i + 1) + ".", skin);
            rankLabel.setColor(rowColor);
            scoreTable.add(rankLabel);

            Label nameLabel = new Label(player.getUsername(), skin);
            nameLabel.setColor(rowColor);
            scoreTable.add(nameLabel);

            Label scoreLabel = new Label(String.format("%.2f", player.getScore()), skin);
            scoreLabel.setColor(rowColor);
            scoreTable.add(scoreLabel);

            Label killsLabel = new Label(String.valueOf(player.getKills()), skin);
            killsLabel.setColor(rowColor);
            scoreTable.add(killsLabel);

            Label timeLabel = new Label(player.getFormattedTime(), skin);
            timeLabel.setColor(rowColor);
            scoreTable.add(timeLabel);

            scoreTable.row();
        }
    }

    private void sortScores() {
        switch (currentSort) {
            case "score":
                users.sort((a, b) -> Float.compare(b.getScore(), a.getScore()));
                break;
            case "kills":
                users.sort((a, b) -> Integer.compare(b.getKills(), a.getKills()));
                break;
            case "time":
                users.sort((a, b) -> Float.compare(b.getMaxTimeLived(), a.getMaxTimeLived()));
                break;
            case "username":
                users.sort((a, b) -> {
                    if (a == null || b == null) return 0;
                    if (a.getUsername() == null || b.getUsername() == null) return 0;
                    return a.getUsername().toLowerCase().compareTo(b.getUsername().toLowerCase());
                });
                break;
        }
    }

    private void setStage() {
        stage = new Stage(new ScreenViewport());
        Image back = new Image(GameAssetManager.getManager().getGameBackGround());
        back.setFillParent(true);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(v);
        stage.draw();
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
