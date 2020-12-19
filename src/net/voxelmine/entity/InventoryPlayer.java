package net.voxelmine.entity;

import net.voxelmine.items.Item;

public class InventoryPlayer extends Inventory{
	public InventoryPlayer(int size) {
		super(size);
		addItems(Item.WORKBENCH.getId(), 1);
	}
}
