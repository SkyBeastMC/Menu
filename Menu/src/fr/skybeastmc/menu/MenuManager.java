package fr.skybeastmc.menu;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MenuManager {
	private static List<Menu> menus = new ArrayList<Menu>();

	public static void getMenuAndProcess(InventoryClickEvent event) {
		event.setCancelled(true);
		for(Menu menu : menus) {
			if(menu.getInventory().getName().equals(event.getInventory().getName())) {
				Debug.debug("menu found");
				String name = menu.getName();
				
				File file = new File(Main.getFolder()+File.separator+"menus"+File.separator+name+".yml");
				Debug.debug(Main.getFolder()+File.separator+"menus"+File.separator+name+".yml");
				Debug.debug(file.exists());
				YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
				ConfigurationSection items = config.getConfigurationSection("items");
				for(String key : items.getKeys(false)) {
					int menuSlot = 9 * items.getInt(key+".y") + items.getInt(key+".x");
					if(menuSlot == event.getRawSlot()) {
						process(items, (Player) event.getWhoClicked(), key);
					}
				}
			}
		}
		event.setCancelled(false);
	}
	
	public static void process(ConfigurationSection items, Player player, String itemid) {
		ConfigurationSection item = items.getConfigurationSection(itemid);
		Debug.bc("Yes!");
		
		String action = item.getString("action");
		if(action != null) {
			List<String> commands;
			switch(action) {
			case "command":
				commands = item.getStringList("commands");
				if(commands != null)
					for(String command : commands) {
						Bukkit.dispatchCommand(player, 
								PlaceHolders.format(command, player));
						Debug.bc(command);
					}
				break;
			case "console_command":
				commands = item.getStringList("commands");
				if(commands != null)
					for(String command : commands) {
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
								PlaceHolders.format(command, player));
						Debug.bc(command);
					}
				break;
			case "op_command":
				break;
			case "open_menu":
				break;
			}
		}
		
		if(item.getBoolean("close")) {
			player.closeInventory();
		}
		return;
	}
	
	protected static void addMenu(Menu menu) {
		if(!menus.contains(menu)) menus.add(menu);
	}
	
	protected static void removeMenu(Menu menu) {
		if(menus.contains(menu)) menus.remove(menu);
	}
}
