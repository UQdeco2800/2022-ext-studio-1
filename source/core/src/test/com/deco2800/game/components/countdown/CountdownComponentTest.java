package com.deco2800.game.components.countdown;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.deco2800.game.extensions.GameExtension;
import com.deco2800.game.components.countDownClock.countdownDisplay;
import com.deco2800.game.components.gameoverScreen.GameOverDisplay;
import com.deco2800.game.screens.MainGameScreen;
import org.junit.Before;
import com.deco2800.game.ui.UIComponent;

import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.deco2800.game.GdxGame;

@ExtendWith(GameExtension.class)
public class CountdownComponentTest {

    private static final Logger logger = LoggerFactory.getLogger(CountdownComponentTest.class);
    private GdxGame game =  mock(GdxGame.class);
    private countdownDisplay countdown = new countdownDisplay(game);

    private countdownDisplay countdown2 = mock(countdownDisplay.class);

    // game needs to run so that countdown ui can be created but how?

    @Test
    public void shouldDisplayCorrectTime() {
        if (countdown.counterLabel != null) {
            if (countdown.getRemainingTime() <= 0) {
                logger.info("testing whether label displays game over when it should.");
                assertTrue(countdown.counterLabel.textEquals("GAME OVER!"));
            } else {
                logger.info("running test to display correct remaining time.");
                assertEquals(countdown.counterLabel.getText().toString(), String.valueOf(countdown.getRemainingTime()));
            }
        }

    }

    @Test
    public void shouldPauseCountdown() {
        
    }

    @Test
    public void shouldResumeCountdown() {

    }

    @Test
    public void shouldIncreaseTime() {
        float initialTime = countdown.getRemainingTime();
        logger.info(String.valueOf(initialTime));
        countdown.increaseRemainingTime(300);
        float increasedTime = initialTime + 300;

        assertEquals(increasedTime, countdown.getRemainingTime());
    }
    @Test
    public void shouldGameOver() {
        // Assert that the game's assets have stopped
        countdown.stopCountDownAssets();
        assertEquals(true, countdown.paused);
    }
}
