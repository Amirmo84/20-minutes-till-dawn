package com.TillDawn.Controllers.MenuControllers;

import com.TillDawn.Models.*;
import com.TillDawn.Models.Enums.Heroes;
import com.TillDawn.Models.Enums.Weapons;
import com.TillDawn.TillDawn;
import com.TillDawn.Views.GameView;
import com.TillDawn.Views.MainMenu;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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

    private void handlePlay(){
        play.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Game game1 = new Game();
                GameAssetManager.getManager().getClickSound().play();
                App.getApp().setLoggedInUser(new User("", "", "", ""));
//                App.getApp().getLoggedInUser().getKeyManagment().changeMovement();
                Player player = new Player(App.getApp().getLoggedInUser());
                player.setHero(Heroes.getHeroByName(heroChooser.getSelected()));
                player.setWeapon(Weapons.getWeaponByName(weaponChooser.getSelected()));
                player.setPlayerTexture(GameAssetManager.getManager().firstFrameOfChar(heroChooser.getSelected()));
                Animation<TextureRegion> animation = GameAssetManager.getManager().getCharAnimation(heroChooser.getSelected());
                player.setAnimation(animation);
                player.setWidth(animation.getKeyFrame(0).getRegionWidth() * 3f);
                player.setHeight(animation.getKeyFrame(0).getRegionHeight() * 3f);
                player.setPlayerSprite(new Sprite(player.getPlayerTexture()));
                player.setGridPos(new GridPos());
                player.getGridPos().setX(Gdx.graphics.getWidth() / 2f);
                player.getGridPos().setY(Gdx.graphics.getHeight() / 2f);
                player.setMaxHp(player.getHero().getHp());
                player.setHp(player.getHero().getHp());
                player.setAmmo(Weapons.getWeaponByName(weaponChooser.getSelected()).getAmmoMax());
                player.setXp(0);
                game1.setPlayer(player);
                game1.setPlayer(player);
                game1.setMaxTime((float) gameLengthSelectBox.getSelected());
                App.getApp().getLoggedInUser().setCurrentGame(game1);

                game.getScreen().dispose();
                game.setScreen(new GameView());
            }
        });
    }

    public void handleButtons(){
        handleBack();
        handlePlay();
    }

    public Table getTable() {
        return table;
    }
}
