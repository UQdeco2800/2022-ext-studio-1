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
    private static final Logger logger = LoggerFactory.getLogger(NpcEvictionMenuDisplay.class);
    private static final int CONFIRM_BUTTON_WIDTH = 80;
    private static final int CONFIRM_BUTTON_HEIGHT = 50;
    private static final int NPC_CARD_HEIGHT = 450;
    private static final  int NPC_CARD_WIDTH = 400;
    private static final int CONFIRM_BUTTON_LEFT_PADDING = 40;
    private static final int NPC_CARD_TOP_PADDING = 0;
    private static final int BACKGROUND_HEIGHT_GAP =40 ;
    private static final int BACKGROUND_WIDTH_GAP = 40;
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
        bgTable.add(background_npc_menu).height(Gdx.graphics.getHeight()-BACKGROUND_HEIGHT_GAP).width(Gdx.graphics.getWidth()-BACKGROUND_WIDTH_GAP);

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
        Button confirmBtn5 = new Button(styleSelect);
        Button confirmBtn6 = new Button(styleSelect);
        Button confirmBtn7 = new Button(styleSelect);
        Button confirmBtn8 = new Button(styleSelect);

        Texture mytexture = new Texture(Gdx.files.internal("images/eviction_menu/evictionCard_single.png"));
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(mytexture));

        ImageButton npcButton1 = new ImageButton(drawable);
        ImageButton npcButton2 = new ImageButton(drawable);
        ImageButton npcButton3 = new ImageButton(drawable);
        ImageButton npcButton4 = new ImageButton(drawable);
        ImageButton npcButton5 = new ImageButton(drawable);
        ImageButton npcButton6 = new ImageButton(drawable);
        ImageButton npcButton7 = new ImageButton(drawable);
        ImageButton npcButton8 = new ImageButton(drawable);

        confirmBtn1.addListener(confirmListener);
        confirmBtn2.addListener(confirmListener);
        confirmBtn3.addListener(confirmListener);
        confirmBtn4.addListener(confirmListener);
        npcButton1.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.debug("Start button clicked");
                        //entity.getEvents().trigger("start");
                    }
                });

        Table table = new Table();

        table.add(npcButton1).height(NPC_CARD_HEIGHT).width(NPC_CARD_WIDTH).padTop(NPC_CARD_TOP_PADDING);
        table.add(npcButton2).height(NPC_CARD_HEIGHT).width(NPC_CARD_WIDTH);
        table.add(npcButton3).height(NPC_CARD_HEIGHT).width(NPC_CARD_WIDTH);
        table.add(npcButton4).height(NPC_CARD_HEIGHT).width(NPC_CARD_WIDTH);
        table.row();
        table.add(confirmBtn1).height(CONFIRM_BUTTON_HEIGHT).width(CONFIRM_BUTTON_WIDTH).padLeft(CONFIRM_BUTTON_LEFT_PADDING);
        table.add(confirmBtn2).height(CONFIRM_BUTTON_HEIGHT).width(CONFIRM_BUTTON_WIDTH).padLeft(CONFIRM_BUTTON_LEFT_PADDING);
        table.add(confirmBtn3).height(CONFIRM_BUTTON_HEIGHT).width(CONFIRM_BUTTON_WIDTH).padLeft(CONFIRM_BUTTON_LEFT_PADDING);
        table.add(confirmBtn4).height(CONFIRM_BUTTON_HEIGHT).width(CONFIRM_BUTTON_WIDTH).padLeft(CONFIRM_BUTTON_LEFT_PADDING);
        table.row();
        table.add(npcButton5).height(NPC_CARD_HEIGHT).width(NPC_CARD_WIDTH);
        table.add(npcButton6).height(NPC_CARD_HEIGHT).width(NPC_CARD_WIDTH);
        table.add(npcButton7).height(NPC_CARD_HEIGHT).width(NPC_CARD_WIDTH);
        table.add(npcButton8).height(NPC_CARD_HEIGHT).width(NPC_CARD_WIDTH);
        table.row();
        table.add(confirmBtn5).height(CONFIRM_BUTTON_HEIGHT).width(CONFIRM_BUTTON_WIDTH).padLeft(CONFIRM_BUTTON_LEFT_PADDING);
        table.add(confirmBtn6).height(CONFIRM_BUTTON_HEIGHT).width(CONFIRM_BUTTON_WIDTH).padLeft(CONFIRM_BUTTON_LEFT_PADDING);
        table.add(confirmBtn7).height(CONFIRM_BUTTON_HEIGHT).width(CONFIRM_BUTTON_WIDTH).padLeft(CONFIRM_BUTTON_LEFT_PADDING);
        table.add(confirmBtn8).height(CONFIRM_BUTTON_HEIGHT).width(CONFIRM_BUTTON_WIDTH).padLeft(CONFIRM_BUTTON_LEFT_PADDING);



       //table.debug();


        return table;
    }

    private void exitMenu() {
        game.setScreen(GdxGame.ScreenType.MAIN_GAME);
    }

    @Override
    protected void draw(SpriteBatch batch) {

    }
}


