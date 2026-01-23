package com.example.exampleplugin;

import com.hypixel.hytale.server.core.command.system.Commands;
import com.hypixel.hytale.server.core.event.EventHandler;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.plugin.ServerPlugin;

public class ExamplePlugin extends ServerPlugin {
    @Override
    public void onEnable() {
        getLogger().info("ExamplePlugin enabled!");

        // Register the event handler
        EventHandler.register(PlayerReadyEvent.class, ExampleEvents::onPlayerReady);

        // Register the example command
        Commands.register(new ExampleCommand(getName(), getVersion()));
    }

    @Override
    public void onDisable() {
        getLogger().info("ExamplePlugin disabled!");
    }
}
