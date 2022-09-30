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
        dialogBox.setSize((float) (stage.getWidth() * 0.9), (float) (stage.getHeight() * 0.3));
        stage.addActor(dialogBox);

        setDialog(1);

//        String playerName = "test";
//        TextField text1 = new TextField("Dr." + playerName + "! Wake up!", skin);
//        String playerName = "Player";
//        TextField text1 = new TextField("???: Dr." + playerName + "! Wake up!", skin);
//        text1.setDisabled(true);
//        text1.setPosition((float) (stage.getWidth() * 0.5), (float) (stage.getHeight() * 0.5));
//        text1.setSize((float) (stage.getWidth() * 0.11), (float) (stage.getHeight() * 0.05));
//        text1.setPosition((float) (stage.getWidth() * 0.41), (float) (stage.getHeight() * 0.5));
//        text1.setSize((float) (stage.getWidth() * 0.18), (float) (stage.getHeight() * 0.05));
//        stage.addActor(text1);
//
//        TextArea text2 = new TextArea("Ugh...What happended...Why the power is off?\n" +
//                "Zoe, is that you? Are you okay? It's too dark now, I have to restore the power.", skin);
//        TextArea text2 = new TextArea(playerName + ": Ugh...What happened...Why the power is off?\n" +
//                "Zoe, is that you? Are you okay?\n" +
//                "It's too dark now, I have to restore the power.", skin);
//        text2.setDisabled(true);
//        text2.setPosition(-1000000, -1000000);
//        text2.setSize((float) (stage.getWidth() * 0.30), (float) (stage.getHeight() * 0.05));
//        text2.setSize((float) (stage.getWidth() * 0.34), (float) (stage.getHeight() * 0.08));
//        stage.addActor(text2);
//
//        TextField text3 = new TextField("Go to the electric switch.", skin);
//        TextField text3 = new TextField("Task: Go to the electric switch.", skin);
//        text3.setDisabled(true);
//        text3.setPosition(-1000000, -1000000);
//        text3.setSize((float) (stage.getWidth() * 0.18), (float) (stage.getHeight() * 0.05));
//        text3.setSize((float) (stage.getWidth() * 0.22), (float) (stage.getHeight() * 0.05));
//        stage.addActor(text3);
//
//        TextField text4 = new TextField("You are at the electric switch.", skin);
//        TextField text4 = new TextField("Tips: You are at the electric switch.", skin);
//        text4.setDisabled(true);
//        text4.setPosition(-1000000, -1000000);
//        text4.setSize((float) (stage.getWidth() * 0.25), (float) (stage.getHeight() * 0.05));
//        text4.setSize((float) (stage.getWidth() * 0.24), (float) (stage.getHeight() * 0.05));
//        stage.addActor(text4);
//
//        TextField text5 = new TextField("This does not look right...but I have to fix it first anyway.", skin);
//        TextField text5 = new TextField(playerName + ": This does not look right...but I have to fix it first anyway.", skin);
//        text5.setDisabled(true);
//        text5.setPosition(-1000000, -1000000);
//        text5.setSize((float) (stage.getWidth() * 0.40), (float) (stage.getHeight() * 0.05));
//        text5.setSize((float) (stage.getWidth() * 0.42), (float) (stage.getHeight() * 0.05));
//        stage.addActor(text5);
//
//        TextField text6 = new TextField("Collect 3 batteries to fix it.", skin);
//        TextField text6 = new TextField("Task: Collect 3 batteries to fix it.", skin);
//        text6.setDisabled(true);
//        text6.setPosition(-1000000, -1000000);
//        text6.setSize((float) (stage.getWidth() * 0.25), (float) (stage.getHeight() * 0.05));
//        text6.setSize((float) (stage.getWidth() * 0.24), (float) (stage.getHeight() * 0.05));
//        stage.addActor(text6);
//
//        TextArea text7 = new TextArea("No! It's Princess Nereus! She can't be dead! Princess Nereus is the guardian of Atlantics. " +
//                TextArea text7 = new TextArea("Zoe: No! It's Princess Nereus! She can't be dead!\n" +
//                "Princess Nereus is the guardian of Atlantics.\n" +
//                "If she dies, it means that Atlantics will be fallen soon!", skin);
//        text7.setDisabled(true);
//        text7.setPosition(-1000000, -1000000);
//        text7.setSize((float) (stage.getWidth() * 0.40), (float) (stage.getHeight() * 0.08));
//        stage.addActor(text7);
//
//        TextField text8 = new TextField("She hurts so bad! We have to help her!", skin);
//        TextField text8 = new TextField(playerName + ": She hurts so bad! We have to help her!", skin);
//        text8.setDisabled(true);
//        text8.setPosition(-1000000, -1000000);
//        text8.setSize((float) (stage.getWidth() * 0.30), (float) (stage.getHeight() * 0.05));
//        stage.addActor(text8);
//        TextArea text9 = new TextArea("CLUE 1: THE BIGGEST WOUND IS ON HER WAIST, AND IT LOOKS LIKE SOMEONE HURT HER\n"
//                + "CLUE 2: BLOODSTAIN ARE FROM OUTSIDE, SHE SEEMED TO HOBBLE HERE WITH ALL HER STRENGTH\n"
//                + "CLUE 3: SHATTERED SCALE FROM NEREUS", skin);
//        text9.setDisabled(true);
//        text9.setPosition(-1000000, -1000000);
//        text9.setSize((float) (stage.getWidth() * 0.50), (float) (stage.getHeight() * 0.10));
//        text9.setSize((float) (stage.getWidth() * 0.60), (float) (stage.getHeight() * 0.08));
//        stage.addActor(text9);
//
//        TextField text10 = new TextField("No...we are already too late.", skin);
//        TextField text10 = new TextField("Zoe: No...we are already too late.", skin);
//        text10.setDisabled(true);
//        text10.setPosition(-1000000, -1000000);
//        text10.setSize((float) (stage.getWidth() * 0.20), (float) (stage.getHeight() * 0.05));
//        text10.setSize((float) (stage.getWidth() * 0.24), (float) (stage.getHeight() * 0.05));
//        stage.addActor(text10);
//
//        TextField text11 = new TextField("PLAYER: Why someone wants to kill Princess Nereus? What should we do?", skin);
//        TextField text11 = new TextField(playerName + ": Why someone wants to kill Princess Nereus? What should we do?", skin);
//        text11.setDisabled(true);
//        text11.setPosition(-1000000, -1000000);
//        text11.setSize((float) (stage.getWidth() * 0.50), (float) (stage.getHeight() * 0.05));
//        stage.addActor(text11);
//        TextArea text12 = new TextArea("ZOE: Dr." + playerName + ", we have to catch the killer who did this to Nereus.\n" +
//                " We all know that Nereus's scale will contain her memory for 3 days,\n" +
//                " so we have to collect more of her scales to find out the truth,\n" +
//                " and save our home Atlantics before it falls!", skin);
//        text12.setDisabled(true);
//        text12.setPosition(-1000000, -1000000);
//        text12.setSize((float) (stage.getWidth() * 0.50), (float) (stage.getHeight() * 0.10));
//        stage.addActor(text12);
//
//        TextField text13 = new TextField("PLAYER: You are right. Let's move now. We only have 3 days.", skin);
//        TextField text13 = new TextField(playerName + ": You are right. Let's move now. We only have 3 days.", skin);
//        text13.setDisabled(true);
//        text13.setPosition(-1000000, -1000000);
//        text13.setSize((float) (stage.getWidth() * 0.40), (float) (stage.getHeight() * 0.05));
//        text13.setSize((float) (stage.getWidth() * 0.36), (float) (stage.getHeight() * 0.05));
//        stage.addActor(text13);
//
//        table.addListener(
//                new ClickListener() {
//                    @Override
//                    public void clicked(InputEvent event, float x, float y) {
//                        super.clicked(event, x, y);
//                        switch (stepNum) {
//                            case 0:
//                                table.addActor(lab);//show the lab
//                                table.addActor(darkLab);//show the lab
//                                text1.setPosition(-100000, -100000);
//                                text2.setPosition((float) (stage.getWidth() * 0.5), (float) (stage.getHeight() * 0.5));
//                                text2.setPosition((float) (stage.getWidth() * 0.33), (float) (stage.getHeight() * 0.5));
//                                break;
//                            case 1:
//                                text2.setPosition(-100000, -100000);
//                                text3.setPosition((float) (stage.getWidth() * 0.5), (float) (stage.getHeight() * 0.5));
//                                text3.setPosition((float) (stage.getWidth() * 0.39), (float) (stage.getHeight() * 0.5));
//                                break;
//                            case 2:
//                                text3.setPosition(-100000, -100000);
//                                text4.setPosition((float) (stage.getWidth() * 0.5), (float) (stage.getHeight() * 0.5));
//                                text4.setPosition((float) (stage.getWidth() * 0.38), (float) (stage.getHeight() * 0.5));
//                                break;
//                            case 3:
//                                text4.setPosition(-100000, -100000);
//                                text5.setPosition((float) (stage.getWidth() * 0.5), (float) (stage.getHeight() * 0.5));
//                                text5.setPosition((float) (stage.getWidth() * 0.29), (float) (stage.getHeight() * 0.5));
//                                break;
//                            case 4:
//                                text5.setPosition(-100000, -100000);
//                                text6.setPosition((float) (stage.getWidth() * 0.5), (float) (stage.getHeight() * 0.5));
//                                text6.setPosition((float) (stage.getWidth() * 0.38), (float) (stage.getHeight() * 0.5));
//                                break;
//                            case 5:
//                                //show the lab and blooding.
//                                table.addActor(bloodLab);
//                                text6.setPosition(-100000, -100000);
//                                text7.setPosition((float) (stage.getWidth() * 0.5), (float) (stage.getHeight() * 0.5));
//                                text7.setPosition((float) (stage.getWidth() * 0.3), (float) (stage.getHeight() * 0.5));
//                                break;
//                            case 6:
//                                text7.setPosition(-100000, -100000);
//                                text8.setPosition((float) (stage.getWidth() * 0.5), (float) (stage.getHeight() * 0.5));
//                                text8.setPosition((float) (stage.getWidth() * 0.35), (float) (stage.getHeight() * 0.5));
//                                break;
//                            case 7:
//                                text8.setPosition(-100000, -100000);
//                                text9.setPosition((float) (stage.getWidth() * 0.25), (float) (stage.getHeight() * 0.5));
//                                break;
//                            case 8:
//                                text9.setPosition(-100000, -100000);
//                                text10.setPosition((float) (stage.getWidth() * 0.5), (float) (stage.getHeight() * 0.5));
//                                text10.setPosition((float) (stage.getWidth() * 0.38), (float) (stage.getHeight() * 0.5));
//                                break;
//                            case 9:
//                                text10.setPosition(-100000, -100000);
//                                text11.setPosition((float) (stage.getWidth() * 0.25), (float) (stage.getHeight() * 0.5));
//                                break;
//                            case 10:
//                                text11.setPosition(-100000, -100000);
//                                text12.setPosition((float) (stage.getWidth() * 0.25), (float) (stage.getHeight() * 0.5));
//                                break;
//                            case 11:
//                                text12.setPosition(-100000, -100000);
//                                text13.setPosition((float) (stage.getWidth() * 0.4), (float) (stage.getHeight() * 0.5));
//                                text13.setPosition((float) (stage.getWidth() * 0.32), (float) (stage.getHeight() * 0.5));
//                                break;
//                            case 12:
//                                text13.setPosition(-100000, -100000);
//                                lab.setPosition(-100000, -100000);
//                                darkLab.setPosition(-100000, -100000);
//                                bloodLab.setPosition(-100000, -100000);
//                                break;
//                            default:
//                                break;
//                        }
//                        if (stepNum <= 4 || isBatteryCollected)
//                            stepNum++;
//                        System.out.println(stepNum);
//                    }
//                }
//        );
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
            } else {// chapter with selections
                if (chapterNum == 2) {
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
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        dialogBox.addListener(clickListener);
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
        optionBox1.setSize((float) (stage.getWidth() * 0.44), (float) (stage.getHeight() * 0.3));
        optionBox2.setSize((float) (stage.getWidth() * 0.44), (float) (stage.getHeight() * 0.3));
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

    private void chapter2Opening() {
        Image lab = new Image(ServiceLocator.getResourceService().getAsset(
                "images/map/LAB/whole lab.png", Texture.class));
        lab.setPosition(0, 0);
        lab.setSize(stage.getWidth(), stage.getHeight());
        table.addActor(lab);
    }

    private void chapter2Listener() {
        readDialogWithSelections();
        // condition for switch map
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
}
