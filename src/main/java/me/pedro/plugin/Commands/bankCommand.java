package me.pedro.plugin.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import me.pedro.plugin.Core;

public class bankCommand extends BukkitCommand {
	
	private Core plugin = new Core();

	public bankCommand() {
		super("bank");
		this.description = "Check balance & transfer funds";
		this.usageMessage = "/banks <args> - deposite, tranfer, balance";
		this.setPermission("default.player");
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		
		if (!(sender instanceof Player)) {
			sender.sendMessage("Player only commmand!");
			return false;
		}
		
		Player senderPlayer = (Player) sender;
		
		if (!plugin.playerBank.containsKey(senderPlayer.getUniqueId())) {
			plugin.playerBank.put(senderPlayer.getUniqueId(), 0.0);
		}
		 
		if (args[1].equalsIgnoreCase("balance")) {
			
			if (args.length == 1) {
				 
				try {
					Player target = Bukkit.getPlayer(args[1]);
					int balance = (int) plugin.economyEmplementar.getBalance(target);
					target.sendMessage("Avalible Balance " + balance);
				}
				 
				catch (Exception e) {
					e.printStackTrace();
				}
				return true;
			 }
		 }
		
		 // TODO Tranfer funds
		 if (args[1].equalsIgnoreCase("tranfer")) {
			 
			 if (args.length == 1)
				 senderPlayer.sendMessage("/bank tranfer <player> <ammount>");
			 
			 if (args.length == 2) 
				 senderPlayer.sendMessage("/bank tranfer " + args[2] + " <amount>");
			 
			 if (args.length == 3) {
				 
				 Player target = Bukkit.getPlayer(args[2]);
				 int tranferAmount = Integer.parseInt(args[3]);
				 
				 try {
					 plugin.economyEmplementar.bankWithdraw(sender.getName(), tranferAmount);
					 plugin.economyEmplementar.depositPlayer(target, tranferAmount);
				 }
				 
				 catch (Exception e) {
					 e.printStackTrace();
				 }
				 
				 target.sendMessage("You've received " + tranferAmount + " from " + sender.getName());
				 senderPlayer.sendMessage("You've Transfered " + tranferAmount + " to " + target.getName());
			 }
		 }
		return false;
	}
}
