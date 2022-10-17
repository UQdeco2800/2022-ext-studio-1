package com.deco2800.game.components.npc;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.deco2800.game.GdxGame;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class NpcInteractionDisplay extends UIComponent {
    private static final Logger logger = LoggerFactory.getLogger(NpcInteractionDisplay.class);
    private static final float Z_INDEX = 2f;
    private Table table;
    private final GdxGame game;
    private int step;
    private Image dialogBox;
    private Label dialog;
    private final Label prompt;
    private final Image promptBox;
    private int chapterNum;
    private ClickListener clickListener;
    private DialogWithSelection root;
    private boolean inChapter1 = false;
    private boolean inChapter2 = false;
    private boolean inChapter3 = false;
    private boolean inChapter4 = false;
    private boolean inChapter5 = false;
    private boolean dialogRepeated = false;
    private Window npcEvictionMenuWindow;

    public NpcInteractionDisplay(GdxGame game) {
        super();
        this.game = game;
        this.step = 0;
        prompt = new Label("Press F to interact", skin);
        prompt.setWidth(10f);
        promptBox = new Image(ServiceLocator.getResourceService().getAsset(
                "images/npc_interaction/dialog_box.png", Texture.class));
    }

    @Override
    public void create() {
        super.create();
        addActors();
    }

    private void addActors() {
//        table = new Table();
//        table.setFillParent(true);
//        Image background = new Image(ServiceLocator.getResourceService().getAsset(
//                "images/black.jpg", Texture.class));
//
//        Button exitBtn = createButton("images/eviction_menu/exitButton.png",
//                "images/eviction_menu/exitButton_selected.png");
//        exitBtn.setPosition((float) (stage.getWidth() * 0.944), (float) (stage.getHeight() * 0.91));
//        exitBtn.setSize((float) (stage.getWidth() * 0.042), (float) (stage.getHeight() * 0.07116));
//        exitBtn.addListener(
//                new ChangeListener() {
//                    @Override
//                    public void changed(ChangeEvent changeEvent, Actor actor) {
//                        exitMenu();
//                    }
//                }
//        );
//
//        table.add(background);
//        stage.addActor(table);
//        stage.addActor(exitBtn);

        // set dialog box
        dialogBox = new Image(ServiceLocator.getResourceService().getAsset(
                "images/npc_interaction/dialog_box.png", Texture.class));
        dialogBox.setPosition((float) (stage.getWidth() * 0.05), 0);
        dialogBox.setSize((float) (stage.getWidth() * 0.9), (float) (stage.getHeight() * 0.2));
        stage.addActor(dialogBox);

        // set npc eviction menu window for the game ending
//        npcEvictionMenuWindow = new NpcEvictionMenuDisplayNew(
//                logger, ServiceLocator.getResourceService(), stage.getWidth(), stage.getHeight(),
//                this.gameArea, this.game).creatEvictionMenu();

        chapterNum = 1;
        setDialog();
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
        super.dispose();
        dialog.remove();
        dialogBox.remove();
        dialogBox.removeListener(clickListener);
    }

    private ArrayList<String> getLinearDialog(int chapterNum) throws IOException {
        BufferedReader br;
        ArrayList<String> texts = new ArrayList<>();
        try {
            br = new BufferedReader(new FileReader("dialogs/Chapter " + chapterNum + ".txt"));
            String line;
            while ((line = br.readLine()) != null) {
                texts.add(line);
            }
            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return texts;
    }

    private ArrayList<String> getSelectionDialog(int chapterNum, int selectionNum, int option) throws IOException {
        BufferedReader br;
        ArrayList<String> dialogs = new ArrayList<>();
        try {
            br = new BufferedReader(new FileReader("dialogs/Chapter " + chapterNum
                    + "/Chap" + chapterNum + "_option" + selectionNum + "_" + option + ".txt"));
            String line;
            while ((line = br.readLine()) != null) {
                dialogs.add(line);
            }
            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return dialogs;
    }

    private void setDialog() {
        dialog = new Label("Chapter " + chapterNum, skin);
        dialog.setPosition((float) (stage.getWidth() * 0.1), (float) (stage.getHeight() * 0.1));
        dialog.setWrap(true);
        dialog.setWidth((float) (stage.getWidth() * 0.8));
        stage.addActor(dialog);

        try {
            if (chapterNum == 1 || chapterNum == 4) {// chapter without selections
                ArrayList<String> texts = getLinearDialog(chapterNum);
                Iterator<String> it = texts.iterator();
                switch (chapterNum) {
                    case 1 -> {
                        inChapter1 = true;
                        clickListener = new ClickListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                super.clicked(event, x, y);
                                chapter1Listener(it);
                            }
                        };
                    }
                    case 4 -> {
                        inChapter4 = true;
                        clickListener = new ClickListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                super.clicked(event, x, y);
                                chapter4Listener(it);
                            }
                        };
                    }
                }

            } else {
                switch (chapterNum) {
                    case 2 -> {
//                        chapter2Opening();
                        inChapter2 = true;
                        step = 0;
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
                        inChapter3 = true;
                        root = DialogWithSelection.getChapter3Dialog();
                        clickListener = new ClickListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                super.clicked(event, x, y);
                                chapter3Listener();
                            }
                        };
                    }
                    case 5 -> {
                        inChapter5 = true;
                        root = DialogWithSelection.getChapter5Dialog();
                        clickListener = new ClickListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                super.clicked(event, x, y);
                                chapter5Listener();
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
        dialogBox.addListener(clickListener);
    }

    private void chapter1Listener(Iterator<String> it) {
//        if (step != 6 || SwitchFactory.isWorking) {
        if (it.hasNext()) {
            dialog.setText(it.next());
            if (step == 6) {
                dispose();
            }
        } else {// chapter 1 ends
            dispose();
        }
//        }
        step++;
        logger.info(String.valueOf(step));
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
            if (root.getDialog().equals("ENDING")) {// player selected wrong option
                dispose();
            } else {
                if (root.getDialog().equals("You: \"This woman seems know something, maybe she can help me.\"")) {
                    dialogRepeated = false;
                    step = 8;
                } else if (root.getDialog().equals("You: \"Right. But she does not know it. We do not need to bother her with it either. I think it worth a try. At least we could borrow the scale from her afterwards.\"")) {
                    dialogRepeated = false;
                    step = 25;
                }
                dialog.setText(root.getDialog());
                root = root.getNext();
            }
        } else if (root.isSelectionPoint()) {
            setSelection();
        } else if (DialogWithSelection.getChapter2Endings().contains(root)) {// chapter 2 ends
            dialogBox.removeListener(clickListener);
            dialog.remove();
            inChapter2 = false;
            chapterNum = 3;
            setDialog();
        } else {
            dispose();
        }
        if (!dialogRepeated) {
            step++;
        }
        logger.info(String.valueOf(step));
    }

    private void chapter3Listener() {
        if (root.getNext() != null) {
            dialog.setText(root.getDialog());
            root = root.getNext();
        } else if (root.isSelectionPoint()) {
            setSelection();
        } else if (DialogWithSelection.getChapter3Endings().contains(root)) {// chapter 3 ends
            dialogBox.removeListener(clickListener);
            dialog.remove();
            inChapter3 = false;
            chapterNum = 4;
            setDialog();
        } else {
            dispose();
        }
    }

    private void chapter4Listener(Iterator<String> it) {
        if (it.hasNext()) {
            dialog.setText(it.next());
            step++;
        } else {// chapter 4 ends
            dialog.remove();
            dialogBox.removeListener(clickListener);
            inChapter4 = false;
            chapterNum = 5;
            setDialog();
        }
    }

    private void chapter5Listener() {
        if (root.getNext() != null) {
            dialog.setText(root.getDialog());
            root = root.getNext();
        } else if (root.isSelectionPoint()) {
            setSelection();
        } else if (DialogWithSelection.getChapter5Ending().equals(root)) {// select the murderer
            inChapter5 = false;
            dispose();
            // show murderer selection page
            stage.addActor(this.npcEvictionMenuWindow);
        } else {
            dispose();
        }
    }

    private ArrayList<String> continueToLinearDialog(int chapterNum, int step) throws IOException {
        ArrayList<String> dialogs = getLinearDialog(chapterNum);
        return new ArrayList<>(dialogs.subList(step, dialogs.size() - 1));
    }

    private void continueToSelectionDialog(int chapterNum, int selectionNum, int option, int nthDialog) {
        switch (chapterNum) {
            case 2 -> {
                root = DialogWithSelection.getChapter2Dialog();
            }
            case 3 -> {
                root = DialogWithSelection.getChapter3Dialog();
            }
            case 5 -> {
                root = DialogWithSelection.getChapter5Dialog();
            }
        }
        if (selectionNum == 1 && option == 2) {
            root = root.getLastDialog().getOption2();
        } else if (selectionNum == 3 && option == 1) {
            root = root.getLastDialog().getOption2()
                    .getLastDialog().getOption2()
                    .getLastDialog().getOption1();
        }
        for (int i = 0; i < nthDialog; i++) {
            root = root.getNext();
        }
    }

    private boolean dialogBoxNotShowing() {
        return dialogBox.getStage() == null;
    }

    public void interactWithZoe() throws IOException {
        if (dialogBoxNotShowing()) {// if conversation ended
            switch (step) {
                case 5 -> {// recall selection 2-1
                    if (inChapter2) {
                        stage.addActor(dialogBox);
                        setDialog();
                    }
                }
                case 7 -> {// continue dialog after fixing the battery
                    if (inChapter1) {
                        try {
                            ArrayList<String> texts = continueToLinearDialog(chapterNum, step);
                            Iterator<String> it = texts.iterator();
                            clickListener = new ClickListener() {
                                @Override
                                public void clicked(InputEvent event, float x, float y) {
                                    super.clicked(event, x, y);
                                    chapter1Listener(it);
                                }
                            };
                            stage.addActor(dialogBox);
                            stage.addActor(dialog);
                            dialogBox.addListener(clickListener);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                case 16 -> {// chapter 1 ends
                    if (inChapter1) {
                        inChapter1 = false;
                        chapterNum = 2;
                        stage.addActor(dialogBox);
                        setDialog();
                    }
                }
                case 18 -> {// after leaving from Metis
                    if (inChapter2) {
                        root = root.getNext();
                        showSelectionDialog();
                    }
                }
            }
            logger.info("interact successfully");
        } else {// if conversation in progress
            logger.info("interact failed");
        }
    }

    public void interactWithMetis() throws IOException {
        if (dialogBoxNotShowing()) {// if conversation ended
            switch (step) {
                case 6 -> {// player see metis for the first time
                    if (inChapter2) {
                        root = root.getNext();
                        showSelectionDialog();
                    }
                }
                case 11 -> {// recall dialog after player ignoring metis
                    continueToSelectionDialog(2, 1, 2, 5);
                    showSelectionDialog();
                    dialogRepeated = true;
                }
            }
            logger.info("interact successfully");
        } else {// if conversation in progress
            logger.info("interact failed");
        }
    }

    public void interactWithDoris() {
        if (dialogBoxNotShowing()) {// if conversation ended
            switch (step) {
                case 21 -> {// first meet Doris
                    if (inChapter2) {
                        root = root.getNext();
                        showSelectionDialog();
                    }
                }
                case 30 -> {// player collected 5 corals
                    root = root.getNext();
                    showSelectionDialog();
                }
                case 31 -> {// recall dialog after player telling the truth
                    continueToSelectionDialog(2, 3, 1, 11);
                    showSelectionDialog();
                    dialogRepeated = true;
                }
            }
            logger.info("interact successfully");
        } else {// if conversation in progress
            logger.info("interact failed");
        }
    }

    public void showInteractionPrompt() {
        prompt.setPosition((float) (stage.getWidth() * 0.42), (float) (stage.getHeight() * 0.08));
        promptBox.setSize((float) (stage.getWidth() * 0.26), (float) (stage.getHeight() * 0.1));
        promptBox.setPosition((float) (stage.getWidth() * 0.38), (float) (stage.getHeight() * 0.05));
        if (dialogBoxNotShowing()) {
            stage.addActor(promptBox);
            stage.addActor(prompt);
        }
    }

    public void hideInteractionPrompt() {
        promptBox.remove();
        prompt.remove();
    }

    private void showSelectionDialog() {
        dialog.setText(root.getDialog());
        stage.addActor(dialogBox);
        stage.addActor(dialog);
        dialogBox.addListener(clickListener);
    }
}
