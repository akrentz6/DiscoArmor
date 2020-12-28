package me.scruffyboy13.DiscoArmor.utils;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import de.tr7zw.nbtapi.NBTItem;
import me.scruffyboy13.DiscoArmor.DiscoArmor;

public class ArmorUtils {

	public static boolean isWearingDisco(Player player) {
		for (ItemStack armor : player.getInventory().getArmorContents()) {
			if (!isDisco(armor)) {
				return false;
			}
		}
		return true;
	}

	public static boolean isDisco(ItemStack item) {
		if (item == null || item.getType() == Material.AIR) {
			return false;
		}
		NBTItem nbtItem = new NBTItem(item);
		return nbtItem.hasKey("DiscoArmor");
	}

	public static void removeDisco(Player player) {
		int index = 0;
		ItemStack[] contents = player.getInventory().getArmorContents();
		for (ItemStack item : contents) {
			if (isDisco(item)) {
				contents[index] = null;
			}
			index++;
		}
		player.getInventory().setArmorContents(contents);
	}

	public static void clearIllegalDisco(Player player) {
		int index = 0;
		for (ItemStack item : player.getInventory().getContents()) {
			if (isDisco(item) && (index < 37 || index > 40)) {
				player.getInventory().setItem(index, null);
			}
			index++;
		}
	}

	public static ItemStack addArmorNBTTag(ItemStack item) {
		NBTItem nbtItem = new NBTItem(item);
		nbtItem.setBoolean("DiscoArmor", true);
		return nbtItem.getItem();
	}

	public static void changeColor(Player player, Color color) {
		for (ItemStack item : player.getInventory().getArmorContents()) {
			if (isDisco(item)) {
				LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
		        meta.setColor(color);
		        item.setItemMeta((ItemMeta) meta);
			}
			else {
				removeDisco(player);
			}
		}
	}

	public static void giveDisco(Player player) {
		for (ItemStack item : player.getInventory().getArmorContents()) {
			if (item != null) {
				if (player.getInventory().firstEmpty() == -1) {
					player.getWorld().dropItemNaturally(player.getLocation(), item);
				} else
					player.getInventory().addItem(item);
			}
		}
		player.getInventory().setArmorContents(DiscoArmor.getArmor());
	}

}
