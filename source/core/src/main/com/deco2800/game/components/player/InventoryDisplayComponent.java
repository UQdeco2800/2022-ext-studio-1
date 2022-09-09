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
import com.badlogic.gdx.utils.Array;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.EntityService;
import com.deco2800.game.rendering.TextureRenderComponent;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;


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
    private HashMap<Integer, Integer> inventoryHashMap;


    public InventoryDisplayComponent(HashMap<Integer, Integer> inventory) {
        super();
        inventoryHashMap = inventory;
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
        Drawable emptySlot = new TextureRegionDrawable(new TextureRegion(texture));

        // instantiating buttons
        Drawable drawSlot1 = emptySlot;
        Drawable drawSlot2 = emptySlot;
        Drawable drawSlot3 = emptySlot;
        Drawable drawSlot4 = emptySlot;
        Drawable drawSlot5 = emptySlot;
        Drawable drawSlot6 = emptySlot;
        Drawable drawSlot7 = emptySlot;
        Drawable drawSlot8 = emptySlot;
        Drawable drawSlot9 = emptySlot;
        Drawable drawSlot10 = emptySlot;

        // Get the entities existing in the game
        Array<Entity> entities = ServiceLocator.getEntityService().getEntities();

        // used to check each slot for its available item
        int slotIncrement = 1;
        // Implicitly going up. Should be done 1-10 for each inventory item
        for (int value : inventoryHashMap.values()) {
            for (Entity i: entities)
            {
                switch (slotIncrement) {
                    case 1:
                        if (i.getId() == value)
                        {
                            drawSlot1 = new TextureRegionDrawable((i.getComponent(TextureRenderComponent.class)).getTexture());
                        }
                        break;
                    case 2:
                        if (i.getId() == value)
                        {
                            drawSlot2 = new TextureRegionDrawable((i.getComponent(TextureRenderComponent.class)).getTexture());
                        }
                        break;
                    case 3:
                        if (i.getId() == value)
                        {
                            drawSlot3 = new TextureRegionDrawable((i.getComponent(TextureRenderComponent.class)).getTexture());
                        }
                        break;
                    case 4:
                        if (i.getId() == value)
                        {
                            drawSlot4 = new TextureRegionDrawable((i.getComponent(TextureRenderComponent.class)).getTexture());
                        }
                        break;
                    case 5:
                        if (i.getId() == value)
                        {
                            drawSlot5 = new TextureRegionDrawable((i.getComponent(TextureRenderComponent.class)).getTexture());
                        }
                        break;
                    case 6:
                        if (i.getId() == value)
                        {
                            drawSlot6 = new TextureRegionDrawable((i.getComponent(TextureRenderComponent.class)).getTexture());
                        }
                        break;
                    case 7:
                        if (i.getId() == value)
                        {
                            drawSlot7 = new TextureRegionDrawable((i.getComponent(TextureRenderComponent.class)).getTexture());
                        }
                        break;
                    case 8:
                        if (i.getId() == value)
                        {
                            drawSlot8 = new TextureRegionDrawable((i.getComponent(TextureRenderComponent.class)).getTexture());
                        }
                        break;
                    case 9:
                        if (i.getId() == value)
                        {
                            drawSlot9 = new TextureRegionDrawable((i.getComponent(TextureRenderComponent.class)).getTexture());
                        }
                        break;
                    case 10:
                        if (i.getId() == value)
                        {
                            drawSlot10 = new TextureRegionDrawable((i.getComponent(TextureRenderComponent.class)).getTexture());
                        }
                        break;
                    default:

                }
            }
            slotIncrement ++;
        }

        //Inventory slots
        ImageButton slot1 = new ImageButton(drawSlot1);
        ImageButton slot2 = new ImageButton(drawSlot2);
        ImageButton slot3 = new ImageButton(drawSlot3);
        ImageButton slot4 = new ImageButton(drawSlot4);
        ImageButton slot5 = new ImageButton(drawSlot5);
        ImageButton slot6 = new ImageButton(drawSlot6);
        ImageButton slot7 = new ImageButton(drawSlot7);
        ImageButton slot8 = new ImageButton(drawSlot8);
        ImageButton slot9 = new ImageButton(drawSlot9);
        ImageButton slot10 = new ImageButton(drawSlot10);

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
