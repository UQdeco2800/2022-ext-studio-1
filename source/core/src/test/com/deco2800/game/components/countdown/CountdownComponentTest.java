package com.deco2800.game.components.countdown;

import com.deco2800.game.extensions.GameExtension;
import com.deco2800.game.components.countDownClock.countdownDisplay;
import org.junit.Before;
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
    // game needs to run so that countdown ui can be created but how?

    @Test
    public void shouldDisplayCorrectTime() {
        logger.info("shouldDisplayCorrectTime is called.");
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

    }

    @Test
    public void cdShouldNotRestart() {

    }
}
