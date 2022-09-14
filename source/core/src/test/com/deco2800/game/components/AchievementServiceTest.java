package com.deco2800.game.components;

import com.badlogic.gdx.files.FileHandle;
import com.deco2800.game.extensions.GameExtension;
import com.deco2800.game.files.AchievementArchive;
import com.deco2800.game.files.FileLoader;
import com.deco2800.game.services.AchievementService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ExtendWith(GameExtension.class)
public class AchievementServiceTest {

    @Test
    void shouldWriteFile() {
        AchievementService achievementService = new AchievementService();
        achievementService.wirteToLocal();
        AchievementArchive achievementArchive = FileLoader.readClass(AchievementArchive.class, "DECO2800Game/achievement_archive.json", FileLoader.Location.EXTERNAL);
        assertNotEquals(achievementArchive, null);
    }
}
