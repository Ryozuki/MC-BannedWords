package com.ryozuki.bannedwords;


import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {
	private List<String> words;
	private String censor;
	
	public ChatListener(List<String> words, String censor) {
		// TODO Auto-generated constructor stub
		this.words = words;
		this.censor = censor;
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		
		String msg = e.getMessage();		
		for (String word : words) {
			msg = msg.toLowerCase().replace(word, repeat(censor, word.length()));
		}
		e.setMessage(msg);
	}
	
	public void AddWord(String word)
	{
		if(!words.contains(word))
			words.add(word);
	}
	
	public void DelWord(String word)
	{
		words.remove(word);
	}
	
	public void SetCensor(String censor) {
		this.censor = censor;
	}
	
	public List<String> GetWords() {
		return words;
	}
	
	private String repeat(String str, int count)
	{
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < count; i++)
		{
			builder.append(str);
		}
		return builder.toString();
	}
}
