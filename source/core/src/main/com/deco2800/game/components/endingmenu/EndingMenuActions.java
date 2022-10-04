package com.deco2800.game.components.endingmenu;

import com.deco2800.game.GdxGame;
import com.deco2800.game.components.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class listens to events relevant to the Main Menu Screen and does something when one of the
 * events is triggered.
 */
public class EndingMenuActions extends Component {
    private static final Logger logger = LoggerFactory.getLogger(EndingMenuActions.class);
    private GdxGame game;

    public EndingMenuActions(GdxGame game) {
        this.game = game;
    }

    @Override
    public void create() {
        entity.getEvents().addListener("start", this::onStart);
        entity.getEvents().addListener("menu", this::onExit);
    }

    /**
     * Swaps to the Main Game screen.
     */
    private void onStart() {
        logger.info("Start game");
        game.setScreen(GdxGame.ScreenType.MAIN_GAME);
    }

    /**
     * Exits the game.
     */
    private void onExit() {
        logger.info("Exit game");
//        game.exit();
        game.setScreen(GdxGame.ScreenType.MAIN_MENU);
    }

    public void onEnding() {
        logger.info("Launching ending screen");
        game.setScreen(GdxGame.ScreenType.ENDING);
    }
}
