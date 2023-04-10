package me.pedro.plugin.Commands;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class pmCommand extends BukkitCommand{

	public pmCommand() {
		super("pm");
		this.description = "Send and recive Private Menssages";
		this.usageMessage = "/pm <player> <msg>";
		
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		
		if ((sender instanceof Player)) {
			Player p = (Player)sender;
			
			if (args.length == 0)
				p.sendMessage("§4§l»§r§7 Please use §c /pm <player> <msg>");
			
			else if (args.length == 1)
				p.sendMessage("§4§l»§r§7 Please use §c /pm" + args[1] + "<msg>");
			
			else if (args.length > 1) {
				StringBuilder str = new StringBuilder();
				
				for (int i = 1; i < args.length; i++)
					str.append(args[i] + " "); 
				
				Player target = (Player) Bukkit.getPlayerExact(args[0]);
				
				if (target != null) {
					
					if (p == target) {
						p.sendTitle("", "§c§lYou can't a pm your self!" , 10 , 20, 10);
						p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 8.0F, 1.0F);
						return false;
					}
					
					if (target.isOnline()) {
						target.sendMessage("§c§lPrivate Message");
						target.sendMessage("");
						target.sendMessage(" §4§l»§r§7 " + str.toString().trim());
						target.sendMessage("");
						
						TextComponent text = new TextComponent("§7From:§c " + p.getName());
						text.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,"/pm " + p.getName()));
						text.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§a§lClick to reply!").create()));
						
						target.spigot().sendMessage(text);
						
						p.sendTitle("", "§a§lYour message as been send!" , 10 , 20, 10);
					}
				} else p.sendTitle("", "§7That player is§c§l OFFLINE§r§7!", 10, 20, 10); 
			}
		} else sender.sendMessage("§cYou are not a player!");
		return true;
	}
}