package com.deco2800.game.areas;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.deco2800.game.GdxGame;
import com.deco2800.game.areas.terrain.TerrainFactory;
import com.deco2800.game.areas.terrain.TerrainFactory.TerrainType;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.factories.*;
import com.deco2800.game.utils.math.GridPoint2Utils;
import com.deco2800.game.utils.math.RandomUtils;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.components.gamearea.GameAreaDisplay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Forest area for the demo game with trees, a player, and some enemies.
 */
public class ForestGameArea extends GameArea {
    private static final Logger logger = LoggerFactory.getLogger(ForestGameArea.class);
    private static final int NUM_TREES = 7;
    private static final int NUM_GHOSTS = 2;
    private static final int NUM_BATTERIES = 3;
    private static final GridPoint2 PLAYER_SPAWN = new GridPoint2(10, 10);
    private static final float WALL_WIDTH = 0.1f;
    private static final String[] forestTextures = {
            "images/village/building1.png",
            "images/village/building2.png",
            "images/village/building3.png",
            "images/village/building4.png",
            "images/village/building5.png",
            "images/village/building6.png",
            "images/village/building7.png",
            "images/village/building8.png",
            "images/village/building9.png",
            "images/village/building10.png",
            "images/village/enclosure.png",
            "images/village/mine.png",
            "images/village/stela.png",
            "images/village/tree1.png",
            "images/village/tree2.png",
            "images/village/tree3.png",
            "images/village/well.png",
            "images/village/wood.png",

            "images/orpheus_front.png",
            "images/Ares_front.png",
            "images/mermaid.png",
            "images/blank.png",
            "images/box_boy.png",
            "images/tree.png",
            "images/ghost_1.png",
            "images/grass_1.png",
            "images/grass_2.png",
            "images/grass_3.png",
            "images/hex_grass_1.png",
            "images/hex_grass_2.png",
            "images/hex_grass_3.png",
            "images/iso_grass_1.png",
            "images/iso_grass_2.png",
            "images/iso_grass_3.png",
            "images/player_front.png",
            "images/up_0.png",
            "images/up_1.png",
            "images/down_0.png",
            "images/down_1.png",
            "images/left_0.png",
            "images/left_1.png",
            "images/right_0.png",
            "images/right_1.png",
            "images/inventory/time_item.png",
            "images/inventory/scales1.png",
            "images/inventory/confirm.png",
            "images/inventory/emtpyInventorySlot.png",
            "images/inventory/inventoryBG.png",
            "images/KEY.png",
            "images/knight.png",
            "images/Piranha.png",
            "images/robot.png",
            "images/slime.png",
            "images/bobo.png",
            "images/switch/Tools.png",
            "images/switch/Battery.png",
            "images/switch/Electric Switch Broken.png",
            "images/KEY.png",
            "images/coral/scales1.png",
            "images/coral/scales2.png",
            "images/coral/scales3.png",
            "images/coral/scales4.png",
            "images/coral/scales5.png",
    };
    private static final String[] forestTextureAtlases = {
            "images/terrain_iso_grass.atlas", "images/ghost.atlas", "images/ghostKing.atlas", "images/player.atlas", "images/orpheus.atlas",
            "images/Ares.atlas", "images/knight.atlas", "images/Piranha.atlas", "images/robot.atlas", "images/slime.atlas", "images/bobo.atlas"
    };
    private static final String[] forestSounds = {"sounds/Impact4.ogg"};
    private static final String backgroundMusic = "sounds/new.mp3";
    private static final String movementMusic = "sounds/Movement_sound.wav";
    private static final String clickMusic = "sounds/button.mp3";


    private static final String[] forestMusic = {backgroundMusic, movementMusic, clickMusic,"sounds/CloseClueWindow.wav","sounds/OpenClueWindow.wav","sounds/CloseEvictionMenu.wav","sounds/OpenEvictionMenu.wav"};

    private final TerrainFactory terrainFactory;

    private Entity player;

    public Entity key;

    private GdxGame game;

    public ForestGameArea(TerrainFactory terrainFactory, GdxGame game) {
        super();
        this.terrainFactory = terrainFactory;
        this.game = game;
    }

