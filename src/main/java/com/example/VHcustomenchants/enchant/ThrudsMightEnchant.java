// Author: Reflexible
// V 0.0.1
// 3/7/2026
// INTENDED FOR VALLALHA HYTALE ONLY
// THIS FILE IS NOT FOR PUBLIC DISSEMINATION



// Imports
package com.example.VHcustomenchants.enchant;
import com.example.VHcustomenchants.VHcustomEnchants;

import com.hypixel.hytale.logger.HytaleLogger;
import org.herolias.plugin.api.EnchantmentApi;
import org.herolias.plugin.api.EnchantmentApiProvider;
import org.herolias.plugin.enchantment.EnchantmentType;
import org.herolias.plugin.enchantment.ItemCategory;



public class ThrudsMightEnchant {

    // Responsible for loading in the main plugin
    private static final HytaleLogger LOGGER = HytaleLogger.get("ThrudsMightEnchant");

    private final VHcustomEnchants plugin;
    private EnchantmentType enchantmentType;

    public ThrudsMightEnchant(VHcustomEnchants plugin) {
        this.plugin = plugin;
    }

    // Create the register method
    // Use by main file to initialize the enchant
    public void register() {

        try {
            // Try to get the API from SimpleEnchantments.jar
            EnchantmentApi enchantmentApi = EnchantmentApiProvider.get();

            // Register the acutal enchantement
            enchantmentType = enchantmentApi.registerEnchantment("vhcustomenchants:thruds_might", "Thrud's Might")
                    .description("A surge of divine strength grants the chance to strike with double the force.")
                    .maxLevel(3)
                    .multiplierPerLevel(1.0, "Double damage chance per level")
                    .bonusDescription("Attacks have a {amount}% chance to deal double damage")
                    .modDisplayName("VH Custom Enchants")
                    .walkthrough("While attacking with a melee weapon, there is a chance to deal double damage. "
                            + "Each level increases the chance by {amount}%.")
                    .appliesTo(ItemCategory.MELEE_WEAPON)
                    .build();


            // Register the logger
            LOGGER.atInfo().log("Sucessfully applied %s!", enchantmentType.getDisplayName());

        } catch (Exception e) {

            LOGGER.atSevere().log("Failed to register Thrud's Might: %s", e.getMessage());
            e.printStackTrace();
        }





    }


}
