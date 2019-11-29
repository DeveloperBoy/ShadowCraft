package nl.ThorbenBP.ShadowCraftWorking.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.milkbowl.vault.economy.EconomyResponse;
import nl.ThorbenBP.ShadowCraftWorking.FileManager;
import nl.ThorbenBP.ShadowCraftWorking.Main;
import nl.ThorbenBP.ShadowCraftWorking.events.GUICreator;

public class JobCommandExecutor implements CommandExecutor {
	
	@SuppressWarnings("unused")
	private final Main plugin;
	public static FileManager fm = Main.fm;
	public static String plprefix = ChatColor.translateAlternateColorCodes('&', fm.getConfig().getString("Settings.MessagePrefix")) + " ";

	public JobCommandExecutor(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length == 0) {
			Player p = (Player) sender;
			if(!fm.getData().getStringList("PlayerCommandMap").contains(p.getName())) {
				//1ste keer
				GUICreator.createMenu(p);
				return true;
			} else {
				//Vaker gedaan! BETALEN!
				if(!Main.topaymap.contains(p)) {
					//1ste keer uitgevoerd!
					Integer tebetalen = fm.getConfig().getInt("Settings.JobCommandCost");
					Main.topaymap.add(p);
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', fm.getMessages().getString("Messages.Betalen").replaceAll("<Bedrag>", tebetalen.toString())));
					return true;
				} else {
					//2de keer uitgevoerd!
					Main.topaymap.remove(p);
					Integer tebetalen = fm.getConfig().getInt("Settings.JobCommandCost");
					EconomyResponse r = Main.econ.withdrawPlayer(p, tebetalen);
					if(!r.transactionSuccess()) {
						//Betalen mislukt!
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', fm.getMessages().getString("Messages.BetalenMislukt")));
						return false;
					}
					GUICreator.createMenu(p);
					return true;
				}
			}
		} else {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', fm.getMessages().getString("Messages.NoCommandArgs")));
			return false;
		}
	}

}
