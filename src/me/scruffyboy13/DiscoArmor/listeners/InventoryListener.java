package me.scruffyboy13.DiscoArmor.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import me.scruffyboy13.DiscoArmor.utils.ArmorUtils;

public class InventoryListener implements Listener {

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		
		if (ArmorUtils.isDisco(event.getCurrentItem())) {
			event.setCancelled(true);
		}
		
	}
	
}
