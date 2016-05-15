package fr.skybeastmc.menu;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import fr.skybeastmc.menu.bridge.EconomyBridge;
import fr.skybeastmc.menu.bridge.VaultEconomyBridge;
import fr.skybeastmc.menu.commands.MenuCommand;
import fr.skybeastmc.menu.listeners.InventoryClickListener;
import fr.skybeastmc.menu.listeners.InventoryCloseListener;

public class Main extends JavaPlugin {
	private static JavaPlugin plugin;
	private static YamlConfiguration config;
	
	public void onEnable() {
		try {
			File f = new File(getDataFolder()+File.separator+"config.yml");
			f.getParentFile().mkdirs();
			if(!f.exists()) f.createNewFile();
			
			config = YamlConfiguration.loadConfiguration(f);
			
		} catch (Exception e) {Debug.error(e, "Config file loading", true);}

		try {
			plugin = this;
			
			boolean debug = config.getBoolean("debug");
			Debug.setDebug(debug);
			if(debug) getServer().getPluginManager().registerEvents(new Debug(), this);
			getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);
			getServer().getPluginManager().registerEvents(new InventoryCloseListener(), this);
			getCommand("menu").setExecutor(new MenuCommand());
			
		} catch (Exception e) {Debug.error(e, "Enabling", true);}
		
		try {
			for(Plugin plugin : getServer().getPluginManager().getPlugins()) {
				if(plugin.getName().equals("Vault")) {
					new VaultEconomyBridge().setupEconomy();
					break;
				}
			}
			if(!EconomyBridge.isUsable()) {
				Debug.info("No compatible money plugin found!");
				Debug.info("Please install one of the following:");
				Debug.info(String.valueOf(EconomyBridge.getCompatibleEconomyPlugins()));
				Debug.info("The currency features are not working.");
			}
			
		} catch (Exception e) {Debug.error(e, "Bridge", true);}
		
	}
	
	public void onDisable() {
		HandlerList.unregisterAll(this);
	}
	

	public static JavaPlugin getPlugin() {
		return plugin;
	}
	
	public static File getFolder() {
		return plugin.getDataFolder();
	}

	public static void setPlugin(JavaPlugin plugin) {
		Main.plugin = plugin;
	}

	public static YamlConfiguration getConf() {
		return config;
	}

	public static void setConf(YamlConfiguration config) {
		Main.config = config;
	}
}
