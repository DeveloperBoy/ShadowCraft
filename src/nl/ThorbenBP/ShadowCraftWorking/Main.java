package nl.ThorbenBP.ShadowCraftWorking;

import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.economy.Economy;
import nl.ThorbenBP.ShadowCraftWorking.commands.JobCommandExecutor;
import nl.ThorbenBP.ShadowCraftWorking.commands.SetJobCommandExecutor;
import nl.ThorbenBP.ShadowCraftWorking.events.BoerEvents;
import nl.ThorbenBP.ShadowCraftWorking.events.GUIClick;
import nl.ThorbenBP.ShadowCraftWorking.events.HouthakkerEvents;
import nl.ThorbenBP.ShadowCraftWorking.events.MinerEvents;
import nl.ThorbenBP.ShadowCraftWorking.events.VisserEvents;
import nl.ThorbenBP.ShadowCraftWorking.wg.WG;

public class Main extends JavaPlugin implements Listener {
	
	public static Main plugin;
	public static FileManager fm = FileManager.getInstance();
	public static ArrayList<Player> topaymap = new ArrayList<Player>();
	public static Economy econ = null;
	public static HashMap<String, Long> fishcooldown = new HashMap<String, Long>();
	
	@Override
	public void onEnable() {
		plugin = this;
		
		//Filemanager setup
		if(!plugin.getDataFolder().exists()) {
			plugin.getDataFolder().mkdir();
		}
		fm.setup(this);
		
		saveDefaultMessageFile();
		
		// Register WorldGuard and WorldEdit
		if (!setupPlugins()) {
			Bukkit.getLogger().severe(String.format("[%s] - Disabled due to no WorldGuard dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
		}
		
		if (!setupEconomy() ) {
            Bukkit.getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
		
		Bukkit.getPluginManager().registerEvents(this, this);
		Bukkit.getPluginManager().registerEvents(new BoerEvents(), this);
		Bukkit.getPluginManager().registerEvents(new HouthakkerEvents(), this);
		Bukkit.getPluginManager().registerEvents(new MinerEvents(), this);
		Bukkit.getPluginManager().registerEvents(new VisserEvents(), this);
		Bukkit.getPluginManager().registerEvents(new GUIClick(), this);
		
		this.getCommand("job").setExecutor(new JobCommandExecutor(this));
		this.getCommand("setjob").setExecutor(new SetJobCommandExecutor(this));
	}
	
	@Override
	public void onDisable() { }
	
	private boolean setupPlugins() {
		if (hasWorldGuardOnServer() && hasWorldEditOnServer()) {
			WG.setWorldGuard(getServer().getPluginManager().getPlugin("WorldGuard"));
			WG.setWorldEdit(getServer().getPluginManager().getPlugin("WorldEdit"));
			return true;
		}
		return false;
	}
	
	private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
	
	/**
	 * Does the server have world guard on it
	 * 
	 * @return true/false
	 */
	private static boolean hasWorldGuardOnServer() {
		return Bukkit.getPluginManager().getPlugin("WorldGuard") != null;
	}
	
	/**
	 * Does the server have world edit on it
	 * 
	 * @return true/false
	 */
	private static boolean hasWorldEditOnServer() {
		return Bukkit.getPluginManager().getPlugin("WorldEdit") != null;
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e) {
		Player p = (Player) e.getPlayer();
		if(Main.topaymap.contains(p)) {
			Main.topaymap.remove(p);
		}
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		Player p = (Player) e.getPlayer();
		if(Main.topaymap.contains(p)) {
			e.setCancelled(true);
			if(e.getMessage().equalsIgnoreCase("STOP")) {
				Main.topaymap.remove(p);
			}
		}
	}
	
	public void saveDefaultMessageFile() {
		
		Reader defConfigStream = null;
		try {
			defConfigStream = new InputStreamReader(getResource("messages.yml"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	    if (defConfigStream != null) {
	        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
	        fm.getMessages().setDefaults(defConfig);
	        fm.getMessages().options().copyDefaults(true);
			fm.saveMessages();
		
	    }
	}

}
