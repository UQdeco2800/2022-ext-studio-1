package com.deco2800.game.components.player;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.deco2800.game.ui.UIComponent;

public class PlayerProfileDisplay extends UIComponent {

    Table table;

    @Override
    public void create() {
        super.create();
        addActors();

    }

    private void addActors() {
        
    }

    @Override
    public void draw(SpriteBatch batch)  {
        // draw is handled by the stage
    }

}
