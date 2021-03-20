package ru.ragnok123.minigameAPI.player;

import ru.ragnok123.minigameAPI.kit.Kit;

public interface IGameData {
	
	GamePlayer getPlayer();
	
	Kit getKit();
	boolean hasKit();
	void equipKit(String kit, boolean s);
	
	boolean isDead();
	
	void save();
}