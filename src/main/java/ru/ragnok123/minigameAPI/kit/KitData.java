package ru.ragnok123.minigameAPI.kit;

import cn.nukkit.Player;
import ru.ragnok123.minigameAPI.player.GamePlayer;

public interface KitData {
	String getName();
	void buy(Player p, GamePlayer d);
}
