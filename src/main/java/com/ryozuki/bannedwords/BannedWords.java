package com.ryozuki.bannedwords;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class BannedWords extends JavaPlugin implements CommandExecutor {
	public FileConfiguration Config = getConfig();
	private ChatListener chatlistener;
	
	@Override
	public void onEnable() {
		setupConfig();
		chatlistener = new ChatListener(Config.getStringList("Words"), Config.getString("CensorChar"));
		getServer().getPluginManager().registerEvents(chatlistener, this);
		this.getCommand("bw").setExecutor(this);
	}
	
	@Override
	public void onDisable() {
	}
	
	private void setupConfig() {
		java.util.Map<String, Object> conf = new HashMap<String, Object>();
		List<String> defaultWords = new ArrayList<String>();
		defaultWords.add("fuck");
		defaultWords.add("bitch");
		defaultWords.add("asshole");
		conf.put("CensorChar", "*");
		conf.put("Words", defaultWords);
		Config.addDefaults(conf);
		Config.options().copyDefaults(true);
		saveConfig();
	}
	
	@Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length == 0)
        {
			sender.sendMessage(ChatColor.YELLOW + cmd.getUsage());
        	return true;
        }
        
        switch (args[0]) {
		case "help":
			sender.sendMessage(ChatColor.BOLD + "BannedWords commands: (Made by Ryozuki)");
			sender.sendMessage(ChatColor.GRAY + "---------------------");
			sender.sendMessage(ChatColor.GOLD + "/bw add " + ChatColor.DARK_GRAY + "<word>");
			sender.sendMessage(ChatColor.GOLD + "/bw del " + ChatColor.DARK_GRAY + "<word>");
			sender.sendMessage(ChatColor.GOLD + "/bw replace " + ChatColor.DARK_GRAY + "<char>");
			break;
		case "add":
			if(args.length < 2) {
				sender.sendMessage("Correct usage: " + ChatColor.GOLD + "/bw add " + 
						ChatColor.DARK_GRAY + "<word>");
				break;
			}
			chatlistener.AddWord(args[1]);
			sender.sendMessage(ChatColor.GREEN + "Success! Now censoring the word: " + 
					ChatColor.BOLD + args[1]);
			Config.set("Words", chatlistener.GetWords());
			saveConfig();
			break;
		case "del":
			if(args.length < 2) {
				sender.sendMessage("Correct usage: " + ChatColor.GOLD + "/bw del " + 
						ChatColor.DARK_GRAY + "<word>");
				break;
			}
			chatlistener.DelWord(args[1]);
			sender.sendMessage(ChatColor.GREEN + "Success! Now players are free to use the word: " + 
					ChatColor.BOLD + args[1]);
			Config.set("Words", chatlistener.GetWords());
			saveConfig();
			break;
		case "replace":
			if(args.length < 2) {
				sender.sendMessage("Correct usage: " + ChatColor.GOLD + "/bw replace " + 
						ChatColor.DARK_GRAY + "<char>");
				break;
			}
			chatlistener.SetCensor(args[1]);
			sender.sendMessage(ChatColor.GREEN + "Success! Now words will be censored with: " + 
					ChatColor.BOLD + args[1]);
			Config.set("CensorChar", args[1]);
			saveConfig();
			break;
		default:
			sender.sendMessage(ChatColor.RED + "Command not found, " + cmd.getUsage());
			break;
		}

        // If the player (or console) uses our command correct, we can return true
        return true;
    }
}
