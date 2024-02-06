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
	public String update(Map map) {
		boolean moving = false;
		String teleportTo = null;
		
		if(keyboardManager.getKeyIsPressed('w')) {
			teleportTo = player.move(Direction.UP, map);
			moving = true;
		}
		
		if(keyboardManager.getKeyIsPressed('a')) {
			teleportTo = player.move(Direction.LEFT, map);
			moving = true;
		}
		
		if(keyboardManager.getKeyIsPressed('s')) {
			teleportTo = player.move(Direction.DOWN, map);
			moving = true;
		}
		
		if(keyboardManager.getKeyIsPressed('d')) {
			teleportTo = player.move(Direction.RIGHT, map);
			moving = true;
		}
		
		if(!moving) {
			player.exhaustExtraMovement();
		}
		
		return teleportTo;
	}
	
}
