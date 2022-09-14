package com.deco2800.game.components.map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.deco2800.game.GdxGame;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An ui component for displaying the Map.
 */
public class LabDisplay extends UIComponent{
    private static final Logger logger = LoggerFactory.getLogger(LabDisplay.class);
    private static final float Z_INDEX = 2f;
    private Table table;
    private final GdxGame game;

    public LabDisplay(GdxGame game) {
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
        Image lab = new Image(ServiceLocator.getResourceService().getAsset("images/map/LAB/whole lab.png", Texture.class));

        /**TextButton exitBtn = new TextButton("Exit", skin);

         // Triggers an event when the button is pressed
         exitBtn.addListener(
         new ChangeListener() {
        @Override
        public void changed(ChangeEvent changeEvent, Actor actor) {
        logger.debug("Exit button clicked");
        exitMenu();
        }
        });
         */

        Button exitBtn = createButton("images/eviction_menu/exitButton.png",
                "images/eviction_menu/exitButton_selected.png");
        exitBtn.setPosition((float) (stage.getWidth() * 0.944), (float) (stage.getHeight() * 0.91));
        exitBtn.setSize((float) (stage.getWidth()*0.042), (float) (stage.getHeight()*0.07116));
        exitBtn.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.debug("Exit button clicked");
                        exitMenu();
                    }
                });


        table.add(lab);
        //table.row();
        //table.add(exitBtn).expandX().left();



        stage.addActor(table);
        stage.addActor(exitBtn);
    }

    @Override
    public void draw(SpriteBatch batch){

    }

    private void exitMenu() {
        game.setScreen(GdxGame.ScreenType.MAP);}

    @Override
    public float getZIndex(){
        return Z_INDEX;
    }

    @Override
    public void dispose() {
        table.clear();
        super.dispose();
    }
    private Button createButton(String Up, String Down) {
        logger.debug("createButton with path:" + Up + Down);
        ResourceService resService = ServiceLocator.getResourceService();
        TextureRegionDrawable up = new TextureRegionDrawable(resService.getAsset(Up, Texture.class));
        TextureRegionDrawable down = new TextureRegionDrawable(resService.getAsset(Down, Texture.class));
        Button.ButtonStyle style = new Button.ButtonStyle();
        style.up = up;
        style.over = down;
        return new Button(style);
    }
}
