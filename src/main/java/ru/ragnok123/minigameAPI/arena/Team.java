package ru.ragnok123.minigameAPI.arena;

import java.util.ArrayList;
import java.util.HashMap;

import cn.nukkit.Player;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.TextFormat;
import lombok.Getter;
import lombok.Setter;

public abstract class Team {
	
	@Getter
	@Setter
	public TeamSettings settings;
	
	public Vector3 spawnPos;
	
	public int maxMembers;
	public HashMap<String, Player> players = new HashMap<String, Player>();
	
	
	public Team(TeamSettings settings, Vector3 pos, int maxPlayers) {
		this.settings = settings;
		this.spawnPos = pos;
		this.maxMembers = maxPlayers;
	}
	
	
	public Vector3 getTeamSpawn() {
		return spawnPos;
	}
	
	
	public void setMaxMembers(int members) {
		maxMembers = members;
	}
	
	public boolean addPlayer(Player player) {
		if(!players.containsKey(player.getName())) {
			if(players.size() < maxMembers) {
				players.put(player.getName(), player);
				return true;
			}
			return false;
		}
		return false;
	}
	
	public void removePlayer(Player player) {
		if(players.containsKey(player.getName())) {
			players.remove(player.getName());
		}
	}
	
	
	
	public abstract String getName();
	
	public TextFormat getColor() {
		return getSettings().getColor();
	}
	
	
}