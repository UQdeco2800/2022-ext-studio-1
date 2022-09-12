package com.deco2800.game.entities.factories;

/**
 * Factory to create item entities with predefined components.
 *
 * <p>Each item entity type should have a creation method that returns a corresponding entity.
 * Predefined entity properties can be loaded from configs stored as json files which are defined in
 * "ItemConfigs".
 *
*/
public class ClueItemFactory extends ItemFactory {

    ClueItemFactory() {
        super();
    }
    private int guiltyRating = 30;

    public int getGuiltyRating() {
        return guiltyRating;
    }
}

