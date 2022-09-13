package com.deco2800.game.components.countDownClock;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.deco2800.game.GdxGame;
import com.deco2800.game.ui.UIComponent;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.deco2800.game.GdxGame.ScreenType;
//import com.deco2800.game.components.player.ConsumeableItemComponent;
import com.deco2800.game.files.UserSettings;
import com.deco2800.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class countdownDisplay extends UIComponent {

    private static final Logger logger = LoggerFactory.getLogger(countdownDisplay.class);
    private final GdxGame game;
    private float timeRemaining;
    private float timeCount;
    public boolean paused = false;
    public countdownDisplay(GdxGame game) {
        super();
        this.game = game;
        this.timeRemaining = 300; //- (ServiceLocator.getTimeSource().getTime() / 1000);
        timeCount = 0;
    }
    public Label counterLabel;

    @Override
    public void create() {
        super.create();
        addActors();
    }

    @Override
    public void update() {
        super.update();
        timeCount = Gdx.graphics.getDeltaTime();

        if (this.timeRemaining <= 0) {
            counterLabel.setText("GAME OVER!");
            logger.info("negative time: {}", String.valueOf(this.getRemainingTime()));
            logger.info(counterLabel.getText().toString());
        }
        else {
            this.timeRemaining -= timeCount;
            counterLabel.setText(String.valueOf(this.timeRemaining));
            logger.info("remaining time: {}", String.valueOf(this.getRemainingTime()));
            logger.info(counterLabel.getText().toString());
        }


    }

    private void addActors() {

        counterLabel = new Label(String.valueOf(timeRemaining), skin);
        counterLabel.setPosition((float) (stage.getWidth() * 0.8), (float) (stage.getHeight() * 0.1));

        counterLabel.setFontScale(2);

        stage.addActor(counterLabel);

    }

    public void pauseGame() {
        this.paused = true;
    }

    private void exitScreen() {
        game.setScreen(ScreenType.MAIN_GAME);
    }
    private Table makeExitBtn() {
        TextButton exitBtn = new TextButton("EXIT", skin);

        exitBtn.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.debug("Exit button clicked");
                        exitScreen();
                    }
                });

        Table table = new Table();
        table.add(exitBtn).expandX().left().pad(0f, 100f, 100f, 0f);
        return table;
    }

    public void increaseTime(float increment)
    {
        this.timeRemaining += increment;
    }

    public float getRemainingTime() {
        return this.timeRemaining;
    }

    @Override
    protected void draw(SpriteBatch batch) {

    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public String toString() {
        return "this is the countdown timer with remaining time: " + String.valueOf(getRemainingTime());
    }


}
