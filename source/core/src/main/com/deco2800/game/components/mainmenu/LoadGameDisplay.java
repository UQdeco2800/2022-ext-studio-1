package com.deco2800.game.components.mainmenu;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.deco2800.game.GdxGame;
import com.deco2800.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoadGameDisplay extends UIComponent {
    private static final Logger logger = LoggerFactory.getLogger(LoadGameDisplay.class);
    private final GdxGame game;

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

    }

    @Override
    protected void draw(SpriteBatch batch) {
    }

    @Override
    public void dispose() {
        rootTable.clear();
        super.dispose();
    }
}
