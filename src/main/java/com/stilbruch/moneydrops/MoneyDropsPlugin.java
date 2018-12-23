package com.stilbruch.moneydrops;

import com.stilbruch.moneydrops.config.MessagesConfig;
import com.stilbruch.moneydrops.config.MoneyDropsConfig;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class MoneyDropsPlugin extends JavaPlugin {

    public MoneyDropsConfig moneyDropsConfig;
    public MessagesConfig messagesConfig;
    public DropManager dropManager;

    @Override
    public void onEnable() {
        //Initialize managers
        try {
            moneyDropsConfig = new MoneyDropsConfig(this);
        } catch (Exception e) {
            System.out.println("Failed to load in config, shutting down plugin...");
            return; //Not sure if there is another way to shut down a plugin. At least this stops the listeners from starting
        }
        messagesConfig = new MessagesConfig(this);
        dropManager = new DropManager(this);

        //Add listeners
        getServer().getPluginManager().registerEvents(dropManager, this);
    }

    public String formatMessage(String message) {
        return ChatColor.DARK_GRAY + "[" + ChatColor.YELLOW + "MoneyDrops" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY + message;
    }
}