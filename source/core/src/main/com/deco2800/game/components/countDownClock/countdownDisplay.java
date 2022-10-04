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
import com.deco2800.game.components.endingmenu.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class countdownDisplay extends UIComponent {

    private static final Logger logger = LoggerFactory.getLogger(countdownDisplay.class);
    private final GdxGame game;
    private float timeRemaining;

    private boolean stop=false;
    private float timeCount;
    public boolean paused = false;
    public countdownDisplay(GdxGame game) {
        super();
        this.game = game;
        this.timeRemaining = 7260; //- (ServiceLocator.getTimeSource().getTime() / 1000);
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

            game.theGameScreen.changeStatus();

           // logger.info("negative time: {}", String.valueOf(this.getRemainingTime()));
           // logger.info(counterLabel.getText().toString());
//            EndingMenuDisplay.setLose();
//            System.out.println("111");
//            entity.getEvents().trigger("ending");
//
        }
        if(timeRemaining>0 && stop==false) {
            int equHours;
            int equMins;
            int equSeconds;
            this.timeRemaining -= timeCount;
            if (timeRemaining>=60){
                float mins = timeRemaining/60;
                if (mins>=60){
                    int hours = (int)(mins/60);
                    equHours = hours;
                    equMins =(int) (mins-equHours*60);
                    equSeconds=(int)(timeRemaining-equHours*60*60-equMins*60);


                }else{
                    equHours=0;
                    equMins=(int) mins;
                    equSeconds=(int)(timeRemaining-equMins*60);

                }

            }else{
                equHours=0;
                equMins=0;
                equSeconds=(int)timeRemaining;
            }
            counterLabel.setText(String.valueOf(equHours+":"+equMins+":"+equSeconds));
         //   logger.info("remaining time: {}", String.valueOf(this.getRemainingTime()));
          //  logger.info(counterLabel.getText().toString());
        }


    }

    private void addActors() {

        counterLabel = new Label(String.valueOf(timeRemaining), skin);
        counterLabel.setPosition((float) (stage.getWidth() * 0.85), (float) (stage.getHeight() * 0.3));

        counterLabel.setFontScale(2);

        stage.addActor(counterLabel);



        Table pauseBtn = pauseButton();
        Table resumeBtn = resumeButton();
        stage.addActor(pauseBtn);
        stage.addActor(resumeBtn);



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

    //Make the pause button, and change stop  to true when clicked
    private Table pauseButton(){
        TextButton pauseButton = new TextButton("PAUSE", skin);


        pauseButton.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.debug("pause button clicked");
                        stop = true;
                        //  MainGameScreen.stopGame = true;
                        //  MainGameScreen.render.stopGame = true;
                        // game.stopGame = true;
                        game.theGameScreen.changeStatus();
                    }
                });

        Table table =new Table();
        table.add(pauseButton).expandX().right().pad(0f, 2400f, 400f, 0f);
        return table;


    }


    //Make the resume button, change stop to false when clicked
    private Table resumeButton(){
        TextButton resumeButton = new TextButton("RESUME", skin);

        resumeButton.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.debug("resume button clicked");
                        stop = false;
                        game.theGameScreen.changeStatus2();
                    }
                });

        Table table =new Table();
        table.add(resumeButton).expandX().right().pad(0f, 2150f, 400f, 0f);
        return table;
    }

    public void increaseRemainingTime(float increment) {
        this.timeRemaining += increment;
    }

    public float getRemainingTime() {
        return this.timeRemaining;
    }
    public void setTimeRemaining(float time){
        this.timeRemaining = time;
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
