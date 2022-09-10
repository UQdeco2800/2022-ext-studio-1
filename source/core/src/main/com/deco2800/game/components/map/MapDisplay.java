package com.deco2800.game.components.map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.deco2800.game.GdxGame;
import com.deco2800.game.components.mainmenu.MainMenuDisplay;
import com.deco2800.game.screens.MapScreen;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An ui component for displaying the Map.
 */
public class MapDisplay extends UIComponent{
    private static final Logger logger = LoggerFactory.getLogger(MapDisplay.class);
    private static final float Z_INDEX = 2f;
    private Table table;
    private final GdxGame game;

    public MapDisplay(GdxGame game) {
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
        Image map = new Image(ServiceLocator.getResourceService().getAsset("images/map/Map.PNG", Texture.class));

        TextButton exitBtn = new TextButton("Exit", skin);

        // Triggers an event when the button is pressed
        exitBtn.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.debug("Exit button clicked");
                        exitMenu();
                    }
                });

        table.add(map);
        table.row();
        table.add(exitBtn).expandX().left();


        stage.addActor(table);
    }

    @Override
    public void draw(SpriteBatch batch){

    }

    private void exitMenu() {
        game.setScreen(GdxGame.ScreenType.MAIN_MENU);}

    @Override
    public float getZIndex(){
        return Z_INDEX;
    }

    @Override
    public void dispose() {
        table.clear();
        super.dispose();
    }
}
