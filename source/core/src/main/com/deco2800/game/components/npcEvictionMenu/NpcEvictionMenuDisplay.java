package com.deco2800.game.components.npcEvictionMenu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;


import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.deco2800.game.GdxGame;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NpcEvictionMenuDisplay extends UIComponent {
    private static final Logger logger = LoggerFactory.getLogger(NpcEvictionMenuDisplay.class);

    private static final int NUMBER_OF_NPC =8 ;
    private static float backgroundWidth;
    private static float backgroundHeight;

    private static final String IMAGE_PATH = "images/eviction_menu/";
    private  ResourceService resourceService;

    private final GdxGame game;

    public NpcEvictionMenuDisplay(GdxGame game) {
        super();
        this.game = game;

    }

    @Override
    public void create() {
        super.create();
        backgroundWidth = stage.getWidth();
        backgroundHeight = stage.getHeight();
        resourceService = ServiceLocator.getResourceService();
        addActors();
    }


    private void addActors() {
        // Adding background
        Image backgroundNpcMenu =
                new Image(resourceService.getAsset(IMAGE_PATH+"evictionMenu_background.png", Texture.class));
        backgroundNpcMenu.setSize((float) (backgroundWidth * 0.89), (float) (backgroundHeight * 0.9851));
        backgroundNpcMenu.setPosition(backgroundWidth / 2, 0, Align.bottom);
        stage.addActor(backgroundNpcMenu);


        // Button for exit the select page, will go back to previous page

        Button exitBtn = createButton(IMAGE_PATH+"exitButton.png",IMAGE_PATH+"exitButton_selected.png");
        exitBtn.setSize((float) (backgroundWidth * ((1493.33 - 1436.67) / 1600)), (float) (backgroundHeight * (53.33 / 900)));
        exitBtn.setPosition((float) (backgroundWidth * 0.887), (float) (backgroundHeight * (1 - 153.33 / 900)));
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


        // Images of cards, Will be updated in later sprints
        String[] cardImagesUp = { // images for 8 card selected up
                IMAGE_PATH + "evictionCard_single.png", IMAGE_PATH + "evictionCard_single.png",
                IMAGE_PATH + "evictionCard_single.png", IMAGE_PATH + "evictionCard_single.png",
                IMAGE_PATH + "evictionCard_single.png", IMAGE_PATH + "evictionCard_single.png",
                IMAGE_PATH + "evictionCard_single.png", IMAGE_PATH + "evictionCard_single.png"};
        String [] cardImagesDown ={ // images for 8 card selected down
                IMAGE_PATH + "evictionCard_single.png", IMAGE_PATH + "evictionCard_single.png",
                IMAGE_PATH + "evictionCard_single.png", IMAGE_PATH + "evictionCard_single.png",
                IMAGE_PATH + "evictionCard_single.png", IMAGE_PATH + "evictionCard_single.png",
                IMAGE_PATH + "evictionCard_single.png", IMAGE_PATH + "evictionCard_single.png"};

        // Creating cards and corresponding selected buttons
        Button[] confirmButton = new Button[NUMBER_OF_NPC];
        Button[] npcCardButton = new Button[NUMBER_OF_NPC];
        for (int i = 0; i < NUMBER_OF_NPC; i++) {
            String index = String.valueOf(i);

            npcCardButton[i] = createButton(cardImagesUp[i], cardImagesDown[i]);
            npcCardButton[i].setSize((float) (backgroundWidth * (207.26 / 1600)), (float) (backgroundHeight * (284.1 / 900)));
            if (i < 4) {
                npcCardButton[i].setPosition((float) (backgroundWidth * ((350.5 + (i * 230.32)) / 1600)), (float) (backgroundHeight * (1 - 436.01 / 900)));
            } else {
                npcCardButton[i].setPosition((float) (backgroundWidth * ((350.5 + ((i - 4) * 230.32)) / 1600)), (float) (backgroundHeight * (1 - 757.1 / 900)));
            }
            npcCardButton[i].addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent changeEvent, Actor actor) {
                    logger.debug("npcCard" + index + "clicked");
                    // No action yet
                }
            });
            stage.addActor(npcCardButton[i]);

            confirmButton[i] = createButton(IMAGE_PATH + "selectButton_single.png", IMAGE_PATH + "selectButton_selected.png");
            confirmButton[i].setSize((float) (backgroundWidth * (112.48 / 1600)), (float) (backgroundHeight * (47.5 / 900)));
            if (i < 4) {
                confirmButton[i].setPosition((float) (backgroundWidth * ((420.34 + (i * 231.03)) / 1600)), (float) (backgroundHeight * (1 - 465.36 / 900)));
            } else {
                confirmButton[i].setPosition((float) (backgroundWidth * ((420.34 + ((i - 4) * 231.03)) / 1600)), (float) (backgroundHeight * (1 - 786.75 / 900)));
            }
            confirmButton[i].addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent changeEvent, Actor actor) {
                    logger.debug("confirmButton" + index + "clicked");
                    createConfirmDialog("confirmButton" + index);   // 传入对应的对话框
                }
            });
            stage.addActor(confirmButton[i]);
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
                resourceService.getAsset(IMAGE_PATH + "confirmBox.png", Texture.class));
        Window.WindowStyle windowStyle = new Window.WindowStyle(new BitmapFont(), Color.BLACK, styleImage);
        Window dialog = new Window("", windowStyle);
        dialog.setModal(true);    // The dialog is always at the front
        float dialog_size_x = (float) (backgroundWidth * 0.2537);
        float dialog_size_y = (float) (backgroundHeight * 0.3037);
        dialog.setSize(dialog_size_x, dialog_size_y);
        float dialog_pos_x = (float) (backgroundWidth * 0.3756);
        float dialog_pos_y = (float) (backgroundHeight * (1 - 0.65));
        dialog.setPosition(dialog_pos_x, dialog_pos_y);

        // Set Cancel and Ok buttons for dialog
        Button cancelButton = createButton(IMAGE_PATH + "confirmBtn_cancel.png", IMAGE_PATH + "confirmBtn_cancel1.png");
        cancelButton.setSize((float) (dialog_size_x * 0.361), (float) (dialog_size_y * 0.2317));
        cancelButton.setPosition((float) (dialog.getWidth() * 0.1067), 0);
        cancelButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                logger.debug("cancel_button from " + button_name + " clicked");
                dialog.remove();
            }
        });

        Button okButton = createButton(IMAGE_PATH + "confirmBtn_ok.png", IMAGE_PATH + "confirmBtn_ok1.png");
        okButton.setSize((float) (dialog_size_x * 0.377), (float) (dialog_size_y * 0.2317));
        okButton.setPosition((float) (dialog.getWidth() * 0.5239), 0);
        okButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                logger.debug("yes_button from " + button_name + " clicked");
                // No action yet
                dialog.remove();
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
     * @param Down path of image: The style of the button when mouse hovering
     * @return a Button with up and down style
     * @author Team7: Yingxin Liu  Shaohui Wang
     */
    private Button createButton(String Up, String Down) {
        logger.debug("createButton with path:" + Up + Down);
        TextureRegionDrawable up = new TextureRegionDrawable(resourceService.getAsset(Up, Texture.class));
        TextureRegionDrawable down = new TextureRegionDrawable(resourceService.getAsset(Down, Texture.class));
        Button.ButtonStyle style = new Button.ButtonStyle();
        style.up = up;
        style.over = down;
        return new Button(style);
    }


    private void exitMenu() {
        game.setScreen(GdxGame.ScreenType.MAIN_GAME);
    }

    @Override
    protected void draw(SpriteBatch batch) {

    }


}