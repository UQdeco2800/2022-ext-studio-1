package com.deco2800.game.components.countDownClock;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.deco2800.game.GdxGame;
import com.deco2800.game.components.player.PlayerProfileDisplay;
import com.deco2800.game.music.MusicStuff;
import com.deco2800.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;

public class PausedWindow extends UIComponent {

    private static final int bgWidth = 500;
    private static final int bgHeight = 350;

    private static final String buttonPath = "sounds/button.mp3";

    private static final Logger logger = LoggerFactory.getLogger(PlayerProfileDisplay.class);

    countdownDisplay countdownDisplay;

    GdxGame game;

    Table background;
    Table buttons;

    Stack stack;

    boolean paused = true;

    public PausedWindow(GdxGame game) {
        super();
        this.game = game;
    }
    @Override
    public void create(){
        super.create();
        addActors();
    }

    private void addActors() {
        Texture pauseWindowTexture = new Texture(Gdx.files.internal("images/countdown_clock/pausedWindow.png"));
        TextureRegionDrawable pausedWindow = new TextureRegionDrawable(pauseWindowTexture);

        Texture resumeBtnTexture = new Texture(Gdx.files.internal("images/countdown_clock/resume.png"));
        TextureRegionDrawable resumeBtnDrawable = new TextureRegionDrawable(resumeBtnTexture);

        Button resumeButton = new ImageButton(resumeBtnDrawable);

        Image pausedWindowImage = new Image(pausedWindow);
        background = new Table();
        background.setFillParent(true);
        background.add(pausedWindowImage).width(bgWidth).height(bgHeight);

        buttons = new Table();
        buttons.add(resumeButton).expandY().width(100).height(100);

        stack = new Stack();
        stack.add(background);
        stack.add(buttons);

        stack.setFillParent(true);

        stage.addActor(stack);

        resumeButton.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        MusicStuff.playMusic(buttonPath, false);
                        logger.debug("resume button clicked");
//                        stop = false;
                        game.theGameScreen.changeStatus2();
                        resumeGame();
                    }
                });
    }

    public boolean getStatus() {
        return paused;
    }



    @Override
    public void draw(SpriteBatch batch)  {
        // draw is handled by the stage
    }

    public void resumeGame() {
        super.dispose();
        stack.remove();
        paused = false;
    }

}
