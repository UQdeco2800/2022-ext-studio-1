package com.deco2800.game.components.mainmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.deco2800.game.GdxGame;
import com.deco2800.game.GdxGame.ScreenType;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.files.UserSettings;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoadGameDisplay extends UIComponent {
    private static final Logger logger = LoggerFactory.getLogger(LoadGameDisplay.class);
    private final GdxGame game;

    private Table table;

    private Table descriptionTable;
    private Table rootTable;


    public LoadGameDisplay(GdxGame game) {
        super();
        this.game = game;
    }

    @Override
    public void create() {
        super.create();
        addActors();
    }

    private void addActors() {
        table = new Table();
        table.setFillParent(true);


        Image title =
                new Image(
                        ServiceLocator.getResourceService()
                                .getAsset("images/menu/LoadGameTitle.png", Texture.class));
        title.setPosition((Gdx.graphics.getWidth()-title.getWidth())/2f,Gdx.graphics.getHeight()-title.getHeight()-15);

        stage.addActor(title);


        stage.addActor(table);

        table.add(makeLoadSlots());


        Table exitButton = makeExitBtn();
        stage.addActor(exitButton);

    }


    private Table makeLoadSlots(){
        Table loadSlots = new Table();
        loadSlots.setFillParent(true);
        loadSlots.setWidth(500);
        loadSlots.padTop(30f);

        Table slots = new Table();

        TextButton save1Btn = new TextButton("Empty Save", skin);
        TextButton save2Btn = new TextButton("Empty Save", skin);
        TextButton save3Btn = new TextButton("Empty Save", skin);
        TextButton save4Btn = new TextButton("Empty Save", skin);

        table.row();
        table.add(save1Btn).padTop(30f).left();
        table.row();
        table.add(save2Btn).padTop(30f).left();
        table.row();
        table.add(save3Btn).padTop(30f).left();
        table.row();
        table.add(save4Btn).padTop(30f).left();
        table.row();

        loadSlots.add(slots).width(300);









    save1Btn.addListener(
            new ChangeListener() {
                @Override
                public void changed(ChangeEvent changeEvent, Actor actor) {
                    showSaveDescription();
                }
            });







        return loadSlots;
    }


    private Table showSaveDescription() {
        descriptionTable = new Table();
        descriptionTable.setFillParent(true);

        Label description = new Label("Emtpy Save Slot", skin);
        description.setFontScale(1f);
        descriptionTable.add(description).pad(5);


        stage.addActor(descriptionTable);

        return descriptionTable;

    }

    private void exit() {
        game.setScreen(ScreenType.MAIN_MENU);
    }

    private Table makeExitBtn() {
        TextButton exitBtn = new TextButton("Exit", skin);

        exitBtn.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.debug("Exit button clicked");
                        exit();
                    }
                });

        Table table = new Table();
        table.add(exitBtn).expandX().left().pad(0f, 100f, 100f, 0f);
        return table;
    }


    @Override
    protected void draw(SpriteBatch batch) {
    }

    @Override
    public void dispose() {
        rootTable.clear();
        super.dispose();
    }
}
