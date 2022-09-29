package com.deco2800.game.components.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
import com.deco2800.game.services.ResourceService;
import org.slf4j.Logger;
import com.badlogic.gdx.graphics.Texture;

import java.security.Provider;


public class PlayerProfileDisplay extends UIComponent {

    private static final int bgWidth = 500;
    private static final int bgHeight = 350;

    Table root;
    Table background;
    Table content;

    Button exitButton;

    private static final String[] playerProfileTextures = {
            "images/exitbtn.png"
    };

    public final ResourceService resourceService = ServiceLocator.getResourceService();

    @Override
    public void create() {
        super.create();
        resourceService.loadTextures(playerProfileTextures);
        addActors();
    }


    private void addActors() {
        Image bgImage = new Image(
                resourceService.getAsset(
                        "images/blank.png", Texture.class
                )
        );

//        TextureRegionDrawable exitBtnStyle = new TextureRegionDrawable(resourceService.getAsset(
//                "images/exitbtn.png", Texture.class
//        ));

//        exitButton = new ImageButton(exitBtnStyle);
//
//        exitButton.addListener(
//                new ChangeListener() {
//                    @Override
//                    public void changed(ChangeEvent event, Actor actor) {
//                        closeWindow();
//                    }
//                }
//        );

//        exitButton.setPosition((float) (this.stage.getWidth() * 0.45), (float) (this.stage.getHeight() * 0.1));
//        exitButton.setSize((float) (this.stage.getWidth() * ((1493.33 - 1436.67) / 1600)), (float) (this.stage.getHeight() * (53.33 / 900)));

        Stack stack = new Stack();

        root = new Table();
        root.setFillParent(true);

        background = new Table();

        background.setFillParent(true);
        background.add(bgImage).height(Gdx.graphics.getHeight()-bgHeight).width(Gdx.graphics.getWidth()-bgWidth);

        Label title = new Label("Player Profile", skin);


        content = new Table();
        content.add(title);
        content.row();


        stack.add(background);
        stack.add(content);

        root.add(stack);

        this.stage.addActor(root);
//        this.stage.addActor(exitButton);
    }

    @Override
    public void draw(SpriteBatch batch)  {
        // draw is handled by the stage
    }

    private void closeWindow() {
        super.dispose();
        root.remove();
        exitButton.remove();
    }

}
