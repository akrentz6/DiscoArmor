package me.scruffyboy13.DiscoArmor.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import me.scruffyboy13.DiscoArmor.DiscoArmor;
import me.scruffyboy13.DiscoArmor.utils.ArmorUtils;

public class PlayerDamageListener implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onPlayerDamage(EntityDamageEvent event) {
		
		if (event.getEntityType() != EntityType.PLAYER) {
			return;
		}
		
		Player player = (Player) event.getEntity();
		
		if (!ArmorUtils.isWearingDisco(player)) {
			return;
		}
		
		if (!DiscoArmor.getInstance().getConfig().getBoolean("armor.aesthetic")) {
			return;
		}
		
		DiscoArmor.getNms().damage(event);
		
	}
	
}
