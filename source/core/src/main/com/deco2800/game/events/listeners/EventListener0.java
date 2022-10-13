package com.deco2800.game.events.listeners;

import java.io.IOException;

/**
 * An event listener with 0 arguments
 */
@FunctionalInterface
public interface EventListener0 extends EventListener {
  void handle() throws IOException;
}