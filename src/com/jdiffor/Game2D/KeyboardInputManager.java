package com.jdiffor.Game2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInputManager implements KeyListener {

	private boolean[] pressedLetters;
	private boolean[] pressedNumbers;
	
	private HotBarGUI hotBarGUI;
	
	public KeyboardInputManager(HotBarGUI hotBarGUI) {
		pressedLetters = new boolean[26];
		pressedNumbers = new boolean[10];
		this.hotBarGUI = hotBarGUI;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		// NOT USING
	}

	@Override
	public void keyPressed(KeyEvent e) {
		setKey(e, true);
		
		// Toggle Debug
		if(e.getKeyCode() == KeyEvent.VK_F5) {
			Main.DEBUG = !Main.DEBUG;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		setKey(e, false);
	}
	
	public void setKey(KeyEvent e, boolean isBeingPressed) {
		int keyLetter = e.getKeyChar() - 'a';
		keyLetter = keyLetter < 0 ? keyLetter + ('a' - 'A') : keyLetter;
		
		// Set entries in pressedLetters for use by direction manager
		if(Utils.isInRange(keyLetter, 0, this.pressedLetters.length - 1)) {
			this.pressedLetters[keyLetter] = isBeingPressed;
		}
		
		// Directly select items in hotbar
		int keyNum = e.getKeyChar() - '0';
		if(Utils.isInRange(keyNum, 0, 9)) {
			this.pressedNumbers[keyNum] = isBeingPressed;
			this.hotBarGUI.select(keyNum);
		}
	}
	
	public boolean getKeyIsPressed(char key) {
		return this.pressedLetters[key - 'a'];
	}
	
}
