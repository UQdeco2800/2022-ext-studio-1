package com.deco2800.game.components.player;

import com.badlogic.gdx.Gdx;
import com.deco2800.game.entities.configs.PlayerProfileConfig;
import com.deco2800.game.entities.configs.PlayerProfileProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import com.deco2800.game.extensions.GameExtension;
import static org.junit.jupiter.api.Assertions.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import com.badlogic.gdx.utils.Json;

@ExtendWith(GameExtension.class)
public class PlayerProfileDisplayTest {

    private static final Logger logger = LoggerFactory.getLogger(PlayerProfileDisplay.class);

    private PlayerProfileDisplay playerProfile = new PlayerProfileDisplay();

    int num1 = 1;
    int num2 = 5;
    int num3 = 8;

//    Json json = new Json();
//    PlayerProfileConfig playerProfileConfig = json.fromJson(PlayerProfileConfig.class, Gdx.files.internal("configs/playerStatsInfo.json"));
//    HashMap<String, Integer> property = new HashMap<String, Integer>();
//    HashMap<String, Integer> property2 = new HashMap<String, Integer>();
//    HashMap<String, Integer> property3 = new HashMap<String, Integer>();
//    HashMap<String, Integer> property4 = new HashMap<String, Integer>();


    @Test
    public void shouldCalculateCorrectAvg() {
        int sum = num1 + num2 + num3;
        int numElements = 3;
        assertEquals((float) sum / numElements, playerProfile.calculateAvg(sum, numElements));
    }

    @Test
    public void shouldGetPlayerProfile() {
//        property.put("timeRemaining", 7000);
//        property.put("result", 1);
//        property.put("attempt", 1);
//
//        property2.put("timeRemaining", 6500);
//        property2.put("result", 1);
//        property2.put("attempt", 2);
//
//        property3.put("timeRemaining", 6400);
//        property3.put("result", 1);
//        property3.put("attempt", 2);
//
//        property4.put("timeRemaining", 7000);
//        property4.put("result", 0);
//        property4.put("attempt", 2);
//
//        playerProfileProperties.add(property);
//        playerProfileProperties.add(property2);
//        playerProfileProperties.add(property3);
//        playerProfileProperties.add(property4);
//
//        assertEquals(playerProfileProperties, String.valueOf(playerProfile.getPlayerProfile()));

//        assertEquals(playerProfileConfig.playerStats, playerProfile.getPlayerProfile());

    }
}
