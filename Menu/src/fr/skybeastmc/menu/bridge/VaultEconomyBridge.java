package fr.skybeastmc.menu.bridge;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultEconomyBridge extends EconomyBridge {
	private Economy economy = null;
	
	@Override
	public boolean setupEconomy() {
		if(setup) throw new RuntimeException("Economy already setup!");
		
        RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }
        setup();
        return (economy != null);
    }

	@Override
	public double get(OfflinePlayer player) {
		return economy.getBalance(player);
	}

	@Override
	public boolean has(OfflinePlayer player, double money) {
		return economy.has(player, money);
	}

	@Override
	public boolean take(OfflinePlayer player, double money) {
		return economy.withdrawPlayer(player, money).transactionSuccess();
	}

	@Override
	public boolean give(OfflinePlayer player, double money) {
		return economy.depositPlayer(player, money).transactionSuccess();
		
	}

	@Override
	public String getCurrencyNameSingular() {
		return economy.currencyNameSingular();
		
	}

	@Override
	public String getCurrencyNamePlural() {
		return economy.currencyNamePlural();
		
	}
	
}
