package com.deco2800.game.components.gameoverScreen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.scenes.scene2d.Actor;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import com.deco2800.game.GdxGame;
import com.deco2800.game.components.player.InventoryDisplayComponent;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class GameOverDisplay extends UIComponent {

    private static final Logger logger = LoggerFactory.getLogger(InventoryDisplayComponent.class);

    private Table rootTable;
    private Table backgroundTable;

    private Label title;

    private GdxGame game = new GdxGame();

    public GameOverDisplay(GdxGame game) {
        super();
        this.game = game;
    }
    @Override
    public void create(){
        super.create();
        addActors();
    }

    private void addActors() {


        TextButton exitBtn = new TextButton("exit", skin);
        exitBtn.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.info("Exiting main game screen");
                        game.setScreen(GdxGame.ScreenType.MAIN_MENU);
                    }
                });
        backgroundTable = new Table();
        backgroundTable.setFillParent(true);


        rootTable = new Table();
        rootTable.setFillParent(true);
        title = new Label("GAME OVER", skin);
        title.setFontScale(2f);
        rootTable.add(title).pad(5);
        rootTable.row();
        rootTable.row();
        rootTable.add(exitBtn);

        stage.addActor(rootTable);

    }


    @Override
    public void draw(SpriteBatch batch) {
    }

}
