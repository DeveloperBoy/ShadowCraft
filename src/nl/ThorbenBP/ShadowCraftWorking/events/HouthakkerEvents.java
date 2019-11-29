package nl.ThorbenBP.ShadowCraftWorking.events;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import nl.ThorbenBP.ShadowCraftWorking.FileManager;
import nl.ThorbenBP.ShadowCraftWorking.Main;
import nl.ThorbenBP.ShadowCraftWorking.wg.WG;

public class HouthakkerEvents implements Listener {
	
	public static FileManager fm = Main.fm;
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onBreakLogs(BlockBreakEvent e) {
		if(fm.getData().getString("Players." + e.getPlayer().getName() + ".Baan").equalsIgnoreCase("houthakker")) {
			if (e.getBlock().getType() == Material.LOG && e.getBlock().getData() == (byte)0 && e.getPlayer().getInventory().getItemInMainHand().getType() == Material.IRON_AXE) {
		ArrayList<ProtectedRegion> loc = WG.getRegionsIn(e.getPlayer().getLocation());
		for (ProtectedRegion region : loc) {
			//Alle regions waar hij/zij in staat!
		    if (region.getId().equalsIgnoreCase("houtgebied")) {
		    		//Goede gebied en het is een oak wood log!
		    	    Main.plugin.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
	                    public void run() {
	                    	Block locblock = e.getBlock();
	                    	locblock.setType(Material.LOG);
	                    	locblock.setData((byte) 0);
	                    }
	                }, 20 * 60 * 5);
		    	    return;
		    }
		}
		//NIKS GEVONDEN!
		e.setCancelled(true);
		e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', fm.getMessages().getString("Messages.NietInGebied")));
		return;
			}
		}
	}
}
