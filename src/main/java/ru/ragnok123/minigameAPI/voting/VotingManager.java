package ru.ragnok123.minigameAPI.voting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.nukkit.Player;
import ru.ragnok123.minigameAPI.arena.IArena;

public class VotingManager {
	
	private IArena arena;
	
	public final List<VotingMap> maps = new ArrayList<VotingMap>();
	private final HashMap<String, VotingMap> votes = new HashMap<String, VotingMap>();
	private boolean running = false;

	public VotingManager(IArena arena) {
		this.arena = arena;
		run();
	}
	
	public void addMaps(ArrayList<String> maps) {
		for(String map : maps) {
			VotingMap mapa = new VotingMap(map);
			this.maps.add(mapa);
		}
	}
	
	public VotingMap getMapByName(String map) {
		for(VotingMap mapa : maps) {
			if(mapa.getName().equals(map));
			return mapa;
		}
		return null;
	}
	
	public void vote(Player player, VotingMap vote) {
		if(votes.containsKey(player.getName())) {
			votes.remove(player.getName());
			votes.put(player.getName(),vote);
		} else {
			votes.put(player.getName(),vote);
		}
	}
	
	public VotingMap getWinner() {
		List<VotingMap> allMaps = new ArrayList<VotingMap>();
		allMaps.addAll(maps);
		Collections.shuffle(allMaps);
		
		VotingMap best = maps.get(0);
		int bestVotes = 0;
		for(VotingMap map : allMaps) {
			int votes = getVotes(map);
			if(votes > bestVotes) {
				best = map;
				bestVotes = votes;
			}
		}
		return best;
	}
	
	public int getVotes(VotingMap map) {
		int votes = 0;
		for(VotingMap mapa : this.votes.values()) {
			if(mapa == map) {
				votes++;
			}
		}
		return votes;
	}

	
	public Map<String,VotingMap> getVotes(){ return votes;}

	public void end() {
		running = false;
		votes.clear();
	}
	
	public void run() {
		running = true;
	}

	public boolean isRunning() {
		return running;
	}
	
}