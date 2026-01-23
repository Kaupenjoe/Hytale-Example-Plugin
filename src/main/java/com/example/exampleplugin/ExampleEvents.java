// src/main/java/com/example/exampleplugin/ExampleEvents.java
package com.example.exampleplugin;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

public final class ExampleEvents {
    private ExampleEvents() {}

    public static void onPlayerReady(PlayerReadyEvent event) {
        // In your build, this is Ref<EntityStore>, not PlayerRef
        Ref<EntityStore> entityRef = event.getPlayerRef();
        PlayerRef playerRef = resolvePlayerRef(entityRef);

        if (playerRef == null || playerRef.getPacketHandler() == null) return;

        // Apply isometric top-down camera
        IsometricTopDownCamera.apply(playerRef);
    }

    private static PlayerRef resolvePlayerRef(Ref<EntityStore> entityRef) {
        if (entityRef == null) return null;

        for (PlayerRef pr : Universe.get().getPlayers()) {
            Ref<EntityStore> prRef = pr.getReference();
            if (entityRef.equals(prRef)) {
                return pr;
            }
        }
        return null;
    }
}
