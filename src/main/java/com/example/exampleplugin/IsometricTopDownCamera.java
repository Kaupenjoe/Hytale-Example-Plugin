// src/main/java/com/example/exampleplugin/IsometricTopDownCamera.java
package com.example.exampleplugin;

import com.hypixel.hytale.protocol.ClientCameraView;
import com.hypixel.hytale.protocol.Direction;
import com.hypixel.hytale.protocol.MouseInputType;
import com.hypixel.hytale.protocol.MovementForceRotationType;
import com.hypixel.hytale.protocol.PositionDistanceOffsetType;
import com.hypixel.hytale.protocol.RotationType;
import com.hypixel.hytale.protocol.ServerCameraSettings;
import com.hypixel.hytale.protocol.Vector3f;
import com.hypixel.hytale.protocol.packets.camera.SetServerCamera;
import com.hypixel.hytale.server.core.universe.PlayerRef;

public final class IsometricTopDownCamera {
    private IsometricTopDownCamera() {}

    public static void apply(PlayerRef playerRef) {
        ServerCameraSettings settings = new ServerCameraSettings();

        // Smoothing
        settings.positionLerpSpeed = 0.2f;
        settings.rotationLerpSpeed = 0.2f;

        // Zoom / distance
        settings.distance = 25.0f;

        // Cursor + mode
        settings.displayCursor = true;
        settings.isFirstPerson = false;

        // Avoid clipping through walls (use DistanceOffset if you prefer)
        settings.positionDistanceOffsetType = PositionDistanceOffsetType.DistanceOffsetRaycast;

        // === Isometric camera ===
        // Direction(yaw, pitch, roll) in radians
        // yaw = -45°, pitch ≈ -54.7356° (classic isometric), roll = 0
        final float isoYaw = -0.7853981634f;
        final float isoPitch = -0.9553166181f;

        settings.rotationType = RotationType.Custom;
        settings.rotation = new Direction(isoYaw, isoPitch, 0.0f);

        // === Make WASD match the camera yaw ===
        settings.movementForceRotationType = MovementForceRotationType.Custom;
        settings.movementForceRotation = new Direction(isoYaw, 0.0f, 0.0f);

        // Mouse projects to ground plane
        settings.mouseInputType = MouseInputType.LookAtPlane;
        settings.planeNormal = new Vector3f(0.0f, 1.0f, 0.0f);

        playerRef.getPacketHandler().writeNoCache(
                new SetServerCamera(ClientCameraView.Custom, true, settings)
        );
    }

    public static void reset(PlayerRef playerRef) {
        playerRef.getPacketHandler().writeNoCache(
                new SetServerCamera(ClientCameraView.Custom, false, null)
        );
    }
}
