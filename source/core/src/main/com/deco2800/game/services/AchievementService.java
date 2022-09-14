package com.deco2800.game.services;

import com.badlogic.gdx.utils.Disposable;
import com.deco2800.game.components.achievements.pojo.AchievementStatus;
import com.deco2800.game.components.achievements.pojo.CollectionAchievementStatus;
import com.deco2800.game.entities.configs.achievementConfigs.AchievementConfigs;
import com.deco2800.game.entities.configs.achievementConfigs.AchievementPropertyConfig;
import com.deco2800.game.files.AchievementArchive;
import com.deco2800.game.files.FileLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AchievementService implements Disposable {

    private static final Logger logger = LoggerFactory.getLogger(AchievementService.class);

    private static final String ROOT_DIR = "DECO2800Game";
    private static final String ACHE_FILE = "achievement_archive.json";

    private Map<String, AchievementStatus> achievementStatusMap;

    public Map<String, AchievementStatus> getAchievementStatusMap() {
        return achievementStatusMap;
    }

    public AchievementService() {
        String path = ROOT_DIR + File.separator + ACHE_FILE;
        AchievementArchive achievementArchive = FileLoader.readClass(AchievementArchive.class, path, FileLoader.Location.EXTERNAL);

        /* If the local achievement archive does not exist,
            create initial state blank achievement information
         */
        if (achievementArchive == null) {
            achievementStatusMap = new LinkedHashMap<>();
            List<AchievementPropertyConfig> achievements = FileLoader.readClass(AchievementConfigs.class, "configs/achievements.json").achievements;
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
        } else {
            this.achievementStatusMap = achievementArchive.achievementStatusMap;
            logger.info("Successfully write achievement.json");
        }
    }

    public void wirteToLocal() {
        String path = ROOT_DIR + File.separator + ACHE_FILE;
        if (achievementStatusMap != null) {
            FileLoader.writeClass(new AchievementArchive(achievementStatusMap), path, FileLoader.Location.EXTERNAL);
        }
        logger.info("Successfully write achievement.json");
    }


    @Override
    public void dispose() {
        wirteToLocal();
    }
}
