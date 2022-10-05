package com.deco2800.game.components.npcInteraction;

import com.deco2800.game.entities.factories.SwitchFactory;
import com.deco2800.game.extensions.GameExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(GameExtension.class)
public class NpcInteractionTest {

    @Test
    void shouldSwitchStatusTestCanChange() {
        SwitchFactory.isWorking = true;
        assertEquals(true, SwitchFactory.isWorking);
    }
}
