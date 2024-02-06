package com.jdiffor.Game2D;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class HotBarGUI {
	
	// These values determine on-screen size
	private static final int SLOT_SIZE = 50;
	private static final int GAP_SIZE = 5;
	
	private Camera camera;
	private HotBar hotBar;
	
	public HotBarGUI(Camera camera) {
		this.camera = camera;
		this.hotBar = new HotBar();
	}
	
	public void select(int slotNumber) {
		this.hotBar.selectItem(slotNumber);
	}
	
	public HotBar getHotbar() {
		return this.hotBar;
	}
	
	public void draw(Graphics2D g) {
		int centerX = camera.getScreenWidth() / 2;
		int halfWidth = (int)((SLOT_SIZE * hotBar.getInventorySize()/2) + (GAP_SIZE * (hotBar.getInventorySize()+1)/2));
		int leftMost = centerX - halfWidth;
		int slotHeight = SLOT_SIZE + (2 * GAP_SIZE);
		
		// Draw hotbar background
		g.setColor(Color.DARK_GRAY);
		g.fillRect(leftMost, camera.getScreenHeight() - slotHeight, halfWidth*2, slotHeight);
		
		InventoryItem[] inventory = hotBar.getItems();
		int selectedItem = hotBar.getSelectedArrayItemSlot();
		
		// Draw each hotbar box
		for(int i = 0; i < inventory.length; i++) {
			int x = leftMost + ((i+1) * GAP_SIZE) + (i*SLOT_SIZE);
			int y = camera.getScreenHeight() - (SLOT_SIZE + GAP_SIZE);
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(x, y, SLOT_SIZE, SLOT_SIZE);
			
			if(inventory[i] != null) {
				inventory[i].drawIcon(g, x+1, y+1, SLOT_SIZE-2, SLOT_SIZE-2);
			}
			
			if(i == selectedItem) {
				g.setColor(Color.WHITE);
				g.setStroke(new BasicStroke(2));
				g.drawRect(x, y, SLOT_SIZE, SLOT_SIZE);
				g.setColor(Color.LIGHT_GRAY);
			}
		}
	}
}
