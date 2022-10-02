package com.deco2800.game.components.endingmenu;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An ui component for displaying the Main menu.
 */
public class EndingMenuDisplay extends UIComponent {
    private static final Logger logger = LoggerFactory.getLogger(EndingMenuDisplay.class);
    private static final float Z_INDEX = 2f;
    private Table table;
    private static boolean judge = true;

    @Override
    public void create() {
        super.create();
        addActors();
    }

    public static void setWin() {
        judge = true;
    }

    public static void setLose() {
        judge = false;
    }

    private void addActors() {
        table = new Table();
        table.setFillParent(true);
        Image title = setTitle(judge);

        TextButton restartBtn = new TextButton("Restart the game", skin);
        TextButton menuBtn = new TextButton("Back to menu", skin);

        // Triggers an event when the button is pressed
        restartBtn.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.debug("Restart button clicked");
                        entity.getEvents().trigger("start");
                    }
                });

        menuBtn.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.debug("Menu button clicked");
                        entity.getEvents().trigger("menu");
                    }
                });

        table.add(title);
        table.row();
        table.add(restartBtn).padTop(30f);
        table.row();

        table.add(menuBtn).padTop(30f);
        table.row();

        stage.addActor(table);
    }

    private Image setTitle(boolean judge) {
        if (judge) {
            Image title = new Image(
                    ServiceLocator.getResourceService()
                            .getAsset("images/endingMenu/you_win.png", Texture.class));
            return title;
        } else {
            Image title = new Image(
                    ServiceLocator.getResourceService()
                            .getAsset("images/endingMenu/you_lose.png", Texture.class));
            return title;
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        // draw is handled by the stage
    }

    @Override
    public float getZIndex() {
        return Z_INDEX;
    }

    @Override
    public void dispose() {
        table.clear();
        super.dispose();
    }
}