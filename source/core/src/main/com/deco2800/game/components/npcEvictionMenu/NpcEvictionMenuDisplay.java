package com.deco2800.game.components.npcEvictionMenu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
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

import java.util.Objects;

/**
 * An ui component for displaying the npc eviction menu.
 *
 * @author 2022-ext-studio-1-Team7
 */
public class NpcEvictionMenuDisplay extends UIComponent {
    private static final Logger logger = LoggerFactory.getLogger(NpcEvictionMenuDisplay.class);

    private static final int NUMBER_OF_NPC = 8;
    private static float bgWidth;
    private static float bgHeight;

    private static final String IMAGE_PATH = "images/eviction_menu/";  //path of team7 images
    private ResourceService resourceService;

    private final GdxGame game;

    public NpcEvictionMenuDisplay(GdxGame game) {
        super();
        this.game = game;

    }

    @Override
    public void create() {
        super.create();
        bgWidth = stage.getWidth();
        bgHeight = stage.getHeight();
        resourceService = ServiceLocator.getResourceService();
        addActors();
    }

    /**
     * Add actors of eviction_menu to the stage
     *
     * @author Team7: Shaohui Wang Yingxin Liu
     */
    private void addActors() {
        // Adding background
        Image backgroundNpcMenu =
                new Image(resourceService.getAsset(IMAGE_PATH + "evictionMenu_background.png", Texture.class));
        backgroundNpcMenu.setSize((float) (bgWidth * 0.89), (float) (bgHeight * 0.9851));
        backgroundNpcMenu.setPosition(bgWidth / 2, 0, Align.bottom);
        stage.addActor(backgroundNpcMenu);

        // Button for exit the select page, will go back to previous page
        Button exitBtn = createButton(IMAGE_PATH + "exitButton.png", IMAGE_PATH + "exitButton_selected.png");
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

        // Images of cards, Will be updated in later sprints
        String[] cardImages = { // images for 8 characters in cards: waiting for Team 1
                IMAGE_PATH + "evictionCard_single.png", IMAGE_PATH + "evictionCard_single.png",
                IMAGE_PATH + "evictionCard_single.png", IMAGE_PATH + "evictionCard_single.png",
                IMAGE_PATH + "evictionCard_single.png", IMAGE_PATH + "evictionCard_single.png",
                IMAGE_PATH + "evictionCard_single.png", IMAGE_PATH + "evictionCard_single.png"};

        // Creating cards and corresponding selected buttons
        Button[] buttons = new Button[NUMBER_OF_NPC];
        Button[] cards = new Button[NUMBER_OF_NPC];
        for (int i = 0; i < NUMBER_OF_NPC; i++) {
            int index = i;
            cards[i] = createButton(IMAGE_PATH + "evictionCard_single.png",
                    IMAGE_PATH + "evictionCard_hover.png");
            setButton(cards, i, "cardDefault");
            cards[i].addListener(new InputListener() {
                @Override  // mouse hovering
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    setButton(cards, index, "cardHovering");
                }

                @Override  // default
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    setButton(cards, index, "cardDefault");
                }

                @Override // clicked
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    logger.debug("card" + index + "clicked");
                    return true;
                }
            });
            stage.addActor(cards[i]);

            buttons[i] = createButton(IMAGE_PATH + "selectButton_single.png", IMAGE_PATH + "selectButton_selected.png");
            setButton(buttons, i, "selectButton");
            buttons[i].addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent changeEvent, Actor actor) {
                    logger.debug("button" + index + "clicked");
                    createConfirmDialog("button" + index);
                }
            });
            stage.addActor(buttons[i]);
        }
    }


    /**
     * set the position and size of the given button <br/>
     * this is an internal function, do not use it !
     *
     * @param buttons set of buttons
     * @param i       index of the set buttons
     * @param type    type of button to be set
     * @author Team7 Yingxin Liu
     */
    private void setButton(Button[] buttons, int i, String type) {
        if (Objects.equals(type, "cardDefault")) {
            buttons[i].setSize((float) (bgWidth * (207.26 / 1600)), (float) (bgHeight * (283.91 / 900)));
            buttons[i].setPosition((float) (bgWidth * ((454.13 + ((i % 4) * 230.32)) / 1600)),
                    (float) (bgHeight * (1 - (295.055 + (i / 4) * 334.09) / 900)), Align.center);
        } else if (Objects.equals(type, "cardHovering")) {
            buttons[i].setSize((float) (bgWidth * (235.79 / 1600)), (float) (bgHeight * (323.0 / 900)));
            buttons[i].setPosition((float) (bgWidth * ((452.135 + ((i % 4) * 230.32)) / 1600)),
                    (float) (bgHeight * (1 - (294.06 + (i / 4) * 334.09) / 900)), Align.center);
        } else if (Objects.equals(type, "selectButton")) {
            buttons[i].setSize((float) (bgWidth * (112.48 / 1600)), (float) (bgHeight * (47.5 / 900)));
            buttons[i].setPosition((float) (bgWidth * ((420.34 + ((i % 4) * 231.03)) / 1600)),
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
                resourceService.getAsset(IMAGE_PATH + "confirmBox.png", Texture.class));
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


    private void exitMenu() {
        game.setScreen(GdxGame.ScreenType.MAIN_GAME);
    }

    @Override
    protected void draw(SpriteBatch batch) {

    }


}