package com.stilbruch.moneydrops;

import java.text.DecimalFormat;
import java.util.Random;

import com.stilbruch.moneydrops.config.EntityDropSettings;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

public class DropManager implements Listener {

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");

    private MoneyDropsPlugin plugin;

    public DropManager(MoneyDropsPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        //Test to see if the entity was killed by a player, if the config specifies to only drop money from entities killed by players
        if (plugin.moneyDropsConfig.DROP_KILLS_ONLY && event.getEntity().getKiller() == null) return;

        EntityDropSettings dropSettings = plugin.moneyDropsConfig.getEntityDropSettings(event.getEntityType());
        Random rand = new Random();

        //Test to see if we actually want to drop the item
        if (!(rand.nextDouble() < dropSettings.dropChance)) return;
        
        //Drop the item
        Location deathLocation = event.getEntity().getLocation();
        Item droppedItem = deathLocation.getWorld().dropItem(deathLocation, new ItemStack(Material.GOLD_NUGGET)); //TODO: Load the item type from config
        
        double dropWorth;
        if (dropSettings.maxDropValue == dropSettings.minDropValue || dropSettings.minDropValue > dropSettings.maxDropValue) {
            dropWorth = dropSettings.minDropValue;
        } else {
            dropWorth = dropSettings.minDropValue + (rand.nextDouble() * (dropSettings.maxDropValue - dropSettings.minDropValue));
        }
        droppedItem.setMetadata("dropValue", new FixedMetadataValue(plugin, dropWorth));
        droppedItem.setCustomName(ChatColor.GOLD + "Gold"); //TODO: Have this loaded in from the config
        droppedItem.setCustomNameVisible(true); //TODO: Keep gold items from stacking
    }

    @EventHandler
    public void onItemPickup(EntityPickupItemEvent event) {
        if (event.getEntityType() == EntityType.PLAYER && event.getItem().hasMetadata("dropValue")) {
            Player player = (Player) event.getEntity();
            double moneyValue = event.getItem().getMetadata("dropValue").get(0).asDouble();
            String message = plugin.messagesConfig.ITEM_PICKUP.replace("%money%", DECIMAL_FORMAT.format(moneyValue));
            
            //I really hate the way this looks in game, but as for now I cannot
            //figure out a way to have the player pick up an item and have it not add to
            //the inventory.
            player.sendMessage(plugin.formatMessage(message));
            plugin.economy.depositPlayer(player, moneyValue);
            event.setCancelled(true);
            event.getItem().remove();
            event.getItem().getLocation().getWorld().playSound(event.getItem().getLocation(), Sound.ENTITY_ITEM_PICKUP, 0.5f, 1.0f);
        }
    }
}