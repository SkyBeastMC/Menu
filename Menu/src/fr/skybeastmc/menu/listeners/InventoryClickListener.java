package fr.skybeastmc.menu.listeners;

import java.io.File;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import fr.skybeastmc.menu.CommandDispatcher;
import fr.skybeastmc.menu.Debug;
import fr.skybeastmc.menu.Main;
import fr.skybeastmc.menu.Menu;
import fr.skybeastmc.menu.MenuManager;

public class InventoryClickListener implements Listener {

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		try {
			event.setCancelled(true);
			for(Menu menu : MenuManager.getMenus()) {
				if(menu.getInventory().getName().equals(event.getInventory().getName())) {
					String name = menu.getName();
					
					File file = new File(Main.getFolder()+File.separator+"menus"+File.separator+name+".yml");
					YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
					ConfigurationSection items = config.getConfigurationSection("items");
					for(String key : items.getKeys(false)) {
						int menuSlot = 9 * items.getInt(key+".y") + items.getInt(key+".x");
						if(menuSlot == event.getRawSlot()) {
							ConfigurationSection item = items.getConfigurationSection(key);
							CommandDispatcher.dispatch(item.getConfigurationSection("onClick"),
									(Player) event.getWhoClicked());

							if(item.getBoolean("close")) {
								event.getWhoClicked().closeInventory();
							}
							return;
						}
					}
					break;
				}
			}
			event.setCancelled(false);
		} catch (Exception e) {Debug.error(e, "InventoryClick event", false);}
	}
	
}
