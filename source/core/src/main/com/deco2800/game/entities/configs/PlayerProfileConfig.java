package com.deco2800.game.entities.configs;

import java.util.ArrayList;
import java.util.List;

/**
 * This class defines a list of player stats representing each game user plays, stored in
 * the player profile configs (configs/playerStatsInfo.json)
 * The file is loaded in the component PlayerProfileDisplay (components/player/PlayerProfileDisplay.java).
 */
public class PlayerProfileConfig {
    public List<PlayerProfileProperties> playerStats = new ArrayList<>();
}
