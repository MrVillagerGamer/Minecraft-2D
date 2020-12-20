package net.voxelmine.entity;

import net.voxelmine.items.Item;

public class Inventory {
	private ItemStack[] stacks;
	private int size;
	private int sel = 0;
	public Inventory(int size) {
		stacks = new ItemStack[size];
		this.size = size;
		for(int i = 0; i < size; i++) {
			stacks[i] = new ItemStack(0, 0);
		}
	}
	public ItemStack[] getStacks() {
		return stacks;
	}
	public void setStacks(ItemStack[] stacks) {
		this.stacks = stacks;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getSel() {
		return sel;
	}
	public void setSel(int sel) {
		this.sel = sel;
	}
	public boolean hasStack(ItemStack stack) {
		int total = 0;
		for(int i = 0; i < size; i++) {
			if(stack.canStackWith(stacks[i])) {
				total += stacks[i].getCount();
			}
		}
		if(total >= stack.getCount()) {
			return true;
		}
		return false;
	}
	public void splitSlots(int slot1, int slot2, Inventory other) {
		if(slot1 == slot2 && other == this) {
			return;
		}
		if(other.stacks[slot2].getItem() == 0) {
			int count = stacks[slot1].getCount() / 2;
			if(count == 0) {
				return;
			}
			stacks[slot1].setCount(stacks[slot1].getCount() - count);
			ItemStack stack = new ItemStack(stacks[slot1]);
			stack.setCount(count);
			other.stacks[slot2] = stack;
		}else if(other.stacks[slot2].canStackWith(stacks[slot1])) {
			other.stacks[slot2].setCount(other.stacks[slot2].getCount()+stacks[slot1].getCount());
			stacks[slot1] = new ItemStack(0, 0);
		}
	}
	
	public void splitSlots(int slot1, int slot2) {
		if(slot1 == slot2) {
			return;
		}
		if(stacks[slot2].getItem() == 0) {
			int count = stacks[slot1].getCount() / 2;
			if(count == 0) {
				return;
			}
			stacks[slot1].setCount(stacks[slot1].getCount() - count);
			ItemStack stack = new ItemStack(stacks[slot1]);
			stack.setCount(count);
			stacks[slot2] = stack;
		}else if(stacks[slot2].canStackWith(stacks[slot1])) {
			stacks[slot2].setCount(stacks[slot1].getCount());
			stacks[slot1] = new ItemStack(0, 0);
		}
	}
	public void swapSlots(int slot1, int slot2, Inventory other) {
		ItemStack tempItem = stacks[slot1];
		stacks[slot1] = other.stacks[slot2];
		other.stacks[slot2] = tempItem;
	}
	public void swapSlots(int slot1, int slot2) {
		ItemStack tempItem = stacks[slot1];
		stacks[slot1] = stacks[slot2];
		stacks[slot2] = tempItem;
	}
	public int getLength() {
		return stacks.length;
	}
	public void delItems(ItemStack stack) {
		if(hasStack(stack)) {
			for(int i = 0; i < size; i++) {
				if(stack.canStackWith(stacks[i]) && stacks[i].getCount() == stack.getCount()) {
					stacks[i] = new ItemStack(0, 0);
					break;
				}else if(stack.canStackWith(stacks[i]) && stacks[i].getCount() > stack.getCount()) {
					stacks[i].setCount(stacks[i].getCount()-stack.getCount());
					break;
				}else if(stack.canStackWith(stacks[i]) && stacks[i].getCount() < stack.getCount()) {
					stack.setCount(stack.getCount() - stacks[i].getCount());
					stacks[i] = new ItemStack(0, 0);
				}
			}
		}
	}
	public void addItems(ItemStack stack) {
		boolean newStack = false;
		boolean added = false;
		for(int i = 0; i < size; i++) {
			if(stack.canStackWith(stacks[i]) && stack.getCount() + stacks[i].getCount() <= 64) {
				stacks[i].setCount(stacks[i].getCount()+stack.getCount());
				added = true;
				break;
			}
		}
		if(!added) {
			for(int i = 0; i < size; i++) {
				if(stack.canStackWith(stacks[i]) && stack.getCount() + stacks[i].getCount() <= 64) {
					stack.setCount(64 - stacks[i].getCount());
					stacks[i].setCount(64);
					if(stack.getCount() > 0) {
						newStack = true;
					}
					added = true;
					break;
				}
			}
		}
		if(newStack || !added) {
			for(int i = 0; i < size; i++) {
				if(stacks[i].getItem() == 0) {
					ItemStack stack2 = new ItemStack(stack);
					stacks[i] = stack2;
					break;
				}
			}
		}
	}
	public int getSelectedSlot() {
		return sel;
	}
	public void setSelectedSlot(int sel) {
		this.sel = sel;
	}
	public ItemStack getStack(int sel) {
		return stacks[sel];
	}
	public void setStack(int sel, ItemStack stack) {
		stacks[sel] = stack;
	}
}
