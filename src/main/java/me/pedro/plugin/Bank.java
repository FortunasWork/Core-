package me.pedro.plugin;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

public class Bank implements Economy {
	
	private Core plugin = Core.getPlugin();

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasBankSupport() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int fractionalDigits() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String format(double amount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String currencyNamePlural() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String currencyNameSingular() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasAccount(String playerName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasAccount(OfflinePlayer player) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasAccount(String playerName, String worldName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasAccount(OfflinePlayer player, String worldName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double getBalance(String s) {
		Player player = Bukkit.getPlayer(s);
		UUID uuid = player.getUniqueId();
		return plugin.playerBank.get(uuid);
	}

	@Override
	public double getBalance(OfflinePlayer player) {
		UUID uuid = player.getUniqueId();
		return plugin.playerBank.get(uuid);
	}

	@Override
	public double getBalance(String s, String world) {
		Player player = Bukkit.getPlayer(s);
		UUID uuid = player.getUniqueId();
		return plugin.playerBank.get(uuid);
	}

	@Override
	public double getBalance(OfflinePlayer player, String world) {
		UUID uuid = player.getUniqueId();
		return plugin.playerBank.get(uuid);
	}

	@Override
	public boolean has(String playerName, double amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean has(OfflinePlayer player, double amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean has(String playerName, String worldName, double amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean has(OfflinePlayer player, String worldName, double amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public EconomyResponse withdrawPlayer(String playerName, double amount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EconomyResponse withdrawPlayer(OfflinePlayer player, double amount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EconomyResponse withdrawPlayer(String playerName, String worldName, double amount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EconomyResponse withdrawPlayer(OfflinePlayer player, String worldName, double amount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EconomyResponse depositPlayer(String playerName, double amount) {
		Player player = Bukkit.getPlayer(playerName);
		UUID uuid = player.getUniqueId();
		double oldBalance = plugin.playerBank.get(uuid);
		plugin.playerBank.put(uuid, oldBalance + amount);
		return null;
	}

	@Override
	public EconomyResponse depositPlayer(OfflinePlayer player, double amount) {
		UUID uuid = player.getUniqueId();
		double oldBalance = plugin.playerBank.get(uuid);
		plugin.playerBank.put(uuid, oldBalance + amount);
		return null;
	}

	@Override
	public EconomyResponse depositPlayer(String playerName, String worldName, double amount) {
		Player player = Bukkit.getPlayer(playerName);
		UUID uuid = player.getUniqueId();
		double oldBalance = plugin.playerBank.get(uuid);
		plugin.playerBank.put(uuid, oldBalance + amount);
		return null;
	}

	@Override
	public EconomyResponse depositPlayer(OfflinePlayer player, String worldName, double amount) {
		UUID uuid = player.getUniqueId();
		double oldBalance = plugin.playerBank.get(uuid);
		plugin.playerBank.put(uuid, oldBalance + amount);
		return null;
	}

	@Override
	public EconomyResponse createBank(String name, String player) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EconomyResponse createBank(String name, OfflinePlayer player) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EconomyResponse deleteBank(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EconomyResponse bankBalance(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EconomyResponse bankHas(String name, double amount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EconomyResponse bankWithdraw(String name, double amount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EconomyResponse bankDeposit(String name, double amount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EconomyResponse isBankOwner(String name, String playerName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EconomyResponse isBankOwner(String name, OfflinePlayer player) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EconomyResponse isBankMember(String name, String playerName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EconomyResponse isBankMember(String name, OfflinePlayer player) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getBanks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean createPlayerAccount(String playerName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean createPlayerAccount(OfflinePlayer player) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean createPlayerAccount(String playerName, String worldName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean createPlayerAccount(OfflinePlayer player, String worldName) {
		// TODO Auto-generated method stub
		return false;
	}

}
