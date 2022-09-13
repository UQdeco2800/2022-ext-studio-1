package com.deco2800.game.components.countdown;

import com.deco2800.game.extensions.GameExtension;
import com.deco2800.game.components.countDownClock.countdownDisplay;
import org.junit.Before;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;



@ExtendWith(GameExtension.class)
public class CountdownComponentTest {

    private countdownDisplay countdown;

    @Before
    public void testSetUp() {
        countdown = mock(countdownDisplay.class);
    }

    @Test
    public void shouldDisplayCorrectTime() {
        if (countdown.getRemainingTime() <= 0) {
            assertTrue(countdown.counterLabel.textEquals("GAME OVER!"));
        } else {
            assertEquals(countdown.counterLabel.getText(), String.valueOf(countdown.getRemainingTime()));
        }
    }
//
//    @Test
//    public void shouldPauseCountdown() {
//
//    }
//
//    @Test
//    public void shouldResumeCountdown() {
//
//    }
//
//    @Test
//    public void shouldIncreaseTime() {
//
//    }
//
//    @Test
//    public void ctShouldNotRestart() {
//
//    }
}
