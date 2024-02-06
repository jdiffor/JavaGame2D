package com.jdiffor.Game2D;

public class HotBar {
	
	private static final int DEFAULT_SLOT_COUNT = 4;
	
	private InventoryItem[] items;
	private int selectedSlot;
	

	public HotBar() {
		this(DEFAULT_SLOT_COUNT);		
	}
	
	public HotBar(int numSlots) {
		this.items = new InventoryItem[numSlots];
		this.selectedSlot = 0;
	}
	
	public int getInventorySize() {
		return DEFAULT_SLOT_COUNT;
	}
	
	public InventoryItem selectItem(int slotNumber) {
		if(isInSlotRange(slotNumber)) {
			this.selectedSlot = convertSlotNumber(slotNumber);
		}
		return this.items[this.selectedSlot];
	}
	
	public InventoryItem addItem(InventoryItem item, int slotNumber) {
		if(isInSlotRange(slotNumber)) {
			InventoryItem deselected = this.items[convertSlotNumber(slotNumber)];
			this.items[convertSlotNumber(slotNumber)] = item;
			return deselected;
		}
		return null;
	}
	
	public void removeSelectedItem() {
		this.items[this.selectedSlot] = null;
	}
	
	public InventoryItem scrollRight() {
		this.selectedSlot++;
		if(this.selectedSlot >= this.items.length) {
			this.selectedSlot = 0;
		}
		return this.items[this.selectedSlot];
	}
	
	public InventoryItem scrollLeft() {
		this.selectedSlot--;
		if(this.selectedSlot < 0) {
			this.selectedSlot = this.items.length - 1;
		}
		return this.items[this.selectedSlot];
	}
	
	public InventoryItem[] getItems() {
		return this.items;
	}
	
	public int getSelectedArrayItemSlot() {
		return this.selectedSlot;
	}
	
	public int getSelectedInventoryItemSlot() {
		return this.selectedSlot + 1;
	}
	
	public InventoryItem getSelectedItem() {
		return this.items[this.selectedSlot];
	}
	
	private boolean isInSlotRange(int slotNumber) {
		return slotNumber >= 1 && slotNumber <= this.items.length;
	}
	
	private int convertSlotNumber(int slotNumber) {
		return slotNumber - 1;
	}
	
}
