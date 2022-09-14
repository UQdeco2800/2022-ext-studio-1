package com.deco2800.game.components.achievements.pojo;

/**
 * Each achievement has a corresponding achievement status,
 * which records various information about the achievement
 */
public abstract class AchievementStatus {

    /**
     * The name of the achievement
     */
    protected String name;
    /**
     * Whether the achievement has been completed
     */
    protected boolean obtained;

    public AchievementStatus(String name, boolean obtained) {
        this.name = name;
        this.obtained = obtained;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isObtained() {
        return obtained;
    }

    public void setObtained(boolean obtained) {
        this.obtained = obtained;
    }
}
