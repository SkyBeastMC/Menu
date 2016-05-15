package fr.skybeastmc.menu;

import java.io.File;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Menu {
	private Inventory inventory;
	private String name;
	
	public static Menu get(String name) {// ./plugins/Menu/menus/{name}.yml
		File file = new File(Main.getFolder()+File.separator+"menus"+File.separator+name+".yml");
		Debug.debug(Main.getFolder()+File.separator+"menus"+File.separator+name+".yml");
		Debug.debug(file.exists());
		if(!file.exists()) return null;
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		ConfigurationSection items = config.getConfigurationSection("items");
		Inventory inv = Bukkit.createInventory(null, config.getInt("settings.rows")*9,
				ChatColor.translateAlternateColorCodes('&', config.getString("settings.title")));
		
		for(String key : items.getKeys(false)) {
			Debug.debug(key);
			
			ConfigurationSection item = config.getConfigurationSection("items."+key);
			int amount = item.getInt("amount");
			if(amount == 0) amount = 1;
			ItemStack is = new ItemStack(Material.getMaterial(item.getString("item")),
					amount, (short) item.getInt("damage"));
			ItemMeta meta = is.getItemMeta();

			List<String> lore = item.getStringList("lore");
			if(lore != null) {
				for(int i = 0; i < lore.size(); i++) {
					lore.set(i, ChatColor.translateAlternateColorCodes('&', lore.get(i)));
				}
				meta.setLore(lore);
			}
			String itemname =  ChatColor.translateAlternateColorCodes('&', item.getString("name"));
			if(itemname != null) meta.setDisplayName(itemname);
			
			is.setItemMeta(meta);
			List<String> enchants = item.getStringList("enchants");
			if(enchants != null) {
				for(String enchant : enchants) {
					String[] a = enchant.split(" ");
					is.addUnsafeEnchantment(Enchantment.getByName(a[0]), Integer.valueOf(a[1]));
				}
			}
			int slot = 9 * item.getInt("y") + item.getInt("x"); //slot = 9y + x
			inv.setItem(slot, is);
		}
		
		return new Menu(name, inv);
	}
	
	public void open(Player player) {
		player.openInventory(inventory);
	}
	
	public Menu(String name, Inventory inventory) {
		this.inventory = inventory;
		this.name = name;
		MenuManager.addMenu(this);
	}
	
	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
