package com.deco2800.game.components.npcEvictionMenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;


import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.deco2800.game.GdxGame;
import com.deco2800.game.components.npc.NPCClueLibrary;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;


/**
 * A Window actor for displaying the npc eviction menu. <br/>
 * In order to match the game, this class is rebuilt passing Window() rather than UIComponent <br/>
 * How to use: <br/>
 * NpcEvictionMenuDisplayNew var = new NpcEvictionMenuDisplayNew(...);<br/>
 * stage.addActor(var.creatEvictionMenu()); <br/>
 * @see NpcEvictionMenuDisplay for the old version of code and more information
 * @see com.deco2800.game.screens.MainGameScreen all textures will load from here
 *
 * @author 2022-ext-studio-1-Team7, rebuilt by Yingxin Liu 15/09/2022
 */
public class NpcEvictionMenuDisplayNew {
    private Logger logger;

    private static final int NUMBER_OF_NPC = 8;
    private static float bgWidth;
    private static float bgHeight;

    private static final String IMAGES_PATH = "images/eviction_menu/";  //path of team7 images

    //with these names you can call clues as well as calling image path of each npc.
    private static final String[] cardNames = {
            "Nereus", "Heph", "Metis", "Doris",
            "Zoe", "Ares", "Orpheus", "Nereus"
    };
    private final ResourceService resourceService;

    private final Window stage;

    private static final Skin skin =
            new Skin(Gdx.files.internal("flat-earth/skin/flat-earth-ui.json"));
    private final NPCClueLibrary library = NPCClueLibrary.getInstance();
    private final NpcEvictionMenuDisplayHelper helper = new NpcEvictionMenuDisplayHelper();

    private Integer errorNum;

    /**
     * Implement NpcEvictionMenu in to a Window(), use creatEvictionMenu() to get this Window()
     * @param logger logger from Screen
     * @param resourceService resourceService where load/unload the textures
     * @param width  width of stage of the Screen
     * @param height height of stage of the Screen
     * @author Team7 Yingxin Liu
     */
    public NpcEvictionMenuDisplayNew(Logger logger, ResourceService resourceService, float width, float height) {
        this.logger = logger;
        logger.debug("initialize NpcEvictionMenu Window");
        this.resourceService = resourceService;
        bgWidth = width;
        bgHeight = height;
        errorNum = 0;

        // creat the Npc eviction menu window with transparent background
        TextureRegionDrawable styleImage = new TextureRegionDrawable(
                resourceService.getAsset(IMAGES_PATH + "transparentBg.png", Texture.class));
        Window.WindowStyle windowStyle = new Window.WindowStyle(new BitmapFont(), Color.BLUE, styleImage);
        this.stage = new Window("", windowStyle);
        this.stage.setModal(true);       // The window is always in front
        this.stage.setFillParent(true);  // Fill all space with the stage
        addActors();
    }

    /**
     * return the window of Eviction Menu, use stage.addActor() to add onto stage
     * @return the window of Eviction Menu
     * @author Team7 Yingxin Liu
     */
    public Window creatEvictionMenu (){
        return this.stage;
    }


