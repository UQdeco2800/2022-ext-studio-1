package com.deco2800.game.components.mainmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.deco2800.game.GdxGame;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoadGameDisplay extends UIComponent {
    private static final Logger logger = LoggerFactory.getLogger(LoadGameDisplay.class);
    private final GdxGame game;

    private Table table;

    private Table rootTable;

    public LoadGameDisplay(GdxGame game) {
        super();
        this.game = game;
    }

    @Override
    public void create() {
        super.create();
        addActors();
    }

    private void addActors() {
        table = new Table();
        table.setFillParent(true);
        Image title =
                new Image(
                        ServiceLocator.getResourceService()
                                .getAsset("images/menu/LoadGameTitle.png", Texture.class));
        title.setPosition((Gdx.graphics.getWidth()-title.getWidth())/2f,Gdx.graphics.getHeight()-title.getHeight()-15);

        table.add(title);
        stage.addActor(table);

    }


    //private Table makeLoadTable(){

    //}

    @Override
    protected void draw(SpriteBatch batch) {
    }

    @Override
    public void dispose() {
        rootTable.clear();
        super.dispose();
    }
}
