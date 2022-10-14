package com.deco2800.game.components.npcEvictionMenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.JsonWriter;
import com.deco2800.game.GdxGame;
import com.deco2800.game.areas.ForestGameArea;
import com.deco2800.game.components.CombatStatsComponent;

import com.deco2800.game.components.countDownClock.countdownDisplay;

import com.deco2800.game.components.endingmenu.EndingMenuDisplay;

import com.deco2800.game.components.npc.NPCClueLibrary;
import com.deco2800.game.entities.configs.PlayerProfileConfig;
import com.deco2800.game.entities.configs.PlayerProfileProperties;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.ui.UIComponent;
import com.deco2800.game.music.MusicStuff;
import org.slf4j.Logger;

import java.util.List;
import java.util.Objects;

import com.deco2800.game.components.player.PlayerProfileDisplay;
import com.deco2800.game.entities.configs.PlayerProfileProperties;
import com.badlogic.gdx.utils.Json;


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
public class NpcEvictionMenuDisplayNew extends UIComponent {
    private final Logger logger;

    private static final int NUMBER_OF_NPC = 8;
    private static float bgWidth;
    private static float bgHeight;

    private static final String IMAGES_PATH = "images/eviction_menu/";  //path of team7 images
    private static final String buttonPath = "sounds/button.mp3";

    //with these names you can call clues as well as calling image path of each npc.
    private static final String[] cardNames = {
            "Nereus", "Heph", "Metis", "Doris",
            "Zoe", "Ares", "Orpheus", "Nereus"
    };
    private final ResourceService resourceService;

    private final ForestGameArea gameArea;
    private final GdxGame game;

    private final Window stage;

    private static final Skin skin =
            new Skin(Gdx.files.internal("flat-earth/skin/flat-earth-ui.json"));
    private final NPCClueLibrary library = NPCClueLibrary.getInstance();

    private final NpcEvictionMenuDisplayHelper helper = new NpcEvictionMenuDisplayHelper();

    private Integer errorNum;
    private Boolean findKey;

    //create instance of playerProfileDisplay here
    PlayerProfileDisplay profileObject = new PlayerProfileDisplay();
    //Get the json object here
    private List<PlayerProfileProperties> profileInfo = profileObject.getPlayerProfile();

    //Create instance of PlayerProfileProperties
    PlayerProfileProperties profileProp = new PlayerProfileProperties();


    PlayerProfileConfig PPCInstance = new PlayerProfileConfig();






    /**
     * Implement NpcEvictionMenu in to a Window(), use creatEvictionMenu() to get this Window()
     * @param logger logger from Screen
     * @param resourceService resourceService where load/unload the textures
     * @param width  width of stage of the Screen
     * @param height height of stage of the Screen
     * @author Team7 Yingxin Liu
     */
    public NpcEvictionMenuDisplayNew(Logger logger, ResourceService resourceService,
                                     float width, float height, ForestGameArea gameArea, GdxGame game) {
        this.logger = logger;
        logger.debug("initialize NpcEvictionMenu Window");
        this.resourceService = resourceService;
        this.gameArea = gameArea;
        this.game = game;

        bgWidth = width;
        bgHeight = height;
        errorNum = 0;
        findKey = false;


        // creat the Npc eviction menu window with transparent background
        TextureRegionDrawable styleImage = new TextureRegionDrawable(
                resourceService.getAsset(IMAGES_PATH + "transparentBg.png", Texture.class));
        Window.WindowStyle windowStyle = new Window.WindowStyle(new BitmapFont(), Color.BLACK, styleImage);
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
        if (findKey) {handleWin();}
        return this.stage;
    }
    /**
     * return the errorNum
     * @return errorNum
     * @author Team7 WangShaohui
     */
    public Integer getErrorNum() {
        return errorNum;
    }

    /**
     * Add actors of eviction_menu to the stage
     * All functions below are Moved from:
     * @see NpcEvictionMenuDisplay
     *
     * @author Team7: Shaohui Wang Yingxin Liu
     */
    private void addActors() {
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
                        MusicStuff.playMusic("sounds/CloseEvictionMenu.wav", false);
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
                    MusicStuff.playMusic(buttonPath,false);
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
//                MusicStuff.playMusic(buttonPath, false);
                setButton(card, index, "cardHovering");
                cardImage.setDrawable(imageHover);
                setImage(cardImage, card, "imageHovering");
            }

