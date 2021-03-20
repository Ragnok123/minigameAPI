package ru.ragnok123.minigameAPI;

import java.lang.reflect.Constructor;
import java.util.HashMap;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.level.ThunderChangeEvent;
import cn.nukkit.event.level.WeatherChangeEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerLoginEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.scheduler.NukkitRunnable;
import ru.ragnok123.minigameAPI.arena.ArenaManager;
import ru.ragnok123.minigameAPI.arena.IArena;
import ru.ragnok123.minigameAPI.player.GameDataCreationEvent;
import ru.ragnok123.minigameAPI.player.GamePlayer;
import lombok.Getter;

public abstract class Minigame extends PluginBase implements Listener{
	
	private static Minigame instance;
	public static HashMap<String, GamePlayer> players = new HashMap<String, GamePlayer>();
	
	@Getter
	public ArenaManager arenaManager;
	
	public void onLoad() {
		instance = this;
	}
	
	public void onEnable() {
		loadMinigame();
		loadModes();
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	public abstract String getPrefix();
	public abstract String getPrefix(String c);
	
	public static GamePlayer getPlayer(String nick) {
		return players.get(nick.toLowerCase());
	}
	
	public static void addPlayer(String nick, GamePlayer p) {
		players.put(nick.toLowerCase(), p);
	}
	
	public void removePlayer(String nick) {
		players.remove(nick.toLowerCase());
	}
	
	@EventHandler
	public void preLogin(PlayerLoginEvent e) {
		Player p = e.getPlayer();
		GameDataCreationEvent event = new GameDataCreationEvent(GamePlayer.class, GamePlayer.class);
		getServer().getPluginManager().callEvent(event);
		Class<? extends GamePlayer> targetClass = event.getTargetClass();
		try {
			Constructor<? extends GamePlayer> constructor = targetClass.getConstructor(Player.class);
			GamePlayer gamePlayer = constructor.newInstance(p);
			new NukkitRunnable() {
				@Override
				public void run() {
					addPlayer(p.getName(), gamePlayer);
				}
			}.runTaskLater(this,10);
		} catch(Exception ex) {
		}
	}
	
	@EventHandler
	public void join(PlayerJoinEvent e) {
		getServer().getScheduler().scheduleDelayedTask(new Runnable() {
			public void run() {
				handleSpawn(e.getPlayer(), true);
			}
		}, 10);
	}
	
	@EventHandler
	public void quit(PlayerQuitEvent e) {
		if(getPlayer(e.getPlayer().getName()) != null) {
			GamePlayer data = getPlayer(e.getPlayer().getName());
			try {
				IArena a = data.getArena();
				if(a != null) {
					a.leave(e.getPlayer(),"quit");
				}
			}catch(NullPointerException ex) {}
			data.save();
			removePlayer(e.getPlayer().getName());
		}
	}
	
	@EventHandler
	public void antiRain(WeatherChangeEvent e) {
		if(e.toWeatherState()) {
			e.setCancelled();
		}
	}
	
	@EventHandler
	public void antiThunder(ThunderChangeEvent e) {
		if(e.toThunderState()) {
			e.setCancelled();
		}
	}
	
	public static Minigame get() {
		return instance;
	}
	
	public void handleSpawn(Player p) {
		handleSpawn(p, false);
	}
	public abstract void handleSpawn(Player p, boolean join);
	
	public abstract void loadMinigame();
	public abstract void loadModes();
	
	public abstract String getMinigameCommand();
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		switch(cmd.getName()) {
		case "hub":
			Player pl = (Player)sender;
			GamePlayer da= getPlayer(pl.getName());
			try {
				IArena ar = da.getArena();
				ar.leave(pl,"lobby");
			} catch(NullPointerException e) {}
			break;
		}
		return false;
	}
	
}