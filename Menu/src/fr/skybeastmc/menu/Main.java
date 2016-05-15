package fr.skybeastmc.menu;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
	private static JavaPlugin plugin;
	private static YamlConfiguration config;
	
	public void onEnable() {
		try {
			File f = new File(getDataFolder()+"config.yml");
			f.getParentFile().mkdirs();
			if(!f.exists()) f.createNewFile();
			
			config = YamlConfiguration.loadConfiguration(f);
			
		} catch (IOException e) {
			Debug.error(e, "Config file loading", true);
		}
		
		plugin = this;
		
		boolean debug = true;
		Debug.setDebug(debug);
		if(debug) getServer().getPluginManager().registerEvents(new Debug(), this);
		getServer().getPluginManager().registerEvents(new MenuManager(), this);
		
		
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
