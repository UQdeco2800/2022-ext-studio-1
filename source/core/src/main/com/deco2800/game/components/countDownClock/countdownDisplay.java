package com.deco2800.game.components.countDownClock;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.deco2800.game.GdxGame;
import com.deco2800.game.ui.UIComponent;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.deco2800.game.GdxGame.ScreenType;
//import com.deco2800.game.components.player.ConsumeableItemComponent;
import com.deco2800.game.files.UserSettings;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.components.endingmenu.*;
import com.deco2800.game.music.MusicStuff;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class countdownDisplay extends UIComponent {

    private static final Logger logger = LoggerFactory.getLogger(countdownDisplay.class);
    private  final GdxGame game;
    private float timeRemaining;

    private static final String buttonPath = "sounds/button.mp3";


    private boolean stop=false;
    private float timeCount;
    public boolean paused = false;

    Table root;
    Table widgetBackground;
    Table timerText;

    Button pauseResumeBtn;

    private PausedWindow pausedWindow;

    //private float theTime = game.gameTimeVar;

    public countdownDisplay(GdxGame game) {
        super();
        this.game = game;
        this.timeRemaining = game.gameTimeVar; //- (ServiceLocator.getTimeSource().getTime() / 1000);
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
            //game.gameTimeVar=0;

           // logger.info("negative time: {}", String.valueOf(this.getRemainingTime()));
           // logger.info(counterLabel.getText().toString());
//            EndingMenuDisplay.setLose();
//            System.out.println("111");
//            entity.getEvents().trigger("ending");
//
        }
        if(timeRemaining>0 && game.theGameScreen.getStatus() == false) {
            int equHours;
            int equMins;
            int equSeconds;

            String hoursString;
            String minsString;
            String secondsString;

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

            if (equMins < 10) {
                minsString = "0"+String.valueOf(equMins);
            } else {
                minsString = String.valueOf(equMins);
            }

            if (equHours < 10) {
                hoursString = "0" + String.valueOf(equHours);
            } else {
                hoursString = String.valueOf(equHours);
            }

            if (equSeconds < 10) {
                secondsString = "0" + String.valueOf(equSeconds);
             } else {
                secondsString = String.valueOf(equSeconds);
            }

//            counterLabel.setText(String.valueOf(equHours+":"+equMins+":"+equSeconds));
            counterLabel.setText(hoursString + ":" + minsString + ":" + secondsString);
         //   logger.info("remaining time: {}", String.valueOf(this.getRemainingTime()));
          //  logger.info(counterLabel.getText().toString());
        }


    }

    private void addActors() {

        Texture widgetTexture = new Texture(Gdx.files.internal("images/countdown_clock/timerWidget.png"));
        TextureRegionDrawable widgetDrawable = new TextureRegionDrawable(widgetTexture);
        Image widget = new Image(widgetDrawable);

        counterLabel = new Label(String.valueOf(timeRemaining), skin);
        counterLabel.setPosition((float) (stage.getWidth() * 0.85), (float) (stage.getHeight() * 0.3));

        counterLabel.setFontScale(1.25F);

        widgetBackground = new Table();
        widgetBackground.add(widget);
        timerText = new Table();
        timerText.add(counterLabel);
//        root = new Table();

        widgetBackground.setSize((float) (timerText.getWidth() * 1.5), (float) (timerText.getHeight() * 1.5));

        Stack stack = new Stack();

        stack.add(widgetBackground);
        stack.add(timerText);

        stack.setPosition((float) (stage.getWidth() * 0.88), (float) (stage.getHeight() * 0.3));

//        stage.addActor(counterLabel);
//        root.add(stack);
        stage.addActor(stack);
        Table pauseBtn = pauseButton();
        pauseBtn.setPosition((float) (stage.getWidth() * 0.9325), (float) (stage.getHeight() * 0.29));
//        root.add(pauseBtn);

//        Table resumeBtn = resumeButton();
        stage.addActor(pauseBtn);
//        stage.addActor(resumeBtn);



    }


    //Make the pause button, and change stop  to true when clicked
    private Table pauseButton(){
//        TextButton pauseButton = new TextButton("PAUSE", skin);

        Texture pauseBtnTexture = new Texture(Gdx.files.internal("images/countdown_clock/pause.png"));
        TextureRegionDrawable pauseBtnDrawable = new TextureRegionDrawable(pauseBtnTexture);

        Texture resumeBtnTexture = new Texture(Gdx.files.internal("images/countdown_clock/start.png"));
        TextureRegionDrawable resumeBtnDrawable = new TextureRegionDrawable(resumeBtnTexture);

        Button pauseButton = new ImageButton(pauseBtnDrawable);
        Button resumeButton = new ImageButton(resumeBtnDrawable);

//        pauseResumeBtn = pauseButton;


        pauseButton.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        MusicStuff.playMusic(buttonPath, false);
                        logger.debug("pause button clicked");
                        stop = true;
                        //  MainGameScreen.stopGame = true;
                        //  MainGameScreen.render.stopGame = true;
                        // game.stopGame = true;
                        pausedWindow = new PausedWindow(game);
                        pausedWindow.create();
                        game.theGameScreen.changeStatus();
                    }
                });


        Table table =new Table();
        table.add(pauseButton).size(75, 75);

        return table;


    }


    //Make the resume button, change stop to false when clicked
    private Table resumeButton(){
        TextButton resumeButton = new TextButton("RESUME", skin);

        resumeButton.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        MusicStuff.playMusic(buttonPath, false);
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
