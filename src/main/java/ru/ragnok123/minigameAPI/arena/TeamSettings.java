package ru.ragnok123.minigameAPI.arena;

import cn.nukkit.utils.DyeColor;
import cn.nukkit.utils.TextFormat;

public enum TeamSettings {

	RED("RED", TextFormat.RED, DyeColor.RED),
	BLUE("BLUE", TextFormat.BLUE, DyeColor.BLUE),
	GREEN("GREEN", TextFormat.DARK_GREEN, DyeColor.GREEN),
	YELLOW("YELLOW", TextFormat.YELLOW, DyeColor.YELLOW),
	BLACK("BLACK", TextFormat.BLACK, DyeColor.BLACK),
	WHITE("WHITE", TextFormat.WHITE, DyeColor.WHITE),
	CYAN("CYAN", TextFormat.DARK_AQUA, DyeColor.CYAN),
	GRAY("GRAY", TextFormat.GRAY, DyeColor.GRAY),
	PINK("PINK", TextFormat.LIGHT_PURPLE, DyeColor.PINK),
	PURPLE("PURPLE", TextFormat.DARK_PURPLE, DyeColor.PURPLE),
	GOLD("GOLD", TextFormat.GOLD, DyeColor.ORANGE),
	LIME("LIME", TextFormat.GREEN, DyeColor.LIME),
	AQUA("AQUA", TextFormat.AQUA, DyeColor.CYAN);
	
	
	TeamSettings(String name, TextFormat textColor, DyeColor dyeColor){
		this.name = name;
		this.text = textColor;
		this.dyeColor = dyeColor;
	}
	
	private String name;
	private TextFormat text;
	private DyeColor dyeColor;
	
	public String getName() {
		return this.name;
	}
	
	public TextFormat getColor() {
		return this.text;
	}
	
	public DyeColor getMeta() {
		return this.dyeColor;
	}
	
	public static TeamSettings parseColor(String color) {
		for(TeamSettings settings : TeamSettings.values()) {
			if(settings.getName().equalsIgnoreCase(color)) {
				return settings;
			}
		}
		return null;
	}
	
}