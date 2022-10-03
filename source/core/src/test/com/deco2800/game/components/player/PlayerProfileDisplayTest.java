package com.deco2800.game.components.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.deco2800.game.entities.configs.PlayerProfileConfig;
import com.deco2800.game.entities.configs.PlayerProfileProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import com.deco2800.game.extensions.GameExtension;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.print.attribute.HashAttributeSet;
import java.util.ArrayList;
import java.util.HashMap;

@ExtendWith(GameExtension.class)
public class PlayerProfileDisplayTest {

    private static final Logger logger = LoggerFactory.getLogger(PlayerProfileDisplay.class);

    private PlayerProfileDisplay playerProfile = new PlayerProfileDisplay();

    int num1 = 1;
    int num2 = 5;
    int num3 = 8;

    ArrayList<PlayerProfileProperties> playerProfileProperties = new ArrayList<>();

    @Test
    public void shouldCalculateCorrectAvg() {
        int sum = num1 + num2 + num3;
        int numElements = 3;
        assertEquals((float) sum / numElements, playerProfile.calculateAvg(sum, numElements));
    }

    @Test
    public void shouldGetPlayerProfile() {


    }
}
