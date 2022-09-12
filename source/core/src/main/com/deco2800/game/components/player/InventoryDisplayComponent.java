package com.deco2800.game.components.player;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Null;
import com.deco2800.game.components.countDownClock.countdownDisplay;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.EntityService;
import com.deco2800.game.entities.factories.ClueItemFactory;
import com.deco2800.game.entities.factories.ConsumableItemFactory;
import com.deco2800.game.rendering.TextureRenderComponent;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;

import java.util.HashMap;
import java.util.Map;


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

    private Table descriptionTable;
    private Label title;
    private Label description;
    private HashMap<Integer, Integer> inventoryHashMap;

    //TODO - Stand in for integrated guilt values for seperate npcs
    public int guiltLevel;


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
                                .getAsset("images/blank.png", Texture.class));


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

    /**
     * The main logic used to control the inventory and dynamically pull the hashmap and interact with other entities you pick up
     */
    private Table createInventory() {

        //empty slot
        Texture texture = new Texture(Gdx.files.internal("images/inventory/emptyInventorySlot.png"));
        Drawable emptySlot = new TextureRegionDrawable(new TextureRegion(texture));

        //Confirm down <a href="https://www.flaticon.com/free-icons/pixelated" title="pixelated icons">Pixelated icons created by Freepik - Flaticon</a>
        Texture confirmTexture = new Texture(Gdx.files.internal("images/inventory/confirm.png"));
        Drawable confirmDown = new TextureRegionDrawable(new TextureRegion(confirmTexture));

        // instantiating buttons
        ImageButton drawSlot1 = new ImageButton(emptySlot);
        ImageButton drawSlot2 = new ImageButton(emptySlot);
        ImageButton drawSlot3 = new ImageButton(emptySlot);
        ImageButton drawSlot4 = new ImageButton(emptySlot);
        ImageButton drawSlot5 = new ImageButton(emptySlot);
        ImageButton drawSlot6 = new ImageButton(emptySlot);
        ImageButton drawSlot7 = new ImageButton(emptySlot);
        ImageButton drawSlot8 = new ImageButton(emptySlot);
        ImageButton drawSlot9 = new ImageButton(emptySlot);
        ImageButton drawSlot10 = new ImageButton(emptySlot);

        // Get the entities existing in the game
        Array<Entity> entities = ServiceLocator.getEntityService().getEntities();

        // used to check each slot for its available item
        int slotIncrement = 1;

        // Implicitly going up. Should be done 1-10 for each inventory item
        for (Map.Entry<Integer, Integer> entry  : inventoryHashMap.entrySet()) {
            Integer key = entry.getKey();
            Integer value = entry.getValue();

            for (Entity i: entities)
            {
                switch (slotIncrement) {
                    case 1:
                        // Check if the hashmap value is equal to the entity registered in the entity service to get the appropriate functionality
                        if (i.getId() == value)
                        {
                            Drawable buttonGraphic = new TextureRegionDrawable((i.getComponent(TextureRenderComponent.class)).getTexture());
                            drawSlot1 = new ImageButton(buttonGraphic, confirmDown);

                            //TODO - Apply new hover and onclick logic to all slots
                            //Hover popup for item description and Item slot click event
                            drawSlot1.addListener(new InputListener() {
                                //Shows item description when mouse hovers item slot
                                @Override
                                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                                    showItemDescription(i);
                                }
                                //Removes item description when not hovering
                                @Override
                                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                                    destroyItemDescription();
                                }
                                //Uses the item once an item slot is clicked
                                @Override
                                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                                    useItem(i, key);
                                    destroyItemDescription();
                                    return true;
                                }

                            });

                        }
                        break;
                    case 2:
                        if (i.getId() == value)
                        {
                            Drawable buttonGraphic = new TextureRegionDrawable((i.getComponent(TextureRenderComponent.class)).getTexture());
                            drawSlot2 = new ImageButton(buttonGraphic, confirmDown);

                            drawSlot2.addListener(new ClickListener() {
                                @Override
                                public void clicked(InputEvent event, float x, float y) {
                                    useItem(i, key);
                                }
                            });

                            //Hover popup for item description
                            drawSlot2.addListener(new InputListener() {
                                //Shows item description when mouse hovers item slot
                                @Override
                                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                                    showItemDescription(i);
                                }
                                //Removes item description when not hovering
                                @Override
                                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                                    destroyItemDescription();
                                }

                            });
                        }
                        break;
                    case 3:
                        if (i.getId() == value)
                        {
                            Drawable buttonGraphic = new TextureRegionDrawable((i.getComponent(TextureRenderComponent.class)).getTexture());
                            drawSlot3 = new ImageButton(buttonGraphic, confirmDown);

                            drawSlot3.addListener(new ClickListener() {
                                @Override
                                public void clicked(InputEvent event, float x, float y) {
                                    useItem(i, key);
                                }
                            });

                            //Hover popup for item description
                            drawSlot3.addListener(new InputListener() {
                                //Shows item description when mouse hovers item slot
                                @Override
                                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                                    showItemDescription(i);
                                }
                                //Removes item description when not hovering
                                @Override
                                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                                    destroyItemDescription();
                                }

                            });
                        }
                        break;
                    case 4:
                        if (i.getId() == value)
                        {
                            Drawable buttonGraphic = new TextureRegionDrawable((i.getComponent(TextureRenderComponent.class)).getTexture());
                            drawSlot4 = new ImageButton(buttonGraphic, confirmDown);

                            drawSlot4.addListener(new ClickListener() {
                                @Override
                                public void clicked(InputEvent event, float x, float y) {
                                    useItem(i, key);
                                }
                            });

                            //Hover popup for item description
                            drawSlot4.addListener(new InputListener() {
                                //Shows item description when mouse hovers item slot
                                @Override
                                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                                    showItemDescription(i);
                                }
                                //Removes item description when not hovering
                                @Override
                                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                                    destroyItemDescription();
                                }

                            });
                        }
                        break;
                    case 5:
                        if (i.getId() == value)
                        {
                            Drawable buttonGraphic = new TextureRegionDrawable((i.getComponent(TextureRenderComponent.class)).getTexture());
                            drawSlot5 = new ImageButton(buttonGraphic, confirmDown);

                            drawSlot5.addListener(new ClickListener() {
                                @Override
                                public void clicked(InputEvent event, float x, float y) {
                                    useItem(i, key);
                                }
                            });

                            //Hover popup for item description
                            drawSlot5.addListener(new InputListener() {
                                //Shows item description when mouse hovers item slot
                                @Override
                                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                                    showItemDescription(i);
                                }
                                //Removes item description when not hovering
                                @Override
                                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                                    destroyItemDescription();
                                }

                            });
                        }
                        break;
                    case 6:
                        if (i.getId() == value)
                        {
                            Drawable buttonGraphic = new TextureRegionDrawable((i.getComponent(TextureRenderComponent.class)).getTexture());
                            drawSlot6 = new ImageButton(buttonGraphic, confirmDown);

                            drawSlot6.addListener(new ClickListener() {
                                @Override
                                public void clicked(InputEvent event, float x, float y) {
                                    useItem(i, key);
                                }
                            });

                            //Hover popup for item description
                            drawSlot6.addListener(new InputListener() {
                                //Shows item description when mouse hovers item slot
                                @Override
                                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                                    showItemDescription(i);
                                }
                                //Removes item description when not hovering
                                @Override
                                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                                    destroyItemDescription();
                                }

                            });
                        }
                        break;
                    case 7:
                        if (i.getId() == value)
                        {
                            Drawable buttonGraphic = new TextureRegionDrawable((i.getComponent(TextureRenderComponent.class)).getTexture());
                            drawSlot7 = new ImageButton(buttonGraphic, confirmDown);

                            drawSlot7.addListener(new ClickListener() {
                                @Override
                                public void clicked(InputEvent event, float x, float y) {
                                    useItem(i, key);
                                }
                            });

                            //Hover popup for item description
                            drawSlot7.addListener(new InputListener() {
                                //Shows item description when mouse hovers item slot
                                @Override
                                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                                    showItemDescription(i);
                                }
                                //Removes item description when not hovering
                                @Override
                                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                                    destroyItemDescription();
                                }

                            });
                        }
                        break;
                    case 8:
                        if (i.getId() == value)
                        {
                            Drawable buttonGraphic = new TextureRegionDrawable((i.getComponent(TextureRenderComponent.class)).getTexture());
                            drawSlot8 = new ImageButton(buttonGraphic, confirmDown);

                            drawSlot8.addListener(new ClickListener() {
                                @Override
                                public void clicked(InputEvent event, float x, float y) {
                                    useItem(i, key);
                                }
                            });

                            //Hover popup for item description
                            drawSlot8.addListener(new InputListener() {
                                //Shows item description when mouse hovers item slot
                                @Override
                                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                                    showItemDescription(i);
                                }
                                //Removes item description when not hovering
                                @Override
                                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                                    destroyItemDescription();
                                }

                            });
                        }
                        break;
                    case 9:
                        if (i.getId() == value)
                        {
                            Drawable buttonGraphic = new TextureRegionDrawable((i.getComponent(TextureRenderComponent.class)).getTexture());
                            drawSlot9 = new ImageButton(buttonGraphic, confirmDown);

                            drawSlot9.addListener(new ClickListener() {
                                @Override
                                public void clicked(InputEvent event, float x, float y) {
                                    useItem(i, key);
                                }
                            });

                            //Hover popup for item description
                            drawSlot9.addListener(new InputListener() {
                                //Shows item description when mouse hovers item slot
                                @Override
                                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                                    showItemDescription(i);
                                }
                                //Removes item description when not hovering
                                @Override
                                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                                    destroyItemDescription();
                                }

                            });
                        }
                        break;
                    case 10:
                        if (i.getId() == value)
                        {
                            Drawable buttonGraphic = new TextureRegionDrawable((i.getComponent(TextureRenderComponent.class)).getTexture());
                            drawSlot10 = new ImageButton(buttonGraphic, confirmDown);
                            drawSlot10.addListener(new ClickListener() {
                                @Override
                                public void clicked(InputEvent event, float x, float y) {
                                    useItem(i, key);

                                }
                            });

                            //Hover popup for item description
                            drawSlot10.addListener(new InputListener() {
                                //Shows item description when mouse hovers item slot
                                @Override
                                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                                    showItemDescription(i);
                                }
                                //Removes item description when not hovering
                                @Override
                                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                                    destroyItemDescription();
                                }

                            });
                        }
                        break;
                    default:

                }
            }
            slotIncrement ++;
        }

        //Inventory slots
        ImageButton slot1 = drawSlot1;
        ImageButton slot2 = drawSlot2;
        ImageButton slot3 = drawSlot3;
        ImageButton slot4 = drawSlot4;
        ImageButton slot5 = drawSlot5;
        ImageButton slot6 = drawSlot6;
        ImageButton slot7 = drawSlot7;
        ImageButton slot8 = drawSlot8;
        ImageButton slot9 = drawSlot9;
        ImageButton slot10 = drawSlot10;


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


    public void useItem(Entity i, Integer key) {
        //If it's a time item
        if (i.getComponent(ConsumeableItemComponent.class) != null)
        {
            consumeTimeItem(i, key);
        }
        //If it's a clue item
        if (i.getComponent(ClueItemComponent.class) != null){
            consumeClueItem(i, key);
        }
    }


    @Override
    public void draw(SpriteBatch batch) {
    }

    public void consumeTimeItem(Entity i, int key){
        /// Funky locator magic to identify the entity that contains the time display
        Array<Entity> entityLocator = ServiceLocator.getEntityService().getEntities();
        for (Entity timeDisplay: entityLocator)
        {
            if (timeDisplay.getComponent(countdownDisplay.class) != null)
            {
                float increaseValue = i.getComponent(ConsumeableItemComponent.class).increaseTime();
                (timeDisplay.getComponent(countdownDisplay.class)).increaseTime(increaseValue);

//                // find player to remove item after consumption
//                for (Entity player: entityLocator) {
//                    if (player.getComponent(InventoryComponent.class) != null) {
//                        player.getComponent(InventoryComponent.class).removeItemFromInventory(key);
//                        //Remove after consumption and close inventory
//                        destroyInventory();
//                    }
//                }

                inventoryHashMap.remove(key);
                destroyInventory();

            }

        }
    }

    private String getItemDescriptionText(Entity i) {

        //If it's a time item
        if (i.getComponent(ConsumeableItemComponent.class) != null)
        {
            return "Time Item - This item can be used to increase the countdown clock!";
        }
        //If it's a clue item
        if (i.getComponent(ClueItemComponent.class) != null){
            return "Mermaid Scale - This item can be used to increase the guilt rating of npcs!";
        }

        return "Emtpy Inventory Slot!"; //TODO - not setup to listen for empty slots

    }


    //Shows the items specific description and buffs
    //TODO - Currently stays on screen when item used as no hover off event happens
    private Table showItemDescription(Entity i) {
        descriptionTable = new Table();
        descriptionTable.setFillParent(true);

        description = new Label(getItemDescriptionText(i), skin);
        description.setFontScale(1f);
        description.setColor(Color.WHITE); //TODO color setting doesn't work
        descriptionTable.add(description).pad(5);


        stage.addActor(descriptionTable);

        return descriptionTable;

    }

    private void destroyItemDescription() {
        super.dispose();
        descriptionTable.remove();
    }



    public void consumeClueItem(Entity i, int key) {

        //Increase the guilt rating of an NPC
        float increaseGuilt = i.getComponent(ClueItemComponent.class).increaseGuilt();
        guiltLevel += increaseGuilt;

        inventoryHashMap.remove(key);
        destroyInventory();

    }

    public void destroyInventory() {
        super.dispose();
        backgroundTable.remove();
        rootTable.remove();

    }


}
