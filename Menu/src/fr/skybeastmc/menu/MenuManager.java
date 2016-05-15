package fr.skybeastmc.menu;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class MenuManager implements Listener {
	private static List<Menu> menus = new ArrayList<Menu>();

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		for(Menu menu : menus) {
			if(menu.getInventory().getName().equals(event.getInventory().getName())) {
				event.setCancelled(true);
				Debug.debug("menu found");
				String name = menu.getName();
				
				File file = new File(Main.getFolder()+File.separator+"menus"+File.separator+name+".yml");
				Debug.debug(Main.getFolder()+File.separator+"menus"+File.separator+name+".yml");
				Debug.debug(file.exists());
				YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
				ConfigurationSection items = config.getConfigurationSection("items");
				for(String key : items.getKeys(false)) {
					int menuSlot = 9 * items.getInt(key+".y") + items.getInt(key+".x");
					if(menuSlot == event.getSlot()) {
						Debug.bc("Yes!");
						
						
						return;
					}
				}
			}
		}
		Debug.bc("No :(");
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		for(Menu menu : menus) {
			if(menu.getInventory().getName().equals(event.getInventory().getName())) {
				Debug.debug("menu found");
			}
		}
	}
	
	protected static void addMenu(Menu menu) {
		if(!menus.contains(menu)) menus.add(menu);
	}
	
	protected static void removeMenu(Menu menu) {
		if(menus.contains(menu)) menus.remove(menu);
	}
}
