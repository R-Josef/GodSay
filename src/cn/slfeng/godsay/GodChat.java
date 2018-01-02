package cn.slfeng.godsay;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class GodChat implements Listener {
	private final Main plugin;

	public GodChat(Main plugin) {
		this.plugin =plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);// 在PluginManager中注册监听器
	}

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent chat) {// 玩家聊天时随机触发GodSay的方法
		String prob = plugin.getConfig().getString("godsay.prob");
		if (prob.contains("%")) {
			float probfloat = Float.parseFloat(prob.replaceAll("%", ""));
			float f = probfloat / 100;
			if (Math.random() < f) {
				Bukkit.broadcastMessage(plugin.GodSay().replaceAll("&", "§"));
			}
		}
	}
}
