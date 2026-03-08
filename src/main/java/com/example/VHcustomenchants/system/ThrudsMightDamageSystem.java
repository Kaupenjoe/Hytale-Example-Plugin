package com.example.VHcustomenchants.system;

import com.hypixel.hytale.component.Archetype;
import com.hypixel.hytale.component.ArchetypeChunk;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.SystemGroup;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.inventory.Inventory;
import com.hypixel.hytale.server.core.inventory.ItemStack;
import com.hypixel.hytale.server.core.modules.entity.damage.Damage;
import com.hypixel.hytale.server.core.modules.entity.damage.DamageEventSystem;
import com.hypixel.hytale.server.core.modules.entity.damage.DamageModule;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.herolias.plugin.api.EnchantmentApi;
import org.herolias.plugin.api.EnchantmentApiProvider;
import org.herolias.plugin.enchantment.EnchantmentEventHelper;
import org.herolias.plugin.enchantment.EnchantmentType;

import javax.annotation.Nonnull;
import java.util.concurrent.ThreadLocalRandom;

/**
 * ECS system that applies the Thrud's Might enchantment to outgoing damage.
 */
public class ThrudsMightDamageSystem extends DamageEventSystem {

    private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
    private static final String THRUDS_MIGHT_ID = "vhcustomenchants:thruds_might";

    public ThrudsMightDamageSystem() {
        LOGGER.atInfo().log("ThrudsMightSystem initialized");
    }

    @Override
    @Nonnull
    public SystemGroup<EntityStore> getGroup() {
        return DamageModule.get().getFilterDamageGroup();
    }

    @Override
    @Nonnull
    public Query<EntityStore> getQuery() {
        return Archetype.empty();
    }

    @Override
    public void handle(int index,
                       @Nonnull ArchetypeChunk<EntityStore> archetypeChunk,
                       @Nonnull Store<EntityStore> store,
                       @Nonnull CommandBuffer<EntityStore> commandBuffer,
                       @Nonnull Damage damage) {

        if (damage.isCancelled()) {
            return;
        }

        EnchantmentApi enchantmentApi = EnchantmentApiProvider.get();
        if (enchantmentApi == null) {
            return;
        }

        Damage.Source source = damage.getSource();
        if (!(source instanceof Damage.EntitySource entitySource)) {
            return;
        }

        Ref<EntityStore> attackerRef = entitySource.getRef();
        if (attackerRef == null || !attackerRef.isValid()) {
            return;
        }

        ItemStack weapon = getWeaponInHand(store, attackerRef);
        if (weapon == null || weapon.isEmpty()) {
            return;
        }

        int level = enchantmentApi.getEnchantmentLevel(weapon, THRUDS_MIGHT_ID);
        if (level <= 0) {
            return;
        }

        EnchantmentType type = enchantmentApi.getRegisteredEnchantment(THRUDS_MIGHT_ID);
        if (type == null) {
            return;
        }

        double chance = type.getScaledMultiplier(level);
        if (ThreadLocalRandom.current().nextDouble() >= chance) {
            return;
        }

        damage.setAmount(damage.getAmount() * 2.0f);

        PlayerRef playerRef = store.getComponent(attackerRef, PlayerRef.getComponentType());
        EnchantmentEventHelper.fireActivated(playerRef, weapon, type, level);
    }

    private ItemStack getWeaponInHand(@Nonnull Store<EntityStore> store,
                                      @Nonnull Ref<EntityStore> attackerRef) {

        Player player = store.getComponent(attackerRef, Player.getComponentType());
        if (player == null) {
            return null;
        }

        Inventory inventory = player.getInventory();
        if (inventory == null) {
            return null;
        }

        return inventory.getItemInHand();
    }
}