package me.scruffyboy13.DiscoArmor.listeners;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import me.scruffyboy13.DiscoArmor.DiscoArmor;
import me.scruffyboy13.DiscoArmor.utils.ArmorUtils;
import me.scruffyboy13.DiscoArmor.utils.StringUtils;

public class PlayerChangedWorldListener implements Listener {

	@EventHandler
	public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
		
		Player player = event.getPlayer();
		
		if (!ArmorUtils.isWearingDisco(player)) {
			return;
		}
		
		List<String> worldNames = DiscoArmor.getInstance().getConfig().getStringList("armor.worlds");
		
		if (worldNames.contains("*") || worldNames.contains(player.getWorld().getName())) {
			return;
		}

		DiscoArmor.getPlayers().remove(player.getUniqueId());
		ArmorUtils.removeDisco(player);
		StringUtils.sendConfigMessage(player, "message.wrongworld");
		
	}
	
}
