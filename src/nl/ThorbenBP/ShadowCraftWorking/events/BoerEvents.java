package nl.ThorbenBP.ShadowCraftWorking.events;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.CropState;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.material.Crops;
import org.bukkit.material.MaterialData;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import nl.ThorbenBP.ShadowCraftWorking.FileManager;
import nl.ThorbenBP.ShadowCraftWorking.Main;
import nl.ThorbenBP.ShadowCraftWorking.wg.WG;

public class BoerEvents implements Listener {
	
	public static FileManager fm = Main.fm;
	
	@EventHandler
	public void onBreakLogs(BlockBreakEvent e) {
		if(fm.getData().getString("Players." + e.getPlayer().getName() + ".Baan").equalsIgnoreCase("boer")) {
			MaterialData md = e.getBlock().getState().getData();
			Crops crop = null;
			if(md instanceof Crops) {
				 crop = (Crops) md;
			} else {
				return;
			}
			if(e.getPlayer().getInventory().getItemInMainHand().getType() != Material.IRON_HOE || !crop.getState().equals(CropState.RIPE)) {
				//GEEN IRON HOE!
				e.setCancelled(true);
				return;
			} else {
			ArrayList<ProtectedRegion> loc = WG.getRegionsIn(e.getPlayer().getLocation());
			for (ProtectedRegion region : loc) {
				//Alle regions waar hij/zij in staat!
			    if (region.getId().equalsIgnoreCase("farmgebied")) {
			    	Material mat = e.getBlock().getType();
			    	
			    		//Goede gebied en het is wheat, een potato of een carrot!
			    	    Main.plugin.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
		                    public void run() {
		                    	Block locblock = e.getBlock();
		                    	locblock.setType(mat);
		                    	
		                    	Crops crop = (Crops) locblock.getState();
		                    	crop.setState(CropState.VERY_SMALL);
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
