package com.deco2800.game.entities.configs.achievementConfigs;

/**
 * Defines a set of properties for an achievement to be loaded by the Achievement Factory.
 */
public class AchievementPropertyConfig {

    public static final String TYPE_COLLECTION = "collection";
    public static final String TYPE_PLOT = "plot";

    public String type = "";
    public String name = "";
    public String path = "";
    public AchievementConditionConfig condition = new AchievementConditionConfig();
    public boolean obtained = false;

}
