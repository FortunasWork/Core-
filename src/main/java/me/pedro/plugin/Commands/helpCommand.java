package me.pedro.plugin.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

public class helpCommand extends BukkitCommand {

	public helpCommand() {
		super("help");
		this.description = "Help";
		this.usageMessage = "/help - to get all the info";
		this.setPermission("default.player");
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		
		sender.sendMessage(" §c§lDEATHZONE §4§lNETWORK");
		sender.sendMessage(" §cAll the help you can get!");
		sender.sendMessage("");
		
		sender.sendMessage(" §4§l»§7 /pm - Private Message other Players");
		sender.sendMessage(" §4§l»§7 /clean - Clean Chat");
		sender.sendMessage(" §4§l»§7 /report - Report a Player");
		sender.sendMessage(" §4§l»§7 /bank - Check balance & transfer funds");
		sender.sendMessage("");
		
		sender.sendMessage(" §cNeed more Help? Look for a staff member.");
		
		return false;
	}

}
