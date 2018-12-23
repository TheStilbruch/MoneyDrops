package com.stilbruch.moneydrops.config;

import java.io.File;
import java.util.HashMap;

import com.stilbruch.moneydrops.MoneyDropsPlugin;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;

public class MoneyDropsConfig {

    private final EntityDropSettings defaultDropSettings;
    private final HashMap<EntityType, EntityDropSettings> dropSettingsMap;

    public MoneyDropsConfig(MoneyDropsPlugin plugin) throws MoneyDropsConfigException {
        dropSettingsMap = new HashMap<EntityType,EntityDropSettings>();

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

    /**
     * Get's the drop settings for the specified entity type. If the type isn't in the config, return the default
     */
    public EntityDropSettings getEntityDropSettings(EntityType type) {
        return dropSettingsMap.getOrDefault(type, defaultDropSettings);
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