    /**
     * Create the game area, including terrain, static entities (trees), dynamic entities (player)
     */
    @Override
    public void create() {
        loadAssets();
        displayUI();
        spawnTerrain();
        loadLevel("village");
        player = spawnPlayer();
        spawnKnight();
        spawnSlime();
        spawnRobot();
        spawnPiranha();
        spawnBobo();
        spawnTimeConsumeableItem();
        spawnClueItem();
        spawnSwitchItems();
        playMusic();
        spawnCoralItems();
    }

    private void loadLevel(String name){
        switch (name){
            case "village":
                loadVillage();
                break;
            case "random":
                loadRandomLevel();
        }

    }

    private void loadRandomLevel(){
        spawnTrees();
    }

    private Entity loadMaterial(Integer type){
        Entity entity = null;
        switch (type){
            case 10:
                entity = ObstacleFactory.createTreeByNum(1,2.5f,0.5f,0.2f);
                break;
            case 11:
                entity = ObstacleFactory.createTreeByNum(2,2.5f,0.5f,0.2f);
                break;
            case 12:
                entity = ObstacleFactory.createTreeByNum(3,2.5f,0.5f,0.2f);
                break;

            case 20:
                entity = ObstacleFactory.createBuilding(1,1.5f,0.5f,0.2f);
                break;
            case 21:
                entity = ObstacleFactory.createBuilding(2,1.5f,0.5f,0.2f);
                break;
            case 22:
                entity = ObstacleFactory.createBuilding(3,1.5f,0.5f,0.2f);
                break;
            case 23:
                entity = ObstacleFactory.createBuilding(4,1.5f,0.5f,0.2f);
                break;
            case 24:
                entity = ObstacleFactory.createBuilding(5,1.5f,0.5f,0.2f);
                break;
            case 25:
                entity = ObstacleFactory.createBuilding(6,1.5f,0.5f,0.2f);
                break;
            case 26:
                entity = ObstacleFactory.createBuilding(7,1.5f,0.5f,0.2f);
                break;
            case 27:
                entity = ObstacleFactory.createBuilding(8,1.5f,0.5f,0.2f);
                break;
            case 28:
                entity = ObstacleFactory.createBuilding(9,1.5f,0.5f,0.2f);
                break;
            case 29:
                entity = ObstacleFactory.createBuilding(10,2.5f,0.5f,0.2f);
                break;

            case 40:
                entity = ObstacleFactory.createMine();
                break;

            case 50:
                entity = ObstacleFactory.createWell();
                break;

            case 60:
                entity = ObstacleFactory.createWood();
                break;

            case 70:
                entity = ObstacleFactory.createEnclosure();
                break;

            case 80:
                entity = ObstacleFactory.createStela();
                break;
        }
        return entity;
    }

    private void loadVillage(){
        List<Integer[]> materials = new ArrayList<>();
        //tree
        materials.add(new Integer[]{10,10,5});
        materials.add(new Integer[]{10,5,10});
        materials.add(new Integer[]{10,5,15});
        materials.add(new Integer[]{10,24,8});
        materials.add(new Integer[]{11,5,20});
        materials.add(new Integer[]{11,15,25});
        materials.add(new Integer[]{11,26,20});
        materials.add(new Integer[]{12,20,20});
        materials.add(new Integer[]{12,15,27});
        materials.add(new Integer[]{12,20,28});
        materials.add(new Integer[]{12,10,28});
        //Building
        materials.add(new Integer[]{29,15,15});
        materials.add(new Integer[]{20,20,5});
        materials.add(new Integer[]{21,23,5});
        materials.add(new Integer[]{22,20,18});
        materials.add(new Integer[]{23,5,25});
        materials.add(new Integer[]{24,9,21});
        materials.add(new Integer[]{25,10,10});

        materials.add(new Integer[]{70,25,15});
        materials.add(new Integer[]{70,22,15});
        materials.add(new Integer[]{70,28,15});

        materials.add(new Integer[]{40,8,16});
        materials.add(new Integer[]{40,15,20});
        materials.add(new Integer[]{40,11,11});

        materials.add(new Integer[]{80,18,11});
        materials.add(new Integer[]{80,23,22});

        materials.add(new Integer[]{60,13,22});
        materials.add(new Integer[]{60,14,22});
        materials.add(new Integer[]{60,15,22});
        materials.add(new Integer[]{60,22,8});

        GridPoint2 minPos = new GridPoint2(0, 0);
        GridPoint2 maxPos = terrain.getMapBounds(0).sub(2, 2);

        for(Integer[] material : materials){
            GridPoint2 randomPos = new GridPoint2(material[1],material[2]);
            Entity entity = loadMaterial(material[0]);
            spawnEntityAt(entity, randomPos, true, false);
        }
    }

