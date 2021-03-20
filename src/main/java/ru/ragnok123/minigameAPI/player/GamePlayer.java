package ru.ragnok123.minigameAPI.player;

import cn.nukkit.Player;
import cn.nukkit.utils.TextFormat;
import lombok.Getter;
import lombok.Setter;
import ru.ragnok123.gtscoreboard.scoreboard.Scoreboard;
import ru.ragnok123.minigameAPI.arena.IArena;

public abstract class GamePlayer {
	
	public Player player;
	
	@Getter
	@Setter
	public IGameData gameData;
	
	@Getter
	@Setter
	public IArena arena = null;
	
	public GamePlayer(Player player) {
		this.player = player;
		loadData();
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	
	public abstract String getGameNick();
	public abstract void setupLobbyDisplayName();
	public abstract void setupTeamDisplayName(TextFormat color);
	
	public abstract void loadData();
	public abstract void createData();
	public abstract void save();
	
}