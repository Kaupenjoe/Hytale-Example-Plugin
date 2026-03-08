// Author: Reflexible
// V 0.0.1
// 3/7/2026
// INTENDED FOR VALLALHA HYTALE ONLY
// THIS FILE IS NOT FOR PUBLIC DISSEMINATION

// All imports defined
package com.example.VHcustomenchants;

import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.example.VHcustomenchants.enchant.ThrudsMightEnchant;
import org.herolias.plugin.SimpleEnchanting;
import org.herolias.plugin.command.EnchantCommand;
import com.example.VHcustomenchants.system.ThrudsMightDamageSystem;
import com.example.VHcustomenchants.command.ThrudsMightCommand;
import com.example.VHcustomenchants.system.ThrudsMightDamageSystem;
import javax.annotation.Nonnull;

public class VHcustomEnchants extends JavaPlugin {

     private static final HytaleLogger LOGGER = HytaleLogger.get("VHcustomEnchants");

     // Intialize the enchants class
     // Limited to scope of this file
    private ThrudsMightEnchant thrudsMightEnchant;

     // Setup the loader
     // Displays that it is loading the plugin to the user
    public VHcustomEnchants(JavaPluginInit init) {
            super(init);
            LOGGER.atInfo().log("Loading %s", getName());
    }

    @Override
     protected void setup() {
        LOGGER.atInfo().log("Setting up custom enchants for", getName());


        // Intializse new instance of the enhchant class
        // This file is reponsible for actually loading in the enchantment class
        // Registers it to the API
        thrudsMightEnchant = new ThrudsMightEnchant(this);
        thrudsMightEnchant.register();

        this.getEntityStoreRegistry().registerSystem(new ThrudsMightDamageSystem());

        LOGGER.atInfo().log("ALL ENCHANTS ARE OPERATIONAL FOR", getName());
    }

    public ThrudsMightEnchant getThrudsMightEnchant() {
        return thrudsMightEnchant;
    }

 }