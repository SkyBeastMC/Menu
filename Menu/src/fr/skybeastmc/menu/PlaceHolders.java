package fr.skybeastmc.menu;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import fr.skybeastmc.menu.bridge.EconomyBridge;

public class PlaceHolders {
	
	public static String format(String str, Player player) {
		return ChatColor.translateAlternateColorCodes('&', str
				.replaceAll("\\{player\\}", player.getName())
				.replaceAll("\\{currency\\}", EconomyBridge.getEconomy().getCurrencyNamePlural())
				.replaceAll("\\{money\\}", EconomyBridge.getEconomy().get(player)+""));
	}
	
	public static String format(String str) {
		return ChatColor.translateAlternateColorCodes('&', str);
	}
}