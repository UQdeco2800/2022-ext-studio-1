package com.deco2800.game.components.endingmenu;

import com.badlogic.gdx.audio.Music;
import com.deco2800.game.GdxGame;
import com.deco2800.game.components.Component;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;
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

    private static final String failMusic = "sounds/lose.mp3";

    private static final String successMusic = "sounds/win.mp3";

    private static final String[] endingMusic = {successMusic, failMusic};

    @Override
    public void create() {
        loadAssets();
        playMusic();
        entity.getEvents().addListener("start", this::onStart);
        entity.getEvents().addListener("menu", this::onExit);
    }

    private void playMusic() {
        Music music;
        if (EndingMenuDisplay.isJudge()){
            music = ServiceLocator.getResourceService().getAsset(successMusic, Music.class);
        } else {
            music = ServiceLocator.getResourceService().getAsset(failMusic, Music.class);
        }
        music.setLooping(false);
        music.setVolume(0.3f);
        music.play();
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

    private void loadAssets() {
        logger.debug("Loading assets");
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.loadMusic(endingMusic);

        while (!resourceService.loadForMillis(10)) {
            // This could be upgraded to a loading screen
            //logger.info("Loading... {}%", resourceService.getProgress());
        }
    }

    private void unloadAssets() {
        logger.debug("Unloading assets");
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.unloadAssets(endingMusic);
    }

    @Override
    public void dispose() {
        super.dispose();
        ServiceLocator.getResourceService().getAsset(failMusic, Music.class).stop();
        this.unloadAssets();
    }
}
