package fr.skybeastmc.menu.listeners;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import fr.skybeastmc.menu.CommandDispatcher;
import fr.skybeastmc.menu.Debug;
import fr.skybeastmc.menu.Main;
import fr.skybeastmc.menu.Menu;
import fr.skybeastmc.menu.MenuManager;

public class InventoryCloseListener implements Listener {

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		try {
			for(Menu menu : MenuManager.getMenus()) {
				if(menu.getInventory().getName().equals(event.getInventory().getName())) {
					String name = menu.getName();

					YamlConfiguration config = YamlConfiguration.loadConfiguration(
							new File(Main.getFolder()+File.separator+"menus"+File.separator+name+".yml"));
					CommandDispatcher.dispatch(config.getConfigurationSection("settings.onClose"),
							(Player) event.getPlayer());
				}
				break;
			}
		} catch (Exception e) {Debug.error(e, "InventoryClose event", false);}
	}
	
}
