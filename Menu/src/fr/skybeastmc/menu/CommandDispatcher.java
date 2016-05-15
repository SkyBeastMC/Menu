package fr.skybeastmc.menu;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class CommandDispatcher {
	public static void dispatch(ConfigurationSection section, Player player) {
		if(section != null) {
			for(String key : section.getKeys(false)) {
				Debug.bc(key);
				List<String> commands;
				switch(key) {
				case "command":
					commands = section.getStringList(key);
					if(commands != null)
						for(String command : commands) {
							Bukkit.dispatchCommand(player, 
									PlaceHolders.format(command, player));
							Debug.bc(command);
						}
					break;
				case "console_command":
					commands = section.getStringList(key);
					if(commands != null)
						for(String command : commands) {
							Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
									PlaceHolders.format(command, player));
							Debug.bc(command);
						}
					break;
				case "op_command":
					break;
				case "open_menu":
					break;
				}
			}
		}
	}
}
