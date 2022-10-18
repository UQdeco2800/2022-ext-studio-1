package com.deco2800.game.components.npcEvictionMenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.particles.ParallelArray;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.*;
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
import com.badlogic.gdx.utils.Json;


/**
 * A Window actor for displaying the npc eviction menu. <br/>
 * In order to match the game, this class is rebuilt passing Window() rather than UIComponent <br/>
 * How to use: <br/>
 * NpcEvictionMenuDisplayNew var = new NpcEvictionMenuDisplayNew(...);<br/>
 * window.addActor(var.creatEvictionMenu()); <br/>
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
            "Zoe", "Ares", "Orpheus", "Zeus"
    };
    private final ResourceService resourceService;

    private final ForestGameArea gameArea;
    private final GdxGame game;

    private final Window window;

    private static final Skin skin =
            new Skin(Gdx.files.internal("flat-earth/skin/flat-earth-ui.json"));
    private final NPCClueLibrary library = NPCClueLibrary.getInstance();

    private final NpcEvictionMenuDisplayHelper helper = new NpcEvictionMenuDisplayHelper();
    private final ParticleActor particleWrongActor1 = new ParticleActor("data/wrongone.p");
    private final ParticleActor particleWrongActor2 = new ParticleActor("data/wrongone.p");
    private final ParticleActor particleSecondWrongActor1 = new ParticleActor("data/wrongtwo.p");
    private final ParticleActor particleSecondWrongActor2 = new ParticleActor("data/wrongtwo.p");
    private final ParticleActor particleRightActor = new ParticleActor("data/rightone.p");
    private final ParticleActor particleRightActorTwo = new ParticleActor("data/righttwo.p");

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
     * @param width  width of window of the Screen
     * @param height height of window of the Screen
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
        this.window = new Window("", windowStyle);
        this.window.setModal(true);       // The window is always in front
        this.window.setFillParent(true);  // Fill all space with the window
        addActors();
    }

    // GAME LOGIC, SETTER/GETTER
    /**
     * return the errorNum
     * @return errorNum
     * @author Team7 WangShaohui
     */
    public Integer getErrorNum() {
        return errorNum;
    }

    /**
     * Setting from other class, true if the player has found the key
     * @param findKey whether player has found the key in this game round
     */
    public void setFindKey(Boolean findKey) {
        this.findKey = findKey;
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
     * decrease blood/decrease remaing time: Shaohui Wang <br/>
     * add json to record game data: Team 4
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
            profileProp.result = 1;
            profileProp.attempt = errorNum + 1;
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
                entity.getComponent(countdownDisplay.class).setTimeRemaining(remainingTime *0.9f);

                errorNum++;
                return NpcResultDialogType.WRONG_BOX1;
            } else if (errorNum == 1) {
                //decrease blood 20%
                int health = entity.getComponent(CombatStatsComponent.class).getHealth();
                entity.getComponent(CombatStatsComponent.class).setHealth((int) (health*0.8));

                //at the same time decrease remaing time 20%
                float remainingTime =entity.getComponent(countdownDisplay.class).getRemainingTime();
                entity.getComponent(countdownDisplay.class).setTimeRemaining(remainingTime *0.8f);
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


    // Basic functions
    /**
     * return the window of Eviction Menu, use window.addActor() to add onto window
     * @return the window of Eviction Menu
     * @author Team7 Yingxin Liu
     */
    public Window creatEvictionMenu (){
        if (findKey) {
            creatWinDialog();}
        return this.window;
    }

    /**
     * Add actors of eviction_menu to the window
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
        window.addActor(backgroundNpcMenu);

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
        window.addActor(exitBtn);

        // Creating cards and corresponding selected buttons
        Button[] buttons = new Button[NUMBER_OF_NPC];
        Button[] cards = new Button[NUMBER_OF_NPC];
        for (int i = 0; i < NUMBER_OF_NPC; i++) {
            int index = i;
            setCard(cards, index);
            window.addActor(cards[i]);

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
            window.addActor(buttons[i]);
        }
    }

    private void exitMenu() {
        window.remove();
    }

    @Override
    protected void draw(SpriteBatch batch) {}


    // Tools for creating/setting the basic actors
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


    // Creating dialogs for various situations
    /**
     * Types of result dialog box
     */
    protected enum NpcResultDialogType {
        RIGHT_BOX, WRONG_BOX1, WRONG_BOX2, LOSE, WIN
    }

    /**
     * Display a Card information dialog on the window, the style is based on Team7 prototype <br/>
     * All scales are calculated according to the prototype from team 7 only <br/>
     * The context of this dialog will be provided by Team 9
     *
     * @param card_name The name of the card which calls this function
     * @author Code: Team7 Yingxin Liu <br/>
     * Put the label with context: Team 7 Shaohui Wang <br/>
     * Update the label with context: Team 7 Yingxin Liu <br/>
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
                SequenceAction overallSequence = new SequenceAction();
                overallSequence.addAction(Actions.fadeOut(0.5f));
                RunnableAction exit = new RunnableAction();
                exit.setRunnable(dialog::remove);
                overallSequence.addAction(exit);
                dialog.addAction(overallSequence);
                return true;
            }
        });

        //  add clues of npc
        Color contextColor = Color.NAVY;
        if (Objects.equals(card_name, "Nereus")
                || Objects.equals(card_name, "Metis")
                || Objects.equals(card_name, "Doris")
                || Objects.equals(card_name, "Zoe")) { contextColor = Color.TAN;}
        Table context =  new Table();
        context.setSize((float) (window.getWidth()*(1057.33- 560.57)/1600),
                (float) (window.getHeight()*(660.33 - 280.33)/900));
        context.setPosition((float) (dialog_size_x*((570.67 - 407.34)/810)),
                (float) ( dialog_size_y * ((800.33- 660.33)/653.33)));

        // set context string
        String contextContent = helper.createClueContext(card_name, library);

        // create and set the label with content
        Label contextLabel = new Label(contextContent, skin, "font", contextColor);
        contextLabel.setSize(context.getWidth(),context.getHeight());
        contextLabel.setFontScale(window.getWidth()/1920,window.getHeight()/980);
        contextLabel.setAlignment(Align.left);
        contextLabel.setWrap(true);
        context.addActor(contextLabel);
        dialog.addActor(context);
        SequenceAction overallSequence = new SequenceAction();
        overallSequence.addAction(Actions.alpha(0));
        overallSequence.addAction(Actions.fadeIn(0.7f));
        dialog.addAction(overallSequence);
        window.addActor(dialog);
    }

    /**
     * Display a confirm dialog on the window, the style is based on Team7 prototype <br/>
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
                creatTransDialog(button_name, result);


            }
        });
        dialog.addActor(cancelButton);
        dialog.addActor(okButton);
        window.addActor(dialog);
    }

    /**
     * Display a result dialog and traitor message on the window, the style is based on Team7 prototype <br/>
     * All scales are calculated according to the prototype from team 7 only <br/>
     * Button Ok    : confirm the action from selected button <br/>
     *
     * @param button_name The name of the button that calls this function
     * @param type The type of dialog will be created
     * @author Team7 Yingxin Liu <br/>
     * Put the label with context: Team 7 Shaohui Wang <br/>
     * update label with context: Team 7 Yingxin Liu   <br/>
     * add particle effect: Team 7 Shaohui Wang        <br/>
     * <reference>
     * The three sounds in this function are modified and reprocessed based on resource from open sources <br/>
     * Reference:<br/>
     * RightPromptBox1.wav:
     * kyles. (2022, June 9th). Freesound.
     * <a href="https://freesound.org/people/kyles/sounds/637159/">https://freesound.org/people/kyles/sounds/637159/</a> <br/>
     * WrongPromptBox1.wav:
     * meltormen. (2020,August 17th). Freesound.
     * <a href="https://freesound.org/people/meltormen/sounds/530912/">https://freesound.org/people/meltormen/sounds/530912/</a> <br/>
     * WrongPromptBox2.wav:
     * schroedel. (2016, February 22nd). Freesound.
     * <a href="https://freesound.org/people/schroedel/sounds/337727/">https://freesound.org/people/schroedel/sounds/337727/</a>
     * </reference>
     */
    private void createResultDialog(String button_name, NpcResultDialogType type) {


        logger.debug("create Result dialog from name: " + button_name + " type:" + type);
        // if Lose, it will jump to ending screen, no need to create dialog
        if (type == NpcResultDialogType.LOSE) {
            EndingMenuDisplay.setLose();
            entity.getEvents().trigger("ending");
            return;}
        if (type == NpcResultDialogType.WIN) {
            creatWinDialog();return;}

        // set the style of dialog include font color of title; background; size; position
        float dialog_size_x,dialog_size_y;
        String backgroundPath, buttonPathDefault, buttonPathHover;

        if (type == NpcResultDialogType.RIGHT_BOX) {

            MusicStuff.playMusic("sounds/RightPromptBox1.wav",false);
            backgroundPath = IMAGES_PATH + "rightBox.png";
            buttonPathDefault = IMAGES_PATH + "rightBtn.png";
            buttonPathHover = IMAGES_PATH + "rightBtn_H.png";
            particleRightActor.setPosition((float) (bgWidth * (1200.33 / 1600)), (float) (bgHeight * 640.33 / 900));
            particleRightActor.start();
            window.addActor(particleRightActor);
            particleRightActorTwo.setPosition((float) (bgWidth * (400.33 / 1600)), (float) (bgHeight * 640.33 / 900));
            particleRightActorTwo.start();
            window.addActor(particleRightActorTwo);
        } else if (type == NpcResultDialogType.WRONG_BOX1) {
            MusicStuff.playMusic("sounds/WrongPromptBox1.wav",false);
            backgroundPath = IMAGES_PATH + "wrongBox1.png";
            buttonPathDefault = IMAGES_PATH + "chanceBtn.png";
            buttonPathHover = IMAGES_PATH + "chanceBtn_H.png";
            particleWrongActor1.setPosition((float) (bgWidth * (1200.33 / 1600)), (float) (bgHeight * 440.33 / 900));
            particleWrongActor1.start();
            particleWrongActor2.setPosition((float) (bgWidth * (400.33 / 1600)), (float) (bgHeight * 440.33 / 900));
            particleWrongActor2.start();
            window.addActor(particleWrongActor1);
            window.addActor(particleWrongActor2);
        } else {
            MusicStuff.playMusic("sounds/WrongPromptBox2.wav",false);
            backgroundPath = IMAGES_PATH + "wrongBox2.png";
            buttonPathDefault = IMAGES_PATH + "chanceBtn2.png";
            buttonPathHover = IMAGES_PATH + "chanceBtn2_H.png";
            particleSecondWrongActor1.setPosition((float) (bgWidth * (1200.33 / 1600)), (float) (bgHeight * 440.33 / 900));
            particleSecondWrongActor1.start();
            particleSecondWrongActor2.setPosition((float) (bgWidth * (400.33 / 1600)), (float) (bgHeight * 440.33 / 900));
            particleSecondWrongActor2.start();
            window.addActor(particleSecondWrongActor1);
            window.addActor(particleSecondWrongActor2);

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
        } else {
            dialog_size_x = (float) (bgWidth * (678.67 / 1600));
            dialog_size_y = (float) (bgHeight * (382.38 / 900));
            dialog.setSize(dialog_size_x, dialog_size_y);
            dialog.setPosition((float) (bgWidth * (438.33 / 1600)), (float) (bgHeight * (1 - 663.33 / 900)));

            okButton.setSize((float) (dialog_size_x * (116.67/678.67)), (float) (dialog_size_y * (53.33/382.38)));
            okButton.setPosition((float) (dialog.getWidth() * ((748.04-438.33)/678.67)),
                    (float) (dialog.getHeight() * ((663.33 - 635.65) / 382.38)));
        }
        okButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                logger.info("yes_button from " + button_name + " clicked");
                //when you select ok button
                MusicStuff.playMusic(buttonPath,false);
                dialog.remove();
                particleWrongActor1.remove();
                particleRightActor.remove();
                particleRightActorTwo.remove();
                particleWrongActor2.remove();
                particleSecondWrongActor1.remove();
                particleSecondWrongActor2.remove();
                if (type == NpcResultDialogType.RIGHT_BOX)
                    creatWinDialog();
            }
        });

        showWinLoseContext(dialog, type, button_name, dialog_size_x, dialog_size_y);
        dialog.addActor(okButton);
        window.addActor(dialog);
    }

    /**
     * creat a table include the label with win/lose context and title for result dialog
     * and add it to given window
     * @param dialog window that will be added
     * @author Team 7 Yingxin Liu
     */
    private void showWinLoseContext(Window dialog, NpcResultDialogType type, String name, float dx, float dy){
        // create frame for both title and context
        Table title =  new Table();
        Table context =  new Table();
        Color titleColor = Color.BROWN;
        if (type == NpcResultDialogType.RIGHT_BOX) {
            title.setSize((window.getWidth()*(993-623)/1600),
                    (float) (window.getHeight()*(416.67 - 373.33)/900));
            title.setPosition((float) (dx*((835-433.33)/683.67)),
                    (float) ( dy * ((663.33 - 376.67)/416.24)), Align.top);
            context.setSize((window.getWidth()*(993-613)/1600),
                    (float) (window.getHeight()*(566.67 - 423.33)/900));
            context.setPosition((float) (dx*((835-443.33)/683.67)),
                    (float) ( dy * ((663.33 - 433.33)/416.24)), Align.top);
            titleColor = Color.FOREST;
        } else {
            title.setSize((window.getWidth()*(993-623)/1600),
                    (float) (window.getHeight()*(416.67 - 373.33)/900));
            title.setPosition((float) (dx*((803-438.33)/678.67)),
                    (float) ( dy * ((663.33 - 373.33)/382.38)), Align.top);
            context.setSize((window.getWidth()*(993-623)/1600),
                    (float) (window.getHeight()*(560 - 416.67)/900));
            context.setPosition((float) (dx*((803-438.33)/678.67)),
                    (float) ( dy * ((663.33 - 426.67)/382.38)), Align.top);
            if (type == NpcResultDialogType.WRONG_BOX2){titleColor = Color.DARK_GRAY;}
        }
        // set title/context string
        String titleContent = helper.createTraitorMessageInterjection(type);
        String contextContent = helper.createTraitorMessageForSaveAtlantis(name, type);

        // create and set the label with content
        Label titleLabel = new Label(titleContent, skin, "title",titleColor);
        titleLabel.setAlignment(Align.center);
        titleLabel.setFontScale(window.getWidth()/1920,window.getHeight()/980);

        Label contextLabel = new Label(contextContent, skin, "font",Color.BLACK);
        contextLabel.setSize(context.getWidth(),context.getHeight());
        contextLabel.setAlignment(Align.top);
        contextLabel.setFontScale(window.getWidth()/1920,window.getHeight()/980);
        contextLabel.setWrap(true);

        // add to goal position
        title.add(titleLabel).center();
        context.addActor(contextLabel);
        dialog.addActor(title);
        dialog.addActor(context);
    }

    /**
     * When player select the correct traitor, after click OK button on right_box,
     * An win Information page will appear and the key will be spawn on the game area.
     * @author Team 7 Yingxin Liu <br/>
     * code of spawn key is from <b>Team 5</b>
     */
    private void creatWinDialog() {
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
        window.addActor(dialog);
    }

    /**
     * creat a table include the label with win context and title
     * and add it to given window
     * @param dialog window that will be added
     * @author Team 7 Yingxin Liu
     */
    private void showWinContext(Window dialog){
        // create frame for both title and context
        Table title =  new Table();
        title.setSize(window.getWidth()*(1106-636)/1600,
                window.getHeight()*(220-160)/900);
        title.setPosition((float) (window.getWidth()*(636.67/1600)),
                (float) (window.getHeight()*(1-220.0/900)));

        Table context =  new Table();
        context.setSize((float) (window.getWidth()*(1150.67-696.67)/1600),
                window.getHeight()*(750-290)/900);
        context.setPosition((float) (window.getWidth()*(696.67/1600)),
                (float) (window.getHeight()*(1-750.0/900)));

        // set title/context string
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
        titleLabel.setFontScale(window.getWidth()/1920,window.getHeight()/980);

        Label contextLabel = new Label(contextContent, skin, "font_large",Color.DARK_GRAY);
        contextLabel.setSize(context.getWidth(),context.getHeight());
        contextLabel.setAlignment(Align.top);
        contextLabel.setFontScale(window.getWidth()/1920,window.getHeight()/980);
        contextLabel.setWrap(true);

        // add to goal position
        title.add(titleLabel).center();
        context.addActor(contextLabel);
        dialog.addActor(title);
        dialog.addActor(context);
    }

    /**
     * create a window to play transition animation According to the npc name and result
     * @param button_name npc name
     * @param result whether the npc is traitor
     * @author Team 7 Yingxin Liu
     */
    private void creatTransDialog(String button_name, NpcResultDialogType result) {
        TextureRegionDrawable styleImage = new TextureRegionDrawable(
                resourceService.getAsset(IMAGES_PATH + "transparentBg.png", Texture.class));
        Window.WindowStyle windowStyle = new Window.WindowStyle(new BitmapFont(), Color.BLACK, styleImage);
        Window dialog = new Window("", windowStyle); dialog.setModal(true); dialog.setFillParent(true);

        Image background = new Image(
                resourceService.getAsset(IMAGES_PATH + "transBg.png", Texture.class));
        Image step1 = new Image(resourceService.getAsset(
                IMAGES_PATH + button_name.toLowerCase() + "Trans1.png", Texture.class));
        Image step2 = new Image(resourceService.getAsset(
                IMAGES_PATH + button_name.toLowerCase() + "Trans2.png", Texture.class));
        Image step3 = new Image(resourceService.getAsset(
                IMAGES_PATH + button_name.toLowerCase() + "Trans3.png", Texture.class));
        String resultImg = IMAGES_PATH + "wrongTrans.png";
        if (result == NpcResultDialogType.RIGHT_BOX) {
            resultImg = IMAGES_PATH + "correctTrans.png";
        }
        Image step4 = new Image(
                resourceService.getAsset(resultImg, Texture.class));

        float stepSizeX = (float) (bgWidth * ((601.63-174.82) / 1600));
        float stepSizeY = (float) (bgHeight * ((452.5-3.59) / 900));
        float stepPosX = (float) (bgWidth * (174.82/ 1600));
        float stepPosY = (float) (bgHeight * (1 - 452.5/ 900));
        step1.setSize(stepSizeX, stepSizeY); step1.setPosition(stepPosX, stepPosY);
        step2.setSize(stepSizeX, stepSizeY); step2.setPosition(stepPosX, stepPosY);
        step3.setSize((float) (bgWidth * ((602.5 -174.5) / 1600)), (float) (bgHeight * ((439.5-3.87) / 900)));
        step3.setPosition((float) (bgWidth * (174.5/ 1600)), (float) (bgHeight * (1 - 439.5/ 900)));
        if (result == NpcResultDialogType.RIGHT_BOX) {
            step4.setSize((float) (bgWidth * ((910-690.00) / 1600)), (float) (bgHeight * ((678.34-221.66) / 900)));
            step4.setPosition((float) (bgWidth * (690.00/ 1600)), (float) (bgHeight * (1 - 678.34/ 900)));
        } else {
            step4.setSize((float) (bgWidth * ((1008.34 -591.66) / 1600)), (float) (bgHeight * ((658.34-241.66) / 900)));
            step4.setPosition((float) (bgWidth * (591.66/ 1600)), (float) (bgHeight * (1 - 658.34/ 900)));
        }


        background.setFillParent(true);
        dialog.addActor(background);
        dialog.addActor(step1);
        step1.addAction(Actions.alpha(0));
        window.addActor(dialog);

        SequenceAction overallSequence = new SequenceAction();
        overallSequence.addAction(Actions.alpha(0));
        overallSequence.addAction(Actions.fadeIn(1));
        //All animations
        RunnableAction show1 = new RunnableAction();  //Frame1: Characters appear
        show1.setRunnable(() -> {
            step1.addAction(Actions.fadeIn(1));
        });
        overallSequence.addAction(show1);
        overallSequence.addAction(Actions.delay(1));

        RunnableAction show2 = new RunnableAction(); //Frame2: Tied up npc
        show2.setRunnable(() -> {
            step1.remove();
            dialog.addActor(step2);
        });
        overallSequence.addAction(show2);
        overallSequence.addAction(Actions.delay(0.7f));

        RunnableAction show3 = new RunnableAction(); //Frame3: Put down npc and Dragged it out
        show3.setRunnable(() -> {
            step2.remove();
            dialog.addActor(step3);
            SequenceAction actions = new SequenceAction();
            actions.addAction(Actions.delay(0.8f));
            ParallelAction move = new ParallelAction();
            move.addAction(Actions.moveTo((float) (bgWidth * (754.82/ 1600)), (float) (bgHeight * (1 - 898.88/ 900)), 3));
            move.addAction(Actions.scaleTo(1.37f,1.37f,3));
            actions.addAction(move);
            actions.addAction(Actions.fadeOut(1));
            step3.addAction(actions);

        });
        overallSequence.addAction(show3);
        overallSequence.addAction(Actions.delay(4.8f));

        RunnableAction show4 = new RunnableAction(); //Frame4: show the result
        show4.setRunnable(() -> {
            step3.remove();
            dialog.addActor(step4);
            SequenceAction actions = new SequenceAction();
            actions.addAction(Actions.delay(1.2f));
            actions.addAction(Actions.fadeOut(1));
            step4.addAction(actions);
        });
        overallSequence.addAction(show4);
        overallSequence.addAction(Actions.delay(1.2f));

        // finish
        overallSequence.addAction(Actions.fadeOut(1));
        RunnableAction exit = new RunnableAction();
        exit.setRunnable(() -> {
            dialog.remove();
            createResultDialog(button_name, result);
        });
        overallSequence.addAction(exit);
        background.addAction(overallSequence);
    }
}