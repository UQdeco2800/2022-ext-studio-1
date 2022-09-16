package com.deco2800.game.components;

import com.deco2800.game.components.achievements.AchievementStatsComponent;
import com.deco2800.game.entities.factories.ConsumableItemFactory;
import com.deco2800.game.extensions.GameExtension;
import com.deco2800.game.services.AchievementService;
import com.deco2800.game.services.ServiceLocator;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(GameExtension.class)
class AchievementStatsComponentTest {

    @Test
    void shouldLoadInitializationData() {
        AchievementStatsComponent achievementStats = new AchievementStatsComponent();
        assertEquals(6, achievementStats.getNumOfAchievements());
    }

}