    private void displayUI() {
        Entity ui = new Entity();
        ui.addComponent(new GameAreaDisplay("Box Forest"));
        spawnEntity(ui);
    }

    private void spawnTerrain() {
        // Background terrain
        terrain = terrainFactory.createTerrain(TerrainType.FOREST_DEMO);
        spawnEntity(new Entity().addComponent(terrain));

        // Terrain walls
        float tileSize = terrain.getTileSize();
        GridPoint2 tileBounds = terrain.getMapBounds(0);
        Vector2 worldBounds = new Vector2(tileBounds.x * tileSize, tileBounds.y * tileSize);

        // Left
        spawnEntityAt(
                ObstacleFactory.createWall(WALL_WIDTH, worldBounds.y), GridPoint2Utils.ZERO, false, false);
        // Right
        spawnEntityAt(
                ObstacleFactory.createWall(WALL_WIDTH, worldBounds.y),
                new GridPoint2(tileBounds.x, 0),
                false,
                false);
        // Top
        spawnEntityAt(
                ObstacleFactory.createWall(worldBounds.x, WALL_WIDTH),
                new GridPoint2(0, tileBounds.y),
                false,
                false);
        // Bottom
        spawnEntityAt(
                ObstacleFactory.createWall(worldBounds.x, WALL_WIDTH), GridPoint2Utils.ZERO, false, false);
    }

    private void spawnTrees() {
        GridPoint2 minPos = new GridPoint2(0, 0);
        GridPoint2 maxPos = terrain.getMapBounds(0).sub(2, 2);

        for (int i = 0; i < NUM_TREES; i++) {
            GridPoint2 randomPos = RandomUtils.random(minPos, maxPos);
            Entity tree = ObstacleFactory.createTree();
            spawnEntityAt(tree, randomPos, true, false);
        }
    }

    private Entity spawnPlayer() {
        Entity newPlayer = PlayerFactory.createPlayer(game);
        spawnEntityAt(newPlayer, PLAYER_SPAWN, true, true);
        return newPlayer;
    }

    public void spawnTimeConsumeableItem() {
//    Entity item = ConsumableItemFactory.createItem(player, "images/inventory/time_item.png");
        Entity item = ItemFactory.createItem(1);
        spawnEntityAt(item, new GridPoint2(5, 10), true, true);
    }

    private void spawnClueItem() {
        Entity item = ItemFactory.createItem(1);
        Entity item1 = ItemFactory.createItem(1);
        Entity item2 = ItemFactory.createItem(1);


        spawnEntityAt(item, new GridPoint2(5, 25), true, true);
        spawnEntityAt(item1, new GridPoint2(10, 24), true, true);
        spawnEntityAt(item2, new GridPoint2(15, 23), true, true);
    }

    private void spawnCoralItems() {
        GridPoint2 minPos = new GridPoint2(5, 5);
        GridPoint2 maxPos = terrain.getMapBounds(0).sub(15, 15);

        spawnEntityAt(ItemFactory.createItem(4), RandomUtils.random(minPos, maxPos), true, false);
        spawnEntityAt(ItemFactory.createItem(5), RandomUtils.random(minPos, maxPos), true, false);
        spawnEntityAt(ItemFactory.createItem(6), RandomUtils.random(minPos, maxPos), true, false);
        spawnEntityAt(ItemFactory.createItem(7), RandomUtils.random(minPos, maxPos), true, false);
        spawnEntityAt(ItemFactory.createItem(8), RandomUtils.random(minPos, maxPos), true, false);
    }

    private void spawnSwitchItems() {
        Entity switchItem = SwitchFactory.createSwitch();
        spawnEntityAt(switchItem, new GridPoint2(20, 10), true, true);

        Entity tool = SwitchFactory.createTool();
        spawnEntityAt(tool, new GridPoint2(25, 10), true, true);

        GridPoint2 minPos = new GridPoint2(5, 5);
        GridPoint2 maxPos = terrain.getMapBounds(0).sub(15, 15);

        for (int i = 0; i < NUM_BATTERIES; i++) {
            GridPoint2 randomPos = RandomUtils.random(minPos, maxPos);
//      Entity battery = SwitchFactory.createBattery();
            Entity battery = ItemFactory.createItem(3);
            spawnEntityAt(battery, randomPos, true, false);
        }
    }

