package com.deco2800.game.components.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class InventoryDisplayComponent extends UIComponent {
    private static final int topSlotPadding = 70;
    private static final int bottomSlotPadding = 30;
    private static final int backgroundHeightSpace = 350 ;
    private static final int backgroundWidthSpace = 500;
    private static final Logger logger = LoggerFactory.getLogger(InventoryDisplayComponent.class);
    private static final int slotHeight = 250;
    private static final  int slotWidth = 250;
    private Table rootTable;
    private Table backgroundTable;
    private Label title;
    private boolean slot1IsEmpty = true;
    private Drawable drawSlot1;


    public InventoryDisplayComponent() {
        super();
        create();

    }

    @Override
    public void create(){
        super.create();
        addActors();

    }

    private void addActors() {


        Image inventoryBG =
                new Image(
                        ServiceLocator.getResourceService()
                                .getAsset("images/blank.png", Texture.class)); //Placeholder graphic


        Table inventoryMenu = createInventory();
        TextButton exitBtn = new TextButton("Exit", skin);
        exitBtn.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.debug("Closing inventory");
                        destroyInventory();
                    }
                });

        backgroundTable = new Table();
        backgroundTable.setFillParent(true);
        backgroundTable.add(inventoryBG).height(Gdx.graphics.getHeight()-backgroundHeightSpace)
                .width(Gdx.graphics.getWidth()-backgroundWidthSpace);

        rootTable = new Table();
        rootTable.setFillParent(true);
        title = new Label("Inventory", skin);
        title.setFontScale(2f);
        rootTable.add(title).pad(5);
        rootTable.row();

        rootTable.add(inventoryMenu).center();
        rootTable.row();
        rootTable.add(exitBtn);

        stage.addActor(backgroundTable);
        stage.addActor(rootTable);

    }


    private Table createInventory() {

        //empty slot
        Texture texture = new Texture(Gdx.files.internal("images/hex_grass_1.png")); //Placeholder graphic
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(texture));

        if (slot1IsEmpty == false) {
            Texture slot1Texture = new Texture(Gdx.files.internal("images/heart.png"));//This will path to the entities image as a variable and not a set path
            drawSlot1 = new TextureRegionDrawable(new TextureRegion(slot1Texture));
        } else {
            drawSlot1 = new TextureRegionDrawable(new TextureRegion(texture));
        }

        //Inventory slots
        ImageButton slot1 = new ImageButton(drawSlot1);
        ImageButton slot2 = new ImageButton(drawable);
        ImageButton slot3 = new ImageButton(drawable);
        ImageButton slot4 = new ImageButton(drawable);
        ImageButton slot5 = new ImageButton(drawable);
        ImageButton slot6 = new ImageButton(drawable);
        ImageButton slot7 = new ImageButton(drawable);
        ImageButton slot8 = new ImageButton(drawable);
        ImageButton slot9 = new ImageButton(drawable);
        ImageButton slot10 = new ImageButton(drawable);

        slot1.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        useItem();
                    }
                });



        Table table = new Table();

        table.add(slot1).height(slotHeight).width(slotWidth).padTop(topSlotPadding);
        table.add(slot2).height(slotHeight).width(slotWidth).padTop(topSlotPadding);
        table.add(slot3).height(slotHeight).width(slotWidth).padTop(topSlotPadding);
        table.add(slot4).height(slotHeight).width(slotWidth).padTop(topSlotPadding);
        table.add(slot5).height(slotHeight).width(slotWidth).padTop(topSlotPadding);

        table.row();

        table.add(slot6).height(slotHeight).width(slotWidth).padTop(bottomSlotPadding);
        table.add(slot7).height(slotHeight).width(slotWidth).padTop(bottomSlotPadding);
        table.add(slot8).height(slotHeight).width(slotWidth).padTop(bottomSlotPadding);
        table.add(slot9).height(slotHeight).width(slotWidth).padTop(bottomSlotPadding);
        table.add(slot10).height(slotHeight).width(slotWidth).padTop(bottomSlotPadding);
        table.row();

        return table;
    }


    public void useItem() {
    }


    @Override
    public void draw(SpriteBatch batch) {
    }

    public void destroyInventory() {
        super.dispose();
        backgroundTable.remove();
        rootTable.remove();

    }


}
