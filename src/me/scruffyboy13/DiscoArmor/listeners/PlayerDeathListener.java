package me.scruffyboy13.DiscoArmor.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import me.scruffyboy13.DiscoArmor.DiscoArmor;
import me.scruffyboy13.DiscoArmor.utils.ArmorUtils;
import me.scruffyboy13.DiscoArmor.utils.StringUtils;

public class PlayerDeathListener implements Listener {

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		
		Player player = event.getEntity();
		if (ArmorUtils.isWearingDisco(player)) {
			DiscoArmor.getPlayers().remove(player.getUniqueId());
			List<ItemStack> dropsToRemove = new ArrayList<ItemStack>();
			for (ItemStack item : event.getDrops()) {
				if (ArmorUtils.isDisco(item)) {
					dropsToRemove.add(item);
				}
			}
			for (ItemStack drop : dropsToRemove) {
				event.getDrops().remove(drop);
			}
			if (DiscoArmor.getInstance().getConfig().getBoolean("armor.disable-on-death"))
				StringUtils.sendConfigMessage(player,  "message.disable");
			else {
				DiscoArmor.getRespawn().add(player.getUniqueId());
			}
		}
		
	}
	
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		
		Player player = event.getPlayer();
		if (DiscoArmor.getRespawn().contains(player.getUniqueId())) {
			DiscoArmor.getPlayers().add(player.getUniqueId());
			ArmorUtils.giveDisco(player);
			DiscoArmor.getRespawn().remove(player.getUniqueId());
		}
		
	}
	
}
