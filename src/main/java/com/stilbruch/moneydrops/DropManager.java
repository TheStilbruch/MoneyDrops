package com.stilbruch.moneydrops;

import org.bukkit.Bukkit;
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

    private MoneyDropsPlugin plugin;

    public DropManager(MoneyDropsPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Location deathLocation = event.getEntity().getLocation();
        Item droppedItem = deathLocation.getWorld().dropItem(deathLocation, new ItemStack(Material.GOLD_NUGGET));

        droppedItem.setMetadata("dropValue", new FixedMetadataValue(plugin, 20)); //TODO: This will not be a hardcoded value
        droppedItem.setCustomName(ChatColor.GOLD + "Gold");
        droppedItem.setCustomNameVisible(true);
    }

    @EventHandler
    public void onItemPickup(EntityPickupItemEvent event) {
        if (event.getEntityType() == EntityType.PLAYER && event.getItem().hasMetadata("dropValue")) {
            Player player = (Player) event.getEntity();
            int moneyValue = event.getItem().getMetadata("dropValue").get(0).asInt();
            String message = plugin.messagesConfig.ITEM_PICKUP.replace("%money%", Integer.toString(moneyValue));
            
            //I really hate the way this looks in game, but as for now I cannot
            //figure out a way to have the player pick up an item and have it not add to
            //the inventory.
            player.sendMessage(plugin.formatMessage(message));
            event.setCancelled(true);
            event.getItem().remove();
            event.getItem().getLocation().getWorld().playSound(event.getItem().getLocation(), Sound.ENTITY_ITEM_PICKUP, 0.5f, 1.0f);
        }
    }
}