package me.pedro.plugin.Commands.Managers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandManager implements 	CommandExecutor {
	
	private ArrayList<SubCommand> commands = new ArrayList<SubCommand>();
	
	public CommandManager() {
		
		commands.add(new infoCommand());
	}
	
	// Sub Commands
	public String help = "help";
	public String info = "info";
	
	//TODO: Finish whatever command is under this todo list.
	public String pm = "pm";
	public String money = "money";
	public String friends = "friends";
	
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.DARK_RED + "Player only command!");
		}
		
		if (cmd.getName().equalsIgnoreCase(info)) {
			Player p = (Player) sender;
			
			if(args.length == 0) {
				p.sendMessage("/info 1");
			}
			
			SubCommand target = this.get(args[0]);
			
			if (target == null) {
				p.sendMessage(ChatColor.DARK_RED + "Invalid argument!");
				return true;
			}
			
			ArrayList<String> arrayList = new ArrayList<String>();
			arrayList.addAll(Arrays.asList(args));
			arrayList.remove(0);
			
			try {
				target.onCommand(p, args);
			}  
			
			catch (Exception e) {
				p.sendMessage("ERROR");
			}
		}
		
		if (cmd.getName().equalsIgnoreCase(help)) {
			Player p = (Player) sender;
			
			if(args.length == 0) {
				p.sendMessage("DeathZone Network - HELP");
				p.sendMessage("");
				p.sendMessage("");
				p.sendMessage("");
				p.sendMessage("");
			}
			
			SubCommand target = get(args[0]);
			
			if (target == null) {
				p.sendMessage(ChatColor.DARK_RED + "/help <command>");
				return true;
			}
			
			ArrayList<String> arrayList = new ArrayList<String>();
			arrayList.addAll(Arrays.asList(args));
			arrayList.remove(0);
			
			try {
				target.onCommand(p, args);
			}  
			
			catch (Exception e) {
				p.sendMessage("ERROR");
			}
		}
		return true;
	}
	
	
	private SubCommand get(String name) {
		Iterator<SubCommand> cmdSub = this.commands.iterator();
		
		while (cmdSub.hasNext()) {
			SubCommand sub = (SubCommand) cmdSub.next();
			
			if (sub.name().equalsIgnoreCase(info)) {
				return sub;
			}
			
			String[] aliases;
			int lenght = (aliases = sub.aliases()).length;
			
			for (int i = 0; i < lenght; i++) {
				String alias = aliases[i];
				
				if (name.equalsIgnoreCase(alias)) {
					return sub;
				}
			}
			
		}
		return null;
	}	
}