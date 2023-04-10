package me.pedro.plugin.Player;

import java.util.UUID;

import org.bukkit.entity.Player;

public class PlayerData {
	
	private UUID uuid;
	private String rank;	
	private int money;
	
	public PlayerData() {
	}
	
	
	public PlayerData(UUID uuid, String rank, int money) {
		this.uuid = uuid;
		this.rank = rank;
		this.money = money;
	}
	
	/**
	 * @return the name
	 */
	
	public UUID getUUID(Player p) {
		return uuid;
	}
	
	public void setUUID(UUID uuid) {
		this.uuid = uuid;
	}
	
	public int getMoney(Player p) {
		return money;
	}
	
	public void addMoney(int money) {
		this.money = money;
	}
	
	public String getRank(Player p) {
		return rank;
	}
	
	public void setRank(String rank) {
		this.rank = rank;
	}
	
//	@SuppressWarnings("deprecation")
//	public void addPlayerFile(Player p) {
//		
//		File profile = new File("plugins/Core/Players/" + p.getUniqueId().toString() + ".yml");
//		YamlConfiguration config = new YamlConfiguration();
//		YamlConfiguration.loadConfiguration(profile);
//		String header = "# Player Profile";
//		
//		config.options().header(header);
//		config.addDefault("name", getName(p));
//		config.addDefault("rank", getRank(p));
//		config.addDefault("balance", (int) getMoney(p));	
//		config.options().copyDefaults(true);
//		
//		if (!profile.exists()) {
//		
//			try {
//				config.save(profile);
//			} 
//		
//			catch (IOException e) {
//				e.printStackTrace();
//			}
//			
//			plugin.getLogger().log(Level.INFO, "Profile creaded for " + p.getName().toString() + " UUID: " + p.getUniqueId().toString());
//		}
//	}
}