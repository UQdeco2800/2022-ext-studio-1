package com.deco2800.game.components.achievements;

import com.deco2800.game.components.Component;
import com.deco2800.game.components.achievements.pojo.AchievementStatus;
import com.deco2800.game.components.achievements.pojo.CollectionAchievementStatus;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.configs.achievementConfigs.AchievementConfigs;
import com.deco2800.game.entities.configs.achievementConfigs.AchievementPropertyConfig;
import com.deco2800.game.files.FileLoader;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class AchievementStatsComponent extends Component {

    private static final AchievementConfigs ACHVConfig =
            FileLoader.readClass(AchievementConfigs.class, "configs/achievements.json");

    /**
     * key: the name of the achievement
     * value: AchievementStatus object
     */
    private Map<String, AchievementStatus> achievementStatusMap;

    private int numOfAchievements;

    public int getNumOfAchievements() {
        return numOfAchievements;
    }

    public AchievementStatsComponent(Map<String, AchievementStatus> achievementStatusMap) {
        this.achievementStatusMap = achievementStatusMap;
    }

    public AchievementStatsComponent() {
        achievementStatusMap = new LinkedHashMap<>();
        List<AchievementPropertyConfig> achievements = ACHVConfig.achievements;
        this.numOfAchievements = achievements.size();
        achievements.stream().forEach(config -> {
            String name = config.name;
            if (config.type.equals(AchievementPropertyConfig.TYPE_COLLECTION)) {
                CollectionAchievementStatus status = new CollectionAchievementStatus(
                        name
                        , false
                        , config.condition.item);
                achievementStatusMap.put(name, status);
            }
            if (config.type.equals(AchievementPropertyConfig.TYPE_PLOT)) {
                // TODO Add logic related to plot achievements
            }
        });
    }

    @Override
    public void create() {

    }


    public int numOfAchievementCompleted() {
        return achievementStatusMap.values().stream().filter(AchievementStatus::isObtained).toList().size();
    }

    public void updateConsumableCollectionAchievement(Entity entity) {
        updateAlways();
        updateHourglassRelated();
    }

    public void updateClueCollectionAchievement(Entity entity) {
        updateAlways();
        // TODO Currently, only one clue item is added to the game and different clue items are indistinguishable from the inventory team's code
    }

    /**
     * Update achievements related to the timing hourglass
     */
    private void updateHourglassRelated() {
        achievementStatusMap.keySet().forEach(s -> {
            if (s.contains("Time")) {
                AchievementStatus achievementStatus = achievementStatusMap.get(s);
                if (achievementStatus instanceof CollectionAchievementStatus) {
                    ((CollectionAchievementStatus) achievementStatus).increaseCollectedNum();
                }
            }
        });
    }

    /**
     * Update the achievements related to the total number of collected items
     */
    private void updateAlways() {
        achievementStatusMap.keySet().forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                if ("God's Pocket".equals(s)
                        || "NEREUS!".equals(s)
                        || "Treasurer".equals(s)) {
                    AchievementStatus achievementStatus = achievementStatusMap.get(s);
                    if (achievementStatus instanceof CollectionAchievementStatus) {
                        ((CollectionAchievementStatus) achievementStatusMap.get(s)).increaseCollectedNum();
                    }
                }
            }
        });
    }





}