            @Override  // default
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
//                MusicStuff.playMusic(buttonPath, false);
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
                    MusicStuff.playMusic("sounds/OpenClueWindow.wav",false);
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
     * player has 3 choices to find traitor, if all fail, the game will directly over <br/>
     * if correct, A key will be spawned on the map,
     * player can pick it up and use it in the warehouse. The game will win <br/>
     * For using the key, we have written the code in the warehouse component of <b>team 8</b>
     * @see com.deco2800.game.components.player.InventoryDisplayComponent
     * Button cancel: remove the dialog <br/>
     * Button Ok    : confirm the action from selected button <br/>
     * @see EndingMenuDisplay
     * @param button_name The name of the button that calls this function
     * @author Team7 Yingxin Liu <br/>
     * When lose the game ,it will go to other screen: EndingMenu
     * These code is provided from <b>team 3</b> <br/>
     * When correct, <b>team 5</b> provides code for spawn the key <br/>
     *
     */
    public void createConfirmDialog(String button_name) {
//        entity.getEvents().addListener("ending", this::onEnding);

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
                MusicStuff.playMusic(buttonPath, false);
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
                MusicStuff.playMusic(buttonPath, false);
                logger.debug("yes_button from " + button_name + " clicked");
                dialog.remove();
                // different name will lead to different result
                // traitor is Ares.So here we check it.
                NpcResultDialogType result = handleLogic(button_name);
                createResultDialog(button_name, result);

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
     * @author Code: Team7 Yingxin Liu <br/>
     * Put the label with context: Team 7 Shaohui Wang <br/>
     * Context: Team 9
     */
    private void createCardInfo(String card_name) {
        logger.debug("create card information dialog from name: " + card_name);
        // set the style of dialog include font color of title; background; size; position
        TextureRegionDrawable styleImage = new TextureRegionDrawable(
                resourceService.getAsset(IMAGES_PATH + "infoWindow.png", Texture.class));
        Window.WindowStyle windowStyle = new Window.WindowStyle(new BitmapFont(), Color.BLACK, styleImage);
        Window dialog = new Window("", windowStyle);
        dialog.setModal(true);    // The dialog is always at the front
        float dialog_size_x = (float) (bgWidth * (810.0 / 1600));
        float dialog_size_y = (float) (bgHeight * (653.33 / 900));
        dialog.setSize(dialog_size_x, dialog_size_y);
        dialog.setPosition((float) (bgWidth * (407.34 / 1600)), (float) (bgHeight * (1 - 800.33 / 900)));
        dialog.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                MusicStuff.playMusic("sounds/CloseClueWindow.wav",false);
                logger.debug(card_name + " clicked");
                dialog.remove();
                return true;
            }
        });

        //  add clues of npc
        Label message = new Label(helper.createClueContext(card_name, library),skin);
        message.setFontScale(dialog_size_y/800);
        message.setWrap(true);
        message.setAlignment(Align.left);
        Table table = new Table();
        table.add(message).width(dialog_size_x * 3 / 5);
        dialog.add(table);

        stage.addActor(dialog);
    }

    @Override
    protected void draw(SpriteBatch batch) {

    }

    /**
     * Types of result dialog box
     */
    protected enum NpcResultDialogType {
        RIGHT_BOX, WRONG_BOX1, WRONG_BOX2, LOSE, WIN
    }
    /**
     * Display a result dialog and traitor message on the stage, the style is based on Team7 prototype <br/>
     * All scales are calculated according to the prototype from team 7 only <br/>
     * Button Ok    : confirm the action from selected button <br/>
     *
     * @param button_name The name of the button that calls this function
     * @param type The type of dialog will be created
     * @author Team7 Yingxin Liu <br/>
     * Put the label with context: Team 7 Shaohui Wang
     */
    private void createResultDialog(String button_name, NpcResultDialogType type) {
        logger.debug("create Result dialog from name: " + button_name + " type:" + type);
        // if Lose, it will jump to ending screen, no need to create dialog
        if (type == NpcResultDialogType.LOSE) {
            EndingMenuDisplay.setLose();
            entity.getEvents().trigger("ending");
            return;}
        if (type == NpcResultDialogType.WIN) {handleWin();return;}

        // set the style of dialog include font color of title; background; size; position
        float dialog_size_x,dialog_size_y;
        String backgroundPath, buttonPathDefault, buttonPathHover;

        if (type == NpcResultDialogType.RIGHT_BOX) {
            MusicStuff.playMusic("sounds/RightPromptBox1.wav",false);
            backgroundPath = IMAGES_PATH + "rightBox.png";
            buttonPathDefault = IMAGES_PATH + "rightBtn.png";
            buttonPathHover = IMAGES_PATH + "rightBtn_H.png";
        } else if (type == NpcResultDialogType.WRONG_BOX1) {
            MusicStuff.playMusic("sounds/WrongPromptBox1.wav",false);
            backgroundPath = IMAGES_PATH + "wrongBox1.png";
            buttonPathDefault = IMAGES_PATH + "chanceBtn.png";
            buttonPathHover = IMAGES_PATH + "chanceBtn_H.png";
        } else {
            MusicStuff.playMusic("sounds/WrongPromptBox2.wav",false);
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
            Label message_interjection =new Label(helper.createTraitorMessageInterjection(type),skin);
            message_interjection.setFontScale(dialog_size_y/400);
            message_interjection.setAlignment(Align.center);
            Label message = new Label(helper.createTraitorMessageForSaveAtlantis(button_name,type), skin);
            message.setFontScale(dialog_size_y/400);
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
            Label message_interjection =new Label(helper.createTraitorMessageInterjection(type),skin);
            message_interjection.setFontScale(dialog_size_y/400);
            message_interjection.setAlignment(Align.center);
            Label message = new Label(helper.createTraitorMessageForSaveAtlantis(button_name,type), skin);
            message.setFontScale(dialog_size_y/400);
            message.setWrap(true);
            message.setAlignment(Align.left);
            Table table = new Table();
            table.add(message_interjection).prefWidth((float) (dialog_size_x * (400/678.67)));
            table.row();
            table.add(message).prefWidth((float) (dialog_size_x * (400/678.67)));
            table.padLeft((float) (dialog_size_x * (55/678.67)));
            dialog.add(table);

        }
        okButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                logger.info("yes_button from " + button_name + " clicked");
                 //when you select ok button
                MusicStuff.playMusic(buttonPath,false);
                dialog.remove();
                if (type == NpcResultDialogType.RIGHT_BOX)
                    handleWin();
            }
        });
        dialog.addActor(okButton);


        stage.addActor(dialog);
    }


    private void exitMenu() {
        stage.remove();
    }

    public void setFindKey(Boolean findKey) {
        this.findKey = findKey;
    }

    /**
     * When player select the correct traitor, after click OK button on right_box,
     * An win Information page will appear and the key will be spawn on the game area.
     * @author Team 7 Yingxin Liu <br/>
     * code of spawn key is from <b>Team 5</b>
     */
    private void handleWin() {
        logger.debug("Function handleWin is called");
        // set the style of dialog include font color of title; background; size; position
        TextureRegionDrawable styleImage = new TextureRegionDrawable(
                resourceService.getAsset(IMAGES_PATH + "saveMessage.png", Texture.class));
        Window.WindowStyle windowStyle = new Window.WindowStyle(new BitmapFont(), Color.BLACK, styleImage);
        Window dialog = new Window("", windowStyle);
        dialog.setModal(true);    // The dialog is always at the front
        dialog.setFillParent(true);
        dialog.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                logger.debug("win game page clicked");
                dialog.remove();
                exitMenu();
                // here we need call findKey function from team 5.
                if (!findKey){
                    gameArea.spawnKey(game);
                    findKey = true;
                }
                return true;
            }
        });
        showWinContext(dialog);
        stage.addActor(dialog);
    }

    /**
     * creat a table include the label with win context and title
     * and add it to given stage
     * @param dialog stage that will be added
     * @author Team 7 Yingxin Liu
     */
    private void showWinContext(Window dialog){
        // create frame for both title and context
        Table title =  new Table();
        title.setSize(stage.getWidth()*(1106-636)/1600,
                stage.getHeight()*(220-160)/900);
        title.setPosition((float) (stage.getWidth()*(636.67/1600)),
                (float) (stage.getHeight()*(1-220.0/900)));

        Table context =  new Table();
        context.setSize((float) (stage.getWidth()*(1150.67-696.67)/1600),
                stage.getHeight()*(750-290)/900);
        context.setPosition((float) (stage.getWidth()*(696.67/1600)),
                (float) (stage.getHeight()*(1-750.0/900)));

        // set title/context srting
        String titleContent;
        String contextContent;
        titleContent = "Message from Ares";
        if (findKey) {
            contextContent = """
                    
                    You have found the traitor and got the information to save Atlantis.
                      
                    Go and find the mysterious key!""";

        } else {
            contextContent = """
                    \s
                    I love my country and people very much, but I have to do it for the throne. However, I regretted it after Atlantis sank, but maybe you can save it:
                    \s\s
                    Find the mysterious key, it can help you save Atlantis and help you out of here!""";
        }


        // create and set the label with content
        Label titleLabel = new Label(titleContent, skin, "title",Color.NAVY);
        titleLabel.setAlignment(Align.center);
        titleLabel.setFontScale(stage.getWidth()/1920,stage.getHeight()/980);

        Label contextLabel = new Label(contextContent, skin, "font_large",Color.DARK_GRAY);
        contextLabel.setSize(context.getWidth(),context.getHeight());
        contextLabel.setAlignment(Align.top);
        contextLabel.setFontScale(stage.getWidth()/1920,stage.getHeight()/980);
        contextLabel.setWrap(true);

        // add to goal position
        title.add(titleLabel).center();
        context.addActor(contextLabel);
        dialog.addActor(title);
        dialog.addActor(context);
    }

    /**
     * Handle the game logic of select the traitor<br/>
     * player has 3 choices to find traitor<br/>
     * if wrong, it will decrease the blood and time at the same time
     *
     * @param name name of npc selected
     * @return
     * NpcResultDialogType.RIGHT_BOX: if player correctly select the traitor <br/>
     * NpcResultDialogType.WRONG_BOX1: if player fails for the first time<br/>
     * NpcResultDialogType.WRONG_BOX2: if player fails for the second time <br/>
     * NpcResultDialogType.LOSE: if player select 3 times and all fail <br/>
     * NpcResultDialogType.WIN: if player have correctly select in the past<br/>
     * null: This is not a return result that should occur. If it does,
     * it indicates that there is a problem with in the code
     * @author Team7 Yingxin Liu <br/>
     * decrease blood/decrease remaing time: Shaohui Wang
     *
     */
    protected NpcResultDialogType handleLogic (String name) {
        if (findKey) { // player have selected correct in the past
            return NpcResultDialogType.WIN;
        }
        if (Objects.equals(name, "Ares")){ // select npc correctly
            //Add code here:Write one row of data to Json (win game record)

            //Set the data of the object
            profileProp.timeRemaining=Math.round(entity.getComponent(countdownDisplay.class).getRemainingTime());
            profileProp.result =1;
            profileProp.attempt =errorNum+1;
            Json json = new Json();
            json.setOutputType(JsonWriter.OutputType.json);
          //  json.setElementType(PlayerProfileConfig.class,"playerStats", PlayerProfileProperties.class);
            String profileData = json.toJson(profileProp);

            //Get the file path, so that can write to json
            FileHandle file =Gdx.files.local("configs/playerStatsInfo.json");
            profileInfo.add(profileProp);

            PPCInstance.playerStats = profileInfo;
            //Make it as string, so it can write to json
            String profileData3 = json.toJson(PPCInstance);

            String profileData2 = json.toJson(profileInfo);

            //write to json, append is false, so it will overwrite the whole json by new data
            file.writeString(profileData3,false);
           // profileInfo.set(profileData);


            //Just some code for testing purpose 
            System.out.println(file);
            System.out.print(profileData);
            //file[playerStats].writeString(profileData,true);

            errorNum = 0;
            return NpcResultDialogType.RIGHT_BOX;
        } else {
            if (errorNum == 0){
                //decrease blood 10%
                int health = entity.getComponent(CombatStatsComponent.class).getHealth();
                entity.getComponent(CombatStatsComponent.class).setHealth((int) (health*0.9));

                //at the same time decrease remaing time 10%
                float remainingTime =entity.getComponent(countdownDisplay.class).getRemainingTime();
                entity.getComponent(countdownDisplay.class).setTimeRemaining((float) remainingTime*0.9f);

                errorNum++;
                return NpcResultDialogType.WRONG_BOX1;
            } else if (errorNum == 1) {
                //decrease blood 20%
                int health = entity.getComponent(CombatStatsComponent.class).getHealth();
                entity.getComponent(CombatStatsComponent.class).setHealth((int) (health*0.8));

                //at the same time decrease remaing time 20%
                float remainingTime =entity.getComponent(countdownDisplay.class).getRemainingTime();
                entity.getComponent(countdownDisplay.class).setTimeRemaining((float) remainingTime*0.8f);
                errorNum++;
                return NpcResultDialogType.WRONG_BOX2;
            } else if (errorNum == 2){
                //Insert a row of data into playerStatsInfo.json when the player lose the game
                //First set data to the instance
                profileProp.timeRemaining=Math.round(entity.getComponent(countdownDisplay.class).getRemainingTime());
                profileProp.result =2;
                profileProp.attempt =errorNum+1;
                Json json =new Json();
                json.setOutputType(JsonWriter.OutputType.json);
                String profileData = json.toJson(profileProp);

                FileHandle file =Gdx.files.local("configs/playerStatsInfo.json");
                profileInfo.add(profileProp);


                PPCInstance.playerStats = profileInfo;

                String profileData3 =json.toJson(PPCInstance);

                String profileData2 = json.toJson(profileInfo);

                file.writeString(profileData3,false );

                System.out.println(file);
                System.out.print(profileData);



                // game over
                errorNum = 0;
                return NpcResultDialogType.LOSE;
            }
        }
        return null; // if return null, it means some error in code, please check
    }
}