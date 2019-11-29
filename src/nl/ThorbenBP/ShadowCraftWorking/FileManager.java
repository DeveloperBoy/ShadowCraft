package nl.ThorbenBP.ShadowCraftWorking;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

public class FileManager {
	
	private FileManager() { }
	
	static FileManager instance = new FileManager();
	
	public static FileManager getInstance() {
		return instance;
	}
	
	Plugin p;
	
	FileConfiguration config;
	File cfile;
	
	FileConfiguration data;
	File dfile;
	
	FileConfiguration messages;
	File mfile;
	
	public void setup(Plugin p) {
		
		if(!p.getDataFolder().exists()) {
			p.getDataFolder().mkdir();
		}
		
		cfile = new File(p.getDataFolder(), "config.yml");
		
		if(!cfile.exists()) {
			try {
				cfile.createNewFile();
			} catch (IOException e) {
				Bukkit.getServer().getLogger().severe(ChatColor.DARK_RED + "Het maken van de config.yml is mislukt!");
			}
		}
		config = p.getConfig();
		config.options().copyDefaults(true);
		saveConfig();
		
		//
		
		dfile = new File(p.getDataFolder(), "data.yml");
		
		if(!dfile.exists()) {
			try {
				dfile.createNewFile();
			} catch (IOException e) {
				Bukkit.getServer().getLogger().severe(ChatColor.DARK_RED + "Het maken van de data.yml is mislukt!");
			}
		}
		
		data = YamlConfiguration.loadConfiguration(dfile);
		
		//
		
		mfile = new File(p.getDataFolder(), "messages.yml");
		
		if(!mfile.exists()) {
			try {
				mfile.createNewFile();
			} catch (IOException e) {
				Bukkit.getServer().getLogger().severe(ChatColor.DARK_RED + "Het maken van de messages.yml is mislukt!");
			}
		}
		
		messages = YamlConfiguration.loadConfiguration(mfile);
	}
	
	//SETUP IS KLAAR. NU KOMEN DE ANDERE DINGEN!
	
	//DATA DEEL
	
	public FileConfiguration getData() {
		return data;
	}
	
	public void saveData() {
		try {
			data.save(dfile);
		} catch(IOException e) {
			Bukkit.getServer().getLogger().severe(ChatColor.DARK_RED + "Het opslaan van de data.yml is mislukt!");
		}
	}
	
	public void reloadData() {
		data = YamlConfiguration.loadConfiguration(dfile);
	}
	
	//CONFIG DEEL
	
	public FileConfiguration getConfig() {
		return config;
	}
	
	public void saveConfig() {
		try {
			config.save(cfile);
		} catch(IOException e) {
			Bukkit.getServer().getLogger().severe(ChatColor.DARK_RED + "Het opslaan van de config.yml is mislukt!");
		}
	}
	
	public void reloadConfig() {
		config = YamlConfiguration.loadConfiguration(cfile);
	}
	
	//Mess DEEL
	
		public FileConfiguration getMessages() {
			return messages;
		}
		
		public void saveMessages() {
			try {
				messages.save(mfile);
			} catch(IOException e) {
				Bukkit.getServer().getLogger().severe(ChatColor.DARK_RED + "Het opslaan van de messages.yml is mislukt!");
			}
		}
		
		public void reloadMessages() {
			messages = YamlConfiguration.loadConfiguration(mfile);
		}
	
	//Overige delen
	
	public PluginDescriptionFile getDesc() {
		return p.getDescription();
	}

}
