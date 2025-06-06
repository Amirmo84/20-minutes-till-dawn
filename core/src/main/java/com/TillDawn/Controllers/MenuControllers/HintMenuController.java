package com.TillDawn.Controllers.MenuControllers;

import com.TillDawn.Models.App;
import com.TillDawn.Models.Enums.Heroes;
import com.TillDawn.Models.GameAssetManager;
import com.TillDawn.Models.User;
import com.TillDawn.TillDawn;
import com.TillDawn.Views.HintMenuView;
import com.TillDawn.Views.MainMenu;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class HintMenuController {
    private TillDawn game = TillDawn.getTillDawn();
    private Skin skin = GameAssetManager.getManager().getSkin();
    private Heroes selectedHero = Heroes.SHANA;
    private User user = App.getApp().getLoggedInUser();
    private Label descriptionLabel = new Label(showHeroStats(selectedHero), skin);
    private Label menuTitle=  new Label("Hint Menu", skin);

    private SelectBox<String> heroSelect = new SelectBox<>(skin);

    private TextButton backButton = new TextButton("Back", skin);

    private Table table = new Table(skin);

    public HintMenuController() {
        table.setFillParent(true);
        table.center();
        table.pad(20);
        menuTitle.setFontScale(2f);
        table.add(menuTitle).colspan(2).padBottom(10).row();
        table.add(createHeroSelection()).colspan(2).growX().padBottom(10).row();
        
        Table contentTable = new Table();
        contentTable.setName("contentTable");  // Set name to find it later
        contentTable.add(createControlsSection()).width(400).padRight(20);
        contentTable.add(createCheatsSection()).width(400);
        table.add(contentTable).colspan(2).padBottom(10).row();
        
        table.add(createAbilitiesSection()).colspan(2).growX().row();
        table.add(backButton);
    }

    public ScrollPane addScroll(){
        ScrollPane scrollPane = new ScrollPane(table, skin);
        scrollPane.setFillParent(true);
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setFadeScrollBars(false);
        return scrollPane;
    }

    private Table createHeroSelection() {
        Table table = new Table();
        table.defaults().pad(5);

        table.add(new Label("SELECT HERO", skin)).colspan(2).padBottom(10).row();

        heroSelect.setItems(Heroes.heroesNames());
        heroSelect.setSelected(Heroes.SHANA.getName());

        table.add(heroSelect).width(300).center().padRight(15);
        table.add(descriptionLabel).center().growX().row();

        return table;
    }

    private Table createControlsSection() {
        Table table = new Table();
        table.defaults().pad(5);

        table.add(new Label("CONTROLS", skin)).colspan(2).padBottom(10).row();

        // Only add control rows if user is available
        if (user != null && user.getKeyManagement() != null) {
            addControlRow(table, "UP", Input.Keys.toString(user.getKeyManagement().getUp()));
            addControlRow(table, "DOWN", Input.Keys.toString(user.getKeyManagement().getDown()));
            addControlRow(table, "LEFT", Input.Keys.toString(user.getKeyManagement().getLeft()));
            addControlRow(table, "RIGHT", Input.Keys.toString(user.getKeyManagement().getRight()));
            addControlRow(table, "AUTO AIM", Input.Keys.toString(user.getKeyManagement().getAutoAimButton()));
            addControlRow(table, "RELOAD", Input.Keys.toString(user.getKeyManagement().getReloadButton()));
            addControlRow(table, "SHOOT", "Left click");
        } else {
            addControlRow(table, "Please log in to view controls", "");
        }

        return table;
    }

    private Table createCheatsSection() {
        Table table = new Table();
        table.defaults().pad(5);

        table.add(new Label("CHEAT CODES", skin)).colspan(2).padBottom(10).row();

        addCheatRow(table, "Key 1", "Decrease time of the game by 1 minute");
        addCheatRow(table, "Key 2", "Level up");
        addCheatRow(table, "Key 3", "Increase HP");
        addCheatRow(table, "Key 4", "Go to boss fight");
        addCheatRow(table, "Key 5", "Increase ammo max");

        return table;
    }

    private Table createAbilitiesSection() {
        Table table = new Table();
        table.defaults().pad(10);

        table.add(new Label("ABILITIES", skin)).colspan(2).padBottom(5).row();

        table.add(new Label("Vitality : HP max + 1" , skin)).padRight(10);
        table.add(new Label("Damager : Increase damage of the gun by *1.5f (temporary)", skin)).padBottom(2).row();
        table.add(new Label("Procrease : Increase the projectile by 1", skin)).padRight(10);
        table.add(new Label("Amocrease : Increase the ammo max by 5" , skin)).row();
        addAbilityRow(table, "Speedy", "Increase the speed by *2 (temporary)");

        return table;
    }

    private void addAbilityRow(Table table, String name, String description) {
        table.add().size(32).padRight(10);

        Table textTable = new Table();
        textTable.add(new Label(name, skin)).left().row();
        textTable.add(new Label(description, skin)).left().padTop(1);

        table.add(textTable).left();
        table.row();
    }

    private void addCheatRow(Table table, String key, String effect) {
        table.add(new Label(key, skin)).left().width(80);
        table.add(new Label(effect, skin)).left().padLeft(10);
        table.row();
    }

    private void addControlRow(Table table, String action, String key) {
        table.add(new Label(action, skin)).left().width(150);
        table.add(new Label(key, skin)).left().padLeft(20);
        table.row();
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

    private void handleSelector(){
        heroSelect.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                selectedHero = Heroes.getHeroByName(heroSelect.getSelected());
                descriptionLabel.setText(showHeroStats(selectedHero));
            }
        });
    }

    public void handleButtons(){
        handleBack();
        handleSelector();
    }

    private String showHeroStats(Heroes hero){
        return String.format("Name: %s      Hp: %d      Speed: %d", hero.getName(), hero.getHp(), hero.getSpeed());
    }

    public Table getTable() {
        return table;
    }

    public void setUser(User user) {
        this.user = user;
        // Rebuild the controls section with the new user
        Table contentTable = (Table) table.findActor("contentTable");
        if (contentTable != null) {
            contentTable.clear();
            contentTable.add(createControlsSection()).width(400).padRight(20);
            contentTable.add(createCheatsSection()).width(400);
        }
    }
}
