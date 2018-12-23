package com.stilbruch.moneydrops;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class MoneyDropsPlugin extends JavaPlugin {

    public DropManager dropManager;

    @Override
    public void onEnable() {
        //Initialize managers
        dropManager = new DropManager(this);

        //Add listeners
        getServer().getPluginManager().registerEvents(dropManager, this);
    }

    public String formatMessage(String message) {
        return ChatColor.DARK_GRAY + "[" + ChatColor.YELLOW + "MoneyDrops" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY + message;
    }
}
