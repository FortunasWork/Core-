package me.pedro.plugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.command.CommandMap;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.pedro.plugin.Commands.bankCommand;
import me.pedro.plugin.Commands.helpCommand;
import me.pedro.plugin.Commands.pmCommand;
import me.pedro.plugin.Player.PlayerData;
import me.pedro.plugin.Player.PlayerListener;
import me.pedro.plugin.ScoreBoard.FastBoardRegistry;
import net.md_5.bungee.api.ChatColor;

public final class Core extends JavaPlugin {
	
	public Bank economyEmplementar = new Bank();
	public PlayerData data = new PlayerData();
	public MongoDB database = new MongoDB(this);
	public static Core plugin;
	
	public PluginManager pm = Bukkit.getServer().getPluginManager();
	
	public final HashMap<UUID, Double> playerBank = new HashMap<>();
	
	private Vault vault= new Vault(this);
	private final FastBoardRegistry registry = new FastBoardRegistry(this).setOnRegister(board -> { 
		board.updateTitle("§4§lDeathZone");})
			
			.setOnPeriod(board -> {
		            
			board.updateTitle("§4§lDeathZone");
			board.updateLines(
			        "",
			        ChatColor.translateAlternateColorCodes('&', "&7Rank"),
			        ChatColor.translateAlternateColorCodes('&', "&adefault"),
			        "",
			        ChatColor.translateAlternateColorCodes('&', "&7Money"),
			        ChatColor.translateAlternateColorCodes('&', "&a0"),
			        "",
			        ChatColor.translateAlternateColorCodes('&', "&7Kills "),
			        ChatColor.translateAlternateColorCodes('&', "&a") + board.getPlayer().getStatistic(Statistic.PLAYER_KILLS),
			        "",
			        ChatColor.translateAlternateColorCodes('&', "&7Website "),
			        ChatColor.translateAlternateColorCodes('&', "&aComing soon "),
			        "");
			});
	
	@Override
	public void onEnable() {
		plugin = this;
		
		pm.registerEvents(new PlayerListener(this), this);
		registry.initiate();
		
		database.playerDataHashMap = new HashMap<>();
		database.mongoConnect();
		vault.hook();
		
	}
	
	@Override
	public void onLoad() {
		loadConfig();
		loadCommands();
	}
	
	
	@Override
	public void onDisable() {
		vault.unHook();
		super.onDisable();
	}
	
	public void loadCommands() {
		
        try {
            Field commandMapField = pm.getClass().getDeclaredField("commandMap");
            
            
            commandMapField.setAccessible(true);

            CommandMap commandMap = (CommandMap) commandMapField.get(getServer().getPluginManager());

            commandMap.register("help", new helpCommand());
            commandMap.register("pm", new pmCommand());
            commandMap.register("bank", new bankCommand());
        } 
        
        catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }		
	}

	public void loadConfig(){
		
		File config = new File("plugins/Core/settings.yml");
		YamlConfiguration c = new YamlConfiguration();
		YamlConfiguration.loadConfiguration(config);
		
		c.addDefault("prefix", "&c&lServer&4&l »&7 ");
		
		c.addDefault("noperm", "&cYou do not have permission");
		c.addDefault("permission", "server.admin");
		c.addDefault("unkown-command", "Unknown command. Type /help for help.");
		c.addDefault("Broadcast-Command", Text("&c&lServer &4&lSTAFF BROADCAST", "", "&4&l»&r&7 {msg}", "", "&7By:&c {sender}"));
		
		c.addDefault("Kick", Text("{serverName}" , "&cServer is now Maintenance!", "go to http://www.exemple.com/ for more info."));
		c.addDefault("Kick-msg", "You will be kick from the server in &c&l{time}");
		
		c.addDefault("Maintenance", false);
		c.addDefault("m-on", "&4&lMaintenance&r&c mode has been enable!");
		c.addDefault("m-off", "&4&lMaintenance&r&c mode has been disable!");
		c.addDefault("m-countdown", 5);
		
		c.addDefault("MaintenanceOn-version", "&4&lMaintenance");
		c.addDefault("MaintenanceOff-version", "&a&lServer");
		c.addDefault("Protocol-version", -1);
		c.addDefault("use-protocol-in-disable", true);
		
		c.options().copyDefaults(true);
		
		try {
			c.save(config);
		} 
		
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<String> Text(String... text){
		return Arrays.asList(text);
	}
	
	public String getFormatedTime(int seconds) {
		int minutes = seconds / 60;
	    int hours = minutes / 60;
	    int days = hours / 24;
	    
	    seconds -= minutes * 60;
	    minutes -= hours * 60;
	    hours -= days * 24;
	    
	    String s = "";
	    if (days > 0) {
	    	s = s + days + "d";
	    }
	    
	    if (hours > 0) {
	    	s = s + hours + "h";
	    }
	    
	    if (minutes > 0) {
	    	s = s + minutes + "m";
	    }
	    
	    if (seconds > 0) {
	    	s = s + seconds + "s";
	    }
	    return s;
	}
	
    @SuppressWarnings("unused")
	private double round(double value, int decimals) {
    	return Math.round(value * (double) Math.pow(10, decimals)) / (double) Math.pow(10, decimals);
    }
    
    public static Core getPlugin() {
    	return plugin;
    }
}
