package fr.skybeastmc.menu;

import java.io.File;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Menu {
	private Inventory inventory;
	private String name;
	
	public static Menu get(String name) {// ./plugins/Menu/menus/{name}.yml
		File file = new File(Main.getFolder()+File.separator+"menus"+File.separator+name+".yml");
		if(!file.exists()) return null;
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		ConfigurationSection items = config.getConfigurationSection("items");
		Inventory inv = Bukkit.createInventory(null, config.getInt("settings.rows")*9,
				PlaceHolders.format(config.getString("settings.title")));
		
		for(String key : items.getKeys(false)) {
			
			ConfigurationSection item = config.getConfigurationSection("items."+key);
			int amount = item.getInt("amount");
			if(amount == 0) amount = 1;
			ItemStack is = new ItemStack(Material.getMaterial(item.getString("item")),
					amount, (short) item.getInt("damage"));
			ItemMeta meta = is.getItemMeta();

			List<String> lore = item.getStringList("lore");
			if(lore != null) {
				for(int i = 0; i < lore.size(); i++) {
					lore.set(i, PlaceHolders.format(lore.get(i)));
				}
				meta.setLore(lore);
			}
			String itemname =  PlaceHolders.format(item.getString("name"));
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
		InventoryView view = player.openInventory(inventory);
		for(ItemStack item : view.getTopInventory()) {
			if(item == null) continue;
			ItemMeta meta = item.getItemMeta();
			if(meta == null) continue;
			meta.setDisplayName(PlaceHolders.format(meta.getDisplayName(), player));
			
			List<String> lore = meta.getLore();
			if(lore != null) {
				for(String str : lore) str = PlaceHolders.format(str, player);
				meta.setLore(lore);
			}
			
			item.setItemMeta(meta);
			
			
		}
		YamlConfiguration config = YamlConfiguration.loadConfiguration(
				new File(Main.getFolder()+File.separator+"menus"+File.separator+name+".yml"));
		CommandDispatcher.dispatch(config.getConfigurationSection("settings.onOpen"), player);
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
