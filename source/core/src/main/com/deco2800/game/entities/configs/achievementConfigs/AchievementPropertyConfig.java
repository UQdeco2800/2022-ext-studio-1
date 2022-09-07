package com.deco2800.game.entities.configs.achievementConfigs;

/**
 * Defines a set of properties for an achievement to be loaded by the Achievement Factory.
 */
public class AchievementPropertyConfig {
    public String name = "";
    public String path = "";
    public AchievementConditionConfig condition = new AchievementConditionConfig();
    public boolean obtained = false;
}
