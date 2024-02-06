package com.jdiffor.Game2D;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class ScrollWheelInputManager implements MouseWheelListener {

	private HotBar hotBar;
	
	public ScrollWheelInputManager(HotBar hotBar) {
		this.hotBar = hotBar;
	}

	@Override
	/*
	 * Currently only used for switching inventory in hotbar.
	 * Will become more complicated if used for anything else
	 */
	public void mouseWheelMoved(MouseWheelEvent e) {
		int rotations = e.getWheelRotation();
		if(rotations >= 1) {
			for(int i = 0; i < rotations; i++) {
				hotBar.scrollRight();
			}
		} else if(rotations <= -1) {
			rotations *= -1;
			for(int i = 0; i < rotations; i++) {
				hotBar.scrollLeft();
			}
		} 
	}
	
	
}
