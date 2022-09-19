package com.deco2800.game.components.map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.deco2800.game.GdxGame;
import com.deco2800.game.components.npc.NpcInteraction;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class MainScreenTest_Display extends UIComponent {
    private static final Logger logger = LoggerFactory.getLogger(Lab_1_Display.class);
    private static final float Z_INDEX = 2f;
    private Table table;
    private final GdxGame game;
    private int step;
    private boolean isBatteryCollected = true;

    public MainScreenTest_Display(GdxGame game) {
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
        Image background = new Image(ServiceLocator.getResourceService().getAsset(
                "images/black.jpg", Texture.class));

        Button exitBtn = createButton("images/eviction_menu/exitButton.png",
                "images/eviction_menu/exitButton_selected.png");
        exitBtn.setPosition((float) (stage.getWidth() * 0.944), (float) (stage.getHeight() * 0.91));
        exitBtn.setSize((float) (stage.getWidth() * 0.042), (float) (stage.getHeight() * 0.07116));
        exitBtn.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.debug("Exit button clicked");
                        exitMenu();
                    }
                }
        );

        table.add(background);
        stage.addActor(table);
        stage.addActor(exitBtn);

        setDialog(1);
    }

    @Override
    public void draw(SpriteBatch batch) {

    }

    private void exitMenu() {
        game.setScreen(GdxGame.ScreenType.MAP);
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

    private void setDialog(int chapterNum) {
        step = 0;
        try {
            // set dialog box
            Image dialogBox = new Image(ServiceLocator.getResourceService().getAsset(
                            "images/npc_interaction/dialog_box.png", Texture.class));
            dialogBox.setPosition((float) (stage.getWidth() * 0.05), 0);
            dialogBox.setSize((float) (stage.getWidth() * 0.9), (float) (stage.getHeight() * 0.3));
            stage.addActor(dialogBox);

            // set dialog text
            ArrayList<String> texts = NpcInteraction.readNpcFiles(chapterNum);
            Iterator<String> it = texts.iterator();
            Label dialog = new Label(it.next(), skin);
            dialog.setPosition((float) (stage.getWidth() * 0.1), (float) (stage.getHeight() * 0.1));
            dialog.setWrap(true);
            dialog.setWidth((float) (stage.getWidth() * 0.8));
            stage.addActor(dialog);
            dialogBox.addListener(
                    new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            super.clicked(event, x, y);
                            if (chapterNum == 1) {
                                chapter1Listener(it, dialog);
                            }
                        }
                    }
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void chapter1Listener(Iterator<String> it, Label dialog) {
        Image darkLab = new Image(ServiceLocator.getResourceService().getAsset(
                "images/map/LAB/whole lab dark.png", Texture.class));
        darkLab.setPosition(0, 0);
        darkLab.setSize(stage.getWidth(), stage.getHeight());

        Image bloodLab = new Image(ServiceLocator.getResourceService().getAsset(
                "images/map/LAB/whole lab blood.png", Texture.class));
        bloodLab.setPosition(0, 0);
        bloodLab.setSize(stage.getWidth(), stage.getHeight());

        if (it.hasNext()) {
            dialog.setText(it.next());
            step++;
            if (step == 1) {
                table.addActor(darkLab); // show the lab
            }
            if (step == 6) {
                table.removeActor(darkLab);
                table.addActor(bloodLab); // show the blooding lab
            }
        }
    }
}
