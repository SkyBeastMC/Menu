package fr.skybeastmc.menu;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import fr.skybeastmc.menu.bridge.EconomyBridge;

public class PlaceHolders {
	
	public static String format(String str, Player player) {
		str = ChatColor.translateAlternateColorCodes('&', str);
		str = str.replaceAll("\\{player\\}", player.getName());
		if(EconomyBridge.isUsable()) {
			str = str.replaceAll("\\{currency\\}", EconomyBridge.getEconomy().getCurrencyNamePlural());
			str = str.replaceAll("\\{money\\}", EconomyBridge.getEconomy().get(player)+"");
		} else {
			str = str.replaceAll("\\{currency\\}", "");
			str = str.replaceAll("\\{money\\}", "");
		}
		return str;
	}
	
	public static String format(String str) {
		return ChatColor.translateAlternateColorCodes('&', str);
	}
}