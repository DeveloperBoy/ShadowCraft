package nl.ThorbenBP.ShadowCraftWorking.events;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import nl.ThorbenBP.ShadowCraftWorking.FileManager;
import nl.ThorbenBP.ShadowCraftWorking.Main;

public class GUIClick implements Listener {
	
	public static FileManager fm = Main.fm;
	
	@EventHandler
	public void onInventoryClick (InventoryClickEvent e) {
		if (e.getInventory().getName().equals(ChatColor.translateAlternateColorCodes('&', fm.getConfig().getString("Settings.MenuName")))) {
			e.setCancelled(true);
			if (e.getCurrentItem() == null || e.getCurrentItem().getItemMeta() == null
					|| e.getCurrentItem().getType() == Material.AIR || e.getCurrentItem().getType() == null
					|| e.getCurrentItem().getItemMeta().getDisplayName() == null) {
				return;
			} else {
				Player p = (Player) e.getWhoClicked();
				ItemStack item = e.getInventory().getItem(e.getSlot());
				if (!item.getType().equals(Material.AIR)) {
					//Controles gehad!
					
					if(item.getType().equals(Material.IRON_HOE)) {
						//BOER!
						String baan = "boer";
						fm.getData().set("Players." + p.getName() + ".Baan", baan);
						fm.saveData();
						p.closeInventory();
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', fm.getMessages().getString("Messages.BaanIngesteld").replaceAll("<Player>", p.getName()).replaceAll("<Baan>", baan)));
					} else if(item.getType().equals(Material.IRON_AXE)) {
						//HOUTHAKKER!
						String baan = "houthakker";
						fm.getData().set("Players." + p.getName() + ".Baan", baan);
						fm.saveData();
						p.closeInventory();
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', fm.getMessages().getString("Messages.BaanIngesteld").replaceAll("<Player>", p.getName()).replaceAll("<Baan>", baan)));
					} else if(item.getType().equals(Material.IRON_PICKAXE)) {
						//Miner
						String baan = "miner";
						fm.getData().set("Players." + p.getName() + ".Baan", baan);
						fm.saveData();
						p.closeInventory();
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', fm.getMessages().getString("Messages.BaanIngesteld").replaceAll("<Player>", p.getName()).replaceAll("<Baan>", baan)));
					} else if(item.getType().equals(Material.FISHING_ROD)) {
						//Visser
						String baan = "visser";
						fm.getData().set("Players." + p.getName() + ".Baan", baan);
						fm.saveData();
						p.closeInventory();
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', fm.getMessages().getString("Messages.BaanIngesteld").replaceAll("<Player>", p.getName()).replaceAll("<Baan>", baan)));
					}
				}
			}
		}
	}

}
