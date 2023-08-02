package com.example.addon;

import com.example.addon.commands.CommandExample;
import com.example.addon.hud.HudExample;
import com.example.addon.modules.Fly;
import com.example.addon.modules.ItemFrameDupe;
import com.example.addon.modules.Spam;
import com.example.addon.modules.SpamPlus;
import com.mojang.logging.LogUtils;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.commands.Commands;
import meteordevelopment.meteorclient.systems.hud.Hud;
import meteordevelopment.meteorclient.systems.hud.HudGroup;
import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.systems.modules.Modules;
import org.slf4j.Logger;

public class Addon extends MeteorAddon {
    public static final Logger LOG = LogUtils.getLogger();
    public static final Category CATEGORY = new Category("bakery");
    public static final HudGroup HUD_GROUP = new HudGroup("bakery");

    @Override
    public void onInitialize() {
        LOG.info("Initializing the bakery");

        // Modules
        Modules.get().add(new Fly());
        Modules.get().add(new ItemFrameDupe());
        Modules.get().add(new Spam());
        Modules.get().add(new SpamPlus());

        // Commands
        Commands.add(new CommandExample());

        // HUD
        Hud.get().register(HudExample.INFO);
    }

    @Override
    public void onRegisterCategories() {
        Modules.registerCategory(CATEGORY);
    }

    @Override
    public String getPackage() {
        return "com.example.addon";
    }
}
