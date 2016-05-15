package fr.skybeastmc.menu;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import fr.skybeastmc.menu.bridge.VaultEconomyBridge;
import fr.skybeastmc.menu.commands.MenuCommand;
import fr.skybeastmc.menu.listeners.InventoryClickListener;

public class Main extends JavaPlugin implements Listener {
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
			getCommand("menu").setExecutor(new MenuCommand());;
			
		} catch (Exception e) {Debug.error(e, "Enabling", true);}
		
		try {
			for(Plugin plugin : getServer().getPluginManager().getPlugins()) {
				if(plugin.getName().equals("Vault")) {
					new VaultEconomyBridge().setupEconomy();
					break;
				}
			}
			
		} catch (Exception e) {Debug.error(e, "Bridge", true);}
		
	}
	
	public void onDisable() {
		
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
