package net.voxelmine.entity;

public class InventoryCrafting extends Inventory {
	public InventoryCrafting(int size) {
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
