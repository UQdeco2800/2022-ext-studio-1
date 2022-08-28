package com.deco2800.game.components.npcEvictionMenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.deco2800.game.GdxGame;
import com.deco2800.game.components.settingsmenu.SettingsMenuDisplay;
import com.deco2800.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NpcEvictionMenuDisplay extends UIComponent {
    private static final Logger logger = LoggerFactory.getLogger(SettingsMenuDisplay.class);
    private final GdxGame game;
    private Table rootTable;


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
        mskin = new Skin(Gdx.files.internal("sk/uiskin.json"));
        BitmapFont bitmapFont = new BitmapFont(Gdx.files.internal("flat-earth/skin/fonts/pixel_32_modal.fnt"));
        window = new Window("", mskin);
        // The default text is displayed on the left
        Label.LabelStyle confirmstyle = new Label.LabelStyle();
        confirmstyle.fontColor=new Color(66  , 39, 39, 1);
        confirmstyle.font=bitmapFont;
        confirmLabel = new Label("CONFIRM", confirmstyle);
        confirmLabel.setFontScale(1.2f);
        confirmLabel.setY(140);
        confirmLabel.setAlignment(Align.center);
        confirmLabel.setWidth(500);
        confirmLabel.setHeight(120);
        confirmLabel.setText("CONFIRM");
        confirmLabel.setVisible(true);

        BitmapFont infoBitmapFont = new BitmapFont(Gdx.files.internal("flat-earth/skin/fonts/pixel_32_info.fnt"));
        Label.LabelStyle infostyle = new Label.LabelStyle();
        infostyle.fontColor=new Color(66  , 39, 39, 1);
        infostyle.font=infoBitmapFont;
        infoLabel = new Label("If he is a traitor, you will succeed in saving Atlantis!\n\nOtherwise, keep looking for clues!", infostyle);
        infoLabel.setFontScale(0.46f);
        infoLabel.setY(10);
        infoLabel.setAlignment(Align.center);
        infoLabel.setWidth(500);
        infoLabel.setHeight(220);
        infoLabel.setText("If he is a traitor, you will succeed in saving Atlantis!\n\nOtherwise, keep looking for clues!");
        infoLabel.setVisible(true);

//        window.getTitleLabel().setStyle(style);
//        window.getTitleLabel().setFontScale(1.5f);
//        window.getTitleLabel().setY(-120);
        window.setWidth(500);
        window.setHeight(240);
        // The default window position is in the lower left corner.
        window.setX(Gdx.graphics.getWidth() / 2 - window.getWidth() /2 );
        window.setY(Gdx.graphics.getHeight() / 2 - window.getHeight() / 2);
        // Drag the TitleLabel, the window will move
        window.setMovable(false);

//        window.setBackground();
        tbOk = new TextButton("OK", mskin);
        tbCancel = new TextButton("CANCEL", mskin);
        tbOk.setSize(tbCancel.getPrefWidth(), tbCancel.getPrefHeight());
        tbCancel.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                window.setVisible(false);
            }

        });
        tbOk.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("TAG", "dialog ok button is clicked");
                game.setScreen(GdxGame.ScreenType.MAIN_MENU);
                window.setVisible(false);
            }

        });
        tbOk.setX(window.getWidth() / 2+70);
        tbOk.setY(20);
        tbCancel.setX(window.getWidth() / 2-140);
        tbCancel.setY(20);

        window.addActor(infoLabel);
        window.addActor(confirmLabel);
        window.addActor(tbOk);
        window.addActor(tbCancel);
        window.setVisible(false);


        Label title = new Label("Npc Eviction Menu", skin, "title");
        Table menuNpcs = makeNpcCards();
        Table menuNpcs_two = makeNpcCards();
        TextButton exitBtn = new TextButton("Exit", skin);
        exitBtn.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.debug("Exit button clicked");
                        exitMenu();
                    }
                });
        rootTable = new Table();
        rootTable.setFillParent(true);

        rootTable.add(title).height(80);

        rootTable.row();
        rootTable.add(menuNpcs);
        rootTable.row();
        rootTable.add(menuNpcs_two);
        rootTable.row();

        rootTable.add(exitBtn).expandY();
        rootTable.debug();
        stage.addActor(rootTable);
        stage.addActor(window);
    }
    ClickListener confirmListener= new ClickListener() {

        @Override
        public void clicked(InputEvent event, float x, float y) {
            Gdx.app.log("TAG", "dialog ok button is clicked");
            window.setVisible(true);
        }
    };
    private Table makeNpcCards() {

        TextButton confirmBtn1 = new TextButton("Confirm", skin);
        TextButton confirmBtn2 = new TextButton("Confirm", skin);
        TextButton confirmBtn3 = new TextButton("Confirm", skin);
        TextButton confirmBtn4 = new TextButton("Confirm", skin);
        Texture mytexture = new Texture(Gdx.files.internal("images/evictionCard_single.png"));
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(mytexture));

        ImageButton npcButton = new ImageButton(drawable);
        ImageButton npcButton_two = new ImageButton(drawable);
        ImageButton npcButton_three = new ImageButton(drawable);
        ImageButton npcButton_four = new ImageButton(drawable);

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
        table.add(npcButton_two).height(600).width(500);
        table.add(npcButton_three).height(600).width(500);
        table.add(npcButton_four).height(600).width(500);
        table.row();
        table.add(confirmBtn1);
        table.add(confirmBtn2);
        table.add(confirmBtn3);
        table.add(confirmBtn4);




        table.debug();


        return table;
    }

    private void exitMenu() {
        game.setScreen(GdxGame.ScreenType.MAIN_MENU);
    }

    @Override
    protected void draw(SpriteBatch batch) {

    }
}


