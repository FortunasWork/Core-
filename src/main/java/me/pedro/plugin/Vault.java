package me.pedro.plugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;

import net.milkbowl.vault.economy.Economy;

public class Vault {

	private Core plugin;
	private Bank provider;
	
	public Vault(Core plugin) {
		this.plugin = plugin;
	}
	
	public void hook() {
		provider = plugin.economyEmplementar;
		Bukkit.getServicesManager().register(Economy.class, provider, plugin, ServicePriority.Normal);
		
	}
	
	public void unHook() {
		Bukkit.getServicesManager().unregister(Economy.class, provider);
	}
}
