package me.scruffyboy13.DiscoArmor.utils;

import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.collect.ImmutableMap;

import me.scruffyboy13.DiscoArmor.DiscoArmor;

public class StringUtils {

	public static String color(String string) {
		return ChatColor.translateAlternateColorCodes('&', string);
	}
	
	public static void sendMessage(CommandSender sender, List<String> message) {
		for (String line : message) {
			sender.sendMessage(color(line));
		}
	}

	public static void sendMessage(Player player, List<String> message) {
		for (String line : message) {
			player.sendMessage(color(line));
		}
	}
	
	public static void sendMessage(Player player, List<String> message, ImmutableMap<String, String> placeholders) {
		for (String line : message) {
			for (Map.Entry<String, String> placeholder : placeholders.entrySet()) {
				line = line.replace(placeholder.getKey(), placeholder.getValue());
			}
			player.sendMessage(color(line));
		}
	}
	
	public static void sendMessage(CommandSender sender, List<String> message, ImmutableMap<String, String> placeholders) {
		for (String line : message) {
			for (Map.Entry<String, String> placeholder : placeholders.entrySet()) {
				line = line.replace(placeholder.getKey(), placeholder.getValue());
			}
			sender.sendMessage(color(line));
		}
	}
	
	public static void sendConfigMessage(CommandSender sender, String path) {
		for (String line : DiscoArmor.getInstance().getConfig().getStringList(path)) {
			sender.sendMessage(color(line));
		}
	}
	
	public static void sendConfigMessage(Player player, String path) {
		for (String line : DiscoArmor.getInstance().getConfig().getStringList(path)) {
			player.sendMessage(color(line));
		}
	}

	public static void sendConfigMessage(Player player, String path, ImmutableMap<String, String> placeholders) {
		for (String line : DiscoArmor.getInstance().getConfig().getStringList(path)) {
			for (Map.Entry<String, String> placeholder : placeholders.entrySet()) {
				line = line.replace(placeholder.getKey(), placeholder.getValue());
			}
			player.sendMessage(color(line));
		}
	}

	public static void sendConfigMessage(CommandSender sender, String path, ImmutableMap<String, String> placeholders) {
		for (String line : DiscoArmor.getInstance().getConfig().getStringList(path)) {
			for (Map.Entry<String, String> placeholder : placeholders.entrySet()) {
				line = line.replace(placeholder.getKey(), placeholder.getValue());
			}
			sender.sendMessage(color(line));
		}
	}
	
}
