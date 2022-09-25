package com.deco2800.game.components.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
import com.deco2800.game.services.ResourceService;
import org.slf4j.Logger;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import java.security.Provider;


public class PlayerProfileDisplay extends UIComponent {

    private static final int bgWidth = 500;
    private static final int bgHeight = 350;

    Table root;

    Button exitButton;

    @Override
    public void create() {
        super.create();
        addActors();
    }


    private void addActors() {
        Image bgImage = new Image(
                ServiceLocator.getResourceService().getAsset(
                        "images/blank.png", Texture.class
                )
        );

        TextureRegionDrawable exitBtnStyle = new TextureRegionDrawable(ServiceLocator.getResourceService().getAsset(
                "images/eviction_menu/exitButton.png", Texture.class
        ));

        exitButton = new Button(exitBtnStyle);

        exitButton.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        closeWindow();
                    }
                }
        );

        exitButton.setPosition((float) (this.stage.getWidth() * 0.45), (float) (this.stage.getHeight() * 0.1));
        exitButton.setSize((float) (this.stage.getWidth() * ((1493.33 - 1436.67) / 1600)), (float) (this.stage.getHeight() * (53.33 / 900)));

        root = new Table();
        root.setFillParent(true);
        root.add(bgImage).height(Gdx.graphics.getHeight()-bgHeight).width(Gdx.graphics.getWidth()-bgWidth);
        this.stage.addActor(root);
        this.stage.addActor(exitButton);
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
