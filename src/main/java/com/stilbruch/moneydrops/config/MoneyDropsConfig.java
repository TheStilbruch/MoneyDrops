package com.stilbruch.moneydrops.config;

import java.io.File;

import com.stilbruch.moneydrops.MoneyDropsPlugin;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

public class MoneyDropsConfig {

    public final EntityDropSettings defaultDropSettings;

    public MoneyDropsConfig(MoneyDropsPlugin plugin) throws MoneyDropsConfigException {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(getMessagesFile(plugin));

        ConfigurationSection entitySettingsSection = config.getConfigurationSection("entity_settings");
        if (entitySettingsSection == null) {
            throw new MoneyDropsConfigException("No 'entity_settings' entry was found in the config!");
        }

        ConfigurationSection defaultSettingsSection = entitySettingsSection.getConfigurationSection("default");
        if (defaultSettingsSection == null) {
            throw new MoneyDropsConfigException("No default 'entity_settings' entry was found in the config!");
        }

        defaultDropSettings = new EntityDropSettings(defaultSettingsSection);

        for (String key : entitySettingsSection.getKeys(false)) {
            if (key.equals("default")) continue; // We don't need to load in the default data again
            
            //TODO: Load in other entity data
        }
    }

    private File getMessagesFile(MoneyDropsPlugin plugin) {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }
        File messagesConfigFile = new File(plugin.getDataFolder(), "config.yml");
        if (!messagesConfigFile.exists()) {
            plugin.saveResource("config.yml", false);
        }
        return messagesConfigFile;
    }

    private class MoneyDropsConfigException extends Exception {
        MoneyDropsConfigException(String message) {
            super(message);
        }
    }

}