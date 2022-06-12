package ru.ragnok123.minigameAPI.arena;

import cn.nukkit.scheduler.Task;

public abstract class ArenaTimer extends Task{
	
	private IArena arena;
	
	public ArenaTimer(IArena arena) {
		this.arena = arena;
	}
	
	public IArena getArena() {
		return this.arena;
	}
	
	public void onRun(int currentTick) {
		if(this.arena.PHASE == IArena.WAITING_FOR_PLAYERS) {
			if(this.arena.players.size() >= this.arena.getMinPlayers()) {
				this.arena.PHASE = IArena.COUNTDOWN;
				this.arena.time = getWaitTime();
			}
		} else if(this.arena.PHASE == IArena.COUNTDOWN) {
			this.arena.time--;
			tickCountdown(this.arena.time);
			if(this.arena.time == 0) {
				this.arena.PHASE = IArena.GAME;
				this.arena.time = getGameTime();
			}
		} else if(this.arena.PHASE == IArena.GAME) {
			this.arena.time--;
			tickGame(this.arena.time);
			if(this.arena.time == 0) {
				onEnd();
				this.arena.PHASE = IArena.END;
				this.arena.time = 15;
			}
		} else if(this.arena.PHASE == IArena.END && this.arena.time == 0) {
			
		}
	}
	
	public abstract void tickCountdown(int second);
	public abstract void tickGame(int second);
	public abstract int getWaitTime();
	public abstract int getGameTime(); //it is in seconds;
	public abstract void onEnd();
	
}