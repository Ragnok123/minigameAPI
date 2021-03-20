package ru.ragnok123.minigameAPI.kit;

import cn.nukkit.Player;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.player.PlayerInteractEvent;

public interface Kit extends Cloneable{
	String getName();
	String getDescription(Player p);
	void giveKit(Player p);
	void useItem(PlayerInteractEvent e);
	void useDanage(EntityDamageByEntityEvent e);
	void kill(Player damager, Player target);
	void respawn(Player p);
	Kit clone();
}