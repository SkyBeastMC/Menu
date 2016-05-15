package fr.skybeastmc.menu;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class Debug implements Listener {
	private static boolean debug;

	public static void debug(Object o) {
		if(debug) Bukkit.getLogger().info(String.valueOf(o));
	}
	public static void bc(Object o) {
		if(debug) Bukkit.broadcastMessage(String.valueOf(o));
	}
	public static boolean isDebug() {
		return debug;
	}
	public static void setDebug(boolean debug) {
		Debug.debug = debug;
	}
	public static void error(Throwable error, String phase, boolean disable) {
		for(Player player : Bukkit.getOnlinePlayers()) {
			if(player.isOp()) {
				player.sendMessage("§c[Menu] Oops, there was an error while phase \""+phase+"\"!");
				player.sendMessage("§cStacktrace: ");
				if(debug) player.sendMessage("§c"+stackTraceToString(error));
				else player.sendMessage("§c[See console]");
				if(disable) {
					player.sendMessage("§4The plugin can't work now! Disabling...");
					Bukkit.getPluginManager().disablePlugin(Main.getPlugin());
				}
			}
		}
		Bukkit.getLogger().severe("[Menu] Oops, there was an error while phase \""+phase+"\"!");
		Bukkit.getLogger().severe("Stacktrace: ");
		Bukkit.getLogger().severe(stackTraceToString(error));
		if(disable) {
			Bukkit.getLogger().severe("The plugin can't work now! Disabling...");
			Bukkit.getPluginManager().disablePlugin(Main.getPlugin());
		}
	}
	private static String stackTraceToString(Throwable error) {
		StringWriter errors = new StringWriter();
		error.printStackTrace(new PrintWriter(errors));
		return errors.toString();
	}
	
	@EventHandler
	public void onInteractEvent(PlayerInteractEvent event) {
		try {
			debug(Main.getPlugin().getDataFolder());
			Menu menu = Menu.get("menu1");
			bc(String.valueOf(menu));
			menu.open(event.getPlayer());
		} catch(Exception e) {
			error(e, "Debug", false);
		}
	}
	public static void info(Object o) {
		Bukkit.getLogger().info(String.valueOf(o));
	}
}