    /**
     * Add actors of eviction_menu to the stage
     * All functions below are Moved from:
     * @see NpcEvictionMenuDisplay
     *
     * @author Team7: Shaohui Wang Yingxin Liu
     */
    private void addActors() {
        // Adding background
        Image backgroundNpcMenu =
                new Image(resourceService.getAsset(IMAGES_PATH + "evictionMenu_background.png", Texture.class));
        backgroundNpcMenu.setSize((float) (bgWidth * 0.89), (float) (bgHeight * 0.9851));
        backgroundNpcMenu.setPosition(bgWidth / 2, 0, Align.bottom);
        stage.addActor(backgroundNpcMenu);

        // Button for exit the select page, will go back to previous page
        Button exitBtn = createButton(IMAGES_PATH + "exitButton.png", IMAGES_PATH + "exitButton_selected.png");
        exitBtn.setSize((float) (bgWidth * ((1493.33 - 1436.67) / 1600)), (float) (bgHeight * (53.33 / 900)));
        exitBtn.setPosition((float) (bgWidth * 0.887), (float) (bgHeight * (1 - 153.33 / 900)));
        exitBtn.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.debug("Exit button clicked");
                        logger.info("exit to previous menu");
                        exitMenu();
                    }
                });
        stage.addActor(exitBtn);

        // Creating cards and corresponding selected buttons
        Button[] buttons = new Button[NUMBER_OF_NPC];
        Button[] cards = new Button[NUMBER_OF_NPC];
        for (int i = 0; i < NUMBER_OF_NPC; i++) {
            int index = i;
            setCard(cards, index);
            stage.addActor(cards[i]);

            buttons[i] = createButton(IMAGES_PATH + "selectButton_single.png", IMAGES_PATH + "selectButton_selected.png");
            setButton(buttons[i], i, "selectButton");
            buttons[i].addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent changeEvent, Actor actor) {
                    logger.debug("button" + index + "clicked");
                    createConfirmDialog(cardNames[index]);
                }
            });
            stage.addActor(buttons[i]);
        }
    }

    /**
     * set all attributes of the card <br/>
     * this is an internal function, do not use it !
     *
     * @param cards set of 8 cards
     * @param index the index of the card in the set that will be set <br/>
     *              it will also be used to calculate the position and find image in the card
     * @author Team7 Yingxin Liu
     */
    private void setCard(Button[] cards, int index) {
        Button card = createButton(IMAGES_PATH + "evictionCard_single.png",
                IMAGES_PATH + "evictionCard_hover.png");
        cards[index] = card;
        setButton(cards[index], index, "cardDefault");
        TextureRegionDrawable imageDefault = new TextureRegionDrawable(
                resourceService.getAsset(IMAGES_PATH + "npc" + cardNames[index] + ".png", Texture.class));
        TextureRegionDrawable imageHover = new TextureRegionDrawable(
                resourceService.getAsset(IMAGES_PATH + "npc" + cardNames[index] + "_hover.png", Texture.class));
        Image cardImage = new Image(imageDefault);
        setImage(cardImage, card, "imageDefault");
        card.addActor(cardImage);
        card.addListener(new InputListener() {
            @Override  // mouse hovering
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                setButton(card, index, "cardHovering");
                cardImage.setDrawable(imageHover);
                setImage(cardImage, card, "imageHovering");
            }

            @Override  // default
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                setButton(card, index, "cardDefault");
                cardImage.setDrawable(imageDefault);
                setImage(cardImage, card, "imageDefault");
            }

            @Override // click down: return true to unlock function: touchUp
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override // click up
            // Dialog will be created when 'click up' only in the area of card rather than everywhere.
            // Restored the characteristics of the basic button which implemented by using buttonStyle
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (card.isOver()) {
                    logger.debug("card" + index + "clicked up");
                    createCardInfo(cardNames[index]);
                }
            }
        });
    }

    /**
     * set the position and size of the given image in the card <br/>
     * this is an internal function, do not use it !
     *
     * @param image character image of the card
     * @param card  card which the image belongs to
     * @param type  state of image to be set [imageDefault|imageHovering]
     * @author Team7 Yingxin Liu
     */
    private void setImage(Image image, Button card, String type) {
        float width = card.getWidth();
        float height = card.getHeight();
        if (Objects.equals(type, "imageDefault")) {
            image.setSize((float) (bgWidth * (141.59 / 1600)), (float) (bgHeight * (215.92 / 900)));
            image.setPosition((float) (width * 65.36 / 207.26), (float) (height * (1 - 210.09 / 283.91)));
        } else if (Objects.equals(type, "imageHovering")) {
            image.setSize((float) (bgWidth * (168.57 / 1600)), (float) (bgHeight * (249.63 / 900)));
            image.setPosition((float) (width * 72.83 / 235.79), (float) (height * (1 - 240.99 / 323)));
        }
    }

    /**
     * set the position and size of the given button <br/>
     * this is an internal function, do not use it !
     *
     * @param button the button to be set
     * @param i      index of the set buttons
     * @param type   type of button to be set
     * @author Team7 Yingxin Liu
     */
    private void setButton(Button button, int i, String type) {
        if (Objects.equals(type, "cardDefault")) {
            button.setSize((float) (bgWidth * (207.26 / 1600)), (float) (bgHeight * (283.91 / 900)));
            button.setPosition((float) (bgWidth * ((454.13 + ((i % 4) * 230.32)) / 1600)),
                    (float) (bgHeight * (1 - (295.055 + (i / 4) * 334.09) / 900)), Align.center);
        } else if (Objects.equals(type, "cardHovering")) {
            button.setSize((float) (bgWidth * (235.79 / 1600)), (float) (bgHeight * (323.0 / 900)));
            button.setPosition((float) (bgWidth * ((452.135 + ((i % 4) * 230.32)) / 1600)),
                    (float) (bgHeight * (1 - (294.06 + (i / 4) * 334.09) / 900)), Align.center);
        } else if (Objects.equals(type, "selectButton")) {
            button.setSize((float) (bgWidth * (112.48 / 1600)), (float) (bgHeight * (47.5 / 900)));
            button.setPosition((float) (bgWidth * ((420.34 + ((i % 4) * 231.03)) / 1600)),
                    (float) (bgHeight * (1 - (473.36 + (i / 4) * 335.39) / 900)));
        }
    }

    /**
     * Display a confirm dialog on the stage, the style is based on Team7 prototype <br/>
     * All scales are calculated according to the prototype from team 7 only <br/>
     * Button cancel: remove the dialog <br/>
     * Button Ok    : confirm the action from selected button <br/>
     *
     * @param button_name The name of the button that calls this function
     * @author Team7 Yingxin Liu
     */
    private void createConfirmDialog(String button_name) {
        logger.debug("create confirm dialog from name: " + button_name);
        // set the style of dialog include font color of title; background; size; position
        TextureRegionDrawable styleImage = new TextureRegionDrawable(
                resourceService.getAsset(IMAGES_PATH + "confirmBox.png", Texture.class));

        Window.WindowStyle windowStyle = new Window.WindowStyle(new BitmapFont(), Color.BLACK, styleImage);
        Window dialog = new Window("", windowStyle);
        dialog.setModal(true);    // The dialog is always at the front
        float dialog_size_x = (float) (bgWidth * 0.2537);
        float dialog_size_y = (float) (bgHeight * 0.3037);
        dialog.setSize(dialog_size_x, dialog_size_y);
        float dialog_pos_x = (float) (bgWidth * 0.3756);
        float dialog_pos_y = (float) (bgHeight * (1 - 0.65));
        dialog.setPosition(dialog_pos_x, dialog_pos_y);

        // Set Cancel and Ok buttons for dialog
        Button cancelButton = createButton(IMAGES_PATH + "confirmBtn_cancel.png", IMAGES_PATH + "confirmBtn_cancel1.png");
        cancelButton.setSize((float) (dialog_size_x * 0.361), (float) (dialog_size_y * 0.2317));
        cancelButton.setPosition((float) (dialog.getWidth() * 0.1067), 0);
        cancelButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                logger.debug("cancel_button from " + button_name + " clicked");
                dialog.remove();
            }
        });

        Button okButton = createButton(IMAGES_PATH + "confirmBtn_ok.png", IMAGES_PATH + "confirmBtn_ok1.png");
        okButton.setSize((float) (dialog_size_x * 0.377), (float) (dialog_size_y * 0.2317));
        okButton.setPosition((float) (dialog.getWidth() * 0.5239), 0);
        okButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                logger.debug("yes_button from " + button_name + " clicked");
                dialog.remove();
                // different name will lead to different result
                if (Objects.equals(button_name, cardNames[0])){ // select npc correctly
                    createResultDialog(button_name,NpcResultDialogType.RIGHT_BOX);
                } else {
                    if (errorNum == 0){
                        errorNum++;
                        createResultDialog(button_name,NpcResultDialogType.WRONG_BOX1);
                    } else if (errorNum == 1) {
                        errorNum++;
                        createResultDialog(button_name,NpcResultDialogType.WRONG_BOX2);
                    } else {
                        errorNum = 0;
                        // game over
                    }

                }
            }
        });
        dialog.addActor(cancelButton);
        dialog.addActor(okButton);
        stage.addActor(dialog);
    }

    /**
     * create a button where include an image and can change state when the mouse hovering
     *
     * @param Up   path of image: The style of the button in its normal state
     * @param Over path of image: The style of the button when mouse hovering
     * @return a Button with up and down style
     * @author Team7: Yingxin Liu  Shaohui Wang
     */
    private Button createButton(String Up, String Over) {
        logger.debug("createButton with path:" + Up + Over);
        TextureRegionDrawable up = new TextureRegionDrawable(resourceService.getAsset(Up, Texture.class));
        TextureRegionDrawable over = new TextureRegionDrawable(resourceService.getAsset(Over, Texture.class));
        Button.ButtonStyle style = new Button.ButtonStyle();
        style.up = up;
        style.over = over;
        return new Button(style);
    }


    /**
     * Display a Card information dialog on the stage, the style is based on Team7 prototype <br/>
     * All scales are calculated according to the prototype from team 7 only <br/>
     * The context of this dialog will be provided by Team 9
     *
     * @param card_name The name of the card which calls this function
     * @author Code: Team7 Yingxin Liu Shaohui Wang   <br/>Context: Team 9
     */
    private void createCardInfo(String card_name) {
        logger.debug("create card information dialog from name: " + card_name);
        // set the style of dialog include font color of title; background; size; position
        TextureRegionDrawable styleImage = new TextureRegionDrawable(
                resourceService.getAsset(IMAGES_PATH + "infoWindow.png", Texture.class));
        Window.WindowStyle windowStyle = new Window.WindowStyle(new BitmapFont(), Color.BLUE, styleImage);
        Window dialog = new Window("", windowStyle);
        dialog.setModal(true);    // The dialog is always at the front
        float dialog_size_x = (float) (bgWidth * (810.0 / 1600));
        float dialog_size_y = (float) (bgHeight * (653.33 / 900));
        dialog.setSize(dialog_size_x, dialog_size_y);
        dialog.setPosition((float) (bgWidth * (407.34 / 1600)), (float) (bgHeight * (1 - 800.33 / 900)));
        dialog.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                logger.debug(card_name + " clicked");
                dialog.remove();
                return true;
            }
        });

        //  add clues of npc
        Label message = new Label(helper.createLabelContext(card_name, library), skin, "large");
        message.setWrap(true);
        message.setAlignment(Align.left);
        Table table = new Table();
        table.add(message).width(dialog_size_x * 3 / 5);
        dialog.add(table);

        stage.addActor(dialog);
    }

    /**
     *
     */
    protected enum NpcResultDialogType {
        RIGHT_BOX, WRONG_BOX1, WRONG_BOX2
    }
    /**
     * Display a result dialog on the stage, the style is based on Team7 prototype <br/>
     * All scales are calculated according to the prototype from team 7 only <br/>
     * Button Ok    : confirm the action from selected button <br/>
     *
     * @param button_name The name of the button that calls this function
     * @param type The type of dialog will be created
     * @author Team7 Yingxin Liu
     */
    private void createResultDialog(String button_name, NpcResultDialogType type) {
        float dialog_size_x,dialog_size_y;
        logger.debug("create Result dialog from name: " + button_name);
        // set the style of dialog include font color of title; background; size; position
        String backgroundPath, buttonPathDefault, buttonPathHover;

        if (type == NpcResultDialogType.RIGHT_BOX) {
            backgroundPath = IMAGES_PATH + "rightBox.png";
            buttonPathDefault = IMAGES_PATH + "rightBtn.png";
            buttonPathHover = IMAGES_PATH + "rightBtn_H.png";
        } else if (type == NpcResultDialogType.WRONG_BOX1) {
            backgroundPath = IMAGES_PATH + "wrongBox1.png";
            buttonPathDefault = IMAGES_PATH + "chanceBtn.png";
            buttonPathHover = IMAGES_PATH + "chanceBtn_H.png";
        } else {
            backgroundPath = IMAGES_PATH + "wrongBox2.png";
            buttonPathDefault = IMAGES_PATH + "chanceBtn2.png";
            buttonPathHover = IMAGES_PATH + "chanceBtn2_H.png";
        }
        TextureRegionDrawable styleImage = new TextureRegionDrawable(
                resourceService.getAsset(backgroundPath, Texture.class));

        Window.WindowStyle windowStyle = new Window.WindowStyle(new BitmapFont(), Color.BLACK, styleImage);
        Window dialog = new Window("", windowStyle);
        dialog.setModal(true);    // The dialog is always at the front
        Button okButton = createButton(buttonPathDefault, buttonPathHover);

        if (type == NpcResultDialogType.RIGHT_BOX) {
            dialog_size_x = (float) (bgWidth * (683.67 / 1600));
            dialog_size_y = (float) (bgHeight * (416.24 / 900));
            dialog.setSize(dialog_size_x, dialog_size_y);
            dialog.setPosition((float) (bgWidth * (433.33 / 1600)), (float) (bgHeight * (1 - 663.33 / 900)));

            okButton.setSize((float) (dialog_size_x * (116.67/683.67)), (float) (dialog_size_y * (53.33/416.24)));
            okButton.setPosition((float) (dialog.getWidth() * ((764.04-433.33)/683.67)),
                    (float) (dialog.getHeight() * ((663.33 - 635.65) / 416.24)));
            Label message_interjection =new Label(helper.createTraitorMessageInterjection(type),skin,"large");
            message_interjection.setAlignment(Align.center);
            Label message = new Label(helper.createTraitorMessageForSaveAtlantis(button_name,type), skin, "large");
            message.setWrap(true);
            message.setAlignment(Align.left);
            Table table = new Table();
            table.add(message_interjection).width(dialog_size_x * 3 / 5);
            table.row();
            table.add(message).width(dialog_size_x * 3 / 5);
            table.padLeft(dialog_size_x/6).padTop(dialog_size_y/6);
            dialog.add(table);
        } else {
            dialog_size_x = (float) (bgWidth * (678.67 / 1600));
            dialog_size_y = (float) (bgHeight * (382.38 / 900));
            dialog.setSize(dialog_size_x, dialog_size_y);
            dialog.setPosition((float) (bgWidth * (438.33 / 1600)), (float) (bgHeight * (1 - 663.33 / 900)));

            okButton.setSize((float) (dialog_size_x * (116.67/678.67)), (float) (dialog_size_y * (53.33/382.38)));
            okButton.setPosition((float) (dialog.getWidth() * ((748.04-438.33)/678.67)),
                    (float) (dialog.getHeight() * ((663.33 - 635.65) / 382.38)));
            Label message_interjection =new Label(helper.createTraitorMessageInterjection(type),skin,"large");
            message_interjection.setAlignment(Align.center);
            Label message = new Label(helper.createTraitorMessageForSaveAtlantis(button_name,type), skin, "large");
            message.setWrap(true);
            message.setAlignment(Align.left);
            Table table = new Table();
            table.add(message_interjection).width((float) (dialog_size_x * (400/678.67)));
            table.row();
            table.add(message).width((float) (dialog_size_x * (400/678.67)));
            table.setHeight((float) (dialog_size_y * (295/382.38)));
            table.padLeft((float) (dialog_size_x * (55/678.67)));
            table.debug();
            dialog.add(table);

        }
        okButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                logger.debug("yes_button from " + button_name + " clicked");
                //when you select ok button
                dialog.remove();
            }
        });
        dialog.addActor(okButton);


        stage.addActor(dialog);
    }

    private void exitMenu() {
        stage.remove();
    }


}