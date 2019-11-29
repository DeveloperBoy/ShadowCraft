package nl.ThorbenBP.ShadowCraftWorking.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import nl.ThorbenBP.ShadowCraftWorking.FileManager;
import nl.ThorbenBP.ShadowCraftWorking.Main;

public class SetJobCommandExecutor implements CommandExecutor {
	
	@SuppressWarnings("unused")
	private final Main plugin;
	public static FileManager fm = Main.fm;
	public static String plprefix = ChatColor.translateAlternateColorCodes('&', fm.getConfig().getString("Settings.MessagePrefix")) + " ";

	public SetJobCommandExecutor(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length == 2){
			if(sender.hasPermission("scw.setjob")) {
				Player psended = Bukkit.getPlayer(args[0]);
				if(psended == null) {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', fm.getMessages().getString("Messages.PlayerDoesntExists").replaceAll("<Player>", args[0])));
					return false;
				}
				String baan = args[1];
				if(baan.equalsIgnoreCase("Miner") || baan.equalsIgnoreCase("Visser") ||  baan.equalsIgnoreCase("Boer") ||  baan.equalsIgnoreCase("Houthakker")) {
					//GOED! Doorgaan!
				} else {
					//FOUTE BAAN! ERROR EN RETURN!
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', fm.getMessages().getString("Messages.VerkeerdeBaan")));
					return false;
				}
				fm.getData().set("Players." + psended.getName() + ".Baan", baan);
				fm.saveData();
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', fm.getMessages().getString("Messages.BaanIngesteld").replaceAll("<Player>", psended.getName()).replaceAll("<Baan>", baan)));
				return true;
			} else {
				return false;
			}
		} else {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', fm.getMessages().getString("Messages.NoCommandArgs")));
			return false;
		}
	}

}
