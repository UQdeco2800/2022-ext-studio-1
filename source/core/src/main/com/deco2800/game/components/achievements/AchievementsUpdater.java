package com.deco2800.game.components.achievements;

import com.deco2800.game.components.Component;
import com.deco2800.game.entities.configs.achievementConfigs.AchievementPropertyConfig;
import com.deco2800.game.entities.factories.AchievementFactory;

import java.util.List;

public class AchievementsUpdater extends Component {
    private static final List<AchievementPropertyConfig> achievements = AchievementFactory.getAchievements();

    public AchievementsUpdater() {

    }
}
