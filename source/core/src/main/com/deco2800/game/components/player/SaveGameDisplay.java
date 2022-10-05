package com.deco2800.game.components.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;;
import com.badlogic.gdx.utils.Array;
import com.deco2800.game.GdxGame;
import com.deco2800.game.components.countDownClock.countdownDisplay;
import com.deco2800.game.components.player.entity.Backpack;
import com.deco2800.game.components.player.entity.Item;
import com.deco2800.game.components.endingmenu.EndingMenuDisplay;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.factories.SwitchFactory;
import com.deco2800.game.rendering.TextureRenderComponent;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SaveGameDisplay extends UIComponent{
    public SaveGameDisplay() {
        create();
    }

    @Override
    public void create() {
        super.create();
        addActors();

    }

    private void addActors() {
        Table saveGameTable = new Table();
        Label saveGameLabel = new Label("Save Game", skin);
        saveGameTable.add(saveGameLabel);
        stage.addActor(saveGameTable);
    }

    @Override
    protected void draw(SpriteBatch batch) {

    }
}
