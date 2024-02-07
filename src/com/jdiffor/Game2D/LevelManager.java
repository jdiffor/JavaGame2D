package com.jdiffor.Game2D;

import java.util.ArrayList;

public class LevelManager {

	private World world;
	private WinManager winManager;
	private Map currentMap;
	private ArrayList<String> levelList;
	private int index = 0;
	
	public LevelManager(World world, WinManager wm) {
		this.world = world;
		this.winManager = wm;
		levelList = setLevels();
		world.loadNewMap(levelList.get(0));
	}
	
	public Map getMap() {
		return this.currentMap;
	}
	
	public void setNextLevel() {
		this.index++;
		if(index >= levelList.size()) {
			this.winManager.finish();
		} else {
			world.loadNewMap(levelList.get(index));
		}
	}
	
	private ArrayList<String> setLevels() {
		ArrayList<String> levels = new ArrayList<String>();
		
		// ADD LEVELS HERE		
		levels.add("field");
		levels.add("maze");
		
		return levels;
	}
	
}
