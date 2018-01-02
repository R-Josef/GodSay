package cn.slfeng.godsay;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

	public static Map<String, Long> godsayCD = new HashMap<>();// 声明godsay的冷却的hashmap
	public static Map<String, Long> hwCD = new HashMap<>();// 声明hwCD的冷却hashmap
	public static Map<String, Long> godsaysomeoneCD = new HashMap<>();
	@Override
	public void onEnable() {// 插件载入时要执行的代码
		new GodChat(this);// 读取Godsay方法中注册的监听器
		new GSCommand(this);// 读取HWCommand方法中注册的执行者
		this.saveDefaultConfig();// 没有config就复制一份
		getConfig();// 先加载一次config
		String enable = getConfig().getString("message.enable");// 读取message.enable
		getLogger().info(enable);// log输出
	}

	@Override
	public void onDisable() { // 插件卸载时要执行的代码
		String disable = getConfig().getString("message.disable");// 读取message.disable
		getLogger().info(disable); // log输出
	}

	public Player getRandomPlayer() {// 定义获取随机玩家的方法
		if (Bukkit.getOnlinePlayers().size() != 0) {// 如果在线玩家大于1
			int random = (int) (Math.random() * Bukkit.getOnlinePlayers().size());// 计算一个小于总大小的随机数
			return (Player) getServer().getOnlinePlayers().toArray()[random];// 返回这名随机的玩家
		}
		return null;// 没有玩家就返回null
	}

	public boolean isOnCooldown(CommandSender sender, Map<String, Long> cooldown) {// 定义是否冷却的方法
		int cdint = Integer.parseInt(getConfig().getString("cooldown"));// 读取cooldown的设置并转成数字
		Long t = cooldown.get(sender.getName());// 获取玩家名在hashmap中的值
		if (t != null && t > System.currentTimeMillis()) {// 如果值不为空而且大于系统时间
			return true;// 返回true
		} else {// 如果不是
			long cd = System.currentTimeMillis() + cdint;// 计算加上cd以后的时间，并赋予变量bc
			cooldown.put(sender.getName(), cd);// 在hashmap中添加玩家和他的冷却的键和值
			return false;// 返回false
		}
	}

	public String GodSay() {// 神说的方法
		String god = getConfig().getString("message.god");// 读取message.god
		String colon = getConfig().getString("message.colon");// 读取message.colon中的冒号
		String at = getConfig().getString("message.at");// 读取message.at中的"在"
		List<String> gsskey = getConfig().getStringList("godsay.site");// 读取地点列表
		String[] gss = gsskey.toArray(new String[gsskey.size()]);// 具体地点
		List<String> gsekey = getConfig().getStringList("godsay.event");// 读取事件列表
		String[] gse = gsekey.toArray(new String[gsekey.size()]);// 具体事件
		int numgss = (int) (Math.random() * gsskey.size());// gss的随机数
		int numgse = (int) (Math.random() * gsekey.size());// ges的随机数
		String playername = getRandomPlayer().getName();// 获得随机玩家的名字
		return god + colon + playername + at + gss[numgss] + gse[numgse];// 返回组合
	}
	
	public String GodSaySomeone(String playername) {// 神说,针对指定玩家
		String god = getConfig().getString("message.god");// 读取message.god
		String colon = getConfig().getString("message.colon");// 读取message.colon中的冒号
		String at = getConfig().getString("message.at");// 读取message.at中的"在"
		List<String> gsskey = getConfig().getStringList("godsay.site");// 读取地点列表
		String[] gss = gsskey.toArray(new String[gsskey.size()]);// 具体地点
		List<String> gsekey = getConfig().getStringList("godsay.event");// 读取事件列表
		String[] gse = gsekey.toArray(new String[gsekey.size()]);// 具体事件
		int numgss = (int) (Math.random() * gsskey.size());// gss的随机数
		int numgse = (int) (Math.random() * gsekey.size());// ges的随机数
		return god + colon + playername + at + gss[numgss] + gse[numgse];// 返回组合
	}

	public String[] HelloWorld() {// 输出HelloWorld的方法
		List<String> hwkey = getConfig().getStringList("helloworld".replaceAll("&","§"));// 从helloworld里面获取一个List<String>
		return hwkey.toArray(new String[hwkey.size()]);// 字符串数组hw，List转String[]
	}

	public String RandomBroadcast() {// 随机广播消息的方法
		List<String> bckey = getConfig().getStringList("randombroadcast");// 从randombroadcast里面获取一个List<String>
		String[] bc = bckey.toArray(new String[bckey.size()]);// List转String[]
		int numbc = (int) (Math.random() * bckey.size());// key的随机数
		return bc[numbc];
	}
}
