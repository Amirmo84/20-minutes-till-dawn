package com.TillDawn.Controllers.MenuControllers;

import com.TillDawn.Models.App;
import com.TillDawn.Models.Enums.Heroes;
import com.TillDawn.Models.Enums.Language;
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
    private Language currentLanguage;
    private Label descriptionLabel;
    private Label menuTitle;
    private SelectBox<String> heroSelect;
    private TextButton backButton;
    private Table table = new Table(skin);

    public HintMenuController() {
        user = App.getApp().getLoggedInUser();
        currentLanguage = (user != null) ? user.getLanguage() : Language.ENGLISH;
        
        // Initialize UI components with translations
        descriptionLabel = new Label(showHeroStats(selectedHero), skin);
        menuTitle = new Label(currentLanguage.get("menu.hint"), skin);
        heroSelect = new SelectBox<>(skin);
        backButton = new TextButton(currentLanguage.get("button.back"), skin);
        
        setupUI();
    }

    private void setupUI() {
        table.clear();
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

        table.add(new Label(currentLanguage.get("hint.select_hero"), skin)).colspan(2).padBottom(10).row();

        heroSelect.setItems(Heroes.heroesNames());
        heroSelect.setSelected(Heroes.SHANA.getName());

        table.add(heroSelect).width(300).center().padRight(15);
        table.add(descriptionLabel).center().growX().row();

        return table;
    }

    private Table createControlsSection() {
        Table table = new Table();
        table.defaults().pad(5);

        table.add(new Label(currentLanguage.get("hint.controls"), skin)).colspan(2).padBottom(10).row();

        // Only add control rows if user is available
        if (user != null && user.getKeyManagement() != null) {
            addControlRow(table, currentLanguage.get("controls.up"), Input.Keys.toString(user.getKeyManagement().getUp()));
            addControlRow(table, currentLanguage.get("controls.down"), Input.Keys.toString(user.getKeyManagement().getDown()));
            addControlRow(table, currentLanguage.get("controls.left"), Input.Keys.toString(user.getKeyManagement().getLeft()));
            addControlRow(table, currentLanguage.get("controls.right"), Input.Keys.toString(user.getKeyManagement().getRight()));
            addControlRow(table, currentLanguage.get("controls.auto_aim"), Input.Keys.toString(user.getKeyManagement().getAutoAimButton()));
            addControlRow(table, currentLanguage.get("controls.reload"), Input.Keys.toString(user.getKeyManagement().getReloadButton()));
            addControlRow(table, currentLanguage.get("controls.shoot"), currentLanguage.get("controls.left_click"));
        } else {
            addControlRow(table, currentLanguage.get("hint.login_required"), "");
        }

        return table;
    }

    private Table createCheatsSection() {
        Table table = new Table();
        table.defaults().pad(5);

        table.add(new Label(currentLanguage.get("hint.cheats"), skin)).colspan(2).padBottom(10).row();

        addCheatRow(table, currentLanguage.get("cheats.key1"), currentLanguage.get("cheats.desc1"));
        addCheatRow(table, currentLanguage.get("cheats.key2"), currentLanguage.get("cheats.desc2"));
        addCheatRow(table, currentLanguage.get("cheats.key3"), currentLanguage.get("cheats.desc3"));
        addCheatRow(table, currentLanguage.get("cheats.key4"), currentLanguage.get("cheats.desc4"));
        addCheatRow(table, currentLanguage.get("cheats.key5"), currentLanguage.get("cheats.desc5"));

        return table;
    }

    private Table createAbilitiesSection() {
        Table table = new Table();
        table.defaults().pad(10);

        table.add(new Label(currentLanguage.get("hint.abilities"), skin)).colspan(2).padBottom(5).row();

        table.add(new Label(currentLanguage.get("abilities.vitality"), skin)).padRight(10);
        table.add(new Label(currentLanguage.get("abilities.damager"), skin)).padBottom(2).row();
        table.add(new Label(currentLanguage.get("abilities.procrease"), skin)).padRight(10);
        table.add(new Label(currentLanguage.get("abilities.amocrease"), skin)).row();
        addAbilityRow(table, currentLanguage.get("abilities.speedy.name"), currentLanguage.get("abilities.speedy.desc"));

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
        return String.format(currentLanguage.get("hint.hero_stats"), 
            hero.getName(), 
            hero.getHp(), 
            hero.getSpeed());
    }

    public Table getTable() {
        return table;
    }

    public void setUser(User user) {
        this.user = user;
        currentLanguage = (user != null) ? user.getLanguage() : Language.ENGLISH;
        refreshUI();
    }

    public void refreshUI() {
        // Update all text elements with new language
        menuTitle.setText(currentLanguage.get("menu.hint"));
        backButton.setText(currentLanguage.get("button.back"));
        descriptionLabel.setText(showHeroStats(selectedHero));
        
        // Rebuild the entire table with new translations
        setupUI();
    }
}
