package com.deco2800.game.components.npcEvictionMenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
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

    private void addActors() {
//        Image background_npc_menu =
//                new Image(
//                        ServiceLocator.getResourceService()
//                                .getAsset("images/eviction_menu/evictionMenu_background.png", Texture.class));
        Texture mytexture_npc = new Texture(Gdx.files.internal("images/eviction_menu/evictionMenu_background.png"));
        Drawable drawable_npc_bg = new TextureRegionDrawable(new TextureRegion(mytexture_npc));
        Image background_npc_menu = new Image(drawable_npc_bg);



        Label title = new Label("Npc Eviction Menu", skin, "title");
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

    private Table makeNpcCards() {

        TextButton confirmBtn1 = new TextButton("Confirm", skin);
        TextButton confirmBtn2 = new TextButton("Confirm", skin);
        TextButton confirmBtn3 = new TextButton("Confirm", skin);
        TextButton confirmBtn4 = new TextButton("Confirm", skin);
        Texture mytexture = new Texture(Gdx.files.internal("images/eviction_menu/evictionCard_single.png"));
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(mytexture));

        ImageButton npcButton = new ImageButton(drawable);
        ImageButton npcButton_two = new ImageButton(drawable);
        ImageButton npcButton_three = new ImageButton(drawable);
        ImageButton npcButton_four = new ImageButton(drawable);

        confirmBtn1.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.debug("confirm button clicked");
                       // confirmButtonCheck(); this function is used for check traitor
                    }
                });

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


