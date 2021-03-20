package ru.ragnok123.minigameAPI.arena;

import java.util.ArrayList;
import java.util.HashMap;

import cn.nukkit.Player;
import cn.nukkit.Server;
import lombok.Getter;
import ru.ragnok123.minigameAPI.Minigame;
import ru.ragnok123.minigameAPI.player.GamePlayer;

public abstract class IArena {
	
	public static int WAITING_FOR_PLAYERS = 0;
	public static int COUNTDOWN = 1;
	public static int GAME = 2;
	public static int END = 3;
	
	public int PHASE = 0;
	public int time;
	
	private String modeId;
	protected Integer id;
	@Getter
	private ArenaManager gameManager;
	
	public HashMap<String, Player> players = new HashMap<String, Player>();
	
	public IArena(ArenaManager manager, String modeId, Integer arenaId) {
		this.gameManager = manager;
		this.modeId = modeId;
		this.id = arenaId;
	}
	
	public boolean isJoinable() {
		return PHASE < GAME && players.size() < getMaxPlayers();
	}
	
	public int getId() {
		return this.id;
	}
	public String getModeId() {
		return this.modeId;
	}
	
	public abstract void selectLevel();
	public abstract void loadArenaLevel();
	
	public abstract int getMinPlayers();
	public abstract int getMaxPlayers();
	
	public boolean addPlayer(Player p) {
		GamePlayer pl = Minigame.getPlayer(p.getName());
		pl.setArena(this);
		initGameData(pl);
		
		players.put(p.getName(), p);
		Server.getInstance().getScheduler().scheduleDelayedTask(new Runnable() {
			@Override
			public void run() {
				processSpawn(p);
			}
		}, 20);
		
		return true;
	}
	
	public abstract void initGameData(GamePlayer p);
	public abstract void processSpawn(Player p);
	
	
	public void leaveArena(Player p) {
		GamePlayer pl = Minigame.getPlayer(p.getName());
		players.remove(p.getName());
		pl.getGameData().save();
		pl.setGameData(null);
		pl.setArena(null);
	}
	
	public boolean game(Player p) {
		return players.containsKey(p.getName());
	}
	public void leave(Player player, String cause) {
		if(cause.equals("lobby")) {
			Minigame.get().handleSpawn(player);
			leaveArena(player);
		}
	}
}