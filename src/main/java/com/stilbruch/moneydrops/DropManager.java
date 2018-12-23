package com.stilbruch.moneydrops;

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
        deathLocation.getWorld().playSound(deathLocation, Sound.ENTITY_SHEEP_SHEAR, 3.0f, 2.0f);

        droppedItem.setMetadata("dropValue", new FixedMetadataValue(plugin, 20)); //TODO: This will not be a hardcoded value
        droppedItem.setCustomName(ChatColor.GOLD + "Gold");
        droppedItem.setCustomNameVisible(true);
    }

    @EventHandler
    public void onItemPickup(EntityPickupItemEvent event) {
        if (event.getEntityType() == EntityType.PLAYER && event.getItem().hasMetadata("dropValue")) {
            Player player = (Player) event.getEntity();
            int value = event.getItem().getMetadata("dropValue").get(0).asInt();
            String message = String.format("You picked up %s$%s%s%s.", ChatColor.GOLD, ChatColor.YELLOW, value, ChatColor.GRAY);
            
            player.sendMessage(plugin.formatMessage(message));
            event.setCancelled(true); //Cancel the event, we don't actually want the player picking up the item
        }
    }
}