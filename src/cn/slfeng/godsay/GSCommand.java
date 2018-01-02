package cn.slfeng.godsay;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class GSCommand implements CommandExecutor {
	private final Main plugin;

	public GSCommand(Main plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginCommand("godsay").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		String reload = plugin.getConfig().getString("message.reload");// 读取message.reload
		String nopermission = plugin.getConfig().getString("message.nopermission");// 读取message.nopermission
		String unavailable = plugin.getConfig().getString("message.unavailable");// 读取message.unavailable
		String noplayer = plugin.getConfig().getString("message.noplayer");// 读取message.noplayer
		String oncooldown = plugin.getConfig().getString("message.oncooldown");
		if (args.length == 1) {// 如果子命令长度等于1
			switch (args[0]) {
			case "reload":// 如果子命令是reload
				if (sender.hasPermission("godsay.reload")) {// 判断权限
					plugin.reloadConfig();// 重载config
					sender.sendMessage(reload.replaceAll("&", "§"));// 聊天栏输出String
				} else {
					sender.sendMessage(nopermission.replaceAll("&", "§"));// 聊天栏输出nopermission的字符串
				}
				break;// 跳出switch
			case "hw":// 如果子命令是hw
				if (sender.hasPermission("godsay.hw")) {// 权限判定
					if (plugin.isOnCooldown(sender, Main.hwCD)) {
						sender.sendMessage(oncooldown.replaceAll("&", "§"));
					} else {
						sender.sendMessage(plugin.HelloWorld());// 聊天栏输出字符串数组hw
						Command.broadcastCommandMessage(sender, plugin.RandomBroadcast().replaceAll("&", "§"));// 聊天栏输出bc随机的字符串
					}
				} else {// 如果没有权限
					sender.sendMessage(nopermission.replaceAll("&", "§"));// 聊天栏输出nopermission的字符串
				}
				break;
			default:
				if (Bukkit.getPlayer(args[0]) != null) {// 如果子命令是玩家名
					if (sender.hasPermission("godsay.cmdsomeone")) {//权限判定
						if (plugin.isOnCooldown(sender, Main.godsaysomeoneCD)) {
							sender.sendMessage(oncooldown.replaceAll("&", "§"));
						} else {
							Bukkit.broadcastMessage(plugin.GodSaySomeone(sender.getName()).replaceAll("&", "§"));
						}
					} else {// 如果没有权限
						sender.sendMessage(nopermission.replaceAll("&", "§"));// 聊天栏输出nopermission的字符串
					}
				} else {
					sender.sendMessage(unavailable.replaceAll("&", "§"));// 聊天栏输出String
				}
				break;
			}
		}
		if (args.length == 0) {// 如果没有子命令
			if (sender.hasPermission("godsay.command")) {
				if (plugin.isOnCooldown(sender, Main.godsayCD)) {
					sender.sendMessage(oncooldown.replaceAll("&", "§"));
				} else {
					if (plugin.getRandomPlayer() == null) {// 如果没有抽取到玩家
						sender.sendMessage(noplayer.replaceAll("&", "§"));
					} else {// 如果抽取到玩家
						Bukkit.broadcastMessage(plugin.GodSay().replaceAll("&", "§"));// 输出Godsay组合
					}
				}
			} else {
				sender.sendMessage(nopermission.replaceAll("&", "§"));// 聊天栏输出nopermission的字符串
			}
		}
		if (args.length > 1) {// 如果子命令大于1
			sender.sendMessage(unavailable.replaceAll("&", "§"));// 聊天栏输出String
		}
		return false;
	}
}
