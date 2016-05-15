package fr.skybeastmc.menu.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import fr.skybeastmc.menu.Debug;
import fr.skybeastmc.menu.MenuManager;

public class InventoryClickListener implements Listener {

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		try {
			MenuManager.getMenuAndProcess(event);
		} catch (Exception e) {Debug.error(e, "InventoryClick event", true);}
	}
	
}
