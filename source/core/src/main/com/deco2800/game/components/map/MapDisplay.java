package com.deco2800.game.components.map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
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

        Button loadLab1 = createButton("images/map/1house-2.png", "images/map/1house-2.png");
        loadLab1.setPosition((float) (stage.getWidth() * 0.2), (float) (stage.getHeight() * 0.78));
        loadLab1.setSize((float) (stage.getWidth()*0.042), (float) (stage.getHeight()*0.07116));

        loadLab1.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.debug("Lab1 button clicked");
                        lab1();
                    }
                });

        Button loadLab2 = createButton("images/map/3house-2.png", "images/map/3house-2.png");
        loadLab2.setPosition((float) (stage.getWidth() * 0.283), (float) (stage.getHeight() * 0.69));
        loadLab2.setSize((float) (stage.getWidth()*0.03), (float) (stage.getHeight()*0.0611));

        loadLab2.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.debug("Lab2 button clicked");
                        lab2();
                    }
                });

        Button loadLab3 = createButton("images/map/the ellips house-2.png", "images/map/the ellips house-2.png");
        loadLab3.setPosition((float) (stage.getWidth() * 0.15), (float) (stage.getHeight() * 0.72));
        loadLab3.setSize((float) (stage.getWidth()*0.03), (float) (stage.getHeight()*0.063));

        loadLab3.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.debug("Lab3 button clicked");
                        lab3();
                    }
                });

        Button loadLab4 = createButton("images/map/church-2.png", "images/map/church-2.png");
        loadLab4.setPosition((float) (stage.getWidth() * 0.255), (float) (stage.getHeight() * 0.555));
        loadLab4.setSize((float) (stage.getWidth()*0.035), (float) (stage.getHeight()*0.065));

        loadLab4.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.debug("Lab4 button clicked");
                        lab4();
                    }
                });

        Button loadLab5 = createButton("images/map/thatched cattage-2.png", "images/map/thatched cattage-2.png");
        loadLab5.setPosition((float) (stage.getWidth() * 0.275), (float) (stage.getHeight() * 0.755));
        loadLab5.setSize((float) (stage.getWidth()*0.035), (float) (stage.getHeight()*0.065));

        loadLab5.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.debug("Lab5 button clicked");
                        lab5();
                    }
                });

        Button loadLabHouse = createButton("images/map/lab house.png", "images/map/lab house.png");
        loadLabHouse.setPosition((float) (stage.getWidth() * 0.2), (float) (stage.getHeight() * 0.64));
        loadLabHouse.setSize((float) (stage.getWidth()*0.06), (float) (stage.getHeight()*0.1));

        loadLabHouse.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.debug("Lab5 button clicked");
                        labHouse();
                    }
                });

        table.add(map);
        //table.row();
        //table.add(exitBtn).expandX().left();


        stage.addActor(table);
        stage.addActor(exitBtn);
        stage.addActor(loadLab1);
        stage.addActor(loadLab2);
        stage.addActor(loadLab3);
        stage.addActor(loadLab4);
        stage.addActor(loadLab5);
        stage.addActor(loadLabHouse);
    }

    @Override
    public void draw(SpriteBatch batch){

    }

    private void exitMenu() {
        game.setScreen(GdxGame.ScreenType.MAIN_MENU);}

    private void lab1(){
        game.setScreen(GdxGame.ScreenType.LAB_1);
    }

    private void lab2(){game.setScreen(GdxGame.ScreenType.LAB_2);}

    private void lab3(){game.setScreen(GdxGame.ScreenType.LAB_3);}

    private void lab4(){game.setScreen(GdxGame.ScreenType.LAB_4);}

    private void lab5(){game.setScreen(GdxGame.ScreenType.LAB_5);}

    private void labHouse(){game.setScreen(GdxGame.ScreenType.LAB_HOUSE);}

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
