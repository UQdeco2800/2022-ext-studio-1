package com.deco2800.game.entities.factories;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SwitchFactoryTest {

    @Test
    void createSwitch() {
        // test create switch
        SwitchFactory switchFactory = new SwitchFactory();
        assertNotNull(switchFactory.createSwitch());
    }

    @Test
    void createPodium() {
        // test create podium
        SwitchFactory switchFactory = new SwitchFactory();
        assertNotNull(switchFactory.createPodium(null));
    }

    @Test
    void createTool() {
        // test create tool
        SwitchFactory switchFactory = new SwitchFactory();
        assertNotNull(switchFactory.createTool(null));
    }
}