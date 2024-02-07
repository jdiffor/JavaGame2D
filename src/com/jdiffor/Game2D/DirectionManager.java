package com.jdiffor.Game2D;

public class DirectionManager {
	
	private Player player;
	private KeyboardInputManager keyboardManager;
	
	public DirectionManager(Player player, KeyboardInputManager keyboardManager) {
		this.player = player;
		this.keyboardManager = keyboardManager;
	}
	
	/*
	 * Moves player based on keyboardManager inputs
	 * Returns string of new map to teleport to if player hits a teleporter
	 */
	public String[] update(Map map) {
		boolean moving = false;
		String[] teleports = null;
		
		if(keyboardManager.getKeyIsPressed('w')) {
			teleports = player.move(Direction.UP, map);
			moving = true;
		}
		
		if(keyboardManager.getKeyIsPressed('a')) {
			teleports = player.move(Direction.LEFT, map);
			moving = true;
		}
		
		if(keyboardManager.getKeyIsPressed('s')) {
			teleports = player.move(Direction.DOWN, map);
			moving = true;
		}
		
		if(keyboardManager.getKeyIsPressed('d')) {
			teleports = player.move(Direction.RIGHT, map);
			moving = true;
		}
		
		if(!moving) {
			player.exhaustExtraMovement();
		}
		
		return teleports;
	}
	
}
