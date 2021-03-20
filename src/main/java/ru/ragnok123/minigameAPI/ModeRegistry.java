package ru.ragnok123.minigameAPI;

import java.util.*;

public class ModeRegistry {
	
	private static HashMap<String, String> modes = new HashMap<String, String>();
	
	public static void registerMode(String identifier, String mode) {
		modes.put(identifier, mode);
	}
	
	public static String getMode(String identifier) {
		if(modes.containsKey(identifier)) {
			return modes.get(identifier);
		}
		return "";
	}
	
}