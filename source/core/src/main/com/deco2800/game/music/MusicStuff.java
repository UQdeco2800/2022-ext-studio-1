package com.deco2800.game.music;

import com.badlogic.gdx.audio.Music;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.services.ResourceService;

public class MusicStuff {
    public static void playMusic(String path, boolean loop) {
        Music music = ServiceLocator.getResourceService().getAsset(path, Music.class);
        music.setLooping(loop);
        music.setVolume(0.3f);
        music.play();
    }
}
