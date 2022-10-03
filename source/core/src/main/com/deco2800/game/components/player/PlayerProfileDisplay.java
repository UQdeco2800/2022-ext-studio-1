package com.deco2800.game.components.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.deco2800.game.areas.ForestGameArea;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
import com.deco2800.game.services.ResourceService;
import org.slf4j.Logger;
import com.badlogic.gdx.graphics.Texture;
import org.slf4j.LoggerFactory;

import java.security.Provider;


public class PlayerProfileDisplay extends UIComponent {

    private static final int bgWidth = 500;
    private static final int bgHeight = 350;

    private static final Logger logger = LoggerFactory.getLogger(PlayerProfileDisplay.class);


    Table root;
    Table background;
    Table title;
    Table content;

    Button backButton;
    Button exitButton;

//    private static final String[] playerProfileTextures = {
//            "images/exitbtn.png",
//    };
//
//    private void loadAssets() {
//        logger.debug("loading assets");
//        ResourceService resourceService = ServiceLocator.getResourceService();
//        resourceService.loadTextures(playerProfileTextures);
//    }
//
//    private void unloadAssets() {
//        logger.debug("unloading assets");
//        ResourceService resourceService = ServiceLocator.getResourceService();
//        resourceService.unloadAssets(playerProfileTextures);
//    }


    @Override
    public void create() {
//        loadAssets();
        super.create();
        addActors();
    }


    private void addActors() {
        ResourceService resourceService = ServiceLocator.getResourceService();

        Image bgImage = new Image(
                resourceService.getAsset(
                        "images/blank.png", Texture.class
                )
        );

        Texture backgroundTexture = new Texture(Gdx.files.internal("images/playerprofile/background.png"));
        TextureRegionDrawable ppBackground = new TextureRegionDrawable(backgroundTexture);

        Image backgroundImage = new Image(ppBackground);

        Texture exitBtnTexture = new Texture(Gdx.files.internal("images/exitbtn.png"));
        TextureRegionDrawable exitBtn = new TextureRegionDrawable(exitBtnTexture);

        Stack stack = new Stack();

        root = new Table();
        root.setFillParent(true);

        background = new Table();

        background.setFillParent(true);
        background.add(backgroundImage).height(Gdx.graphics.getHeight()-bgHeight).width(Gdx.graphics.getWidth()-bgWidth);

        Label clueLabel = new Label("Average attempts to win: ", skin);
        Label clue = new Label("2", skin);
        Label timeLabel = new Label ("Average time take to win: ", skin);
        Label time = new Label("01:29", skin);
        Label remainingStepsLabel = new Label("Number of losses: ", skin);
        Label remainingSteps = new Label("1", skin);
        Label levelLabel = new Label("Number of wins: ", skin);
        Label level = new Label("5", skin);

        content = new Table();

        content.add(clueLabel).height(150).expandX(); //.expandX().expandY();
        content.add(timeLabel).expandX(); //.expandX().expandY();
        content.row();
        content.add(clue); //.width(200);
        content.add(time); //.width(200);
        content.row();
        content.add(remainingStepsLabel).height(150); //.expandX().expandY();
        content.add(levelLabel); //.expandX().expandY();
        content.row();
        content.add(remainingSteps); //.width(300);
        content.add(level); //.width(300);

//        backButton = new TextButton("Back", skin);
        backButton = new ImageButton(exitBtn);
        backButton.setSize((float) (bgWidth * 0.2), (float) (bgHeight * 0.2));
        backButton.setPosition((float) (bgWidth * 2.27), (float) (bgHeight * 1.9));

        backButton.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        closeWindow();
                    }
                }
        );

        stack.add(background);
        stack.add(content);

        root.add(stack);

        this.stage.addActor(root);
        this.stage.addActor(backButton);
//        this.stage.addActor(exitButton);
    }

    @Override
    public void draw(SpriteBatch batch)  {
        // draw is handled by the stage
    }

    private void closeWindow() {
        super.dispose();
        root.remove();
        backButton.remove();
    }

}
