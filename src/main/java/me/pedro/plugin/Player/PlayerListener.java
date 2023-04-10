package me.pedro.plugin.Player;

import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.pedro.plugin.Core;

public class PlayerListener implements Listener {
	
	private final Pattern ipPattern = Pattern.compile("([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])");
	private final Pattern ipPattern2 = Pattern.compile("([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\,([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\,([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\,([01]?\\d\\d?|2[0-4]\\d|25[0-5])");
	private final Pattern ipPattern4 = Pattern.compile("([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\{dot}([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\{dot}([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\{dot}([01]?\\d\\d?|2[0-4]\\d|25[0-5])");
	private final Pattern ipPattern5 = Pattern.compile("([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\[dot]([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\[dot]([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\[dot]([01]?\\d\\d?|2[0-4]\\d|25[0-5])");

//	private final NumberFormat nf = NumberFormat.getInstance(Locale.getDefault());
	
	
	private Core plugin;
	
	public PlayerListener(Core plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onLogin(PlayerLoginEvent e) {
		plugin.database.createPorfile(e.getPlayer());
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player p = (Player) event.getPlayer();
		
		
		p.sendMessage("§c§lDeathZone §4§lNetwork");
		p.sendMessage("");
		p.sendMessage("§7Welcome §e" + p.getName());
		p.sendMessage("§7Online §e" + Bukkit.getOnlinePlayers().size() + "§7/§e" + Bukkit.getMaxPlayers());
		p.sendMessage("§7Balance §e" + plugin.data.getMoney(p));
//		MessageConstrutor.sendCenteredMessage(p, "&7Money &a$" + plugin.data.getMoney(p));
		event.setJoinMessage(null);
		p.sendTitle("", "", 0, 0, 0);
		
		
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerChat(AsyncPlayerChatEvent chat) {
	
		if(!chat.getPlayer().hasPermission("deathzone.admin")){
			Player player = chat.getPlayer();
			String message = chat.getMessage();
			if(ipPattern.matcher(message).find() ||
			   ipPattern2.matcher(message).find() ||
			   ipPattern4.matcher(message).find() ||
			   ipPattern5.matcher(message).find() 
					|| message.contains(".com") || 
					   message.contains(".org") ||
					   message.contains(".net") ||
					   message.contains(".us") ||
					   message.contains(".uk") ||
					   message.contains(".ml") ||
					   message.contains(".cf") ||
					   message.contains(".tk") ||
					   message.contains(".info") ||
					   message.contains(".biz") ||
					   message.contains(".eu") ||
					   message.contains(".es") ||
					   message.contains(".fr") ||
					   message.contains(",com") ||
					   message.contains(",org") ||
					   message.contains(",net") ||
					   message.contains(",us") ||
					   message.contains(",ml") ||
					   message.contains(",cf") ||
					   message.contains(",tk") ||
					   message.contains(",info") ||
					   message.contains(",biz") ||
					   message.contains(",eu") ||
					   message.contains(",es") ||
					   message.contains(",fr") ||
					   message.contains(",uk")) {
				chat.setCancelled(true);
				player.sendMessage(" §4§l»§c DO NOT ADVERTISE!");
				player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 8.0F, 1.0F);
			}
		}
	}
  
	@EventHandler
	public void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent e) {
		
		Player p = e.getPlayer();
		Location loc = p.getLocation();
		
		if (Bukkit.getHelpMap().getHelpTopic(e.getMessage().split(" ")[0]) == null) {
			e.setCancelled(true);
			e.getPlayer().sendMessage("§4§l»§7 Unknown command. Type /help for help.");
			p.playSound(loc, Sound.BLOCK_NOTE_BLOCK_BASS, 8.0F, 1.0F);
		}
		
		if (!p.hasPermission("deathzone.admin")) {
			if((e.getMessage().equalsIgnoreCase("/bukkit:?")) ||
					(e.getMessage().equalsIgnoreCase("/bukkit:pl")) || 
					(e.getMessage().toLowerCase().startsWith("/?")) || 
					(e.getMessage().toLowerCase().startsWith("/pl")) ||
					(e.getMessage().toLowerCase().startsWith("/bukkit:pl")) || 
					(e.getMessage().toLowerCase().startsWith("/bukkit:plugins")) ||
					(e.getMessage().toLowerCase().startsWith("/ver")) ||
					(e.getMessage().toLowerCase().startsWith("/bukkit:ver")) ||
					(e.getMessage().toLowerCase().startsWith("/bukkit:version")) ||
					(e.getMessage().toLowerCase().startsWith("/version"))){
				e.setCancelled(true);
				p.playSound(loc, Sound.BLOCK_NOTE_BLOCK_BASS, 8.0F, 1.0F);
				e.getPlayer().sendMessage("§7 You do not have permission to do that!");
			}
			return;
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		
	}
}