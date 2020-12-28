package me.scruffyboy13.DiscoArmor.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.collect.ImmutableMap;

import me.scruffyboy13.DiscoArmor.DiscoArmor;
import me.scruffyboy13.DiscoArmor.utils.ArmorUtils;
import me.scruffyboy13.DiscoArmor.utils.StringUtils;

public class DiscoCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (args.length == 0) {
			
			if (!(sender instanceof Player)) {
				StringUtils.sendConfigMessage(sender, "message.playersOnly");
				return true;
			}
			
			Player player = (Player) sender;
			
			if (!player.hasPermission("discoarmor.disco")) {
				StringUtils.sendConfigMessage(sender, "message.nopermission");
				return true;
			}
			
			if (ArmorUtils.isWearingDisco(player)) {
				DiscoArmor.getPlayers().remove(player.getUniqueId());
				ArmorUtils.removeDisco(player);
				StringUtils.sendConfigMessage(player, "message.disable");
				return true;
			}
			else {
				DiscoArmor.getPlayers().add(player.getUniqueId());
				ArmorUtils.giveDisco(player);
				StringUtils.sendConfigMessage(player, "message.enable");
				return true;
			}
			
		}
		else if (args[0].equalsIgnoreCase("reload")) {
			
			if (!sender.hasPermission("discoarmor.reload")) {
				StringUtils.sendConfigMessage(sender, "message.nopermission");
				return true;
			}
			
			DiscoArmor.getInstance().reloadConfig();
			DiscoArmor.setArmor(DiscoArmor.createDiscoArmor());
			DiscoArmor.setColors(DiscoArmor.getColorsFromConfig());
			StringUtils.sendConfigMessage(sender, "message.reload");
			return true;
			
		}
		else if (args[0].equalsIgnoreCase("list")) {
			
			if (!sender.hasPermission("discoarmor.list")) {
				StringUtils.sendConfigMessage(sender, "message.nopermission");
				return true;
			}
			
			StringUtils.sendConfigMessage(sender, "message.listWorldsHeader");
			for (String worldName : DiscoArmor.getInstance().getConfig().getStringList("armor.worlds")) {
				StringUtils.sendConfigMessage(sender, "message.listWorlds", ImmutableMap.of(
						"%world%", worldName));
			}
			
			return true;
			
		}
		
		return false;
	
	}

	
	
}
