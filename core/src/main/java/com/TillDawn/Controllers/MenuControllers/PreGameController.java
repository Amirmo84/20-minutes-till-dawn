package com.TillDawn.Controllers.MenuControllers;

import com.TillDawn.Controllers.GameControllers.GameController;
import com.TillDawn.Models.App;
import com.TillDawn.Models.Enums.Heroes;
import com.TillDawn.Models.Enums.Weapons;
import com.TillDawn.Models.Game;
import com.TillDawn.Models.GameAssetManager;
import com.TillDawn.Models.Player;
import com.TillDawn.TillDawn;
import com.TillDawn.Views.GameView;
import com.TillDawn.Views.MainMenu;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class PreGameController {
    private TillDawn game = TillDawn.getTillDawn();
    private final Skin skin = GameAssetManager.getManager().getSkin();

    private final Label menuTitle = new Label("PreGame menu", skin);
    private final Label hero = new Label("Choose your hero: ", skin);
    private final Label weapon = new Label("Choose your weapon: ", skin);
    private final Label length = new Label("Choose game length: ", skin);

    private final TextButton back = new TextButton("Back", skin);
    private final TextButton play = new TextButton("Play", skin);

    private final SelectBox<String> heroChooser = new SelectBox<>(skin);
    private final SelectBox<String> weaponChooser = new SelectBox<>(skin);
    private final SelectBox<Integer> gameLengthSelectBox = new SelectBox<>(skin);

    private Table table = new Table(skin);

    public PreGameController() {
        table.setFillParent(true);
        table.center();

        gameLengthSelectBox.setItems(2, 5, 10, 20);
        gameLengthSelectBox.setSelected(5);

        heroChooser.setItems(Heroes.heroesNames());
        heroChooser.setSelected("Shana");

        weaponChooser.setItems(Weapons.weaponsNames());
        weaponChooser.setSelected("Revolver");

        menuTitle.setFontScale(1.5f);
        table.add(menuTitle).padBottom(40).row();
        table.padTop(15).row();
        Table subTable = new Table();
        subTable.add(hero).padRight(20);
        subTable.add(heroChooser).width(350);
        table.add(subTable).row();
        Table subTable1 = new Table();
        subTable1.add(weapon).padRight(20);
        subTable1.add(weaponChooser).width(400);
        table.add(subTable1).row();
        Table subTable2 = new Table();
        subTable2.add(length).padRight(20);
        subTable2.add(gameLengthSelectBox);
        table.add(subTable2).row();
        table.padTop(20).row();
        table.add(play).row();
        table.padTop(10).row();
        table.add(back);
    }

    private void handleBack(){
        back.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.getScreen().dispose();
                game.setScreen(new MainMenu());
            }
        });
    }

    private void handleLength(){
        gameLengthSelectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setGameLength((float) gameLengthSelectBox.getSelected());
            }
        });
    }

    private void handlePlay(){
        play.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Game game1 = new Game();
                Player player = new Player(App.getApp().getLoggedInUser());
                player.setHero(Heroes.getHeroByName(heroChooser.getSelected()));
                player.setWeapon(Weapons.getWeaponByName(weaponChooser.getSelected()));

                game.getScreen().dispose();
                game.setScreen(new GameView(new GameController()));
            }
        });
    }

    public void handleButtons(){
        handleBack();
        handleLength();
        handlePlay();
    }

    public Table getTable() {
        return table;
    }
}
