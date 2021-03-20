package ru.ragnok123.minigameAPI.kit;

import java.util.HashMap;

import cn.nukkit.Player;
import ru.ragnok123.menuAPI.form.impl.SimpleFormMenu;
import ru.ragnok123.menuAPI.form.impl.elements.Button;
import ru.ragnok123.menuAPI.form.impl.response.ButtonResponse;
import ru.ragnok123.minigameAPI.player.GamePlayer;

public abstract class BaseKitManager {
	
	public HashMap<String,Kit> kits = new HashMap<String,Kit>();
	public HashMap<String,KitData> kitsData = new HashMap<String,KitData>();
	public Player p = null;
	
	public HashMap<String, Boolean> plKits = new HashMap<String,Boolean>();
	
	public BaseKitManager(Player p) {
		this.p = p;
		registerKits();
		registerKitsData();
	}
	
	public abstract void loadData();
	
	public abstract void save();
	
	public abstract String getPrefix(String c);
	public abstract String getFormTitle();
	public abstract String getFormButton();
	
	public abstract void registerKits();
	public abstract void registerKitsData();
	public abstract String[] getKitNames();
	
	public void registerNewKitData(String id, KitData data) {
		this.kitsData.put(id,data);
	}
	
	public void registerNewKit(String id, Kit kit) {
		this.kits.put(id,kit);
	}
	
	public Kit getKit(String id) {
		return kits.get(id);
	}
	
	public boolean hasKit(String id) {
		return plKits.get(id) == true;
	}
	
	public String getKitColor(String id) {
		return hasKit(id) ? "§l§a" : "§l§c";
	}
	
	public void buyKit(String id) {
		plKits.remove(id);
		plKits.put(id,true);
	}
	
	public void showBuyMainMenu(GamePlayer p) {
		SimpleFormMenu menu = new SimpleFormMenu(getFormTitle(),"");
		int i = 0;
		for(String kit : kits.keySet()) {
			menu.addButton(getKitColor(kit) + kits.get(kit).getName(), new ButtonResponse() {
				public void onResponse(Player player, Button button) {
					if(hasKit(kit)) {
						p.getPlayer().sendMessage(getPrefix("c")+"You already have this kit");
					} else {
						showBuyMenu(p,kit);
					}
				}
			});
			i++;
			
		}
		menu.show(p.getPlayer());
	}
	
	public void showBuyMenu(GamePlayer player, String id) {
		Kit kit = kits.get(id);
		SimpleFormMenu menu = new SimpleFormMenu(kit.getName(),kit.getDescription(player.getPlayer()));
		menu.addButton(getFormButton(), new ButtonResponse() {
			@Override
			public void onResponse(Player pla, Button button) {
				buyKit(id);
				kitsData.get(id).buy(player.getPlayer(),player);	
			}
		});
		menu.show(player.getPlayer());
	}
	
	public void showAllKits(GamePlayer p) {
		SimpleFormMenu menu = new SimpleFormMenu(getFormTitle(),"");
		for(String kit : kits.keySet()) {
			menu.addButton(getKitColor(kit) + kits.get(kit).getName(), new ButtonResponse() {
				public void onResponse(Player player, Button button) {
					if(hasKit(kit)) {
						p.getGameData().equipKit(kit,true);
					} else {
						p.getGameData().equipKit(kit,false);
					}
				}
			});
		}
		menu.show(p.getPlayer());
	}
	
}