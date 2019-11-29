package nl.ThorbenBP.ShadowCraftWorking.events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import nl.ThorbenBP.ShadowCraftWorking.FileManager;
import nl.ThorbenBP.ShadowCraftWorking.Main;

public class GUICreator implements Listener {
	
	public static FileManager fm = Main.fm;
	public static String plprefix = ChatColor.translateAlternateColorCodes('&', fm.getConfig().getString("Settings.MessagePrefix")) + " ";
	
	public static void createMenu(Player p) {
		Inventory GUI = Bukkit.createInventory(null, 9, ChatColor.translateAlternateColorCodes('&', fm.getConfig().getString("Settings.MenuName")));
		
		//MINER
		Material material = Material.valueOf(fm.getConfig().getString("Settings.MinerItem.Material"));
		int data = fm.getConfig().getInt("Settings.MinerItem.Data");
		ItemStack item = new ItemStack(material, 1, (byte) data);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', fm.getConfig().getString("Settings.MinerItem.Name")));
		ArrayList<String> lorelist = new ArrayList<String>();
		for(String loredeel : fm.getConfig().getStringList("Settings.MinerItem.Lores")) {		
			if(loredeel == null) {
				Bukkit.getLogger().severe("Loredeel null");
			}
			String newloredeel = loredeel.replaceAll("&", "§");
			if(newloredeel == null) {
				Bukkit.getLogger().severe("newloredeel null");
			}
			if(lorelist == null) {
				Bukkit.getLogger().severe("lorelist null");
			}
			lorelist.add(newloredeel);
		}
		meta.setLore(lorelist);
		item.setItemMeta(meta);
		GUI.setItem(1, item);
		
		//VISSER
		Material material1 = Material.valueOf(fm.getConfig().getString("Settings.VisserItem.Material"));
		int data1 = fm.getConfig().getInt("Settings.VisserItem.Data");
		ItemStack item1 = new ItemStack(material1, 1, (byte) data1);
		ItemMeta meta1 = item1.getItemMeta();
		meta1.setDisplayName(ChatColor.translateAlternateColorCodes('&', fm.getConfig().getString("Settings.VisserItem.Name")));
		ArrayList<String> lorelist1 = new ArrayList<String>();
		for(String loredeel : fm.getConfig().getStringList("Settings.VisserItem.Lores")) {		
			String newloredeel = loredeel.replaceAll("&", "§");
			lorelist1.add(newloredeel);
		}
		meta1.setLore(lorelist1);
		item1.setItemMeta(meta1);
		GUI.setItem(3, item1);
		
		//BOER
		Material material11 = Material.valueOf(fm.getConfig().getString("Settings.BoerItem.Material"));
		int data11 = fm.getConfig().getInt("Settings.BoerItem.Data");
		ItemStack item11 = new ItemStack(material11, 1, (byte) data11);
		ItemMeta meta11 = item11.getItemMeta();
		meta11.setDisplayName(ChatColor.translateAlternateColorCodes('&', fm.getConfig().getString("Settings.BoerItem.Name")));
		ArrayList<String> lorelist11 = new ArrayList<String>();
		for(String loredeel : fm.getConfig().getStringList("Settings.BoerItem.Lores")) {		
			String newloredeel = loredeel.replaceAll("&", "§");
			lorelist11.add(newloredeel);
		}
		meta11.setLore(lorelist11);
		item11.setItemMeta(meta11);
		GUI.setItem(5, item11);
		
		//BOER
		Material material111 = Material.valueOf(fm.getConfig().getString("Settings.HouthakkerItem.Material"));
		int data111 = fm.getConfig().getInt("Settings.HouthakkerItem.Data");
		ItemStack item111 = new ItemStack(material111, 1, (byte) data111);
		ItemMeta meta111 = item111.getItemMeta();
		meta111.setDisplayName(ChatColor.translateAlternateColorCodes('&', fm.getConfig().getString("Settings.HouthakkerItem.Name")));
		ArrayList<String> lorelist111 = new ArrayList<String>();
		for(String loredeel : fm.getConfig().getStringList("Settings.HouthakkerItem.Lores")) {		
			String newloredeel = loredeel.replaceAll("&", "§");
			lorelist111.add(newloredeel);
		}
		meta111.setLore(lorelist111);
		item111.setItemMeta(meta111);
		GUI.setItem(7, item111);
		
		List<String> strnu = fm.getData().getStringList("PlayerCommandMap");
		if(!strnu.contains(p.getName())) {
			strnu.add(p.getName());
			fm.getData().set("PlayerCommandMap", strnu);
			fm.saveData();
		}
		
		//OPENEN!
		p.openInventory(GUI);
		
	}
}