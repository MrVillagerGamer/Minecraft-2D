package net.voxelmine.entity;

import net.voxelmine.items.Item;

public class Inventory {
	private int[] items;
	private int[] counts;
	private int size;
	private int sel = 0;
	public Inventory(int size) {
		items = new int[size];
		counts = new int[size];
		this.size = size;
	}
	public int[] getItems() {
		return items;
	}
	public int[] getCounts() {
		return counts;
	}
	public void setItems(int[] items) {
		this.items = items;
	}
	public void setCounts(int[] counts) {
		this.counts = counts;
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
	public boolean hasItems(int item, int count) {
		int total = 0;
		for(int i = 0; i < size; i++) {
			if(items[i] == item) {
				total += counts[i];
			}
		}
		if(total >= count) {
			return true;
		}
		return false;
	}
	public void splitSlots(int slot1, int slot2, Inventory other) {
		if(slot1 == slot2 && other == this) {
			return;
		}
		if(other.getId(slot2) == 0) {
			int count = counts[slot1] / 2;
			if(count == 0) {
				return;
			}
			counts[slot1] -= count;
			other.setCount(slot2, count);
			other.setId(slot2, items[slot1]);
		}else if(other.getId(slot2) == items[slot1]) {
			other.setCount(slot2, other.getCount(slot2)+counts[slot1]);
			counts[slot1] = 0;
			items[slot1] = 0;
		}
	}
	
	public void splitSlots(int slot1, int slot2) {
		if(slot1 == slot2) {
			return;
		}
		if(items[slot2] == 0) {
			int count = counts[slot1] / 2;
			if(count == 0) {
				return;
			}
			counts[slot1] -= count;
			counts[slot2] = count;
			items[slot2] = items[slot1];
		}else if(items[slot2] == items[slot1]) {
			counts[slot2] += counts[slot1];
			counts[slot1] = 0;
			items[slot1] = 0;
		}
	}
	public void swapSlots(int slot1, int slot2, Inventory other) {
		int tempItem = items[slot1];
		items[slot1] = other.getId(slot2);
		other.setId(slot2, tempItem);
		
		int tempCount = counts[slot1];
		counts[slot1] = other.getCount(slot2);
		other.setCount(slot2, tempCount);
	}
	public void swapSlots(int slot1, int slot2) {
		int tempItem = items[slot1];
		items[slot1] = items[slot2];
		items[slot2] = tempItem;
		
		int tempCount = counts[slot1];
		counts[slot1] = counts[slot2];
		counts[slot2] = tempCount;
		
	}
	public int getLength() {
		return items.length;
	}
	public int getId(int slot) {
		return items[slot];
	}
	public int getCount(int slot) {
		return counts[slot];
	}
	public void setCount(int slot, int count) {
		counts[slot] = count;
		if(count == 0) {
			items[slot] = 0;
		}
	}
	public void setId(int slot, int item) {
		items[slot] = item;
	}
	public void delItems(int item, int count) {
		if(hasItems(item, count)) {
			for(int i = 0; i < size; i++) {
				if(items[i] == item && counts[i] == count) {
					counts[i] = 0;
					items[i] = 0;
					break;
				}else if(items[i] == item && counts[i] > count) {
					counts[i] -= count;
					break;
				}else if(items[i] == item && counts[i] < count) {
					count -= counts[i];
					counts[i] = 0;
					items[i] = 0;
				}
			}
		}
	}
	public void addItems(int item, int count) {
		boolean newStack = false;
		boolean added = false;
		for(int i = 0; i < size; i++) {
			if(item == items[i] && count + counts[i] <= 64) {
				counts[i] += count;
				added = true;
				break;
			}
		}
		if(!added) {
			for(int i = 0; i < size; i++) {
				if(item == items[i] && count + counts[i] > 64) {
					count -= 64 - counts[i];
					counts[i] = 64;
					if(count > 0) {
						newStack = true;
					}
					added = true;
					break;
				}
			}
		}
		if(newStack || !added) {
			for(int i = 0; i < size; i++) {
				if(items[i] == 0) {
					items[i] = item;
					counts[i] = count;
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
}
