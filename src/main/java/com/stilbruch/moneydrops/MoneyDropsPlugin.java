package com.stilbruch.moneydrops;

import com.stilbruch.moneydrops.config.MessagesConfig;
import com.stilbruch.moneydrops.config.MoneyDropsConfig;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.economy.Economy;

public class MoneyDropsPlugin extends JavaPlugin {

    public MoneyDropsConfig moneyDropsConfig;
    public MessagesConfig messagesConfig;
    public DropManager dropManager;
    public Economy economy;

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
        economy = getServer().getServicesManager().getRegistration(Economy.class).getProvider();

        //Add listeners
        getServer().getPluginManager().registerEvents(dropManager, this);
    }

    public String formatMessage(String message) {
        return ChatColor.DARK_GRAY + "[" + ChatColor.YELLOW + "MoneyDrops" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY + message;
    }
}