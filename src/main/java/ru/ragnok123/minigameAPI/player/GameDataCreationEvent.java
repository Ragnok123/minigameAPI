package ru.ragnok123.minigameAPI.player;

import cn.nukkit.event.Event;
import cn.nukkit.event.HandlerList;

public class GameDataCreationEvent extends Event {
	
	private static final HandlerList handlers = new HandlerList();
	
	private Class<? extends GamePlayer> baseClass;
	private Class<? extends GamePlayer> targetClass;
	
	public static HandlerList getHandlers() {
		return handlers;
	}
	
	public GameDataCreationEvent(Class<? extends GamePlayer> baseClass, Class<? extends GamePlayer> targetClass) {
		this.baseClass = baseClass;
		this.targetClass = targetClass;
	}
	
	public Class<? extends GamePlayer> getBaseClass(){
		return this.baseClass;
	}
	
	public void setTargetClass(Class<? extends GamePlayer> clazz) {
		this.targetClass = clazz;
	}
	
	public Class<? extends GamePlayer> getTargetClass(){
		return this.targetClass;
	}
	
}