package net.voxelmine.entity;

public class InventoryChest extends Inventory {
	public InventoryChest(int size) {
		super(size);
		setSelectedSlot(-1);
	}
	@Override
	public void setSelectedSlot(int sel) {
		if(sel == -1) {
			super.setSelectedSlot(sel);
		}
	}
}
