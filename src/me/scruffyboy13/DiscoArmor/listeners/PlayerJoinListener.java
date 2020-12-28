package me.scruffyboy13.DiscoArmor.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.scruffyboy13.DiscoArmor.DiscoArmor;
import me.scruffyboy13.DiscoArmor.utils.ArmorUtils;

public class PlayerJoinListener implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		
		Player player = event.getPlayer();
		if (ArmorUtils.isWearingDisco(player)) {
			DiscoArmor.getPlayers().add(player.getUniqueId());
		}
		
	}
	
}
