package fr.skybeastmc.menu.bridge;

import org.bukkit.OfflinePlayer;


public abstract class EconomyBridge {
	protected boolean setup = false;
	private static EconomyBridge economy;
	
	public abstract boolean setupEconomy();

	public boolean isSetup() {
		return setup;
	}

	protected void setup() {
		if(setup) throw new RuntimeException("Economy already setup!");
		economy = this;
		setup = true;
	}
	
	public abstract double get(OfflinePlayer player);
	
	public abstract boolean has(OfflinePlayer player, double money);
	
	public abstract boolean take(OfflinePlayer player, double money);
	
	public abstract boolean give(OfflinePlayer player, double money);
	
	public abstract String getCurrencyNameSingular();
	
	public abstract String getCurrencyNamePlural();

	public static EconomyBridge getEconomy() {
		return economy;
	}
	
}
