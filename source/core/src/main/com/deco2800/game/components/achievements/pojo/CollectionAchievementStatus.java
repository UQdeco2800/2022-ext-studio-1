package com.deco2800.game.components.achievements.pojo;

/**
 * Collection achievement status
 */
public class CollectionAchievementStatus extends AchievementStatus {

    /**
     * The target number of collections for this collection achievement
     */
    private int targetNum;

    /**
     * The number of corresponding items that have been collected
     */
    private int collectedNum;

    public CollectionAchievementStatus(String name, boolean obtained, int targetNum) {
        super(name, obtained);
        this.targetNum = targetNum;
    }

    public int getTargetNum() {
        return targetNum;
    }

    public int getCollectedNum() {
        return collectedNum;
    }

    public void setCollectedNum(int collectedNum) {
        this.collectedNum = collectedNum;
        updateIsObtained();
    }

    public void increaseCollectedNum() {
        collectedNum++;
        updateIsObtained();
    }

    public void setTargetNum(int targetNum) {
        this.targetNum = targetNum;
    }

    public void updateIsObtained() {
        if (collectedNum >= targetNum) {
            setObtained(true);
        } else {
            setObtained(false);
        }
    }
}
