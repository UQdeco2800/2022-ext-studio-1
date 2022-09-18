package com.deco2800.game.components.map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.deco2800.game.GdxGame;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainScreenTest_Display extends UIComponent {
    private static final Logger logger = LoggerFactory.getLogger(Lab_1_Display.class);
    private static final float Z_INDEX = 2f;
    private Table table;
    private final GdxGame game;
    private int stepNum;
    private boolean isBatteryCollected = true;

    public MainScreenTest_Display(GdxGame game) {
        super();
        this.game = game;
        stepNum = 0;
    }

    @Override
    public void create() {
        super.create();
        addActors();
    }

    private void addActors() {
        table = new Table();
        table.setFillParent(true);
        Image background = new Image(ServiceLocator.getResourceService().getAsset("images/black.jpg", Texture.class));
        Image lab = new Image(ServiceLocator.getResourceService().getAsset("images/map/LAB/whole lab.png", Texture.class));
        lab.setPosition(0, 0);
        lab.setSize(stage.getWidth(), stage.getHeight());

        /**TextButton exitBtn = new TextButton("Exit", skin);

         // Triggers an event when the button is pressed
         exitBtn.addListener(
         new ChangeListener() {
        @Override public void changed(ChangeEvent changeEvent, Actor actor) {
        logger.debug("Exit button clicked");
        exitMenu();
        }
        });
         */
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

        String playerName = "test";
        TextField text1 = new TextField("Dr." + playerName + "! Wake up!", skin);
        text1.setDisabled(true);
        text1.setPosition((float) (stage.getWidth() * 0.5), (float) (stage.getHeight() * 0.5));
        text1.setSize((float) (stage.getWidth() * 0.11), (float) (stage.getHeight() * 0.05));
        stage.addActor(text1);

        TextArea text2 = new TextArea("Ugh...What happended...Why the power is off?\n" +
                "Zoe, is that you? Are you okay? It's too dark now, I have to restore the power.", skin);
        text2.setDisabled(true);
        text2.setPosition(-1000000, -1000000);
        text2.setSize((float) (stage.getWidth() * 0.30), (float) (stage.getHeight() * 0.05));
        stage.addActor(text2);

        TextField text3 = new TextField("Go to the electric switch.", skin);
        text3.setDisabled(true);
        text3.setPosition(-1000000, -1000000);
        text3.setSize((float) (stage.getWidth() * 0.18), (float) (stage.getHeight() * 0.05));
        stage.addActor(text3);

        TextField text4 = new TextField("You are at the electric switch.", skin);
        text4.setDisabled(true);
        text4.setPosition(-1000000, -1000000);
        text4.setSize((float) (stage.getWidth() * 0.25), (float) (stage.getHeight() * 0.05));
        stage.addActor(text4);

        TextField text5 = new TextField("This does not look right...but I have to fix it first anyway.", skin);
        text5.setDisabled(true);
        text5.setPosition(-1000000, -1000000);
        text5.setSize((float) (stage.getWidth() * 0.40), (float) (stage.getHeight() * 0.05));
        stage.addActor(text5);

        TextField text6 = new TextField("Collect 3 batteries to fix it.", skin);
        text6.setDisabled(true);
        text6.setPosition(-1000000, -1000000);
        text6.setSize((float) (stage.getWidth() * 0.25), (float) (stage.getHeight() * 0.05));
        stage.addActor(text6);

        TextArea text7 = new TextArea("No! It's Princess Nereus! She can't be dead! Princess Nereus is the guardian of Atlantics. " +
                "If she dies, it means that Atlantics will be fallen soon!", skin);
        text7.setDisabled(true);
        text7.setPosition(-1000000, -1000000);
        text7.setSize((float) (stage.getWidth() * 0.40), (float) (stage.getHeight() * 0.08));
        stage.addActor(text7);

        TextField text8 = new TextField("She hurts so bad! We have to help her!", skin);
        text8.setDisabled(true);
        text8.setPosition(-1000000, -1000000);
        text8.setSize((float) (stage.getWidth() * 0.30), (float) (stage.getHeight() * 0.05));
        stage.addActor(text8);

        TextArea text9 = new TextArea("CLUE 1: THE BIGGEST WOUND IS ON HER WAIST, AND IT LOOKS LIKE SOMEONE HURT HER\n"
                + "CLUE 2: BLOODSTAIN ARE FROM OUTSIDE, SHE SEEMED TO HOBBLE HERE WITH ALL HER STRENGTH\n"
                + "CLUE 3: SHATTERED SCALE FROM NEREUS", skin);
        text9.setDisabled(true);
        text9.setPosition(-1000000, -1000000);
        text9.setSize((float) (stage.getWidth() * 0.50), (float) (stage.getHeight() * 0.10));
        stage.addActor(text9);

        TextField text10 = new TextField("No...we are already too late.", skin);
        text10.setDisabled(true);
        text10.setPosition(-1000000, -1000000);
        text10.setSize((float) (stage.getWidth() * 0.20), (float) (stage.getHeight() * 0.05));
        stage.addActor(text10);

        TextField text11 = new TextField("PLAYER: Why someone wants to kill Princess Nereus? What should we do?", skin);
        text11.setDisabled(true);
        text11.setPosition(-1000000, -1000000);
        text11.setSize((float) (stage.getWidth() * 0.50), (float) (stage.getHeight() * 0.05));
        stage.addActor(text11);

        TextArea text12 = new TextArea("ZOE: Dr." + playerName + ", we have to catch the killer who did this to Nereus.\n" +
                " We all know that Nereus's scale will contain her memory for 3 days,\n" +
                " so we have to collect more of her scales to find out the truth,\n" +
                " and save our home Atlantics before it falls!", skin);
        text12.setDisabled(true);
        text12.setPosition(-1000000, -1000000);
        text12.setSize((float) (stage.getWidth() * 0.50), (float) (stage.getHeight() * 0.10));
        stage.addActor(text12);

        TextField text13 = new TextField("PLAYER: You are right. Let's move now. We only have 3 days.", skin);
        text13.setDisabled(true);
        text13.setPosition(-1000000, -1000000);
        text13.setSize((float) (stage.getWidth() * 0.40), (float) (stage.getHeight() * 0.05));
        stage.addActor(text13);

        table.addListener(
                new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);
                        switch (stepNum) {
                            case 0:
                                table.addActor(lab);//show the lab
                                text1.setPosition(-100000, -100000);
                                text2.setPosition((float) (stage.getWidth() * 0.5), (float) (stage.getHeight() * 0.5));
                                break;
                            case 1:
                                text2.setPosition(-100000, -100000);
                                text3.setPosition((float) (stage.getWidth() * 0.5), (float) (stage.getHeight() * 0.5));
                                break;
                            case 2:
                                text3.setPosition(-100000, -100000);
                                text4.setPosition((float) (stage.getWidth() * 0.5), (float) (stage.getHeight() * 0.5));
                                break;
                            case 3:
                                text4.setPosition(-100000, -100000);
                                text5.setPosition((float) (stage.getWidth() * 0.5), (float) (stage.getHeight() * 0.5));
                                break;
                            case 4:
                                text5.setPosition(-100000, -100000);
                                text6.setPosition((float) (stage.getWidth() * 0.5), (float) (stage.getHeight() * 0.5));
                                break;
                            case 5:
                                //show the lab and blooding.
                                text6.setPosition(-100000, -100000);
                                text7.setPosition((float) (stage.getWidth() * 0.5), (float) (stage.getHeight() * 0.5));
                                break;
                            case 6:
                                text7.setPosition(-100000, -100000);
                                text8.setPosition((float) (stage.getWidth() * 0.5), (float) (stage.getHeight() * 0.5));
                                break;
                            case 7:
                                text8.setPosition(-100000, -100000);
                                text9.setPosition((float) (stage.getWidth() * 0.25), (float) (stage.getHeight() * 0.5));
                                break;
                            case 8:
                                text9.setPosition(-100000, -100000);
                                text10.setPosition((float) (stage.getWidth() * 0.5), (float) (stage.getHeight() * 0.5));
                                break;
                            case 9:
                                text10.setPosition(-100000, -100000);
                                text11.setPosition((float) (stage.getWidth() * 0.25), (float) (stage.getHeight() * 0.5));
                                break;
                            case 10:
                                text11.setPosition(-100000, -100000);
                                text12.setPosition((float) (stage.getWidth() * 0.25), (float) (stage.getHeight() * 0.5));
                                break;
                            case 11:
                                text12.setPosition(-100000, -100000);
                                text13.setPosition((float) (stage.getWidth() * 0.4), (float) (stage.getHeight() * 0.5));
                                break;
                            case 12:
                                text13.setPosition(-100000, -100000);
                                lab.setPosition(-100000, -100000);
                                break;
                            default:
                                break;
                        }
                        if (stepNum <= 4 || isBatteryCollected)
                            stepNum++;
                        System.out.println(stepNum);
                    }
                }
        );
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
}
