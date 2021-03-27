package ru.ragnok123.minigameAPI.arena;

import java.util.ArrayList;

import cn.nukkit.Player;
import cn.nukkit.Server;
import ru.ragnok123.minigameAPI.Minigame;
import ru.ragnok123.minigameAPI.player.GamePlayer;

public abstract class ITeamArena<T extends Team> extends IArena {

	public ArrayList<T> aliveTeams;
	
	public ITeamArena(ArenaManager manager, String modeId, Integer arenaId) {
		super(manager, modeId, arenaId);
	}
	
	public ArrayList<T> getTeams(){
		return aliveTeams;
	}
	
	public void joinRandomTeam(Player player) {
		T t = getSmallestTeam();
		selectTeam(player, t);
	}
	
	public void joinTeams() {
		for(Player p : players.values()) {
			if(getTeam(p) == null) {
				joinRandomTeam(p);
			}
		}
	}
	
	public void selectTeam(Player player, T komanda) {
		GamePlayer p = Minigame.getPlayer(player.getName());
		if(getTeams().contains(komanda)) {
			if(komanda.players.size() >= komanda.maxMembers) {
				return;
			}
			if(komanda.players.containsKey(p.getPlayer().getName())) {
				return;
			}
			if(getTeam(player) != null) {
				getTeam(player).players.remove(p.getPlayer().getName());
			}
			komanda.players.put(player.getName(),player);
			p.setupTeamDisplayName(getTeam(player).getColor());
		}
	}
	
	
	
	public T getTeam(Player p) {
		T t = null;
		for(T team : getTeams()) {
			if(team.players.containsKey(p.getName())) {
				t = team;
			}
		}
		return t;
	}
	
	public T getSmallestTeam() {
		T t = null;
		int i = -1;
		for(T team : getTeams()) {
			if(i == -1) {
				t = team;
				i = team.players.size();
			} else if(team.players.size() < i) {
				t = team;
				i = team.players.size();
			}
		}
		return t;
	}
	
	@Override
	public void leaveArena(Player p) {
		GamePlayer pl = Minigame.getPlayer(p.getName());
		if(getTeam(p) != null) {
			getTeam(p).removePlayer(p);
		}
		players.remove(p.getName());
		pl.getGameData().save();
		pl.setGameData(null);
		pl.setArena(null);
	}
	
}