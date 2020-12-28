package me.scruffyboy13.DiscoArmor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import me.scruffyboy13.DiscoArmor.commands.DiscoCommand;
import me.scruffyboy13.DiscoArmor.listeners.InventoryListener;
import me.scruffyboy13.DiscoArmor.listeners.PlayerChangedWorldListener;
import me.scruffyboy13.DiscoArmor.listeners.PlayerDamageListener;
import me.scruffyboy13.DiscoArmor.listeners.PlayerDeathListener;
import me.scruffyboy13.DiscoArmor.listeners.PlayerJoinListener;
import me.scruffyboy13.DiscoArmor.nms.NMS;
import me.scruffyboy13.DiscoArmor.utils.ArmorUtils;
import me.scruffyboy13.DiscoArmor.utils.StringUtils;

public class DiscoArmor extends JavaPlugin {

	private static DiscoArmor instance;
	private static String sversion;
	private static ItemStack[] armor;
	private static NMS nms;
	private static List<UUID> players = new ArrayList<UUID>();
	private static List<UUID> respawn = new ArrayList<UUID>();
	private static List<Color> colors = new ArrayList<Color>();
	private static BukkitRunnable colorRunnable;
	private static BukkitRunnable illegalCheckRunnable;

	@Override
	public void onEnable() {

		saveDefaultConfig();

		instance = this;
		sversion = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
		armor = createDiscoArmor();
		colors = getColorsFromConfig();

		try {
			nms = (NMS) Class.forName("me.scruffyboy13.DiscoArmor.nms." + sversion).newInstance();
		} catch (ClassNotFoundException e) {
			this.getLogger().warning("Unsupported Version Detected: " + sversion);
			this.getServer().getPluginManager().disablePlugin(this);
			return;
		} catch (IllegalAccessException | InstantiationException e) {
			e.printStackTrace();
			this.getServer().getPluginManager().disablePlugin(this);
			return;
		}

		this.getCommand("disco").setExecutor(new DiscoCommand());
		
		this.getServer().getPluginManager().registerEvents(new InventoryListener(), this);
		this.getServer().getPluginManager().registerEvents(new PlayerChangedWorldListener(), this);
		this.getServer().getPluginManager().registerEvents(new PlayerDamageListener(), this);
		this.getServer().getPluginManager().registerEvents(new PlayerDeathListener(), this);
		this.getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);

		colorRunnable = new BukkitRunnable() {
			Random r = new Random();
			@Override
			public void run() {
				for (UUID uuid : players) {
					Color color = colors.get(r.nextInt(colors.size()));
					Player player = Bukkit.getPlayer(uuid);
					if (player == null) {
						players.remove(uuid);
						return;
					}
					if (!ArmorUtils.isWearingDisco(player)) {
						ArmorUtils.removeDisco(player);
						return;
					}
					ArmorUtils.changeColor(player, color);
				}
			}
		};
		colorRunnable.runTaskTimer(this, 0, getConfig().getInt("armor.ticks"));

		illegalCheckRunnable = new BukkitRunnable() {
			@Override
			public void run() {
				for (Player player : Bukkit.getServer().getOnlinePlayers()) {
					ArmorUtils.clearIllegalDisco(player);
				}
			}
		};
		illegalCheckRunnable.runTaskTimer(this, 0, 1);
		
	}

	@Override
	public void onDisable() {

	}

	public static List<Color> getColorsFromConfig() {
		List<Color> configColors = new ArrayList<Color>();
		for (String color : DiscoArmor.getInstance().getConfig().getStringList("armor.colors")) {
			if (color.equalsIgnoreCase("AQUA"))
				configColors.add(Color.AQUA);
			if (color.equalsIgnoreCase("BLACK"))
				configColors.add(Color.BLACK);
			if (color.equalsIgnoreCase("BLUE"))
				configColors.add(Color.BLUE);
			if (color.equalsIgnoreCase("FUCHSIA"))
				configColors.add(Color.FUCHSIA);
			if (color.equalsIgnoreCase("GRAY"))
				configColors.add(Color.GRAY);
			if (color.equalsIgnoreCase("GREEN"))
				configColors.add(Color.GREEN);
			if (color.equalsIgnoreCase("LIME"))
				configColors.add(Color.LIME);
			if (color.equalsIgnoreCase("MAROON"))
				configColors.add(Color.MAROON);
			if (color.equalsIgnoreCase("NAVY"))
				configColors.add(Color.NAVY);
			if (color.equalsIgnoreCase("OLIVE"))
				configColors.add(Color.OLIVE);
			if (color.equalsIgnoreCase("ORANGE"))
				configColors.add(Color.ORANGE);
			if (color.equalsIgnoreCase("PURPLE"))
				configColors.add(Color.PURPLE);
			if (color.equalsIgnoreCase("RED"))
				configColors.add(Color.RED);
			if (color.equalsIgnoreCase("SILVER"))
				configColors.add(Color.SILVER);
			if (color.equalsIgnoreCase("TEAL"))
				configColors.add(Color.TEAL);
			if (color.equalsIgnoreCase("WHITE"))
				configColors.add(Color.WHITE);
			if (color.equalsIgnoreCase("YELLOW"))
				configColors.add(Color.YELLOW);
		}
		return configColors;
	}

	public static ItemStack[] createDiscoArmor() {
		ItemStack[] items = new ItemStack[4];
		ItemStack item = new ItemStack(Material.LEATHER_BOOTS);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(StringUtils.color(DiscoArmor.getInstance().getConfig().getString("armor.name")));
		if (DiscoArmor.getInstance().getConfig().getBoolean("armor.loreEnabled"))
			meta.setLore(DiscoArmor.getInstance().getConfig().getStringList("armor.lore"));
		meta.spigot().setUnbreakable(true);
		item.setItemMeta(meta);
		item = ArmorUtils.addArmorNBTTag(item);
		items[0] = item.clone();
		item.setType(Material.LEATHER_LEGGINGS);
		items[1] = item.clone();
		item.setType(Material.LEATHER_CHESTPLATE);
		items[2] = item.clone();
		item.setType(Material.LEATHER_HELMET);
		items[3] = item.clone();
		return items;
	}

	public static DiscoArmor getInstance() {
		return instance;
	}

	public static List<UUID> getPlayers() {
		return players;
	}

	public static BukkitRunnable getColorRunnable() {
		return colorRunnable;
	}

	public static NMS getNms() {
		return nms;
	}

	public static ItemStack[] getArmor() {
		return armor;
	}
	
	public static void setArmor(ItemStack[] armor) {
		DiscoArmor.armor = armor;
	}
	
	public static List<Color> getColors() {
		return colors;
	}
	
	public static void setColors(List<Color> colors) {
		DiscoArmor.colors = colors;
	}

	public static List<UUID> getRespawn() {
		return respawn;
	}

	public static BukkitRunnable getIllegalCheckRunnable() {
		return illegalCheckRunnable;
	}

}