    public void spawnKey(GdxGame game) {
        this.key = ItemFactory.createItem(9);
        key.type = "key";
        key.game = game;
        spawnEntityAt(key, new GridPoint2(6, 20), true, true);
    }

    private void spawnGhosts() {
        GridPoint2 minPos = new GridPoint2(0, 0);
        GridPoint2 maxPos = terrain.getMapBounds(0).sub(2, 2);

        for (int i = 0; i < NUM_GHOSTS; i++) {
            GridPoint2 randomPos = RandomUtils.random(minPos, maxPos);
            Entity ghost = NPCFactory.createGhost(player);
            spawnEntityAt(ghost, randomPos, true, true);
        }
    }

    private void spawnKnight() {
        GridPoint2 minPos = new GridPoint2(0, 0);
        GridPoint2 maxPos = terrain.getMapBounds(0).sub(2, 2);

        GridPoint2 randomPos = RandomUtils.random(minPos, maxPos);
        Entity knight = NPCFactory.createKnight(player);
        spawnEntityAt(knight, randomPos, true, true);
    }

    private void spawnSlime() {
        GridPoint2 minPos = new GridPoint2(0, 0);
        GridPoint2 maxPos = terrain.getMapBounds(0).sub(2, 2);

        GridPoint2 randomPos = RandomUtils.random(minPos, maxPos);
        Entity knight = NPCFactory.createSlime(player);
        spawnEntityAt(knight, randomPos, true, true);
    }

    private void spawnBobo() {
        GridPoint2 minPos = new GridPoint2(0, 0);
        GridPoint2 maxPos = terrain.getMapBounds(0).sub(2, 2);

        GridPoint2 randomPos = RandomUtils.random(minPos, maxPos);
        Entity knight = NPCFactory.createBobo(player);
        spawnEntityAt(knight, randomPos, true, true);
    }

    private void spawnPiranha() {
        GridPoint2 minPos = new GridPoint2(0, 0);
        GridPoint2 maxPos = terrain.getMapBounds(0).sub(2, 2);

        GridPoint2 randomPos = RandomUtils.random(minPos, maxPos);
        Entity knight = NPCFactory.createPiranha(player);
        spawnEntityAt(knight, randomPos, true, true);
    }

    private void spawnRobot() {
        GridPoint2 minPos = new GridPoint2(0, 0);
        GridPoint2 maxPos = terrain.getMapBounds(0).sub(2, 2);

        GridPoint2 randomPos = RandomUtils.random(minPos, maxPos);
        Entity knight = NPCFactory.createRobot(player);
        spawnEntityAt(knight, randomPos, true, true);
    }

    private void spawnGhostKing() {
        GridPoint2 minPos = new GridPoint2(0, 0);
        GridPoint2 maxPos = terrain.getMapBounds(0).sub(2, 2);

        GridPoint2 randomPos = RandomUtils.random(minPos, maxPos);
        Entity ghostKing = NPCFactory.createGhostKing(player);
        spawnEntityAt(ghostKing, randomPos, true, true);
    }

    private void playMusic() {
        Music music = ServiceLocator.getResourceService().getAsset(backgroundMusic, Music.class);
        music.setLooping(true);
        music.setVolume(0.3f);
        music.play();
    }

    private void loadAssets() {
        logger.debug("Loading assets");
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.loadTextures(forestTextures);
        resourceService.loadTextureAtlases(forestTextureAtlases);
        resourceService.loadSounds(forestSounds);
        resourceService.loadMusic(forestMusic);

        while (!resourceService.loadForMillis(10)) {
            // This could be upgraded to a loading screen
            //logger.info("Loading... {}%", resourceService.getProgress());
        }
    }

    private void unloadAssets() {
        logger.debug("Unloading assets");
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.unloadAssets(forestTextures);
        resourceService.unloadAssets(forestTextureAtlases);
        resourceService.unloadAssets(forestSounds);
        resourceService.unloadAssets(forestMusic);
    }

    @Override
    public void dispose() {
        super.dispose();
        ServiceLocator.getResourceService().getAsset(backgroundMusic, Music.class).stop();
        this.unloadAssets();
    }
}
