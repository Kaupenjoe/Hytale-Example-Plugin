package com.example.VHcustomenchants.command;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.GameMode;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.inventory.Inventory;
import com.hypixel.hytale.server.core.inventory.ItemStack;
import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.herolias.plugin.api.EnchantmentApi;
import org.herolias.plugin.api.EnchantmentApiProvider;

import javax.annotation.Nonnull;

public class ThrudsMightCommand extends AbstractPlayerCommand {

    private static final String THRUDS_MIGHT_ID = "vhcustomenchants:thruds_might";

    public ThrudsMightCommand() {
        super("thrud", "Applies Thrud's Might I to the item in your hand.");
        this.setPermissionGroup(GameMode.Adventure);
    }

    @Override
    protected void execute(@Nonnull CommandContext context,
                           @Nonnull Store<EntityStore> store,
                           @Nonnull Ref<EntityStore> ref,
                           @Nonnull PlayerRef playerRef,
                           @Nonnull World world) {

        EnchantmentApi enchantmentApi = EnchantmentApiProvider.get();
        if (enchantmentApi == null) {
            context.sendMessage(Message.raw("Simple Enchantments API is not available."));
            return;
        }

        Player player = store.getComponent(ref, Player.getComponentType());
        if (player == null) {
            context.sendMessage(Message.raw("Could not find player component."));
            return;
        }

        Inventory inventory = player.getInventory();
        if (inventory == null) {
            context.sendMessage(Message.raw("Could not find inventory."));
            return;
        }

        ItemStack heldItem = inventory.getItemInHand();
        if (heldItem == null || heldItem.isEmpty()) {
            context.sendMessage(Message.raw("Hold a melee weapon first."));
            return;
        }

        int level = 1;

        ItemStack enchanted = enchantmentApi.addEnchantment(heldItem, THRUDS_MIGHT_ID, level);

        if (inventory.usingToolsItem()) {
            byte slot = inventory.getActiveToolsSlot();
            ItemContainer tools = inventory.getTools();
            if (tools == null || slot < 0) {
                context.sendMessage(Message.raw("No active tools slot found."));
                return;
            }
            tools.setItemStackForSlot((short) slot, enchanted);
        } else {
            byte slot = inventory.getActiveHotbarSlot();
            ItemContainer hotbar = inventory.getHotbar();
            if (hotbar == null || slot < 0) {
                context.sendMessage(Message.raw("No active hotbar slot found."));
                return;
            }
            hotbar.setItemStackForSlot((short) slot, enchanted);
        }

        context.sendMessage(Message.raw("Applied Thrud's Might I to the item in your hand."));
    }
}