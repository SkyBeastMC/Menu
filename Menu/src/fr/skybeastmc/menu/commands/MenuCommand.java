package fr.skybeastmc.menu.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.skybeastmc.menu.Debug;
import fr.skybeastmc.menu.Menu;

public class MenuCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		try {
			if(args.length != 0 && sender instanceof Player) {
				Player player = (Player) sender;
				Menu menu = Menu.get(args[0]);
				if(menu != null) {
					menu.open(player);
					return true;
				} else {
					sender.sendMessage(ChatColor.RED+"Can't find the menu "+args[0]+"!");
				}
				return false;
			} else {
				displayHelp(sender, label);
				return false;
			}
		} catch(Exception error) {Debug.error(error, "Menu command", false);}
		return false;
	}

	private void displayHelp(CommandSender sender, String label) {
		sender.sendMessage(ChatColor.RED+"Usage: /"+label+" {menu}");
	}

}
