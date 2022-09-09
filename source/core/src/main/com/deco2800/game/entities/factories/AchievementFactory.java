package com.deco2800.game.entities.factories;

import com.badlogic.gdx.Gdx;
import com.deco2800.game.components.achievements.AchievementsUpdater;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.configs.achievementConfigs.AchievementConfigs;
import com.deco2800.game.entities.configs.achievementConfigs.AchievementPropertyConfig;
import com.deco2800.game.files.FileLoader;

import java.util.List;

/**
 * Factory to create achievement entities with predefined conditions.
 *
 * <p>Each achievement entity type should have a creation method that returns a corresponding entity.
 * Predefined achievement entity conditions can be loaded from configs stored as json files which are
 * defined in "AchievementConfigs".
 */
public class AchievementFactory {
    private static final AchievementConfigs configs =
            FileLoader.readClass(AchievementConfigs.class, "configs/achievements.json");

    /**
     * Creates an achievement entity.
     *
     * @return achievement entity
     */
    public static Entity createAchievement() {
        return new Entity()
                .addComponent(new AchievementsUpdater());
    }

    /**
     * Returns a list of achievement properties stored in achievement config files.
     *
     * @return a list of achievement properties
     */
    public static List<AchievementPropertyConfig> getAchievements() {
        return configs.achievements;
    }

    public static String[] getTextures() {
        String[] textures = new String[configs.achievements.size()];
        int i = 0;
        for (AchievementPropertyConfig achievement : configs.achievements) {
            textures[i++] = achievement.path;
        }
        return textures;
    }
}
