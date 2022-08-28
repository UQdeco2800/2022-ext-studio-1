package com.deco2800.game.components.npcEvictionMenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.deco2800.game.GdxGame;
import com.deco2800.game.components.settingsmenu.SettingsMenuDisplay;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NpcEvictionMenuDisplay extends UIComponent {
    private static final Logger logger = LoggerFactory.getLogger(SettingsMenuDisplay.class);
    private final GdxGame game;
    private Table rootTable;
    private Table bgTable;


    public NpcEvictionMenuDisplay(GdxGame game) {
        super();
        this.game = game;

    }

    @Override
    public void create() {
        super.create();
        addActors();
    }
    Window window;
    Skin mskin;
    TextButton tbOk, tbCancel;
    Label confirmLabel;
    Label infoLabel;
    private void addActors() {

        Image background_npc_menu =
                new Image(
                        ServiceLocator.getResourceService()
                                .getAsset("images/eviction_menu/evictionMenu_background.png", Texture.class));


        Table menuNpcs = makeNpcCards();
        TextButton exitBtn = new TextButton("Exit", skin);
        exitBtn.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.debug("Exit button clicked");
                        exitMenu();
                    }
                });
        bgTable =new Table();
        bgTable.setFillParent(true);
        bgTable.add(background_npc_menu);

        rootTable = new Table();
        rootTable.setFillParent(true);
        rootTable.add(menuNpcs).center();
        rootTable.row();
        rootTable.add(exitBtn);
        //rootTable.debug();
        stage.addActor(bgTable);

        stage.addActor(rootTable);

    }
    ClickListener confirmListener= new ClickListener() {

        @Override
        public void clicked(InputEvent event, float x, float y) {
            Gdx.app.log("TAG", "dialog ok button is clicked");
            window.setVisible(true);
        }
    };
    private Table makeNpcCards() {

//        TextButton confirmBtn1 = new TextButton("Confirm", skin);
        /** build new style select button */
        Button.ButtonStyle styleSelect = new Button.ButtonStyle();
        styleSelect.up = new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("images/eviction_menu/selectButton_single.png"))));
        //here is for button effect when you pressed on button
        styleSelect.over = new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("images/eviction_menu/selectButton_single.png"))));
        Button confirmBtn1 = new Button(styleSelect);
        Button confirmBtn2 = new Button(styleSelect);
        Button confirmBtn3 = new Button(styleSelect);
        Button confirmBtn4 = new Button(styleSelect);

        Texture mytexture = new Texture(Gdx.files.internal("images/eviction_menu/evictionCard_single.png"));
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(mytexture));

        ImageButton npcButton = new ImageButton(drawable);
        ImageButton npcButtonTwo = new ImageButton(drawable);
        ImageButton npcButtonThree = new ImageButton(drawable);
        ImageButton npcButtonFour = new ImageButton(drawable);

        confirmBtn1.addListener(confirmListener);
        confirmBtn2.addListener(confirmListener);
        confirmBtn3.addListener(confirmListener);
        confirmBtn4.addListener(confirmListener);
        npcButton.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.debug("Start button clicked");
                        //entity.getEvents().trigger("start");
                    }
                });

        Table table = new Table();

        table.add(npcButton).height(600).width(500);
        table.add(npcButtonTwo).height(600).width(500);
        table.add(npcButtonThree).height(600).width(500);
        table.add(npcButtonFour).height(600).width(500);
        table.row();
        table.add(confirmBtn1).height(70).width(140).padLeft(60);
        table.add(confirmBtn2).height(70).width(140).padLeft(60);
        table.add(confirmBtn3).height(70).width(140).padLeft(60);
        table.add(confirmBtn4).height(70).width(140).padLeft(60);




    //   table.debug();


        return table;
    }

    private void exitMenu() {
        game.setScreen(GdxGame.ScreenType.MAIN_MENU);
    }

    @Override
    protected void draw(SpriteBatch batch) {

    }
}


