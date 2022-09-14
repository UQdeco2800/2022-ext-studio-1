package com.deco2800.game.components.player;

import com.deco2800.game.components.Component;

public class ClueItemComponent extends Component {

    private int guiltyRating;

    /**
     * Create a component which is used to increase the guilt level
     * @param guiltyRating The physics layer of the target's collider.
     */
    public ClueItemComponent(int guiltyRating) {
        this.guiltyRating = guiltyRating;
    }

    @Override
    public void create() {
    }

    public int increaseGuilt()
    {
        return this.guiltyRating;
    }
}
