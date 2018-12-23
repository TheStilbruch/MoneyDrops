package com.stilbruch.moneydrops.config;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;

/**
 * This class is just for storing enttiy drop settings in a more understandable manner
 * It also handles parsing the data from a Configuration secion
 */
public class EntityDropSettings {

    public final boolean doDrop;
    public final double dropChance;
    public final int maxDropValue;
    public final int minDropValue;

    public EntityDropSettings(ConfigurationSection configSection) {
        doDrop = configSection.getBoolean("do_drop", true);
        dropChance = configSection.getDouble("drop_chance", 0.5);
        maxDropValue = configSection.getInt("max_drop_value", 20);
        minDropValue = configSection.getInt("min_drop_value", 20);
    }

}