package com.deco2800.game.components.npc;

import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class NpcInteractionTest {

    private NpcInteraction npcInteraction;
    @Before
    public void setUp() throws Exception {
        npcInteraction = new NpcInteraction(0);
    }

    @Test
    public void updateDistance() {
        assertEquals(0, npcInteraction.getDistance(), 0.01d);
        npcInteraction.updateDistance(1);
        assertEquals(1, npcInteraction.getDistance(), 0.01d);
        npcInteraction.updateDistance(2);
        assertEquals(2, npcInteraction.getDistance(), 0.01d);
    }

    @Test
    public void getDistance() {
        assertEquals(0, npcInteraction.getDistance(), 0.01d);
        npcInteraction.updateDistance(1);
        assertEquals(1, npcInteraction.getDistance(), 0.01d);
        npcInteraction.updateDistance(2);
        assertEquals(2, npcInteraction.getDistance(), 0.01d);
    }

    @Test(expected = RuntimeException.class)
    public void meetNpc() {
        npcInteraction.meetNpc(0, 1);
    }
}