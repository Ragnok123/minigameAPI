package ru.ragnok123.minigameAPI.arena;

import java.util.*;

import cn.nukkit.Player;
import cn.nukkit.Server;
import ru.ragnok123.minigameAPI.Minigame;
import ru.ragnok123.minigameAPI.player.GamePlayer;

public abstract class ArenaManager {
	
	private Minigame plugin;
	
	public List<IArena> arenas = new ArrayList<IArena>();
	public HashMap<String, IArenaData> maps = new HashMap<String, IArenaData>();
	
	public ArenaManager(Minigame plugin) {
		this.plugin = plugin;
		loadMaps();
		registerListener();
	}
	
	public abstract void registerListener();
	
	public abstract void loadMaps();
	
	public List<IArena> getArenas(){
		return this.arenas;
	}
	
	public IArena getArenaById(int id) {
		for(IArena arena : arenas) {
			if(arena.getId() == id) {
				return arena;
			}
		}
		return null;
	}
	
	public IArena getArenaByPlayer(Player player) {
		for(IArena arena : arenas) {
			if(arena.players.containsKey(player.getName())) {
				return arena;
			}
		}
		return null;
	}
	
	public abstract IArena generateNewArena(ArenaParameters params);
	
	public IArena getAvailableArena(ArenaParameters params) {
		for(IArena aren : getArenas()) {
			if(aren.getParams().paramsToString().equals(params.paramsToString())) {
				if(aren.isJoinable()) {
					return aren;
				} else {
					return generateNewArena(params);
				}
			}
		}
		return generateNewArena(params);
	}
	
	
	public ArrayList<String> getRandomMaps() {
		Random rand = new Random();
		HashMap<String, IArenaData> mapy = new HashMap<String, IArenaData>(maps);
		ArrayList<String> mapicky = new ArrayList<String>();
		for(String map : mapy.keySet()) {
			mapicky.add(map);
		}
		Collections.shuffle(mapicky);
		
		return mapicky;
		
	}
	
}