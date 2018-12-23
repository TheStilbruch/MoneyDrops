package com.stilbruch.moneydrops.config;

import java.io.File;

import com.stilbruch.moneydrops.MoneyDropsPlugin;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

public class MessagesConfig {

    public final String ITEM_PICKUP;

    public MessagesConfig(MoneyDropsPlugin plugin) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(getMessagesFile(plugin));

        ITEM_PICKUP = ChatColor.translateAlternateColorCodes('&', config.getString("money_pickup"));
    }

    private File getMessagesFile(MoneyDropsPlugin plugin) {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }
        File messagesConfigFile = new File(plugin.getDataFolder(), "messages.yml");
        if (!messagesConfigFile.exists()) {
            plugin.saveResource("messages.yml", false);
        }
        return messagesConfigFile;
    }

}