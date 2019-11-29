package nl.ThorbenBP.ShadowCraftWorking.events;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import nl.ThorbenBP.ShadowCraftWorking.FileManager;
import nl.ThorbenBP.ShadowCraftWorking.Main;
import nl.ThorbenBP.ShadowCraftWorking.wg.WG;

public class VisserEvents implements Listener {
	
	public static FileManager fm = Main.fm;
	
	@EventHandler
	public void onFishStart(PlayerFishEvent e) {
		if(fm.getData().getString("Players." + e.getPlayer().getName() + ".Baan").equalsIgnoreCase("visser")) {
		if (e.getState() == State.FISHING) {
			//Gestart met vissen! Check op region!
			ArrayList<ProtectedRegion> loc = WG.getRegionsIn(e.getPlayer().getLocation());
			for (ProtectedRegion region : loc) {
				//Alle regions waar hij/zij in staat!
			    if (region.getId().equalsIgnoreCase("visgebied")) {
			        Main.fishcooldown.put(e.getPlayer().getName(), System.currentTimeMillis());
			        return;
			    }
			}
			//NIKS GEVONDEN!
			e.setCancelled(true);
			e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', fm.getMessages().getString("Messages.NietInGebied")));
			return;
		} else if(e.getState() == State.BITE) {
			//Bijtende vis!
			int cooldownTime = ThreadLocalRandom.current().nextInt(10, 45 + 1);
	    	if(Main.fishcooldown.containsKey(e.getPlayer().getName())) {
	            long secondsLeft = ((Main.fishcooldown.get(e.getPlayer().getName())/1000)+cooldownTime) - (System.currentTimeMillis()/1000);
	            if(secondsLeft>0) {
	                // Nog in cooldown
	            	e.setCancelled(true);
	                return;
	            }
	        }
		}
		}
	}

}
