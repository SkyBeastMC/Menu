package fr.skybeastmc.menu;

import java.util.ArrayList;
import java.util.List;

public class MenuManager {
	private static List<Menu> menus = new ArrayList<Menu>();
	
	protected static void addMenu(Menu menu) {
		if(!menus.contains(menu)) menus.add(menu);
	}
	
	protected static void removeMenu(Menu menu) {
		if(menus.contains(menu)) menus.remove(menu);
	}
	
	public static List<Menu> getMenus() {
		return menus;
	}
}
