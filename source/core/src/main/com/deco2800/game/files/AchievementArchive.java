package com.deco2800.game.files;

import com.deco2800.game.components.achievements.pojo.AchievementStatus;

import java.util.Map;

public class AchievementArchive {
    public Map<String, AchievementStatus> achievementStatusMap;

    public AchievementArchive(Map<String, AchievementStatus> achievementStatusMap) {
        this.achievementStatusMap = achievementStatusMap;
    }
}
