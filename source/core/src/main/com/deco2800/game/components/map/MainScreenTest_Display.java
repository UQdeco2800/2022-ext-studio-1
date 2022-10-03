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
import com.deco2800.game.components.npc.DialogWithSelection;
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
    private Image dialogBox;
    private Label dialog;
    private ClickListener clickListener;
    private DialogWithSelection root;

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

        // set dialog box
        dialogBox = new Image(ServiceLocator.getResourceService().getAsset(
                "images/npc_interaction/dialog_box.png", Texture.class));
        dialogBox.setPosition((float) (stage.getWidth() * 0.05), 0);
        dialogBox.setSize((float) (stage.getWidth() * 0.9), (float) (stage.getHeight() * 0.2));
        stage.addActor(dialogBox);

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
        dialog = new Label("Chapter " + chapterNum, skin);
        dialog.setPosition((float) (stage.getWidth() * 0.1), (float) (stage.getHeight() * 0.1));
        dialog.setWrap(true);
        dialog.setWidth((float) (stage.getWidth() * 0.8));
        stage.addActor(dialog);

        try {
            if (chapterNum == 1) {// chapter without selections
                ArrayList<String> texts = NpcInteraction.readNpcFiles(chapterNum);
                Iterator<String> it = texts.iterator();
                clickListener = new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);
                        chapter1Listener(it);
                    }
                };
            } else {
                switch (chapterNum) {
                    case 2 -> {
                        chapter2Opening();
                        root = DialogWithSelection.getChapter2Dialog();
                        clickListener = new ClickListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                super.clicked(event, x, y);
                                chapter2Listener();
                            }
                        };
                    }
                    case 3 -> {
                        root = DialogWithSelection.getChapter3Dialog();
                        clickListener = new ClickListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                super.clicked(event, x, y);
                                chapter3Listener();
                            }
                        };
                    }
                }
            }
            dialogBox.addListener(clickListener);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setSelection() {
        // hide the origin dialog box
        dialog.setPosition(-10000, -10000);
        dialogBox.setPosition(-10000, -10000);
        dialogBox.removeListener(clickListener);

        // add new dialog box for selection
        Image optionBox1 = new Image(ServiceLocator.getResourceService().getAsset(
                "images/npc_interaction/dialog_box.png", Texture.class));
        Image optionBox2 = new Image(ServiceLocator.getResourceService().getAsset(
                "images/npc_interaction/dialog_box.png", Texture.class));
        optionBox1.setPosition((float) (stage.getWidth() * 0.05), 0);
        optionBox2.setPosition((float) (stage.getWidth() * 0.5), 0);
        optionBox1.setSize((float) (stage.getWidth() * 0.44), (float) (stage.getHeight() * 0.2));
        optionBox2.setSize((float) (stage.getWidth() * 0.44), (float) (stage.getHeight() * 0.2));
        stage.addActor(optionBox1);
        stage.addActor(optionBox2);

        // add option texts on the boxes
        Label option1 = new Label(root.getOption1().getDialog(), skin);
        Label option2 = new Label(root.getOption2().getDialog(), skin);
        option1.setPosition((float) (stage.getWidth() * 0.08), (float) (stage.getHeight() * 0.1));
        option2.setPosition((float) (stage.getWidth() * 0.53), (float) (stage.getHeight() * 0.1));
        option1.setWrap(true);
        option2.setWrap(true);
        option1.setWidth((float) (stage.getWidth() * 0.4));
        option2.setWidth((float) (stage.getWidth() * 0.4));
        stage.addActor(option1);
        stage.addActor(option2);

        // add click listeners for options
        optionBox1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                option1.remove();
                optionBox1.remove();
                option2.remove();
                optionBox2.remove();
                root = root.getOption1();
                addListenerAfterSelection();
            }
        });

        optionBox2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                option1.remove();
                optionBox1.remove();
                option2.remove();
                optionBox2.remove();
                root = root.getOption2();
                addListenerAfterSelection();
            }
        });
    }

    private void addListenerAfterSelection() {
        root = root.getNext();
        dialog.setText(root.getDialog());
        dialog.setPosition((float) (stage.getWidth() * 0.1), (float) (stage.getHeight() * 0.1));
        dialogBox.setPosition((float) (stage.getWidth() * 0.05), 0);
        clickListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                chapter2Listener();
            }
        };
        dialogBox.addListener(clickListener);
    }

    private void chapter1Listener(Iterator<String> it) {
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
            if (step == 0) {
                table.addActor(darkLab); // show the lab
            } else if (step == 5) {
                table.removeActor(darkLab);
                table.addActor(bloodLab); // show the blooding lab
            }
            step++;
        } else {// chapter 1 ends
            bloodLab.remove();
            dialog.remove();
            dialogBox.removeListener(clickListener);
            setDialog(2);
        }
    }

    private void readDialogWithSelections() {
        if (root.getNext() != null) {
            dialog.setText(root.getDialog());
            root = root.getNext();
        } else if (root.isSelectionPoint()) {
            setSelection();
        } else {
            dialogBox.remove();
            dialog.remove();
            dialogBox.removeListener(clickListener);
        }
    }

    private void chapter2Opening() {
        Image lab = new Image(ServiceLocator.getResourceService().getAsset(
                "images/map/LAB/whole lab.png", Texture.class));
        lab.setPosition(0, 0);
        lab.setSize(stage.getWidth(), stage.getHeight());
        table.addActor(lab);
    }

    private void chapter2Listener() {
        if (root.getNext() != null) {
            dialog.setText(root.getDialog());
            root = root.getNext();
        } else if (root.isSelectionPoint()) {
            setSelection();
        } else if (DialogWithSelection.getChapter2Endings().contains(root)) {
            dialogBox.removeListener(clickListener);
            dialog.remove();
            setDialog(3);
        } else {
            dialogBox.remove();
            dialog.remove();
            dialogBox.removeListener(clickListener);
        }
    }

    private void chapter3Listener() {
        readDialogWithSelections();
        // condition for switch map and exchange items
    }
}