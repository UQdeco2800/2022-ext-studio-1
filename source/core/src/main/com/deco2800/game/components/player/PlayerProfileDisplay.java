package com.deco2800.game.components.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
import com.deco2800.game.services.ResourceService;
import org.slf4j.Logger;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.graphics.Texture;


public class PlayerProfileDisplay extends UIComponent {

    private static final int bgWidth = 500;
    private static final int bgHeight = 350;

    Table root;

    @Override
    public void create() {
        super.create();
        addActors();
    }


    private void addActors() {
//        Image bgImage = new Image(
//                ServiceLocator.getResourceService().getAsset(
//                        "images/Blank.png", Texture.class
//                )
//        );

        root = new Table();
        root.setFillParent(true);
//        root.add(bgImage).height(Gdx.graphics.getHeight()-bgHeight).width(Gdx.graphics.getWidth()-bgWidth);
        root.add(new Label("Player Profile", skin));
        this.stage.addActor(root);
    }

    @Override
    public void draw(SpriteBatch batch)  {
        // draw is handled by the stage
    }

}
